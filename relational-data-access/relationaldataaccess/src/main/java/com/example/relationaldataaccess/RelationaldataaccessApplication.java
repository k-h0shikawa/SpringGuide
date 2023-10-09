package com.example.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationaldataaccessApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(RelationaldataaccessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RelationaldataaccessApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... Strings) throws Exception{
		log.info("Creating tables");

		// テーブル定義
		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers("+
				"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

		// 性と名を分割
		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
				.stream()
				.map(name -> name.split(" "))
				.collect(Collectors.toList());

		splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

		// INSERT処理
		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?, ?)", splitUpNames);

		// first_nameがJoshのカラムのみ取得
		log.info("Querying for customer records where first_name = 'Josh':");
		jdbcTemplate.queryForList(
				"SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" })
		.forEach(column -> {
				Customer customer = new Customer(Long.parseLong(column.get("ID").toString()), column.get("FIRST_NAME").toString(), column.get("LAST_NAME").toString());
				log.info(customer.toString());
			}
		);
	}
}
