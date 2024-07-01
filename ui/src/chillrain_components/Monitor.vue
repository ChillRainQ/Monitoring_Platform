<script setup lang="ts">
import Camera from "~/chillrain_components/Camera.vue";
import service from "~/utils/request";
import {ref} from "vue";

const ca = ref(false)
const camera_show = ref(false)
const apis = {
  camera: "http://localhost:8080/api/connectApi/cameraOpen/open",
  fire: "http://localhost:8080/api/connectApi/fire/open",
  phone: "http://localhost:8080/api/connectApi/phone/open",
  sleep: "http://localhost:8080/api/connectApi/sleep/open",
  smock: "http://localhost:8080/api/connectApi/smock/open",
  smock_close: "http://localhost:8080/api/connectApi/smock/close",
  fire_close: "http://localhost:8080/api/connectApi/fire/close",
  sleep_close: "http://localhost:8080/api/connectApi/sleep/close",
  phone_close: "http://localhost:8080/api/connectApi/phone/close",
  camera_close: "http://localhost:8080/api/connectApi/camera/close",
  get_site: "http://localhost:8080/system/getSite"
}
const linkCamera = async () =>{
  service.get(apis.camera).then(response => {
  })
  setTimeout(() =>{
    camera_show.value = true
  }, 10000);
  // window.location.reload();
}
const linkFire = async () =>{
  service.get(apis.fire).then(response => {
  })
  model_src.value = fire.value
  setTimeout(() => {
    ca.value = true
  },15000);
  // window.location.reload();
}
const linkPhone = async () =>{
  service.get(apis.phone).then(response => {
  })
  model_src.value = phone.value
  setTimeout(() => {
    ca.value = true
  },15000);
  // window.location.reload();
}
const linkSmock = async () =>{
  service.get(apis.smock).then(response => {
  })
  model_src.value = smock.value
  setTimeout(() => {
    ca.value = true
  },15000);
  // window.location.reload();
}

const CameraClose = async () =>{
  service.get(apis.camera_close).then(response => {
  })
  camera_show.value = false
  // window.location.reload();
}
const FireClose = async () =>{
  service.get(apis.fire_close).then(response => {
  })
  model_src.value = ""
  ca.value = false
  // window.location.reload();
}
const SmockClose = async () =>{
  service.get(apis.smock_close).then(response => {
  })
  model_src.value = ""
  ca.value = false
  // window.location.reload();
}
const PhoneClose = async () =>{
  service.get(apis.phone_close).then(response => {
  })
  model_src.value = ""
  ca.value = false
  // window.location.reload();
}

const camera_src = ref("http://127.0.0.1:8888/push/index.m3u8")
const model_src = ref()
const smock = ref("http://127.0.0.1:8888/push/smock/index.m3u8")
const phone = ref("http://127.0.0.1:8888/push/phone/index.m3u8")
const fire = ref("http://127.0.0.1:8888/push/fire/index.m3u8")
const sleep = ref("http://127.0.0.1:8888/push/sleep/index.m3u8")

const getSite = async() =>{
  service.post(apis.get_site).then(response =>{
    const data = response.data;
    camera_src.value = data.src;
    smock.value = data.smock;
    phone.value = data.phone;
    fire.value = data.fire;
    sleep.value = data.sleep;
  })
}
</script>

<template>
  <div id="app">
    <div class="container">
      <div class="left-card">
        <el-card class="sub-card">
          <div class="el-menu-flex">
            <div style="width: 50%; display: inline;" >
              <el-menu class="el-menu-flex" style="border: 0">
                <div class="menu-section">
                  <el-menu-item index="1" @click="linkCamera">Camera Link</el-menu-item>
                  <el-menu-item index="2" @click="linkFire">Fire Link</el-menu-item>
                  <el-menu-item index="3" @click="linkPhone">Phone Link</el-menu-item>
                  <el-menu-item index="4" @click="linkSmock">Smock Link</el-menu-item>
                </div>
              </el-menu>
            </div>
            <div style="width: 50%; display: inline;">
              <el-menu class="el-menu-flex">
                <div class="menu-section right">
                  <el-menu-item index="1" @click="CameraClose">Camera Close</el-menu-item>
                  <el-menu-item index="2" @click="FireClose">Fire Close</el-menu-item>
                  <el-menu-item index="3" @click="PhoneClose">Phone Close</el-menu-item>
                  <el-menu-item index="4" @click="SmockClose">Smock Close</el-menu-item>
                </div>
              </el-menu>
            </div>
          </div>
        </el-card>
        <el-card class="sub-card">
          <div slot="header" class="clearfix">
            <Camera v-if="camera_show" :src="camera_src"></Camera>
          </div>
        </el-card>
      </div>
      <el-card class="right-card">
        <div slot="header" class="clearfix">
          <Camera v-if="ca" :src="model_src"></Camera>
        </div>
      </el-card>
    </div>
  </div>
<!--  <div id="app">-->
<!--    <div class="container">-->
<!--      <el-card class="left-card">-->
<!--&lt;!&ndash;        <div slot="header" class="clearfix">&ndash;&gt;-->
<!--&lt;!&ndash;&lt;!&ndash;          <span>左边的卡片</span>&ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
<!--&lt;!&ndash;        <div>&ndash;&gt;-->
<!--&lt;!&ndash;          这是左边的卡片内容。&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
<!--      </el-card>-->
<!--      <el-card class="right-card">-->
<!--&lt;!&ndash;        <div slot="header" class="clearfix">&ndash;&gt;-->
<!--&lt;!&ndash;          <span>右边的卡片</span>&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
<!--&lt;!&ndash;        <div>&ndash;&gt;-->
<!--&lt;!&ndash;          这是右边的卡片内容。&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
<!--      </el-card>-->
<!--    </div>-->
<!--  </div>-->

  <!--  <div class="card-container" style="margin-top: -15px">-->
<!--    <el-card >-->

<!--    </el-card>-->
<!--    <el-card>-->

<!--    </el-card>-->
<!--    <el-card class="card">-->
<!--      <div class="el-menu-flex">-->
<!--        <div style="width: 50%; display: inline;" >-->
<!--          <el-menu class="el-menu-flex" style="border: 0">-->
<!--            <div class="menu-section">-->
<!--              <el-menu-item index="1" @click="linkCamera">Camera Link</el-menu-item>-->
<!--              <el-menu-item index="2" @click="linkFire">Fire Link</el-menu-item>-->
<!--              <el-menu-item index="3" @click="linkPhone">Phone Link</el-menu-item>-->
<!--              <el-menu-item index="4" @click="linkSmock">Smock Link</el-menu-item>-->
<!--            </div>-->
<!--          </el-menu>-->
<!--        </div>-->
<!--        <div style="width: 50%; display: inline;">-->
<!--          <el-menu class="el-menu-flex">-->
<!--            <div class="menu-section right">-->
<!--              <el-menu-item index="1" @click="CameraClose">Camera Close</el-menu-item>-->
<!--              <el-menu-item index="2" @click="FireClose">Fire Close</el-menu-item>-->
<!--              <el-menu-item index="3" @click="PhoneClose">Phone Close</el-menu-item>-->
<!--              <el-menu-item index="4" @click="SmockClose">Smock Close</el-menu-item>-->
<!--            </div>-->
<!--          </el-menu>-->
<!--        </div>-->
<!--      </div>-->
<!--    </el-card>-->
<!--    <el-card class="card">-->
<!--      <Camera :src="smock"></Camera>-->
<!--    </el-card>-->
<!--    <el-card class="card">-->
<!--      <Camera :src="phone"></Camera>-->
<!--    </el-card>-->
<!--    <el-card class="card">-->
<!--      <Camera :src="src"></Camera>-->
<!--    </el-card>-->
<!--    <el-card class="card">-->
<!--      <Camera :src="fire"></Camera>-->
<!--    </el-card>-->
<!--    <el-card class="card">-->
<!--      <Camera :src="sleep"></Camera>-->
<!--    </el-card>-->
<!--  </div>-->

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
  margin-bottom: 1px; /* 设置卡片之间的垂直间距 */
  height: 55%; /* 设置卡片高度为容器高度的一半 */
}
.no-margin-top {
  margin-top: 0;
}
.el-menu-flex {
  display: flex;
  justify-content: space-between;
}
.menu-section.right {
  margin-left: auto;
}
body, html {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

#app {
  width: 100%;
  height: 100%;
}

.container {
  display: flex;
  width: 100%;
  height: 100%;
}

.left-card {
  display: flex;
  flex-direction: column;
  flex: 1;
  margin: 10px;
}

.sub-card {
  flex: 1;
}

.right-card {
  flex: 2;
  margin: 10px;
}


</style>
