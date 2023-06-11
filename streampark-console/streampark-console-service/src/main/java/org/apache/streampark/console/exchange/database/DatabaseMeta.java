package org.apache.streampark.console.exchange.database;

import org.apache.streampark.console.exchange.bean.ColumnsResponse;
import org.apache.streampark.console.exchange.entity.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** Database元数据接口类 */
public interface DatabaseMeta {

  /**
   * 获取数据库的表列表
   *
   * @param database 数据库
   * @return
   * @throws Exception
   */
  default List<String> getTables(Database database) throws Exception {

    List<String> tables = new ArrayList<>();
    Connection conn = getConnection(database);
    try (ResultSet resultSet = conn.prepareStatement(getTableSql(database)).executeQuery()) {
      while (resultSet.next()) {
        tables.add(resultSet.getString(1));
      }
    }
    conn.close();
    return tables;
  }

  /**
   * 获取字段列表
   *
   * @param database
   * @param tableName
   * @return
   * @throws Exception
   */
  default ColumnsResponse getColumns(Database database, String tableName) throws Exception {

    Connection conn = getConnection(database);
    List<String> fields = new ArrayList<>();
    List<String> types = new ArrayList<>();
    List<String> fieldAndTypes = new ArrayList<>();
    List<String> pkFields = new ArrayList<>();
    try (ResultSet resultSet =
        conn.prepareStatement(getColumnSql(database, tableName)).executeQuery()) {
      while (resultSet.next()) {
        String fieldName = resultSet.getString(1);
        String fieldType = resultSet.getString(2);
        Integer isPkField = resultSet.getInt(3);
        fields.add(fieldName);
        types.add(fieldType);
        fieldAndTypes.add(fieldName + "," + fieldType);
        if (isPkField == 1) {
          pkFields.add(fieldName);
        }
      }
    }
    return new ColumnsResponse(fields, types, pkFields, fieldAndTypes);
  }

  /**
   * 根据数据库获取数据库连接
   *
   * @param database
   * @return
   */
  Connection getConnection(Database database) throws Exception;

  /**
   * 获取表的查询语句
   *
   * @param database
   * @return
   */
  String getTableSql(Database database);

  /**
   * 根据表名和数据库对象获取查询表字段列表
   *
   * @param database 数据库对象
   * @param tableName 表明
   * @return 查询字段有3个，第一个字段名称，第二个字段类型，第三个是否未主键
   */
  String getColumnSql(Database database, String tableName);
}
