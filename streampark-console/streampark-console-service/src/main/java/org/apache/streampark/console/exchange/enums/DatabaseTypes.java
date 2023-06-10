package org.apache.streampark.console.exchange.enums;

import java.util.*;

public enum DatabaseTypes {
    MYSQL(1, "Mysql", true),
    ORACLE(2, "Oracle", true),
    KAFKA(3,"Kafka", true),
    MONGO(4, "Mongo", true),
    STARROCKS(5, "StarRocks",false),
    HDFS(6,"HDFS", false),
    HBASE(7, "Hbase", false)
    ;
    Integer dbType;
    String dbName;
    Boolean isSource;

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
        Arrays.stream(DatabaseTypes.values()).filter(x-> x.isSource).forEach(x->{
            HashMap<String, Object> sourceDbMaps = new HashMap<>();
            sourceDbMaps.put("sourceType", x.dbType);
            sourceDbMaps.put("sourceName", x.dbName);
            result.add(sourceDbMaps);
        });
        return result;
    }

    public static List<Map<String, Object>> getTypes() {
        List<Map<String, Object>> result = new ArrayList<>();
        Arrays.stream(DatabaseTypes.values()).forEach(x->{
            HashMap<String, Object> sourceDbMaps = new HashMap<>();
            sourceDbMaps.put("sourceType", x.dbType);
            sourceDbMaps.put("sourceName", x.dbName);
            result.add(sourceDbMaps);
        });
        return result;
    }


}
