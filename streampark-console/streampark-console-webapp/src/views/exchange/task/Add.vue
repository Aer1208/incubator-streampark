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
import {SubTask, Task} from "/@/views/exchange/task/task.types";
import {useI18n} from "/@/hooks/web/useI18n";
import {Form, FormItem, Select, Input, Table, Card, Button, Drawer, Tag} from 'ant-design-vue';
import {getDataBaseByType, getDBTypes, getSourceDbTypes} from "/@/api/exchange/database/database";

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
    // 支持采集的源数据库类型
    let sourceDbTypes:any = ref<any[]>([]);
    // 所有数据库类型
    let dbTypes:any = ref<any[]>([]);
    // 选择源数据库后，需要展示的数据库列表
    let databases:any = ref([]);
    // 选择目标数据库后，需要展示的数据库列表
    let allDatabase:any = ref([]);
    // 新增天机子任务窗口
    let visible = ref<boolean>(false);
    // 所有子任务列表
    let subTasks:any = ref([])

    // 任务模型
    const taskModel: UnwrapRef<Task> = reactive({
      taskId: 0,
      taskName: null,
      taskDesc: null,
      sourceType: 0,
      sourceId: 0,
      sourceParams: null,
      subTasks: [],
    })

    // 子任务模型
    const subTaskModel: UnwrapRef<SubTask> = reactive({
      subTaskId: 0,
      sourceTableName: null,
      sourceFields:[],
      sourceTypes: [],
      pkFields: null,
      targetType:null,
      targetId:0,
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
      databases.value = await getDataBaseByType(taskModel.sourceType);
    };

    async function changeDbType() {
      allDatabase.value = await getDataBaseByType(subTaskModel.targetType);
    }

    /**
     * TODO: 后端接口未开发
     * 提交新增列表
     */
    function onSubmit() {
      taskModel.subTasks = subTasks;
      console.log(taskModel);
      console.log(JSON.stringify(taskModel));
    };

    /**
     * 新增子任务
     */
    function addSubTask() {
      subTasks.value.push({
        sourceTableName: subTaskModel.sourceTableName,
        sourceFields:subTaskModel.sourceFields.split(","),
        sourceTypes: subTaskModel.sourceTypes.split(","),
        pkFields: subTaskModel.pkFields,
        targetType:subTaskModel.targetType,
        targetId:subTaskModel.targetId,
        targetTableName:subTaskModel.targetTableName,
        targetParams:subTaskModel.targetParams,
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
    }

    /**
     * 删除子任务
     * @param record
     */
    function handleDelete(record) {
      subTasks.value = subTasks.value.filter(item => record.key !== item.key);
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
      databases,
      allDatabase,
      onSubmit,
      visible,
      placement: 'right',
      addTask: addSubTask,
      selectSourceField,
      handleDelete,

      subTasks ,

      columns: [
        {
          title: '源表',
          dataIndex: 'sourceTableName',
          key: 'sourceTableName',
        },
        {
          title: '字段列表',
          dataIndex: 'sourceFields',
          key: 'sourceFields',
          customRender: (sourceFields) => sourceFields.value.join(","),
        },
        {
          title: '字段类型',
          dataIndex: 'sourceTypes',
          key: 'sourceTypes',
          customRender: (sourceTypes) => sourceTypes.value.join(","),
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
          title:'目标数据库',
          dataIndex:'targetId',
          key:'targetId',
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
      ],
      // TODO： 动态获取字段列表
      fields: [
        {
          name: 'id',
          type:'bigint',
        },
        {
          name: 'name',
          type:'string',
        },
        {
          name: 'age',
          type:'integer',
        },
        {
          name: 'sex',
          type:'string',
        },
      ],
      // TODO: 动态获取数据源的表
      sourceTables:['s_table_1', 's_table_2'],
      // TODO: 动态获取目标数据库的表
      targetTables:['target_table_1', 'target_table_2']
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
      <a-form-item label="源数据库类型" >
        <a-select v-model:value = "taskModel.sourceType" placeholder="请选择源数据的类型"
                  @focus="getSourceType" @change="changeSourceType">
          <a-select-option v-for="(item, index) in sourceDbTypes" :value="item.sourceType" :key="index">{{item.sourceName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="源数据库">
        <a-select v-model:value = "taskModel.sourceId" placeholder="请选择源数据库">
          <a-select-option v-for="(item, index) in databases" :value="item.id" :key="index">{{item.dbName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="数据源参数">
        <a-textarea v-model:value="taskModel.sourceParams" aria-rowspan="3"/>
      </a-form-item>
      <a-form-item label="子任务">
        <a-button type="primary" @click="visible=true" >新增子任务</a-button>
        <a-table :dataSource="subTasks" :columns="columns"  >
          <template  #operation="{ record }">
            <a-button type="link" @click="handleDelete(record)">
              Delete
            </a-button>
          </template>
        </a-table>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 22, offset: 20 }">
        <a-button type="primary" @click="onSubmit">保存</a-button>
        <a-button style="margin-left: 10px">取消</a-button>
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
        <a-select v-model:value = "subTaskModel.sourceTableName" placeholder="请选择源表">
          <a-select-option v-for="(table) in sourceTables" :value = "table">{{table}}</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="选择字段">
        <a-select placeholder="请选择字段列表"
                  @change="selectSourceField"
                  mode="multiple">
          <a-select-option v-for="(item, index) in fields" :value="item.name+','+item.type" :key="index">{{item.name}}({{item.type}})</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="字段类型">
        <a-input v-model:value = "subTaskModel.sourceTypes" />
      </a-form-item>
      <a-form-item label="主键列表">
        <a-input v-model:value = "subTaskModel.pkFields" />
      </a-form-item>
      <a-form-item label="目标类型">
        <a-select v-model:value = "subTaskModel.targetType" @focus="getDbType" @change="changeDbType">
          <a-select-option v-for="(item, index) in dbTypes" :value="item.sourceType" :key="index">{{item.sourceName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="目标数据库">
        <a-select v-model:value = "subTaskModel.targetId" placeholder="请选择源数据库">
          <a-select-option v-for="(item, index) in allDatabase" :value="item.id" :key="index">{{item.dbName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="选择目标表">
        <a-select v-model:value = "subTaskModel.targetTableName" placeholder="请选择目标表">
          <a-select-option v-for="(table) in targetTables" :value = "table">{{table}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="目标参数">
        <a-textarea v-model:value = "subTaskModel.targetParams" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="addTask">添加</a-button>
      </a-form-item>
    </a-form>
  </a-drawer>
</template>
<style lang="less">
</style>
