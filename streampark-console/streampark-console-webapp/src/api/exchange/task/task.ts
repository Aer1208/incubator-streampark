import {Task} from "/@/views/exchange/task/task.types";
import {defHttp} from "/@/utils/http/axios";

enum Api{
  ADD = "/task/add",
}
export function addTask(task: Task) {
  defHttp.postJson({
    url: Api.ADD,
    data: task
  })
}
