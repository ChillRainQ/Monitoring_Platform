<script lang="ts" setup>
import { toggleDark } from "../../composables";
import { onMounted, ref } from "vue";
import { useRouter } from 'vue-router'; // 导入路由相关模块
import Register from "../../chillrain_components/Register.vue";
import Login from "../../chillrain_components/Login.vue";
// import axios from "axios";
import service from "../../utils/request";
import { useToggle } from '@vueuse/shared'
import { isDark } from '~/composables'
const apis = {
  logout: "/user/logout",
  getIp: "/user/getIp",
  getNowUser: "/user/getNowUser"
}
const user = ref("login")
const showDialogRegister = ref(false)
const showDialogLogin = ref(false)

// 获取路由实例
const router = useRouter();

onMounted(() => {
  service.get(apis.getNowUser).then(response => {
    user.value = response.data.email
  })
  user.value = String(sessionStorage.getItem("LOG_USER-"))
  // console.log(user.value)
  if (user.value == "null") {
    user.value = "login"
  }
})

// const getIp = async () => {
//   console.log("Sending user info...")
//   await axios.post(apis.getIp, null, {});
// }
const logout = async () => {
  service.post(apis.logout, null, {
  }).then(response => {
    // 处理响应数据
    // localStorage.removeItem("user")
    window.location.reload();
  }).catch(error => {
        // 处理错误信息
        console.error('请求失败', error);
  });
}


// 点击 "Monitor Platform" 后的处理函数，用于将用户重定向到指定页面
const redirectToMonitorPlatform = () => {
  router.push('/'); // 跳转到根路由
}
const toggleDark = useToggle(isDark)
</script>

<template>
  <div class="el-menu-flex">
    <div style="width: 50%; display: inline;" >
      <el-menu class="el-menu-flex" style="border: 0">
        <div class="menu-section">
          <el-menu-item index="1" @click="redirectToMonitorPlatform">Monitor Platform</el-menu-item>
          <el-menu-item h="full" @click="toggleDark()">
            <el-icon>
              <Moon v-if="isDark"/>
              <Sunny v-if="!isDark"/>
            </el-icon>
          </el-menu-item>
        </div>
      </el-menu>
    </div>
    <div style="width: 50%; display: inline;">
      <el-menu class="el-menu-flex">
        <div class="menu-section right">
          <el-menu-item index="1" @click="showDialogLogin = true">{{ user }}</el-menu-item>
          <el-menu-item index="2" @click="showDialogRegister = true">Register</el-menu-item>
          <el-menu-item index="3" @click="logout">Logout</el-menu-item>
        </div>
      </el-menu>
    </div>
  </div>
  <Register :show="showDialogRegister" @close="showDialogRegister = false"></Register>
  <Login :show="showDialogLogin" @close="showDialogLogin = false"></Login>
  <!--  <Dialog :show="showDialog"-->
  <!--          clickToClose="false"-->
  <!--          :buttons="buttons"-->
  <!--          @close="showDialog = false">测试内容</Dialog>-->
</template>
<style scoped>
.button{

}
.el-menu-flex {
  display: flex;
  justify-content: space-between;
}

.menu-section {
  display: flex;
}

.menu-section.right {
  margin-left: auto;
}
</style>
