import {BasicColumn, FormSchema} from "/@/components/Table";
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();
import {DBType} from "/@/views/exchange/database/database.data";

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

export const columns = [
  {
    title: '源表',
    dataIndex: 'sourceTableName',
    key: 'sourceTableName',
  },
  {
    title: '字段列表',
    dataIndex: 'sourceFields',
    key: 'sourceFields',
    // customRender: (sourceFields) => sourceFields.value.join(","),
  },
  {
    title: '字段类型',
    dataIndex: 'sourceTypes',
    key: 'sourceTypes',
    // customRender: (sourceTypes) => sourceTypes.value.join(","),
  },
  {
    title:'主键列表',
    dataIndex:'pkFields',
    key:'pkFields',
  },
  {
    title:'目标数据库类型',
    dataIndex:'targetType',
    key:'targetType',
  },
  {
    title:'目标数据源',
    dataIndex:'targetId',
    key:'targetId',
  },
  {
    title:'目标数据库',
    dataIndex:'targetDbName',
    key:'targetDbName',
  },
  {
    title:'目标表',
    dataIndex:'targetTableName',
    key:'targetTableName',
  },
  {
    title:'目标表其他参数',
    dataIndex:'targetParams',
    key:'targetParams',
  },
  {
    title:'删除?',
    dataIndex:'operation',
    key:'operation',
    slots: { customRender: 'operation' },
  }
];


export const taskColumns: BasicColumn[] = [
  { title: t('exchange.task.form.taskName'), dataIndex: 'taskName', sorter: true },
  { title: t('exchange.task.form.sourceType'), dataIndex: 'sourceType' },
  { title: t('exchange.task.form.sourceId'), dataIndex: 'sourceId' },
  { title: t('exchange.task.form.dbName'), dataIndex: 'dbName' },
  {
    title: t('common.createTime'),
    dataIndex: 'createTime',
    sorter: true,
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'taskName',
    label: t('exchange.task.form.taskName'),
    component: 'Input',
    colProps: { span: 3 },
  },
  {
    field: 'sourceType',
    label: t('exchange.task.form.sourceType'),
    component: 'Select',
    componentProps: {
      options: [
        { label: 'Mysql', value: DBType.MYSQL },
        { label: 'Oracle', value: DBType.ORACLE },
        { label: 'Kafka', value: DBType.KAFKA },
        { label: 'Mongo', value: DBType.MONGO }
      ],
    },
    colProps: { span: 3},
  },
  {
    field: 'sourceId',
    label: t('exchange.task.form.sourceId'),
    component: 'Select',
    componentProps: {
      options: [
        { label: 'Mysql', value: DBType.MYSQL },
        { label: 'Oracle', value: DBType.ORACLE },
        { label: 'Kafka', value: DBType.KAFKA },
        { label: 'Mongo', value: DBType.MONGO }
      ],
    },
    colProps: { span: 4},
  },
  {
    field: 'dbName',
    label: t('exchange.task.form.dbName'),
    component: 'Input',
    colProps: { span: 3 },
  },
  {
    field: 'createTime',
    label: t('common.createTime'),
    component: 'RangePicker',
    colProps: { span: 5 },
  },
];
