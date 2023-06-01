import {BasicColumn, FormSchema} from "/@/components/Table";
import {h} from "vue";
import {Tag} from "ant-design-vue";
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();
export const enum DBType {
  MYSQL = 1,
  ORACLE = 2,
  KAFKA = 3,
  MONGO = 4,
  STARROCKS = 5,
  HDFS = 6,
  HBASE = 7,
};

export const enum OracleType {
  SERVICENAME = 1,
  SID = 2,
}

function getDbTypeName(dbType) {
  if (dbType === DBType.MYSQL) {
    return "Mysql";
  } else if (dbType === DBType.ORACLE) {
    return "Oracle";
  } else if (dbType === DBType.HDFS ) {
    return "Hdfs";
  } else if (dbType === DBType.STARROCKS) {
    return "StarRocks";
  } else if ( dbType === DBType.KAFKA ) {
    return "Kafka";
  } else if ( dbType === DBType.HBASE) {
    return "Hbase";
  } else if (dbType === DBType.MONGO) {
    return "Mongo";
  }
}

export const columns: BasicColumn[] = [
  { title: t('exchange.database.form.dbName'), dataIndex: 'dbName', sorter: true },
  { title: t('exchange.database.form.dbIp'), dataIndex: 'dbIp' },
  { title: t('exchange.database.form.dbPort'), dataIndex: 'dbPort' },
  {
    title: t('exchange.database.form.dbType'),
    dataIndex: 'dbType',
    customRender: ({ record }) => {
      const enable = record?.dbType === DBType.MYSQL;
      const color = enable ? 'green' : 'red';
      const text = getDbTypeName(record?.dbType);
      return h(Tag, { color }, () => text);
    },
  },
  {
    title: t('common.createTime'),
    dataIndex: 'createTime',
    sorter: true,
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'dbType',
    label: t('exchange.database.form.dbType'),
    component: 'Select',
    componentProps: {
      options: [
        { label: 'Mysql', value: DBType.MYSQL },
        { label: 'Oracle', value: DBType.ORACLE },
        { label: 'Kafka', value: DBType.KAFKA },
        { label: 'Mongo', value: DBType.MONGO },
        { label: 'StarRocks', value: DBType.STARROCKS },
        { label: 'Hdfs', value: DBType.HDFS },
        { label: 'Hbase', value: DBType.HBASE },
      ],
    },
    colProps: { span: 6},
  },
  {
    field: 'dbName',
    label: t('exchange.database.form.dbName'),
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    field: 'createTime',
    label: t('common.createTime'),
    component: 'RangePicker',
    colProps: { span: 6 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field:'id',
    component:'Input',
    label:'主键',
    show: false,
  },
  {
    field: 'dbName',
    label: t('exchange.database.form.dbName'),
    component: 'Input',
    rules:[
      {required:true, message: t('exchange.database.rule.dbNameRequire')},
      {max:20, message: t('exchange.database.rule.maxDbName')}
    ]
  },
  {
    field: 'dbDesc',
    label: t('exchange.database.form.dbDesc'),
    component: 'InputTextArea',
  },
  {
    field: 'dbType',
    label: t('exchange.database.form.dbType'),
    component: 'Select',
    required: true,
    defaultValue: DBType.MYSQL,
    componentProps: {
      options: [
        { label: 'Mysql', value: DBType.MYSQL },
        { label: 'Oracle', value: DBType.ORACLE },
        { label: 'Kafka', value: DBType.KAFKA },
        { label: 'Mongo', value: DBType.MONGO },
        { label: 'StarRocks', value: DBType.STARROCKS },
        { label: 'Hdfs', value: DBType.HDFS },
        { label: 'Hbase', value: DBType.HBASE },
      ],
    },
    colProps: { lg: 24, md: 24 },
  },
  {
    field: 'dbIp',
    label: t('exchange.database.form.dbIp'),
    component: 'Input',
    required: true,
  },
  {
    field: 'dbPort',
    label: t('exchange.database.form.dbPort'),
    component: 'InputNumber',
    componentProps: { class: '!w-full' },
    required: true,
  },
  {
    field: 'dbUser',
    label: t('exchange.database.form.dbUser'),
    component: 'Input',
    required: true,
  },
  {
    field: 'dbPass',
    label: t('exchange.database.form.dbPass'),
    component: 'InputPassword',
    required: true,
  },
  {
    field: 'oracleType',
    label: t('exchange.database.form.oracleType'),
    component: 'RadioButtonGroup',
    ifShow: ({ values }) => values.dbType == DBType.ORACLE,
    componentProps: {
      options: [
        { label: 'ServiceName', value: OracleType.SERVICENAME },
        { label: 'SID', value: OracleType.SID },
      ],
    },
  },
]
