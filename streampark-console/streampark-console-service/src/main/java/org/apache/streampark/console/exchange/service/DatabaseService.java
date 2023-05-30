package org.apache.streampark.console.exchange.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.exchange.entity.Database;

import java.util.List;
import java.util.Map;

public interface DatabaseService extends IService<Database> {

    /**
     * 获取所有的数据库类型
     * @return 数据库类型,类型ID,类型名称
     * @throws Exception
     */
    Map<Integer, String> getDbTypes() throws Exception;

    /**
     * 获取源端数据源
     * @return
     * @throws Exception
     */
    Map<Integer,String> getSourceTypes() throws  Exception;

    /**
     * 根据类型ID获取数据源列表
     * @param dbType 类型id
     * @return 数据源列表
     * @throws Exception
     */
    List<Database> getDatabasesByType(int dbType) throws Exception;

    /**
     * 新增数据源
     * @param database 新增数据源对象
     * @throws Exception
     */
    void createDatabase(Database database) throws Exception;

    /**
     * 更新数据源
     * @param database 更新数据源对象
     * @throws Exception
     */
    void updateDatabase(Database database) throws Exception;

    /**
     * 根据数据源ID删除数据源对象
     * @param databaseId
     * @throws Exception
     */
    void deleteById(Long databaseId) throws Exception;

    /**
     * 查询数据源列表
     * @param database 查询对象
     * @param request 查询请求
     * @return 数据源分页对象
     * @throws Exception
     */
    IPage<Database> findDatabases(Database database, RestRequest request) throws Exception;
}
