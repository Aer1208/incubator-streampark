<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<script lang="ts">

import {defineComponent, reactive, ref, UnwrapRef} from "vue";
import {SubTask, Task, columns} from "/@/views/exchange/task/task.types";
import {useI18n} from "/@/hooks/web/useI18n";
import {useMessage} from "/@/hooks/web/useMessage";
import {Form, FormItem, Select, Input, Table, Card, Button, Drawer, Tag} from 'ant-design-vue';
import {
  getColumns,
  getDataBaseByType,
  getDatabases,
  getDBTypes,
  getSourceDbTypes, getTables
} from "/@/api/exchange/database/database";
import {addTask} from "/@/api/exchange/task/task";
import {useRouter} from "vue-router";

export default defineComponent({
  components:{
    'a-form': Form,
    'a-form-item': FormItem,
    'a-select': Select,
    'a-select-option': Select.Option,
    'a-input': Input,
    'a-table': Table,
    'a-card': Card,
    'a-button': Button,
    'a-drawer': Drawer,
    'a-tag':Tag,
  },
  setup() {
    const { t } = useI18n();
    const router = useRouter();
    const { createMessage } = useMessage();
    // 支持采集的源数据库类型
    let sourceDbTypes:any = ref<any[]>([]);
    // 所有数据库类型
    let dbTypes:any = ref<any[]>([]);
    // 选择源数据库后，需要展示的源数据库列表
    let sourceDatabases:any = ref([]);
    // 选择目标数据库后，需要展示的数据库列表
    let allDatabase:any = ref([]);
    // 新增天机子任务窗口
    let visible = ref<boolean>(false);
    // 所有子任务列表
    let subTasks:any = ref([])

    // 选择数据库后，显示改数据库下的所有数据库列表
    let databases = ref([]);

    // 选择源数据库后，需要展示的表列表
    let sourceTables = ref([]);

    // 选择目标数据库后，需要展示的表列表
    let targetTables = ref([])

    // 选择表后，需要展示的字段列表
    let fields = ref([]);

    // 临时存放源字段类型
    let sourceFields = ref([]);

    // 目标数据库列表
    let targetDatabases = ref([]);

    // 任务模型
    const taskModel: UnwrapRef<Task> = reactive({
      taskId: 0,
      taskName: null,
      taskDesc: null,
      sourceType: 0,
      sourceId: 0,
      sourceParams: null,
      subTasks: [],
      dbName:null,
    })

    // 子任务模型
    const subTaskModel: UnwrapRef<SubTask> = reactive({
      subTaskId: 0,
      sourceTableName: null,
      sourceFields:[],
      sourceTypes: [],
      tmpSourceFields:[],
      pkFields: null,
      targetType:null,
      targetId:0,
      targetDbName:null,
      targetTableName:null,
      targetParams:null,
    })

    /**
     * 进入选择获取数据源焦点，调用后端API接口获取数据源类型列表
      */
    async function getSourceType() {
      sourceDbTypes.value = await getSourceDbTypes();
    };

    /**
     * 进入选择目标数据源焦点，调用后端API接口获取所有数据源类型列表
     */
    async function getDbType() {
      dbTypes.value = await  getDBTypes();
    }

    /**
     * 选择数据源后，调用接口获取数据库列表
     */
    async function changeSourceType() {
      taskModel.sourceId = null;
      sourceDatabases.value = await getDataBaseByType(taskModel.sourceType);
    };

    async function changeDbType() {
      allDatabase.value = await getDataBaseByType(subTaskModel.targetType);
    }

    /**
     * TODO: 后端接口未开发
     * 提交新增列表
     */
    async function onSubmit() {
      taskModel.subTasks = subTasks;
      const result:boolean = await addTask(taskModel)
      if (result) {
        createMessage.success("创建任务成功");
        router.push({path : '/exchange/task'});
      }
    };

    /**
     * 新增子任务
     */
    function addSubTask() {
      subTasks.value.push({
        sourceTableName: subTaskModel.sourceTableName,
        sourceFields:subTaskModel.sourceFields,
        sourceTypes: subTaskModel.sourceTypes,
        pkFields: subTaskModel.pkFields,
        targetType:subTaskModel.targetType,
        targetId:subTaskModel.targetId,
        targetTableName:subTaskModel.targetTableName,
        targetParams:subTaskModel.targetParams,
        targetDbName:subTaskModel.targetDbName,
        key: new Date().getMilliseconds(),
      });
      visible.value = false;
    }

    /**
     * 数据字段和数据类型选择，自动补充字段和类型
     * @param val
     */
    function selectSourceField(val) {
      subTaskModel.sourceFields = val.map(x=>x.split(",")[0]).join(",")
      subTaskModel.sourceTypes = val.map(x=>x.split(",")[1]).join(",")
      console.log(subTaskModel)
    }

    /**
     * 删除子任务
     * @param record
     */
    function handleDelete(record) {
      subTasks.value = subTasks.value.filter(item => record.key !== item.key);
    }

    /**
     * 选择源数据后，获取数据库列表
     */
    async function changeDatabases() {
      databases.value = await getDatabases(taskModel.sourceId)
    }

    /**
     * 选择数据库后，获取表列表
     */
    async function changeDatabase() {
      sourceTables.value = await getTables(taskModel.sourceId, taskModel.dbName);
    }

    /**
     * 选择数据库后
     */
    async function changeSourceTable() {
      if(taskModel.sourceType != 3 ) {
        const columns = await getColumns(taskModel.sourceId, subTaskModel.sourceTableName);
        //Kafka手动输入字段、类型、主键信息， Oracle、Mysql、Kafka自动带入
        fields.value = columns.columnAndTypes;
        subTaskModel.tmpSourceFields = columns.columnAndTypes;
        subTaskModel.sourceFields = columns.columns.join(",")
        subTaskModel.pkFields = columns.pkColumns.join(",");
        subTaskModel.sourceTypes = columns.types.join(",");
      }
    }

    async function changeTargetDatabases(){
      targetDatabases.value = await getDatabases(subTaskModel.targetId);
    }

    async function changeTargetDatabase() {
      targetTables.value = await getTables(subTaskModel.targetId, subTaskModel.targetDbName);
    }

    return {
      t,
      taskModel,
      subTaskModel,
      labelCol: { span: 4 },
      wrapperCol: { span: 18 },
      getSourceType,
      getDbType,
      changeSourceType,
      changeDbType,
      sourceDbTypes,
      dbTypes,
      sourceDatabases,
      allDatabase,
      onSubmit,
      visible,
      placement: 'right',
      addSubTask,
      selectSourceField,
      handleDelete,
      changeDatabases,
      changeDatabase,
      changeSourceTable,
      changeTargetDatabases,
      changeTargetDatabase,

      subTasks ,
      columns,
      databases,
      fields,
      sourceFields,
      sourceTables,
      targetDatabases,
      targetTables,
      router,
    }
  }
})
</script>

<template>
  <a-card title="新增采集任务">
    <a-form :model="taskModel" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="任务名称">
        <a-input v-model:value="taskModel.taskName" />
      </a-form-item>
      <a-form-item label="任务描述">
        <a-textarea v-model:value="taskModel.taskDesc"/>
      </a-form-item>
      <a-form-item label="源数据类型" >
        <a-select v-model:value = "taskModel.sourceType" placeholder="请选择源数据的类型"
                  @focus="getSourceType" @change="changeSourceType">
          <a-select-option v-for="(item, index) in sourceDbTypes" :value="item.sourceType" :key="index">{{item.sourceName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="源数据">
        <a-select v-model:value = "taskModel.sourceId" placeholder="请选择源数据" @change="changeDatabases">
          <a-select-option v-for="(item, index) in sourceDatabases" :value="item.id" :key="index">{{item.dbName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="选择数据库">
        <a-select v-model:value="taskModel.dbName" placeholder = "请选择数据库" @change="changeDatabase">
          <a-select-option v-for="(dbName, index) in databases" :value = "dbName" :key = "index">{{dbName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="数据源参数">
        <a-textarea v-model:value="taskModel.sourceParams" aria-rowspan="3"/>
      </a-form-item>
      <a-form-item label="子任务">
        <a-button type="primary" @click="visible=true" >新增子任务</a-button>
        <a-table :dataSource="subTasks" :columns="columns" >
          <template  #operation="{column, record }">
            <a-button type="link" @click="handleDelete(record)">
              Delete
            </a-button>
          </template>
        </a-table>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 22, offset: 20 }">
        <a-button type="primary" @click="onSubmit">保存</a-button>
        <a-button style="margin-left: 10px" @click="router.push({path:'/exchange/task'})">取消</a-button>
      </a-form-item>
    </a-form>
  </a-card>
  <a-drawer
    title="添加子任务"
    :placement="placement"
    :closable="false"
    :visible="visible"
    width="512"
    @close="visible = false"
  >
    <a-form :model = "subTaskModel" :label-col="{span: 6}">

      <a-form-item label="选择源表">
        <a-select v-model:value = "subTaskModel.sourceTableName" placeholder="请选择源表" @change="changeSourceTable">
          <a-select-option v-for="(table) in sourceTables" :value = "table">{{table}}</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="选择字段">
        <a-select v-model:value = "subTaskModel.tmpSourceFields"
                  placeholder="请选择字段列表"
                  @change="selectSourceField"
                  mode="multiple">
          <a-select-option v-for="(item, index) in fields" :value="item" :key="index">{{item}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="字段类型">
        <a-textarea v-model:value = "subTaskModel.sourceTypes" />
      </a-form-item>
      <a-form-item label="主键列表">
        <a-input v-model:value = "subTaskModel.pkFields" />
      </a-form-item>
      <a-form-item label="目标类型">
        <a-select v-model:value = "subTaskModel.targetType" @focus="getDbType" @change="changeDbType">
          <a-select-option v-for="(item, index) in dbTypes" :value="item.sourceType" :key="index">{{item.sourceName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="目标数据">
        <a-select v-model:value = "subTaskModel.targetId" placeholder="请选择源数据库" @change="changeTargetDatabases">
          <a-select-option v-for="(item, index) in allDatabase" :value="item.id" :key="index">{{item.dbName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="目标数据库">
        <a-select v-model:value="subTaskModel.targetDbName" placeholder="请选择目标数据库" @change="changeTargetDatabase">
          <a-select-option v-for="(item, index) in targetDatabases" :value="item" :key = "index" >{{item}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="目标表">
        <a-select v-model:value = "subTaskModel.targetTableName" placeholder="请选择目标表">
          <a-select-option v-for="(table) in targetTables" :value = "table">{{table}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="目标参数">
        <a-textarea v-model:value = "subTaskModel.targetParams" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="addSubTask">添加</a-button>
      </a-form-item>
    </a-form>
  </a-drawer>
</template>
<style lang="less">
</style>
