<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";



const data = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  tableData: [],
  name: null,
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
})



// 签到
const attend = () => {
  // 获取当前用户的信息
  const currentUserName = data.user.name; // 当前名称
  const currentUserTitle = data.user.title; // 当前用户组
  const currentUserRole = data.user.role; // 当前用户角色

  // 确保表单数据包含当前用户信息
  if (!data.form.name) {
    data.form.name = currentUserName;
  }
  if (!data.form.title) {
    data.form.title = currentUserTitle;
  }
  if (!data.form.role) {
    data.form.role = currentUserRole;
  }
  request.post('/attend/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

//请假
const hendleAdd = ()=>{
  data.form = {}
  data.formVisible = true
}

// 新增保存
const leave = () => {
  // 获取当前用户的信息
  const currentUserTitle = data.user.title; // 当前用户组
  const currentUserRole = data.user.role; // 当前用户角色

  // 确保表单数据包含当前用户信息
  if (!data.form.title) {
    data.form.title = currentUserTitle;
  }
  if (!data.form.role) {
    data.form.role = currentUserRole;
  }
  request.post('/leave/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 弹窗保存
const save = () => {
  leave()
}
</script>

<template>
  <div>

    <div class="card"  style="line-height: 30px; text-align: center; margin-bottom: 20px;">
      <div>欢迎您，{{ data.user.name }} 祝您今天过得开心！</div>
    </div>
    <div class="card"  style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px;">
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="attend" style="width: 120px; height: 40px; margin: 0 10px;">签到</el-button>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="hendleAdd" style="width: 120px; height: 40px;">请假</el-button>
    </div>
  </div>
  </div>

  <el-dialog title="信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
    <el-form :model="data.form" label-width="100px" style="padding-right: 50px">

      <el-form-item label="名称" prop="name">
        <el-input v-model="data.form.name"/>
      </el-form-item>
      <el-form-item label="请假类型" prop="style">
        <el-select v-model="data.form.style" placeholder="请选择请假类型" style="width: 100%">
          <el-option label="病假" value="病假"></el-option>
          <el-option label="事假" value="事假"></el-option>
          <el-option label="公假" value="公假"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="请假状态" prop="state" v-if="data.user.role !== 'USER'">
        <el-select v-model="data.form.state" placeholder="未处理" style="width: 100%">
          <el-option label="同意" value="同意"></el-option>
          <el-option label="不同意" value="不同意"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

