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
<template>
  <div>
    <BasicTable @register="registerTable" @fetch-success="onFetchSuccess" >
      <template #toolbar>
        <a-button type="primary" @click="handleCreate" v-auth="'menu:add'">
          <Icon icon="ant-design:plus-outlined" />
          {{ t('common.add') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: { title: t('exchange.task.operation.start') },
                auth: 'task:start',
                icon: 'ant-design:play-circle-outlined',
                onClick: handleStart.bind(null, record),
              },
              {
                icon: 'clarity:note-edit-line',
                auth: 'menu:update',
                tooltip: t('system.menu.modifyMenu'),
                onClick: handleEdit.bind(null, record),
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: t('system.menu.deleteMenu'),
                auth: 'member:delete',
                popConfirm: {
                  title: t('exchange.database.table.deletePopConfirm'),
                  confirm: handleDelete.bind(null, record),
                },
              },
            ]"
          />
        </template>
      </template>
    </BasicTable>
  </div>
</template>
<script lang="ts">
import { defineComponent, nextTick } from 'vue';

import {BasicTable, TableAction, useTable} from '/@/components/Table';
import {taskColumns, searchFormSchema, Task} from './task.types';
import { useMessage } from '/@/hooks/web/useMessage';
import { useI18n } from '/@/hooks/web/useI18n';
import Icon from "/@/components/Icon";
import {deleteTask, getTasks} from "/@/api/exchange/task/task";
import {useRouter} from "vue-router";

export default defineComponent({
  name: 'TaskManagement',
  components: {Icon, TableAction, BasicTable },
  setup() {
    const { createMessage } = useMessage();
    const { t } = useI18n();
    const router = useRouter();
    const [registerTable, { reload, expandAll }] = useTable({
      title: t('exchange.task.table.title'),
      api: getTasks,
      columns: taskColumns,
      formConfig: {
        baseColProps: { style: { paddingRight: '30px' } },
        schemas: searchFormSchema,
        fieldMapToTime: [['createTime', ['createTimeFrom', 'createTimeTo'], 'YYYY-MM-DD']],
      },
      rowKey: "id",
      isTreeTable: false,
      pagination: true,
      striped: false,
      useSearchForm: true,
      showTableSetting: true,
      bordered: true,
      showIndexColumn: true,
      canResize: false,
      actionColumn: {
        width: 130,
        title: 'Operation',
        dataIndex: 'action',
      },
    });
    function handleCreate() {
      router.push({path: '/exchange/task/add'})
    }

    function handleEdit(record: Task) {
      console.log(record)
    }

    function handleSuccess() {
      createMessage.success('success');
      reload();
    }

    function handleStart(task: Task) {
      console.log(task)
    }

    function onFetchSuccess() {
      // Demo expands all table items by default
      nextTick(expandAll);
    }

    async function handleDelete(record: Task) {
      const hide = createMessage.loading('deleteing');
      try {
        await deleteTask(record)
        createMessage.success(t('system.menu.table.deleteSuccess', [record.taskName]));
        reload();
      } catch (error) {
        console.error('menu delete fail:', error);
      } finally {
        hide();
      }

    }

    return {
      t,
      registerTable,
      handleCreate,
      handleEdit,
      handleDelete,
      handleSuccess,
      onFetchSuccess,
      handleStart,
    };
  },
});
</script>
