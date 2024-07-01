<script lang="ts" setup>
import { ref } from "vue";
import {
  Location,
  Document,
  Menu as IconMenu,
  Setting,
} from "@element-plus/icons-vue";
import { useRouter } from 'vue-router'; // 导入路由相关模块

const isCollapse = ref(true);
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const showDialogSettings = ref(false);

// 获取路由实例
const router = useRouter();

// 点击SystemSetting菜单项后的处理函数，用于跳转到指定页面
const navigateToSettings = () => {
  router.push('/settings'); // 跳转到 /settings 路由
};
</script>

<template>
  <el-menu
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      @open="handleOpen"
      @close="handleClose">
    <el-menu-item index="1">
<!--      <el-icon><document /></el-icon>-->
      <el-icon><Monitor /></el-icon>
      <template #title>Monitor</template>
    </el-menu-item>
    <el-menu-item index="2" @click="navigateToSettings">
      <el-icon><setting /></el-icon>
      <template #title>SystemSetting</template>
    </el-menu-item>
  </el-menu>
  <!--  <Settings :show="showDialogSettings" @close="showDialogSettings = false"></Settings>-->
</template>

<style>
</style>
