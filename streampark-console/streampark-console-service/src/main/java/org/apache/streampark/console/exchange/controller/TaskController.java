package org.apache.streampark.console.exchange.controller;

import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.base.domain.RestResponse;
import org.apache.streampark.console.exchange.bean.AddTaskRequest;
import org.apache.streampark.console.exchange.entity.Task;
import org.apache.streampark.console.exchange.service.TaskService;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("task")
public class TaskController {
  @Autowired private TaskService taskService;

  /**
   * 新增采集任务请求
   *
   * @param request
   * @return
   */
  @PostMapping("add")
  @RequiresPermissions("task:add")
  public RestResponse add(@RequestBody AddTaskRequest request) throws Exception {
    taskService.addTask(request);
    return RestResponse.success().data(true);
  }

  @PostMapping("list")
  @RequiresPermissions("task:list")
  public RestResponse list(Task task, RestRequest request) throws Exception {
    return RestResponse.success().data(taskService.findTasks(task, request));
  }
}
