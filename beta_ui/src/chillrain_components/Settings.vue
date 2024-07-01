<script setup lang="ts">
import { onMounted, ref } from 'vue';

import service from "../utils/request";

const apis = {
  getSettings: "/system/readSetting",
  saveSettings: "/system/updateSetting",
};
const saveSettings = async () => {
  for (const key in systemSettingsForm.value) {
    if (!systemSettingsForm.value[key]) {
      alert(`Please fill in ${key}`);
      return; // 如果有空的输入框，不执行保存操作
    }
  }
  console.log("Sending user info...")
  service.post(apis.saveSettings, null, {
    params:{
      message: systemSettingsForm.value.message,
      time: systemSettingsForm.value.time,
      email: systemSettingsForm.value.email,
      title: systemSettingsForm.value.title,
      camera_site: systemSettingsForm.value.camera_site,
      camera_open: systemSettingsForm.value.camera_open,
      stream_site: systemSettingsForm.value.stream_site,
      model_site: systemSettingsForm.value.model_site,
      smock: systemSettingsForm.value.smock,
      phone: systemSettingsForm.value.phone,
      fire: systemSettingsForm.value.fire,
      sleep: systemSettingsForm.value.sleep
    }
  })
}

//   const response = await axios.post(apis.saveSettings,null,{
//     params:{
//       message: systemSettingsForm.value.message,
//       time: systemSettingsForm.value.time,
//       email: systemSettingsForm.value.email,
//       title: systemSettingsForm.value.title,
//       camera_site: systemSettingsForm.value.camera_site,
//       camera_open: systemSettingsForm.value.camera_open,
//       stream_site: systemSettingsForm.value.stream_site,
//       model_site: systemSettingsForm.value.model_site,
//       smock: systemSettingsForm.value.smock,
//       phone: systemSettingsForm.value.phone,
//       fire: systemSettingsForm.value.fire,
//       sleep: systemSettingsForm.value.sleep
//     }
//   });
//   if(response.status == 200){
//     console.log(response.data)
//     alert(`done!`);
//   }
// }
const systemSettingsForm = ref({
  message: "",
  time: "",
  email: "",
  title: "",
  camera_site: "",
  camera_open: "",
  stream_site: "",
  model_site: "",
  smock: "",
  phone: "",
  fire: "",
  sleep: ""
});

const getSystemSettings = async () => {
  try {
    service.get(apis.getSettings).then(response => {
      const data = response.data;
      console.log(data + "-data")
      systemSettingsForm.value.message = data.EMAILS.email_content?.line ?? "";
      systemSettingsForm.value.time = data.EMAILS.email_time?.line ?? "";
      systemSettingsForm.value.email = data.EMAILS.system_email?.line ?? "";
      systemSettingsForm.value.title = data.EMAILS.email_title?.line ?? "";
      systemSettingsForm.value.camera_site = data.APIS.cameraSite?.line ?? "";
      systemSettingsForm.value.camera_open = data.APIS.cameraOpen?.line ?? "";
      systemSettingsForm.value.stream_site = data.APIS.streamSite?.line ?? "";
      systemSettingsForm.value.model_site = data.APIS.modelSite?.line ?? "";
      systemSettingsForm.value.smock = data.APIS.smock?.line ?? "";
      systemSettingsForm.value.phone = data.APIS.phone?.line ?? "";
      systemSettingsForm.value.fire = data.APIS.fire?.line ?? "";
      systemSettingsForm.value.sleep = data.APIS.sleep?.line ?? "";
    })
    // const response = await axios.get(apis.getSettings);
    // if (response.status === 200 && response.data.status === "SUCCESS") {
    //   const data = response.data.data;
    //   console.log(data)
    //   systemSettingsForm.value.message = data.EMAILS.email_content?.line ?? "";
    //   systemSettingsForm.value.time = data.EMAILS.email_time?.line ?? "";
    //   systemSettingsForm.value.email = data.EMAILS.system_email?.line ?? "";
    //   systemSettingsForm.value.title = data.EMAILS.email_title?.line ?? "";
    //   systemSettingsForm.value.camera_site = data.APIS.cameraSite?.line ?? "";
    //   systemSettingsForm.value.camera_open = data.APIS.cameraOpen?.line ?? "";
    //   systemSettingsForm.value.stream_site = data.APIS.streamSite?.line ?? "";
    //   systemSettingsForm.value.model_site = data.APIS.modelSite?.line ?? "";
    //   systemSettingsForm.value.smock = data.APIS.smock?.line ?? "";
    //   systemSettingsForm.value.phone = data.APIS.phone?.line ?? "";
    //   systemSettingsForm.value.fire = data.APIS.fire?.line ?? "";
    //   systemSettingsForm.value.sleep = data.APIS.sleep?.line ?? "";
    // }
  } catch (error) {
    console.error('Failed to fetch system settings:', error);
  }
};

onMounted(() => {
  getSystemSettings();
});
</script>

<template>
  <div class="card-container">
    <el-card class="card" style="margin-top: -15px">
      <el-form :model="systemSettingsForm" label-width="120px">
        <el-form-item label="系统注册信息" prop="message">
          <el-input v-model="systemSettingsForm.message" />
        </el-form-item>
        <el-form-item label="验证码最大时间" prop="time">
          <el-input v-model="systemSettingsForm.time" />
        </el-form-item>
        <el-form-item label="系统邮箱 " prop="email">
          <el-input v-model="systemSettingsForm.email" />
        </el-form-item>
        <el-form-item label="系统邮件标题" prop="title">
          <el-input v-model="systemSettingsForm.title" />
        </el-form-item>
        <!-- 其他表单项省略 -->
      </el-form>
    </el-card>

    <el-card class="card" style="margin-top: -15px">
      <el-form :model="systemSettingsForm" label-width="120px">
        <el-form-item label="摄像头地址" prop="camera_site">
          <el-input v-model="systemSettingsForm.camera_site" />
        </el-form-item>
        <el-form-item label="打开摄像头" prop="camera_open">
          <el-input v-model="systemSettingsForm.camera_open" />
        </el-form-item>
        <el-form-item label="流服务器地址" prop="stream_site">
          <el-input v-model="systemSettingsForm.stream_site" />
        </el-form-item>
        <el-form-item label="识别模型地址" prop="model_site">
          <el-input v-model="systemSettingsForm.model_site" />
        </el-form-item>
        <el-form-item label="吸烟行为" prop="smock">
          <el-input v-model="systemSettingsForm.smock" />
        </el-form-item>
        <el-form-item label="睡觉行为" prop="sleep">
          <el-input v-model="systemSettingsForm.sleep" />
        </el-form-item>
        <el-form-item label="明火检测" prop="fire">
          <el-input v-model="systemSettingsForm.fire" />
        </el-form-item>
        <el-form-item label="玩手机行为" prop="phone">
          <el-input v-model="systemSettingsForm.phone" />
        </el-form-item>
      </el-form>
    </el-card>
  </div>
<!--  <el-card class="card" style="margin-top: 20px">-->
<!--    <el-button type="primary" @click="saveSettings">Save</el-button>-->
<!--  </el-card>-->
  <div class="button-container" style="display: flex; justify-content: flex-end; margin-top: 5px;">
    <el-card class="card" style="margin-left: auto;">
      <el-button type="primary" @click="saveSettings">Save</el-button>
    </el-card>
  </div>
</template>

<style scoped>
.card-container {
  display: flex;
}

.card {
  flex: 1;
  margin-right: 20px; /* 为了增加间距 */
}
</style>

