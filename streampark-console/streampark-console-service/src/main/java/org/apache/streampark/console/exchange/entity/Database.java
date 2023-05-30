package org.apache.streampark.console.exchange.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_database")
public class Database implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String dbName;

    private String dbDesc;

    private Integer dbType;

    private String dbIp;

    private Integer dbPort;

    private String dbUser;

    private String dbPass;

    private String dbConnectionParams;

    private Long userId;

    private Long teamId;

    private Short oracleType;

    private Date createTime;

    private Date modifyTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;

}
