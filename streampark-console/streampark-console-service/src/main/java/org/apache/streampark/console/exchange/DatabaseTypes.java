package org.apache.streampark.console.exchange;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<Integer, String> getSourceTypes() {
        HashMap<Integer, String> sourceDbMaps = new HashMap<>();
        Arrays.stream(DatabaseTypes.values()).filter(x-> x.isSource).map(x->sourceDbMaps.put(x.dbType, x.dbName));
        return sourceDbMaps;
    }

    public static Map<Integer, String> getTypes() {
        HashMap<Integer, String> sourceDbMaps = new HashMap<>();
        Arrays.stream(DatabaseTypes.values()).map(x->sourceDbMaps.put(x.dbType, x.dbName));
        return sourceDbMaps;
    }


}
