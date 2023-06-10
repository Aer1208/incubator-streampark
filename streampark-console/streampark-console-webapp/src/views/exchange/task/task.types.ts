export interface Task {
  taskId: number,
  taskName: string,
  taskDesc: string,
  sourceType: number,
  sourceId: number,
  sourceParams:string,
  subTasks: Array<SubTask>
}

export interface SubTask {
  subTaskId:number,
  sourceTableName: String,
  sourceFields:Array<string>,
  sourceTypes: Array<string>,
  pkFields: string,
  targetType:number,
  targetId:number,
  targetTableName:string,
  targetParams:string,
}
