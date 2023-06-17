export interface Task {
  taskId: number,
  taskName: string,
  taskDesc: string,
  sourceType: number,
  sourceId: number,
  sourceParams:string,
  subTasks: Array<SubTask>,
  dbName:string,
}

export interface SubTask {
  subTaskId:number,
  sourceTableName: String,
  sourceFields:string,
  sourceTypes: string,
  pkFields: string,
  targetType:number,
  targetId:number,
  targetDbName:string,
  targetTableName:string,
  targetParams:string,
  tmpSourceFields:Array<String>,
}
