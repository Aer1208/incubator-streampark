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
    <DatabaseDrawer
      :okText="t('common.submitText')"
      @register="registerDrawer"
      @success="handleSuccess"
    />
  </div>
</template>
<script lang="ts">
import { defineComponent, nextTick } from 'vue';

import {BasicTable, TableAction, useTable} from '/@/components/Table';

import { useDrawer } from '/@/components/Drawer';
import DatabaseDrawer from './DatabaseDrawer.vue';

import { columns, searchFormSchema } from './database.data';
import { useMessage } from '/@/hooks/web/useMessage';
import { useI18n } from '/@/hooks/web/useI18n';
import { isArray } from '/@/utils/is';
import Icon from "/@/components/Icon";
import {deleteMenu} from "/@/api/system/menu";
import {deleteDatabase, getDatabaseList} from "/@/api/exchange/database/database";

export default defineComponent({
  name: 'MenuManagement',
  components: {Icon, TableAction, BasicTable, DatabaseDrawer },
  setup() {
    const [registerDrawer, { openDrawer }] = useDrawer();
    const { createMessage } = useMessage();
    const { t } = useI18n();
    const [registerTable, { reload, expandAll }] = useTable({
      title: t('system.menu.table.title'),
      api: getDatabaseList,
      afterFetch(result) {
        try {
          handleMenuName(result);
        } catch (error) {
          console.error(error);
        }
        return result;
      },
      columns,
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
        width: 100,
        title: 'Operation',
        dataIndex: 'action',
      },
    });
    function handleMenuName(menus: Recordable[]) {
      if (isArray(menus)) {
        menus.forEach((menu: Recordable) => {
          if (menu.children && menu.children.length > 0) handleMenuName(menu.children);
          if (menu.display && menu.type == '0' && /^\w+\.\w+$/.test(menu.title)) {
            menu.text = t(`menu.${menu.text}`);
          }
        });
      }
    }
    function handleCreate() {
      openDrawer(true, { isUpdate: false });
    }

    function handleEdit(record: Recordable) {
      openDrawer(true, {
        record,
        isUpdate: true,
      });
    }

    function handleSuccess() {
      createMessage.success('success');
      reload();
    }

    function onFetchSuccess() {
      // Demo expands all table items by default
      nextTick(expandAll);
    }

    async function handleDelete(record: Recordable) {
      const hide = createMessage.loading('deleteing');
      try {
        await deleteDatabase(record)
        createMessage.success(t('system.menu.table.deleteSuccess', [record.title]));
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
      registerDrawer,
      handleCreate,
      handleEdit,
      handleDelete,
      handleSuccess,
      onFetchSuccess,
    };
  },
});
</script>
