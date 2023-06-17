package org.apache.streampark.console.exchange.service.impl;

import org.apache.streampark.console.exchange.entity.SubTask;
import org.apache.streampark.console.exchange.mapper.SubTaskMapper;
import org.apache.streampark.console.exchange.service.SubTaskService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SubTaskServiceImpl extends ServiceImpl<SubTaskMapper, SubTask>
    implements SubTaskService {}
