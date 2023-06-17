package org.apache.streampark.console.exchange.enums;

import org.apache.streampark.console.exchange.bean.ColumnsResponse;
import org.apache.streampark.console.exchange.database.DatabaseMeta;
import org.apache.streampark.console.exchange.database.KafkaMeta;
import org.apache.streampark.console.exchange.database.MongoMeta;
import org.apache.streampark.console.exchange.database.MysqlDatabaseMeta;
import org.apache.streampark.console.exchange.database.OracleDatabaseMeta;
import org.apache.streampark.console.exchange.entity.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum DatabaseTypes {
  MYSQL(1, "Mysql", true, new MysqlDatabaseMeta()),
  ORACLE(2, "Oracle", true, new OracleDatabaseMeta()),
  KAFKA(3, "Kafka", true, new KafkaMeta()),
  MONGO(4, "Mongo", true, new MongoMeta()),
  STARROCKS(5, "StarRocks", false, new MysqlDatabaseMeta()),
  HDFS(6, "HDFS", false),
  HBASE(7, "Hbase", false);
  Integer dbType;
  String dbName;
  Boolean isSource;

  DatabaseMeta databaseMeta;

  /**
   * @param database
   * @return
   */
  public List<String> getTables(Database database, String dbName) throws Exception {
    if (databaseMeta != null) {
      return databaseMeta.getTables(database, dbName);
    }
    return null;
  }

  public ColumnsResponse getColumns(Database database, String tableName) throws Exception {
    if (databaseMeta != null) {
      return databaseMeta.getColumns(database, tableName);
    }
    return null;
  }

  public List<String> getDatabases(Database database) throws Exception {
    if (databaseMeta != null) {
      return databaseMeta.getDatabases(database);
    }
    return null;
  }

  DatabaseTypes(Integer dbType, String dbName) {
    this.dbType = dbType;
    this.dbName = dbName;
    this.isSource = false;
  }

  DatabaseTypes(Integer dbType, String dbName, Boolean isSource, DatabaseMeta databaseMeta) {
    this(dbType, dbName, isSource);
    this.databaseMeta = databaseMeta;
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
