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

//文件上传的接口
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const data = reactive({
  formVisible:false,
  form:{},
  tableData:[],
  pageNum:1,
  pageSize:10,
  total:0
})

//分页查询
const load = ()=>{
  request.get('/user/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      name: data.name
    }
  }).then(res => {
    if (res.code === '200'){
      data.tableData  = res.data?.list
      data.total = res.data?.total
    }else {
      ElMessage.error(res.msg)
    }
  })
}

//新增按钮
const hendleAdd = ()=>{
  data.form = {}
  data.formVisible = true
}

//编辑按钮
const handleEdit = (row)=>{
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

//删除按钮
const handleDelete = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/user/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}



//新增
const add = ()=>{
  request.post('/user/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

//更新
const update = ()=>{
  request.put('/user/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//保存
const save = ()=>{
  data.form.id?update():add()
}

// 重置
const reset = () => {
  data.name = null
  load()
}

const handleImgSuccess = (res)=>{
  data.form.avatar = res.data
}


load();
</script>

<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" style="width: 300px; margin-right: 10px" placeholder="请输入名称查询"></el-input>
      <el-button type="primary" :icon="Search" @click="load">查询</el-button>
      <el-button type="info" :icon="Delete" @click="reset">重置</el-button>
    </div>
    <div class="card">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="hendleAdd">新增</el-button>
      </div>
      <el-table :data="data.tableData" stripe>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="头像" prop="avatar">
          <template v-slot="scope">
            <el-image :src="scope.row.avatar" style="width: 40px;height: 40px;border-radius: 50%"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="性别" prop="sex"></el-table-column>
        <el-table-column label="用户组" prop="title"></el-table-column>
        <el-table-column label="角色" prop="role"></el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" :icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" :icon="Delete" circle @click="handleDelete(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next" v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" @current-change="changePage"/>
    </div>

    <el-dialog title="高级用户信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="头像" prop="avatar">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="账号" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="data.form.sex" placeholder="请选择性别" style="width: 100%">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户组" prop="title">
          <el-select v-model="data.form.title" placeholder="请选择用户组" style="width: 100%">
            <el-option label="普通用户" value="普通用户"></el-option>
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
  </div>
</template>

