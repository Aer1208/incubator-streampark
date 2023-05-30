package org.apache.streampark.console.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.streampark.console.exchange.entity.Database;

import java.util.List;

public interface DatabaseMapper extends BaseMapper<Database> {

    public List<Integer> getDbTypes();
}
