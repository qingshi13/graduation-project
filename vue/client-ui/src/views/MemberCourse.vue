<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>

      <el-select v-model="courseNature" size="medium" @change="load" placeholder="选择类型">
        <el-option label="全部" value=""></el-option>

        <el-option label="健身操" value="健身操"></el-option>
        <el-option label="搏击" value="搏击"></el-option>
        <el-option label="瑜伽" value="瑜伽"></el-option>
        <el-option label="综合有氧" value="综合有氧"></el-option>
        <el-option label="增肌" value="增肌"></el-option>
        <el-option label="塑形" value="塑形"></el-option>
      </el-select>

      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">

      <el-table-column prop="courseName" label="名称"></el-table-column>
      <el-table-column prop="time" label="订阅时间" ></el-table-column>
      <el-table-column prop="coachName" label="教练"></el-table-column>
      <el-table-column prop="coachPhone" label="手机号"></el-table-column>

      <el-table-column label="预约" width="180"align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="course(scope.row.courseId)"><i class="el-icon-date"></i> 预 约</el-button>
        </template>
      </el-table-column>

      <el-table-column label="评分">
        <template slot-scope="scope">
          <el-rate
            v-model="scope.row.rate"
            show-text
            @change="rating(scope.row.subscribeId,scope.row.courseId,scope.row.rate)"
          >
          </el-rate>
        </template>

      </el-table-column>

    </el-table>

    <el-dialog title="预约" :visible.sync="dialogFormVisible1" width="30%" :close-on-click-modal="false">
      <el-form label-width="80px" :model="appointment" ref="appointment" status-icon
               size="small">

        <el-form-item label="预约时间">
          <el-date-picker v-model="appointment.date" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="日期"></el-date-picker>
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="appointment.message" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>



      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="makingAppointment(appointment)">确 定</el-button>
      </div>
    </el-dialog>

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
    name: "MemberCourse",
    data() {
      return{
        appointment: {},
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
        courseNature: "",
        cId: "",
      }
    },
    created() {
      this.load()
    },
    methods: {
      course(courseId){
        this.dialogFormVisible1 = true
        this.cId = courseId
      },
      rating(id,courseId,rate) {
        this.$axios.post("http://localhost:8081/subscribe/rate/",{subscribeId:id,courseId:courseId,rate:rate}).then(res =>{
          if (res.data.code == 200) {
            this.$message.success("评分成功")
          }else {
            this.$message.error("评分失败")
          }
        })
        console.log(rate)
      },
      makingAppointment(appointment) {
        appointment.memberId=this.user.userId
        appointment.courseId=this.cId
        this.$axios.post("http://localhost:8081/appointment/",appointment).then(res =>{
          if (res.data.code == 200) {
            console.log(res.data)
            this.$message.success(res.data.message)
            // this.load()
          }else {
            this.$message.error(res.data.message)
            console.log(res.data)
          }
        })
        console.log(appointment)
      },
      load() {
        this.$axios.get("http://localhost:8081/subscribe/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
            courseNature: this.courseNature,
            Id: this.user.userId
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
