<script setup>
import { reactive } from "vue";
import router from "@/router";
import {ElMessage} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}')
})

if (!data.user?.id) {
  ElMessage.error('请登录！')
  router.push('/login')
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('system-user') || '{}')
}

const logout = () => {
  ElMessage.success('退出成功')
  localStorage.removeItem('system-user')
  router.push('/login')
}
</script>

<template>
  <div>
    <div style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
      <div style="flex: 1">
        <div style="padding-left: 20px; display: flex; align-items: center" >
          <div style="font-weight: bold; font-size: 24px; margin-left: 5px">通用考勤管理系统</div>
        </div>
      </div>
      <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
        <img style="width: 40px; height: 40px; border-radius: 50%" :src="data.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="">
        <span style="margin-left: 5px">{{ data.user.name }}({{ data.user.role }})</span>
      </div>
    </div>

    <div style="display: flex;background-color:#232323">
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu
            active-text-color="#ffd04b" background-color="#232323"  text-color="#fff"
            style="border: none"
            :default-active="router.currentRoute.value.path"
            :default-openeds="['/home']"
            router>
          <!--系统首页-->
          <el-menu-item index="/home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <!--考勤管理-->
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Promotion /></el-icon>
              <span>考勤管理</span>
            </template>
            <el-menu-item index="/attend"><el-icon><LocationInformation /></el-icon><span>考勤情况</span></el-menu-item>
            <el-menu-item index="/leave"><el-icon><LocationInformation /></el-icon><span>请假情况</span></el-menu-item>
          </el-sub-menu>
          <!--用户管理-->
          <el-sub-menu index="3" v-if="data.user.role === 'ADMIN'">
            <template #title>
              <el-icon><Avatar /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/admin"><el-icon><User /></el-icon><span>管理员信息</span></el-menu-item>
            <el-menu-item index="/prouser"><el-icon><User /></el-icon><span>高级用户信息</span></el-menu-item>
            <el-menu-item index="/user"><el-icon><User /></el-icon><span>普通用户信息</span></el-menu-item>
          </el-sub-menu>
          <!--个人资料-->
          <el-menu-item v-if="data.user.role === 'ADMIN'" index="/aPerson"><el-icon><User /></el-icon><span>个人资料</span></el-menu-item>
          <el-menu-item v-if="data.user.role === 'PROUSER'" index="/pPerson"><el-icon><User /></el-icon><span>个人资料</span></el-menu-item>
          <el-menu-item v-if="data.user.role === 'USER'" index="/uPerson"><el-icon><User /></el-icon><span>个人资料</span></el-menu-item>
          <el-menu-item index="/password">
            <el-icon><Lock /></el-icon>
            <span>修改密码</span>
          </el-menu-item>
          <el-menu-item index="login" @click="logout">
            <el-icon><SwitchButton /></el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </div>

      <div style="flex: 1; width: 0; background-color: #f8f8ff; padding: 10px">
        <router-view @updateUser="updateUser" />
      </div>
    </div>

  </div>
</template>



<style scoped>


</style>
