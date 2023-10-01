## 今回学んだこと

下記はbuildディレクトリ以下に配置されている

- *classes*。プロジェクトのコンパイルされた .class ファイル。
- *reports*。ビルドによって生成されたレポート（テストレポートなど）。
- *libs*。アセンブリされたプロジェクトライブラリ（通常は JAR および / または WAR ファイル）。

下記の記述をすることでMaven Centralリポジトリにアクセスできる

Maven Central リポジトリにはライブラリが格納されており、依存関係の解消も行ってくれる

```groovy
repositories { 
    mavenCentral() 
}
```

implementationはプロジェクト実行時に使用する

testImplementationはテスト時に使用

- `implementation`。プロジェクトコードのコンパイルに必要な依存関係。ただし、実行時にコードを実行するコンテナー（Java Servlet API など）によって提供されます。
- `testImplementation`。テストのコンパイルと実行に使用される依存関係。ただし、プロジェクトのランタイムコードの構築または実行には必要ありません。

```groovy
sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    implementation "joda-time:joda-time:2.2"
    testImplementation "junit:junit:4.12"
}
```


gradle wrapperコマンドでbuild.gradleを参照しながらgradle-wrapper.properties等のファイルを作成する

gradle-wrapper.propertiesを参照し、./gradlew buildが実行される

実際にgradle-wrapper.propertiesを削除すると./gradlew buildがエラーとなることを確認

> rm gradle/wrapper/gradle-wrapper.properties
./gradlew build --stacktrace
Exception in thread "main" java.lang.RuntimeException: Wrapper properties file '~/app/SpringGuide/gs-gradle/gradle/wrapper/gradle-wrapper.properties' does not exist.
at org.gradle.wrapper.WrapperExecutor.forWrapperPropertiesFile(WrapperExecutor.java:46)
at org.gradle.wrapper.GradleWrapperMain.main(GradleWrapperMain.java:62)
>