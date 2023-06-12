package org.apache.streampark.console.exchange.database;

import org.apache.streampark.console.exchange.entity.Database;

import java.sql.Connection;
import java.sql.DriverManager;

/** Mysql的数据库元数据获取 */
public class MysqlDatabaseMeta implements DatabaseMeta {
  @Override
  public Connection getConnection(Database database) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    String url = String.format("jdbc:mysql://%s:%d", database.getDbIp(), database.getDbPort());
    return DriverManager.getConnection(url, database.getDbUser(), database.getDbPass());
  }

  @Override
  public String getTableSql(Database database) {
    return String.format(
        "select TABLE_NAME  from information_schema.TABLES t where t.TABLE_SCHEMA = '%s'",
        database.getDbName());
  }

  @Override
  public String getColumnSql(Database database, String tableName) {
    return String.format(
        "select COLUMN_NAME , DATA_TYPE , "
            + "case when COLUMN_KEY = 'PRI' then 1 else 0 end as isPk  "
            + "from information_schema.`COLUMNS` c  "
            + "where c.TABLE_SCHEMA  ='%s' and TABLE_NAME ='%s'",
        database.getDbName(), tableName);
  }

  @Override
  public String getDatabaseSql() {
    return "show databases";
  }
}
