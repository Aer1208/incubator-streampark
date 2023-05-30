package org.apache.streampark.console.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.streampark.common.util.Utils;
import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.exchange.DatabaseTypes;
import org.apache.streampark.console.exchange.entity.Database;
import org.apache.streampark.console.exchange.mapper.DatabaseMapper;
import org.apache.streampark.console.exchange.service.DatabaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DatabaseServiceImpl extends ServiceImpl<DatabaseMapper, Database> implements DatabaseService {
    @Override
    public Map<Integer, String> getDbTypes() throws Exception {
        return DatabaseTypes.getTypes();
    }

    @Override
    public Map<Integer, String> getSourceTypes() throws Exception {
        return DatabaseTypes.getSourceTypes();
    }

    @Override
    public List<Database> getDatabasesByType(int dbType) throws Exception {
        LambdaQueryWrapper<Database> queryWrapper = new LambdaQueryWrapper<Database>().eq(Database::getDbType, dbType);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDatabase(Database database) throws Exception {
        Utils.required(database != null, "新增数据源对象不能为空");
        database.setCreateTime(new Date());
        database.setModifyTime(new Date());
        save(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDatabase(Database database) throws Exception {
        Utils.required(database != null, "更新是数据源对象不能为空");
        database.setModifyTime(new Date());
        updateById(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long databaseId) throws Exception {
        baseMapper.deleteById(databaseId);
    }

    @Override
    public IPage<Database> findDatabases(Database database, RestRequest request) throws Exception {
        LambdaQueryWrapper<Database> queryWrapper = new LambdaQueryWrapper<>();
        if (database.getDbType() != null) {
            queryWrapper.eq(Database::getDbType, database.getDbType());
        }
        if (StringUtils.isNotEmpty(database.getDbName())) {
            queryWrapper.like(Database::getDbName, database.getDbName());
        }
        if (database.getCreateTimeFrom() != null) {
            queryWrapper.ge(Database::getCreateTime, database.getCreateTimeFrom());
        }
        if (database.getCreateTimeTo() != null) {
            queryWrapper.le(Database::getCreateTime, database.getCreateTimeTo());
        }
        Page<Database> page = new Page<>();
        page.setCurrent(request.getPageNum());
        page.setSize(request.getPageSize());
        Page<Database> databases = baseMapper.selectPage(page, queryWrapper);
        Utils.required(databases!=null);
        if (databases.getTotal() == 0 ) {
            databases.setRecords(Collections.emptyList());
        }
        return databases;
    }
}
