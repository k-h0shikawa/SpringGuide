## 今回学んだこと
@SpringBootApplicationによって追加されるもの
 - @Configuration
 - @EnableAutoConfiguration
 - @ComponentScan

RelationaldataaccessApplication.javaでは、CommandLineRunnerを実装することでアプリケーションコンテキストがロードされたあとに、run()が実行される。

アプリケーションコンテキスト…コンポーネントの作成及び使用される環境の定義。DBの向き先やキャッシュの有無などの設定を決める。
https://qiita.com/koriym/items/d8d14682ce0a542c3b42

spring-jdbcを使用しているため、インメモリRDBエンジンのH2へ自動的に接続
それによって、JdbcTemplateをインジェクションするだけでDBを使用できる

query(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.RowMapper<T>)は非推奨であったため、queryForList(String sql,
@Nullable Object... args)へ変更。
