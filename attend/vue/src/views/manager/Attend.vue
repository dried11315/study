<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";
import {
  Check,
  Delete,
  Edit,
  Message,
  Search,
  Star,
} from '@element-plus/icons-vue'
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

// 分页查询
const load = () => {
  request.get('/attend/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    data.tableData = res.data?.list
    data.total = res.data?.total
  })
}

// 编辑
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// 签到
const add = () => {
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
      load()
      ElMessage.success('签到成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 编辑保存
const update = () => {
  request.put('/attend/update', data.form).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('保存成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 弹窗保存
const save = () => {
  // data.form有id就是更新，没有就是新增
  data.form.id ? update() : add()
}

// 删除
const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/attend/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('删除成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}

// 重置
const reset = () => {
  data.name = null
  load()
}

load()
</script>

<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.name" style="width: 300px; margin-right: 10px" placeholder="请输入名称查询"></el-input>
      <el-button type="primary" :icon="Search" @click="load">查询</el-button>
      <el-button type="info" :icon="Delete" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="add">签到</el-button>
      </div>
      <el-table :data="data.tableData" stripe>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="状态" prop="state"></el-table-column>
        <el-table-column label="签到时间" prop="time"></el-table-column>
        <el-table-column label="用户组" prop="title"></el-table-column>
        <el-table-column label="角色" prop="role"></el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button
                type="primary" :icon="Edit" circle
                :disabled="!(scope.row.name === data.user?.name && !['ADMIN', 'PROUSER'].some(role => scope.row.role?.includes(role)) || ['ADMIN', 'PROUSER'].includes(data.user?.role))"
                @click="handleEdit(scope.row)">
            </el-button>
            <el-button
                type="danger" :icon="Delete" circle
                :disabled="!(scope.row.name === data.user?.name && !['ADMIN', 'PROUSER'].some(role => scope.row.role?.includes(role)) || ['ADMIN', 'PROUSER'].includes(data.user?.role))"
                @click="handleDelete(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next" v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog title="信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">

        <el-form-item label="名称" prop="name">
          <el-input v-model="data.form.name"/>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-input v-model="data.form.state"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

