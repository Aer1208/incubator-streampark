package org.apache.streampark.console.exchange.service;

import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.exchange.bean.AddTaskRequest;
import org.apache.streampark.console.exchange.entity.Task;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TaskService extends IService<Task> {

  /**
   * 创建任务
   *
   * @param request 创建任务参数
   * @throws Exception
   */
  void addTask(AddTaskRequest request) throws Exception;

  /**
   * 分页查询所有的采集任务
   *
   * @param task 查询条件
   * @param request 分页
   * @return
   */
  IPage<Task> findTasks(Task task, RestRequest request);
}
