import {defHttp} from "/@/utils/http/axios";

enum Api{
  LIST = "/database/list",
  CREATE = "/database/add",
  UPDATE = "/database/update",
  DELETE = "/database/delete",
  SOURCE_DB_TYPES = "/database/source_types",
  DB_TYPES = "/database/db_types",
  BY_TYPE = "/database/by_type",
  DATABASES = "/database/databases",
  TABLES = "/database/tables",
  COLUMNS = "/database/columns",
}

export function getDatabaseList(data?: Recordable) {
  return defHttp.post({
    url: Api.LIST,
    data,
  });
}

export function addDatabase(data: Recordable) {
  return defHttp.post({
    url: Api.CREATE,
    data,
  });
}

export function updateDatabase(data: Recordable) {
  return defHttp.post({
    url: Api.UPDATE,
    data,
  });
}

export function deleteDatabase(data: Recordable) {
  return defHttp.post({
    url: Api.DELETE,
    data: {
      id: data.id,
    }
  });
}

export function getSourceDbTypes() {
  return defHttp.get({
    url: Api.SOURCE_DB_TYPES,
  });
}

export function getDBTypes() {
  return defHttp.get({
    url: Api.DB_TYPES,
  });
}

export function getDataBaseByType(dbType:number) {
  return defHttp.post({
    url: Api.BY_TYPE,
    data: {
      dbType: dbType,
    }
  })
}

export function getDatabases(id:number) {
  return defHttp.get({
    url: Api.DATABASES,
    params: {
      id: id,
    }
  })
}

export function getTables(id: number, dbName: String) {
  return defHttp.get({
    url: Api.TABLES,
    params: {
      id: id,
      dbName: dbName,
    }
  })
}

export function getColumns(id: number, tableName:String) {
  return defHttp.get({
    url: Api.COLUMNS,
    params: {
      id: id,
      tableName: tableName,
    }
  })
}