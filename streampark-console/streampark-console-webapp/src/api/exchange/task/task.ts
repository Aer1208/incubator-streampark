import {Task} from "/@/views/exchange/task/task.types";
import {defHttp} from "/@/utils/http/axios";

enum Api{
  ADD = "/task/add",
  LIST = "/task/list",
  DELETE = "/task/delete"
}
export function addTask(task: Task) {
  return defHttp.postJson({
    url: Api.ADD,
    data: task
  })
}

export function getTasks(task: Task) {
  return defHttp.post({
    url: Api.LIST,
    data: task
  })
}

export function deleteTask(task: Task) {
  return defHttp.post({
    url: Api.DELETE,
    data: {
      id: task.taskId
    }
  })
}
