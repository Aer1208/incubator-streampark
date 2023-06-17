package org.apache.streampark.console.exchange.controller;

import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.base.domain.RestResponse;
import org.apache.streampark.console.core.annotation.ApiAccess;
import org.apache.streampark.console.exchange.entity.Database;
import org.apache.streampark.console.exchange.service.DatabaseService;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("database")
public class DatabaseController {

  @Autowired private DatabaseService databaseService;

  @ApiAccess
  @PostMapping("list")
  @RequiresPermissions("database:list")
  public RestResponse databaseList(Database database, RestRequest request) throws Exception {
    return RestResponse.success(databaseService.findDatabases(database, request));
  }

  @PostMapping("add")
  @RequiresPermissions("database:add")
  public RestResponse createDatabase(Database database) throws Exception {
    // 创建数据源
    databaseService.createDatabase(database);
    return RestResponse.success().data(true);
  }

  @PostMapping("update")
  @RequiresPermissions("database:update")
  public RestResponse updateDatabase(Database database) throws Exception {
    databaseService.updateDatabase(database);
    return RestResponse.success().data(true);
  }

  @PostMapping("delete")
  @RequiresPermissions("database:delete")
  public RestResponse deleteDatabase(Long id) throws Exception {
    databaseService.deleteById(id);
    return RestResponse.success().data(true);
  }

  @GetMapping("source_types")
  public RestResponse getSourceTypes() throws Exception {
    return RestResponse.success().data(databaseService.getSourceTypes());
  }

  @GetMapping("db_types")
  public RestResponse getDbTypes() throws Exception {
    return RestResponse.success().data(databaseService.getDbTypes());
  }

  @PostMapping("by_type")
  public RestResponse getDatabasesByType(Integer dbType) throws Exception {
    return RestResponse.success().data(databaseService.getDatabasesByType(dbType));
  }

  @GetMapping("databases")
  public RestResponse databases(Long id) throws Exception {
    return RestResponse.success().data(databaseService.getDatabases(id));
  }

  @GetMapping("tables")
  public RestResponse getTables(Long id, String dbName) throws Exception {
    return RestResponse.success().data(databaseService.getTablesByDbId(id, dbName));
  }

  @GetMapping("columns")
  public RestResponse getColumns(Long id, String tableName) throws Exception {
    return RestResponse.success().data(databaseService.getColumnsByTable(id, tableName));
  }
}
