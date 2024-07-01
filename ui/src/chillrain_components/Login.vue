<script setup lang="ts">
import {getCurrentInstance, reactive, ref} from "vue";
import service from "~/utils/request";

import type {FormRules} from "element-plus";
import {md5} from "js-md5";
interface RuleForm {
  email: string
  password: string
}

const formSize = ref('default')
const userForm = reactive<RuleForm>({
  email: "", password: ""
})
const loginRules = reactive<FormRules<RuleForm>>({
  email: [{ required: true, message: 'Please input Email', trigger: 'blur' }],
  password: [{ required: true, message: 'Please input Password', trigger: 'blur' }]
})
//弹出框设置
const showDialog = ref(true)
// const apis = {
//   loginApi: "/user/login"
// }
const apis = {
  loginApi: "http://localhost:8080/user/login"
}
//登录按钮
const login = reactive({
  title: "Login",
  show: true,
  button: [
    {
      type: "primary",
      text: "login",
      click: () => {
        loginDo()
      }
    }
  ]
});
//登录
// const loginDo = async () =>
// {
//   console.log("Sending user info...")
//   const response = await service.post(apis.loginApi,null,{
//     params:{
//       email: userForm.email,
//       password: md5(userForm.password),
//     }
//   });
//
//   // const response = await axios.post(apis.loginApi,null,{
//   //   params:{
//   //     email: userForm.email,
//   //     password: md5(userForm.password),
//   //   }
//   // });
//   if(response.status == 200){
//     console.log(response.status)
//     localStorage.setItem("user", userForm.email)
//     window.location.reload();
//     showDialog.value = false
//   }
//
// }
const loginDo = async () =>
{
  console.log("Sending user info...")
  service.post(apis.loginApi, null, {
    params:{
      email: userForm.email,
      password: md5(userForm.password),
    }
  }).then(response => {
        // 处理响应数据
        console.log('请求成功', response.data);
        // localStorage.setItem("user", userForm.email)
        window.location.reload();
        showDialog.value = false
      })
      .catch(error => {
        // 处理错误信息
        console.error('请求失败', error);
      });
}
</script>
<template>
  <Dialog title="Login" :buttons="login.button" @close="showDialog = false" :show="showDialog">
    <el-form
        ref="ruleFormRef"
        :model="userForm"
        :rules="loginRules"
        label-width="120px"
        class="demo-ruleForm"
        :size="formSize"
        status-icon>
      <el-form-item label="Email" prop="email">
        <el-input v-model="userForm.email" />
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input v-model="userForm.password" type="password"/>
      </el-form-item>
    </el-form>
  </Dialog>
</template>
<style scoped>

</style>