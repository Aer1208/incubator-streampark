package org.apache.streampark.console.exchange.service.impl;

import org.apache.streampark.common.util.Utils;
import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.core.service.CommonService;
import org.apache.streampark.console.exchange.bean.AddTaskRequest;
import org.apache.streampark.console.exchange.entity.SubTask;
import org.apache.streampark.console.exchange.entity.Task;
import org.apache.streampark.console.exchange.mapper.TaskMapper;
import org.apache.streampark.console.exchange.service.SubTaskService;
import org.apache.streampark.console.exchange.service.TaskService;
import org.apache.streampark.console.system.entity.User;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/** 任务管理服务实列类 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

  @Autowired private SubTaskService subTaskService;

  @Autowired private CommonService commonService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addTask(AddTaskRequest request) throws Exception {
    List<SubTask> subTasks = request.getSubTasks();
    Utils.required(subTasks != null && subTasks.size() > 0, "子任务不能为空");
    Task task = new Task();
    BeanUtils.copyProperties(request, task, "subTasks");
    User user = commonService.getCurrentUser();
    task.setUserId(user.getUserId());
    // 保存主任务
    this.save(task);
    // 补充任务ID属性
    subTasks.forEach(x -> x.setTaskId(task.getTaskId()));
    // 保存子任务
    subTaskService.saveBatch(subTasks);
  }

  @Override
  public IPage<Task> findTasks(Task task, RestRequest request) {
    LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();

    // 根据任务名称模糊查询
    if (StringUtils.isNotEmpty(task.getTaskName())) {
      queryWrapper.like(Task::getTaskName, task.getTaskName());
    }

    // 根据数据源类型查询
    if (task.getSourceType() != null) {
      queryWrapper.eq(Task::getSourceType, task.getSourceType());
    }

    // 根据数据源查询
    if (task.getSourceId() != null) {
      queryWrapper.eq(Task::getSourceId, task.getSourceId());
    }

    // 根据数据库名查询
    if (StringUtils.isNotEmpty(task.getDbName())) {
      queryWrapper.like(Task::getDbName, task.getDbName());
    }

    // 根据创建时间查询
    if (StringUtils.isNotEmpty(task.getCreateTimeFrom())) {
      queryWrapper.ge(Task::getCreateTime, task.getCreateTimeFrom());
    }

    // 根据创建时间查询
    if (StringUtils.isNotEmpty(task.getCreateTimeTo())) {
      queryWrapper.le(Task::getCreateTime, task.getCreateTimeTo());
    }

    Page<Task> page = new Page<>();
    page.setCurrent(request.getPageNum());
    page.setSize(request.getPageSize());
    IPage<Task> tasks = baseMapper.selectPage(page, queryWrapper);

    if (tasks.getTotal() == 0) {
      tasks.setRecords(Collections.emptyList());
    }

    return tasks;
  }
}
