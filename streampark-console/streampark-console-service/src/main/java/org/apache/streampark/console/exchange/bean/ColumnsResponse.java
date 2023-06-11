package org.apache.streampark.console.exchange.bean;

import lombok.Data;

import java.util.List;

/** 根据数据库id和表名获取所有Columns的返回对象 */
@Data
public class ColumnsResponse {

  /** 字段名称 */
  private List<String> columns;

  /** 字段类型列表 */
  private List<String> types;

  /** 主键字段 */
  private List<String> pkColumns;

  /** 字段和类型拼接列表，拼接字段名称+字段类型，用逗号拼接 */
  private List<String> columnAndTypes;

  public ColumnsResponse(
      List<String> columns,
      List<String> types,
      List<String> pkColumns,
      List<String> columnAndTypes) {
    this.columns = columns;
    this.types = types;
    this.pkColumns = pkColumns;
    this.columnAndTypes = columnAndTypes;
  }

  @Override
  public String toString() {
    return "ColumnsResponse{"
        + "columns="
        + columns
        + ", types="
        + types
        + ", pkColumns="
        + pkColumns
        + ", columnAndTypes="
        + columnAndTypes
        + '}';
  }
}
