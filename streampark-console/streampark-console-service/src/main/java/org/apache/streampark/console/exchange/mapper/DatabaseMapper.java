package org.apache.streampark.console.exchange.mapper;

import org.apache.streampark.console.exchange.entity.Database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface DatabaseMapper extends BaseMapper<Database> {

  public List<Integer> getDbTypes();
}
