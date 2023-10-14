package com.example.uploadingfiles.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService{

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties){
        // this.rootLocation = Path.of(properties.getLocation());
        // Java11以降
        this.rootLocation = Path.of(properties.getLocation());
    }


    @Override
    public void init() {
        try{
            Files.createDirectories(rootLocation);
        } catch(IOException e){
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file.");
            }
            System.out.println("file.getOriginalFilename() : " + file.getOriginalFilename());
            System.out.println("Path.of(file.getOriginalFilename()) : " + Path.of(file.getOriginalFilename()));
            System.out.println("rootLocation.resolve(Path.of(file.getOriginalFilename())) : " + rootLocation.resolve(file.getOriginalFilename()));
            System.out.println("rootLocation.resolve(Path.of(file.getOriginalFilename()))\n" +
                    "                    .normalize() : " + rootLocation.resolve(Path.of(file.getOriginalFilename()))
                    .normalize());
            System.out.println("destinationFile : " + this.rootLocation.resolve(Path.of(file.getOriginalFilename()))
                    .normalize().toAbsolutePath());
            Path destinationFile = this.rootLocation.resolve(Path.of(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())){
                throw new StorageException("Cannot store file outside current directory");
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e){
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch(IOException e){
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return this.rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch(MalformedURLException e){
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
