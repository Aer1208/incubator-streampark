<template>
<h1>数据源管理页面</h1>
</template>

<script>
import {defHttp} from "/@/utils/http/axios";
defHttp.get({
  url:'/database/list'
})
export default {
  name: "View"
}
</script>

<style scoped>

</style>
