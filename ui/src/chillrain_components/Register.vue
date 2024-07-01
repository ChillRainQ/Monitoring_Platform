<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

import {md5} from "js-md5";
import service from "~/utils/request";
//定义接口
interface RuleForm {
  email: string
  password: string
  code: string
}
//实现接口
const registerForm = reactive<RuleForm>({
  code: "", email: "", password: ""
})
//定义表单属性
const ruleFormRef = ref<FormInstance>()

const apis = {
  getEmailCode: "http://localhost:8080/checkCode/sendEmailCode",
  register: "http://localhost:8080/user/register"
}

const registerRules = reactive<FormRules<RuleForm>>({
  email: [{ required: true, message: 'Please input Email', trigger: 'blur' }],
  password: [{ required: true, message: 'Please input Password', trigger: 'blur' }],
  code: [{ required: true, message: 'Please input Email Code', trigger: 'blur' }],
})
//添加注册按钮
const register = reactive({
  title: "Register",
  show: true,
  button: [
    {
      type: "primary",
      text: "register",
      click: () => {
        registerDo()
      }
    }
  ]
});
//关闭框
const showDialog = ref(true)
//获取验证码按钮文字
const emailCodeButton = ref("Send Code")
//计时器标记
let timerSign = false
// 获取邮箱验证码
const getCode = () =>{
  console.log("Sending email code request...")
  setTimeout(() => {
    startCountdown()
  }, 1000)
}

//发送验证码
// const sendCode = async () => {
//     await axios.post(apis.getEmailCode, null, {
//       params: {email: registerForm.code}
//     });
// }
const sendCode = async () => {
  await service.post(apis.getEmailCode, null, {
    params: {email: registerForm.email}
  });
}
//启动计数器
const startCountdown = () => {
  //计数器未启动
  if(!timerSign){
    timerSign = true
    sendCode()
  }else{
    return;
  }
  let count = 60
  emailCodeButton.value = `${count}` // 更改按钮文本为倒计时
  const timer = setInterval(() => {
    if (count > 1) {
      count--
      emailCodeButton.value = `${count}`
    } else {
      //重置
      timerSign = false
      clearInterval(timer)
      emailCodeButton.value = "Send Code"
    }
  }, 1000)
}
//注册
const registerDo = async () => {
  console.log("Sending user info...")
  await service.post(apis.register,null,{
    params:{
      email: registerForm.email,
      password: md5(registerForm.password),
      emailCode: registerForm.code
    }
  }).then(response =>{
    showDialog.value = false
  })
}

</script>
<template>
  <Dialog title="Register" :buttons="register.button" @close="showDialog = false" :show="showDialog">
    <el-form
        ref="ruleFormRef"
        :model="registerForm"
        :rules="registerRules"
        label-width="120px"
        class="demo-ruleForm"
        size="default"
        status-icon>
      <el-form-item label="Email" prop="email">
        <el-input v-model="registerForm.email" />
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input v-model="registerForm.password" type="password"/>
      </el-form-item>
      <el-form-item label="Email Code" prop="code" style="margin-bottom: 0;">
        <el-input v-model="registerForm.code" style="flex: 1;" />
        <el-button @click="getCode" style="margin-left: 10px; width: 50px" type="primary" >
          {{ emailCodeButton }}
        </el-button>
      </el-form-item>
    </el-form>
  </Dialog>
</template>
<style scoped>

</style>