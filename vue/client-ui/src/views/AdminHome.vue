<template>
  <div style="width: 1200px">
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>

      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>
    <div style="margin: 10px">
      <el-button class="ml-5" type="success" @click="dialogFormVisible = true" size="medium"><i class="el-icon-circle-plus-outline"></i> 添 加</eL-button>
    </div>
    <el-table :data="messageList" border stripe size="medium" style="width: 701px">
      <el-table-column prop="title" label="标题" width="140px"></el-table-column>
      <el-table-column prop="content" label="内容" width="400px"></el-table-column>
      <el-table-column prop="createTime" label="时间" width="160px"></el-table-column>
    </el-table>




    <el-dialog title="发布公告" :visible.sync="dialogFormVisible" width="30%" @close="handleClose">
      <el-form label-width="40px">

        <el-form-item label="标题">
          <el-input v-model="form.title" size="small" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea"
                    :rows="5"
                    placeholder="请输入内容" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="margin-top: -30px">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="release">确 定</el-button>
      </div>
    </el-dialog>

    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="this.total">
      </el-pagination>
    </div>
    <div class="block" style="position: absolute; top: 200px;left: 1210px;">
      <el-date-picker
        @change="getweek"
        :picker-options="{'firstDayOfWeek': 1}"
        v-model="choseweek"
        type="week"
        format="yyyy 第 WW 周"
        placeholder="选择周"
        value-format="yyyy-MM-dd"
      >
      </el-date-picker>
    </div>

    <div id="mychart" class="chartstyle" ></div>
    <p style="position: absolute; top: 210px;left: 1020px;">健身房人流量周表</p>

  </div>

</template>

<script>
  import * as echarts from 'echarts';

  export default {
    name: "AdminHome",

    data(){
      return{
        choseweek: new Date().toISOString().split('T')[0],
        xData: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"], //横坐标
        yData: [23,], //数据

        dialogFormVisible: false,
        form: {},
        data: {},
        name:"",
        total: 0,
        pageNum: 1,
        pageSize: 6,
        messageList: [],
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      }
    },
    mounted() {

      this.initEcharts();
    },

    created(){
      this.load()
      this.getweek()
    },
    methods: {
      getweek(){
        this.yData=[];
        console.log(this.choseweek)
        this.$axios.get("http://localhost:8081/flow/getweek/" + this.choseweek).then(res => {
          if (res.data.code == 200) {
            for (let i = 0; i < 7; i++) {
              this.yData.push(res.data.data[i]);
            }
            console.log(res.data.data)
            console.log(this.yData)
            this.initEcharts()
          } else {
            this.$message.error("获取失败")
          }
        })
      },
      initEcharts() {
        // 基本柱状图
        const option = {
          xAxis: {
            data: this.xData
          },
          yAxis: {
          },
          series: [
            {
              type: "bar", //形状为柱状图
              data: this.yData,

            }
          ]
        };
        const myChart = echarts.init(document.getElementById("mychart"));
        myChart.setOption(option);
        //随着屏幕大小调节图表
        window.addEventListener("resize", () => {
          myChart.resize();
        });
      },
      handleClose() {
        for (let key in this.form) {
          this.form[key] = '';
        }
      },
      load() {
        const AdminId = this.user.userId
        if(!AdminId){
          this.$message.error("无法获取用户信息")
          this.$router.push('/login')
          return
        }
        this.$axios.get("http://localhost:8081/message/getallmessage",{
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
          }
        }).then(res => {
          if (res.data.code == 200) {
            this.messageList = res.data.data.records
            this.total = res.data.data.total
          } else {
            this.$message.error("获取失败")
          }
        })

      },
      release() {
        this.$axios.post("http://localhost:8081/message/release", this.form).then(res => {
          if (res.data.code == 200) {
            this.$message.success("发布成功")
            this.load()
          } else {
            this.$message.error("保存失败")
          }
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

  .chartstyle {
    position: absolute;
    top:240px;
    left:1020px;
    width: 440px;
    height: 380px;
    display: inline-block
  }

</style>
