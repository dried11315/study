<script setup>
import { reactive, ref } from "vue";
import { User, Lock } from "@element-plus/icons-vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";

const data = reactive({
  form: {},
  rules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' },
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
    ],
  }
})

const formRef = ref()

// 点击登录按钮的时候会触发这个方法
const login = () => {
  formRef.value.validate((valid => {
    if (valid) {
      // 调用后台的接口
      request.post('/login', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success("登录成功")
          router.push('/')
          localStorage.setItem('system-user', JSON.stringify(res.data))
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })).catch(error => {
    console.error(error)
  })
}

</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 登录表单 -->
    <el-form ref="form" size="large">
      <el-form-item>
        <h1>登录</h1>
      </el-form-item>
      <el-form :model="data.form"  ref="formRef" :rules="data.rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="User" size="large" v-model="data.form.username" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" size="large" v-model="data.form.password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item prop="role">
          <el-select size="large" style="width: 100%" v-model="data.form.role">
            <el-option value="ADMIN" label="管理员"></el-option>
            <el-option value="PROUSER" label="高级用户"></el-option>
            <el-option value="USER" label="普通用户"></el-option>
          </el-select>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button size="large" class="button" type="primary" auto-insert-space @click="login">登录</el-button>
        </el-form-item>

        <div style="text-align: right;">
          还没有账号？请 <a href="/register">注册</a>
        </div>

      </el-form>
    </el-form>
    </el-col>
  </el-row>
</template>



<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('../assets/imgs/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>