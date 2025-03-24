<script setup>
import { reactive } from "vue"
import request from "@/utils/request";
import { ElMessage } from "element-plus";
import router from "@/router";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
})

// 把当前修改的用户信息存储到后台数据库
const save = () => {
  if (data.user.newPassword !== data.user.confirmPasword) {
    ElMessage.error('请输入两次一样的密码')
    return
  }
  request.put('/updatePassword', data.user).then(res => {
    if (res.code === '200') {
      ElMessage.success('修改密码成功')
      // 清空缓存
      localStorage.removeItem('system-user')
      router.push('/login')
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>

<template>
  <div class="form-container">
    <div class="card" style="padding: 30px">
      <el-form :model="data.user" label-width="100px" style="padding-right: 50px">
        <el-form-item label="原密码">
          <el-input v-model="data.user.password" show-password />
        </el-form-item>

        <el-form-item label="新密码">
          <el-input v-model="data.user.newPassword" show-password />
        </el-form-item>

        <el-form-item label="确认新密码">
          <el-input v-model="data.user.confirmPasword" show-password />
        </el-form-item>

        <div style="text-align: center">
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>



<style scoped>
/* 修改 form-container 使表单更靠近顶部 */
.form-container {
  display: flex;
  justify-content: center;  /* 水平居中 */
  align-items: flex-start;   /* 垂直对齐到顶部 */
  height: 100vh;             /* 使容器占满整个视口 */
  padding-top: 10px;         /* 将上方的间距减小 */
}

/* 卡片样式 */
.card {
  width: 100%;
  max-width: 500px;  /* 限制卡片最大宽度 */
}



</style>

