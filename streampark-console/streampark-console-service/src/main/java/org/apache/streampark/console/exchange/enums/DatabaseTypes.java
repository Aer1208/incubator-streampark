package org.apache.streampark.console.exchange.enums;

import org.apache.streampark.console.exchange.bean.ColumnsResponse;
import org.apache.streampark.console.exchange.database.MysqlDatabaseMeta;
import org.apache.streampark.console.exchange.entity.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum DatabaseTypes {
  MYSQL(1, "Mysql", true) {
    @Override
    public List<String> getTables(Database database) throws Exception {
      return new MysqlDatabaseMeta().getTables(database);
    }

    @Override
    public ColumnsResponse getColumns(Database database, String tableName) throws Exception {
      return new MysqlDatabaseMeta().getColumns(database, tableName);
    }
  },
  ORACLE(2, "Oracle", true),
  KAFKA(3, "Kafka", true),
  MONGO(4, "Mongo", true),
  STARROCKS(5, "StarRocks", false),
  HDFS(6, "HDFS", false),
  HBASE(7, "Hbase", false);
  Integer dbType;
  String dbName;
  Boolean isSource;

  /**
   * @param database
   * @return
   */
  public List<String> getTables(Database database) throws Exception {
    return null;
  }

  public ColumnsResponse getColumns(Database database, String tableName) throws Exception {
    return null;
  }

  DatabaseTypes(Integer dbType, String dbName) {
    this.dbType = dbType;
    this.dbName = dbName;
    this.isSource = false;
  }

  DatabaseTypes(Integer dbType, String dbName, Boolean isSource) {
    this(dbType, dbName);
    this.isSource = isSource;
  }

  public static List<Map<String, Object>> getSourceTypes() {
    List<Map<String, Object>> result = new ArrayList<>();
    Arrays.stream(DatabaseTypes.values())
        .filter(x -> x.isSource)
        .forEach(
            x -> {
              HashMap<String, Object> sourceDbMaps = new HashMap<>();
              sourceDbMaps.put("sourceType", x.dbType);
              sourceDbMaps.put("sourceName", x.dbName);
              result.add(sourceDbMaps);
            });
    return result;
  }

  public static List<Map<String, Object>> getTypes() {
    List<Map<String, Object>> result = new ArrayList<>();
    Arrays.stream(DatabaseTypes.values())
        .forEach(
            x -> {
              HashMap<String, Object> sourceDbMaps = new HashMap<>();
              sourceDbMaps.put("sourceType", x.dbType);
              sourceDbMaps.put("sourceName", x.dbName);
              result.add(sourceDbMaps);
            });
    return result;
  }

  public static DatabaseTypes valueOf(Integer dbType) {
    Optional<DatabaseTypes> dbTypeOption =
        Arrays.stream(DatabaseTypes.values()).filter(x -> x.dbType == dbType).findFirst();
    return dbTypeOption.get();
  }
}
