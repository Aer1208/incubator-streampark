package org.apache.streampark.console.exchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 采集任务实体 */
@Data
@TableName("t_exchange_task")
public class Task {

  /** 任务ID */
  @TableId(type = IdType.AUTO)
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

  /** 数据源连接属性,以json形式存储 */
  private String sourceParams;

  /** 用户ID */
  private Long userId;

  /** 归属团队ID */
  private Long teamId;

  /** 创建时间 */
  private Date createTime;

  /** 更新时间 */
  private Date modifyTime;
}
