<script setup lang="ts">
import {onMounted} from "vue";
import service from "../utils/request";
onMounted(() => {
  link()
});
const apis = {
  linkDo: "/test/link"
}
const link = async () => {
  const response = await service.post(apis.linkDo)
  if (response.status === 200){
    console.log("server has been linked!")
  }
}
// const link = async () =>{
//
//   const response = await axios.get(apis.linkDo)
//   if (response.status === 200){
//     console.log("server has been linked!")
//   }
// }
</script>
<template>
      <BaseHeader></BaseHeader>
      <div class="flex main-container">
        <BaseSide></BaseSide>
        <div w="full" py="4">
          <router-view></router-view>
        </div>
      </div>
</template>
<style scoped>

</style>