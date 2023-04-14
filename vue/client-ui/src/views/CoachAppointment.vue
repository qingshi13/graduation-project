<template>
  <div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column prop="courseName" label="课程名"></el-table-column>
      <el-table-column prop="memberName" label="会员名" width="200"></el-table-column>
      <el-table-column prop="time" label="日期" ></el-table-column>
      <el-table-column prop="point" label="时间" ></el-table-column>

      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="success" ><i class="el-icon-wallet"></i> 编 辑</el-button>

        </template>
      </el-table-column>
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
    name: "CoachAppointment",
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
        this.$axios.get("http://localhost:8081/appointment/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            coachId: this.user.userId
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
  .img{
    text-align: center;
    margin: 10px;
    display: inline-block;
    position: relative;
    left: 30px;
    top: -30px;
  }
  .img .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 200px;
    height: 200px;
    margin-top: 10px;
    display: block;
  }
  .img .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .img .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .img img{
    width: 200px;
    height: 200px;
    object-fit: cover;
  }
</style>
