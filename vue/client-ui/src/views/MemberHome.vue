<template>
  <div style="width: 1200px">
    <el-carousel :interval="4000" type="card" height="300px" >
      <el-carousel-item v-for="item in 6" :key="item">
        <h3 class="medium">{{ item }}</h3>
      </el-carousel-item>
    </el-carousel>

    <div>
      <el-table style="width: 400px;position: absolute;top: 465px;left: 240px" :data="tableData" border stripe >
        <el-table-column label="日期" prop="date"></el-table-column>
        <el-table-column label="时间" prop="time"></el-table-column>
        <el-table-column label="人数" prop="num"></el-table-column>


        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.times == 1 || scope.row.times == 2" type="primary"
                       @click="appointment(scope.row.date,scope.row.time)">预 约</el-button>
            <span v-else>您今日预约次数已用完</span>

          </template>
        </el-table-column>

      </el-table>
      <el-table style="width: 400px;position: absolute;top: 465px;left: 670px" :data="tableData1" border stripe >
        <el-table-column label="日期" prop="date"></el-table-column>
        <el-table-column label="时间" prop="time"></el-table-column>
        <el-table-column label="人数" prop="num"></el-table-column>


        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.times == 1 || scope.row.times == 2" type="primary"
                       @click="appointment(scope.row.date,scope.row.time)">预 约</el-button>
            <span v-else>您今日预约次数已用完</span>

          </template>
        </el-table-column>

      </el-table>
      <el-table style="width: 400px;position: absolute;top: 465px;left: 1100px" :data="tableData2" border stripe >
        <el-table-column label="日期" prop="date"></el-table-column>
        <el-table-column label="时间" prop="time"></el-table-column>
        <el-table-column label="人数" prop="num"></el-table-column>


        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.times == 1 || scope.row.times == 2" type="primary"
                       @click="appointment(scope.row.date,scope.row.time)">预 约</el-button>
            <span v-else>您今日预约次数已用完</span>

          </template>
        </el-table-column>

      </el-table>

    </div>
  </div>

</template>

<script>
  export default {
    name: "MemberHome",
    data(){
      return{
        data:{},
        times: 0,
        tableData: [{
          date: '',
          time: '上午',
          num: '',
          times: ''
        }, {
          date: '',
          time: '下午',
          num: '',
          times: ''
        }, {
          date: '',
          time: '晚上',
          num: '',
          times: ''
        }],
        tableData1: [{
          date: '',
          time: '上午',
          num: '',
          times: ''
        }, {
          date: '',
          time: '下午',
          num: '',
          times: ''
        }, {
          date: '',
          time: '晚上',
          num: '',
          times: ''
        }],
        tableData2: [{
          date: '',
          time: '上午',
          num: '',
          times: ''
        }, {
          date: '',
          time: '下午',
          num: '',
          times: ''
        }, {
          date: '',
          time: '晚上',
          num: '',
          times: ''
        }],
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      }
    },

    created(){
      this.load()
    },
    methods: {
      load() {
        const memberId = this.user.userId
        if(!memberId){
          this.$message.error("无法获取用户信息")
          return
        }
        this.$axios.get("http://localhost:8081/member/getByMemberId/" + memberId).then(res =>{
          this.tableData[0].times = res.data.data.times
          this.tableData[1].times = res.data.data.times
          this.tableData[2].times = res.data.data.times
          this.tableData1[0].times = res.data.data.times
          this.tableData1[1].times = res.data.data.times
          this.tableData1[2].times = res.data.data.times
          this.tableData2[0].times = res.data.data.times
          this.tableData2[1].times = res.data.data.times
          this.tableData2[2].times = res.data.data.times

        })

        var today = new Date();

        var tomorrow = new Date(today);
        tomorrow.setDate(today.getDate() + 1);

        var afterTomorrow = new Date(today);
        afterTomorrow.setDate(today.getDate() + 2);

        var todayStr = today.getFullYear() + '-' + (today.getMonth() + 1).toString().padStart(2, '0') + '-' + today.getDate().toString().padStart(2, '0');
        var tomorrowStr = tomorrow.getFullYear() + '-' + (tomorrow.getMonth() + 1).toString().padStart(2, '0') + '-' + tomorrow.getDate().toString().padStart(2, '0');
        var afterTomorrowStr = afterTomorrow.getFullYear() + '-' + (afterTomorrow.getMonth() + 1).toString().padStart(2, '0') + '-' + afterTomorrow.getDate().toString().padStart(2, '0');

        this.tableData[0].date = todayStr
        this.tableData[1].date = todayStr
        this.tableData[2].date = todayStr

        this.tableData1[0].date = tomorrowStr
        this.tableData1[1].date = tomorrowStr
        this.tableData1[2].date = tomorrowStr

        this.tableData2[0].date = afterTomorrowStr
        this.tableData2[1].date = afterTomorrowStr
        this.tableData2[2].date = afterTomorrowStr

        this.$axios.get("http://localhost:8081/flow/getflow/"+ this.tableData[0].date).then(res =>{
          this.tableData[0].num = res.data.data[0]
          this.tableData[1].num = res.data.data[1]
          this.tableData[2].num = res.data.data[2]
          this.tableData1[0].num = res.data.data[3]
          this.tableData1[1].num = res.data.data[4]
          this.tableData1[2].num = res.data.data[5]
          this.tableData2[0].num = res.data.data[6]
          this.tableData2[1].num = res.data.data[7]
          this.tableData2[2].num = res.data.data[8]
          console.log(res.data.data)
        })
      },
      appointment(date,time){
        this.$axios.post("http://localhost:8081/flow/add/",{date:date,time:time,memberId:this.user.userId}).then(res =>{
          if (res.data.code == 200){
            this.load()
            this.$message.success("预约成功")
          }else {
            this.$message.error("预约失败")
          }

        })
      },
    }
  }
</script>
<style>
  .el-carousel {
    position: relative;
    left: 47px;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
