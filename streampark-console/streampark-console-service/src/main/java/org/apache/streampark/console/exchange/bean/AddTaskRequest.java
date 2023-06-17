package org.apache.streampark.console.exchange.bean;

import org.apache.streampark.console.exchange.entity.SubTask;

import lombok.Data;

import java.util.List;

/** 新增任务请求实体类 */
@Data
public class AddTaskRequest {

  /** 任务ID */
  private Long taskId;

  /** 任务名称 */
  private String taskName;

  /** 任务描述 */
  private String taskDesc;

  /** 采集数据源类型 */
  private Integer sourceType;

  /** 采集数据源id,对于t_datasource.id */
  private Long sourceId;

  /** 采集数据源中的数据库 */
  private String dbName;

  /** 数据源连接属性 */
  private String sourceParams;

  /** 团队ID */
  private Long teamId;

  /** 子任务 */
  List<SubTask> subTasks;
}
