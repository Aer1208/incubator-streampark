package org.apache.streampark.console.exchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 采集任务子任务实体 */
@Data
@TableName("t_exchange_subtask")
public class SubTask {
  /** 子任务ID */
  @TableId(type = IdType.AUTO)
  private Long subTaskId;
  /** 主任务ID */
  private Long taskId;

  /** 采集数据源中的表名称 */
  private String sourceTableName;

  /** 采集字段列表 */
  private String sourceFields;

  /** 采集字段类型列表 */
  private String sourceTypes;

  /** 主键字段列表 */
  private String pkFields;

  /** 目标类型 */
  private Integer targetType;

  /** 目标ID，对应t_datasource中的ID */
  private Long targetId;

  /** 目标数据库名称 */
  private String targetDbName;

  /** 目标表名称 */
  private String targetTableName;

  /** 目标参数，以json形式存储 */
  private String targetParams;

  /** 创建时间 */
  private Date createTime;
}
