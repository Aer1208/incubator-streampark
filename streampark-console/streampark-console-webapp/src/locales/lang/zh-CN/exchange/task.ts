export default {
  task:{
    name: '任务名称',
    desc: '任务描述',
    sourceType: '数据源类型',
    sourceId: '数据源',
    subTask: {
      sourceTableName: '源表',
      sourceFields: '源表字段',
      sourceTypes: '源表字段类型',
      pkFields:'主键字段',
      targetType: '目标类型',
      targetId: '目标源ID',
      targetTableName: '目标表名称'
    }
  },
  form: {
    taskName: '任务名称',
    sourceType: '数据源类型',
    sourceId: '数据源',
    dbName: '数据库'
  },
  table: {
    title: '数据同步任务列表'
  },
  operation: {
    start: "启动采集任务"
  }
}
