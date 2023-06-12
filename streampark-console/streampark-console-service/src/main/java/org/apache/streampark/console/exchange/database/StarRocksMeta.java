package org.apache.streampark.console.exchange.database;

import org.apache.streampark.console.exchange.entity.Database;

import java.sql.Connection;

public class StarRocksMeta implements DatabaseMeta {
  @Override
  public Connection getConnection(Database database) throws Exception {
    return null;
  }

  @Override
  public String getTableSql(Database database) {
    return null;
  }

  @Override
  public String getColumnSql(Database database, String tableName) {
    return null;
  }

  @Override
  public String getDatabaseSql() {
    return null;
  }
}
