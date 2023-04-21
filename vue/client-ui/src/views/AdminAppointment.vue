<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入会员手机号" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>

      <el-select v-model="state" size="medium" @change="load" placeholder="选择状态">
        <el-option label="全部" value=""></el-option>

        <el-option label="未完成" value="未完成"></el-option>
        <el-option label="已完成" value="已完成"></el-option>

      </el-select>

      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column prop="courseName" label="课程名字"></el-table-column>
      <el-table-column prop="nickName" label="会员昵称" ></el-table-column>
      <el-table-column prop="memberName" label="会员名字" ></el-table-column>
      <el-table-column prop="memberPhone" label="会员手机号" width="112px"></el-table-column>
      <el-table-column prop="coachName" label="教练名字" ></el-table-column>
      <el-table-column prop="coachPhone" label="教练手机号" width="112px"></el-table-column>
      <el-table-column prop="memberName" label="会员名字" ></el-table-column>
      <el-table-column prop="date" label="日期" ></el-table-column>
      <el-table-column prop="point" label="时间" ></el-table-column>
      <el-table-column prop="message" label="备注" ></el-table-column>
      <el-table-column prop="num" label="课节" ></el-table-column>
      <el-table-column prop="state" label="状态" ></el-table-column>

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
    name: "AdminAppointment",
    data() {

      return{
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        name: "",
        state: "",
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
        this.$axios.get("http://localhost:8081/admin/getallappointments", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
            state: this.state
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
