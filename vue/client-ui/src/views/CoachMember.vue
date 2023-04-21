<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column prop="courseName" label="课程名字"></el-table-column>
      <el-table-column prop="nickName" label="会员昵称" width="200"></el-table-column>
      <el-table-column prop="memberName" label="会员名字" width="200"></el-table-column>
      <el-table-column prop="phone" label="会员电话"></el-table-column>
      <el-table-column prop="time" label="订阅时间" ></el-table-column>

    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2,5,10,20]"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>


  </div>
</template>
<script>
  export default {
    name: "CoachMember",
    data() {

      return{
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        name: "",
        form: {},
        dialogFormVisible: false,
        dialogFormVisible1: false,
        multipleSelection: [],
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

      }

    },
    created() {
      this.load()

    },

    methods: {

      load() {
        this.$axios.get("http://localhost:8081/coach/getstudent", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            coachId: this.user.userId,
            name: this.name,
          }
        }).then(res => {
          this.tableData = res.data.data.records
          this.total = res.data.data.total
          console.log(this.tableData)
        })

      },

      handleSizeChange(pageSize) {
        console.log(pageSize)
        this.pageSize = pageSize
        this.load()
      },
      handleCurrentChange(pageNum) {
        console.log(pageNum)
        this.pageNum = pageNum
        this.load()
      }
    }
  }
</script>

<style>
</style>
