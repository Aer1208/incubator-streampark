package org.apache.streampark.console.exchange.service;

import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.exchange.bean.ColumnsResponse;
import org.apache.streampark.console.exchange.entity.Database;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface DatabaseService extends IService<Database> {

  /**
   * 获取所有的数据库类型
   *
   * @return 数据库类型,类型ID,类型名称
   * @throws Exception
   */
  List<Map<String, Object>> getDbTypes() throws Exception;

  /**
   * 获取源端数据源
   *
   * @return
   * @throws Exception
   */
  List<Map<String, Object>> getSourceTypes() throws Exception;

  /**
   * 根据类型ID获取数据源列表
   *
   * @param dbType 类型id
   * @return 数据源列表
   * @throws Exception
   */
  List<Database> getDatabasesByType(int dbType) throws Exception;

  /**
   * 新增数据源
   *
   * @param database 新增数据源对象
   * @throws Exception
   */
  void createDatabase(Database database) throws Exception;

  /**
   * 更新数据源
   *
   * @param database 更新数据源对象
   * @throws Exception
   */
  void updateDatabase(Database database) throws Exception;

  /**
   * 根据数据源ID删除数据源对象
   *
   * @param databaseId
   * @throws Exception
   */
  void deleteById(Long databaseId) throws Exception;

  /**
   * 查询数据源列表
   *
   * @param database 查询对象
   * @param request 查询请求
   * @return 数据源分页对象
   * @throws Exception
   */
  IPage<Database> findDatabases(Database database, RestRequest request) throws Exception;

  /**
   * 获取数据库下所有表
   *
   * @param id
   * @param dbName
   * @return
   */
  public List<String> getTablesByDbId(Long id, String dbName) throws Exception;

  public ColumnsResponse getColumnsByTable(Long id, String tableName) throws Exception;

  /**
   * 获取数据库对象下的所有数据库名称
   *
   * @param id 数据库对象ID
   * @return 所有数据库名称
   * @throws Exception
   */
  List<String> getDatabases(Long id) throws Exception;
}
