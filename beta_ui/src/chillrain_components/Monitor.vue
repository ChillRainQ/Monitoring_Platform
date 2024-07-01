<script setup lang="ts">
import Camera from "../chillrain_components/Camera.vue";
import service from "../utils/request";
import {getCurrentInstance, reactive, ref} from "vue";
const apis = {
  camera: "/api/connectApi/cameraOpen"
}
const linkCamera = async () =>{
  service.get(apis.camera).then(response => {
  })
}
const src = ref("http://192.168.137.38:8888/push/index.m3u8")
</script>

<template>
  <div class="card-container" style="margin-top: -15px">
    <el-card class="card">
      <!-- 第一个卡片的内容 检测链接按钮-->
      <el-button type="primary" @click="linkCamera">Camera Test</el-button>
    </el-card>
    <el-card class="card">
  <!--      <Camera link="http://127.0.0.1:8888/push/index.m3u8" width="500px"></Camera>-->
<!--      <Camera :src="src"></Camera>-->
       第二个卡片的内容
    </el-card>
    <el-card class="card">
       第三个卡片的内容
    </el-card>
    <el-card class="card">
       第四个卡片的内容
    </el-card>
    <el-card class="card">
       第五个卡片的内容
    </el-card>
    <el-card class="card">
       第六个卡片的内容
    </el-card>
  </div>
</template>

<style scoped>
.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  height: 80vh; /* 设置容器高度为屏幕高度的一半 */
}

.card {
  width: calc(33.33% - 10px); /* 每行显示三个卡片，计算宽度 */
  margin-bottom: 20px; /* 设置卡片之间的垂直间距 */
  height: 50%; /* 设置卡片高度为容器高度的一半 */
}
</style>
