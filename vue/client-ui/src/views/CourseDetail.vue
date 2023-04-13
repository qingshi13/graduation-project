<template>
  <div style="padding: 10px">
    <el-card>
      <div style="display: flex;">
        <div style="width: 300px">
          <el-image v-if="course.picture"
                    :src="course.picture"
                    :preview-src-list="[course.picture]"
                    style="width: 100%">
          </el-image>
        </div>
        <div style="flex: 1; padding-left: 50px">
          <div class="item1"><h1 style="font-size: 24px;">{{ course.name }}</h1></div>
          <div class="item1" style="font-size: 18px;">教练：<span style="cursor: pointer;">{{ course.coachName }}</span></div>
          <div class="item1" style="font-size: 16px; ">简介：{{ course.description }}</div>
          <div class="item1" style="font-size: 16px; ">
            <el-rate
              v-model="course.rate "
              disabled
              show-score
              text-color="#ff9900"
              >
            </el-rate>
          </div>

          <div class="item1" style="color: orangered;margin-top: -5px;margin-bottom: -1px">价格 ￥ {{ course.courseFees }}</div>
          <div class="item1" style="">
            <el-button size="medium" style="background-color:#42b983; color: white" v-on:click="getOthers"><i class="el-icon-wallet"></i> 查看同类</el-button>
          </div>
          <div class="item1" style="margin-top: -5px">
            <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定购买吗?"
              @confirm="subscribe"
            >
              <el-button size="medium" style="background-color: coral; color: white" slot="reference"><i class="el-icon-wallet"></i>   订  阅   </el-button>
            </el-popconfirm>
          </div>
        </div>
      </div>
    </el-card>

    <el-dialog title="同类课程" :visible.sync="dialogFormVisible" width="64%" :close-on-click-modal="false">
      <el-table :data="others" border stripe >
        <el-table-column prop="name" label="课程名称" width="80"></el-table-column>
        <el-table-column prop="description" label="课程简介" width="150" ></el-table-column>
        <el-table-column prop="coachName" label="教练名字"></el-table-column>
        <el-table-column label="教练照片" width="150">
          <template slot-scope="scope">
            <el-image style="width: 120px; height: 120px" :src="scope.row.coachPicture" :preview-src-list="[scope.row.coachPicture]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="openTime" label="开课时间" width="100"></el-table-column>
        <el-table-column prop="rate" label="评分" width="80"></el-table-column>
        <el-table-column prop="courseFees" label="课程收费" width="80"></el-table-column>
        <el-table-column label="查看详情" align="center">
          <template slot-scope="scope">
            <el-button type="primary" @click="$router.push('/'+ user.role + '/coursedetail?id=' + scope.row.courseId)"
                       ><i class="el-icon-edit"></i> 查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name: "CourseDetail",
    data() {
      return {
        id: "",
        course: {},
        form: { num: 1},
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        dialogFormVisible: false,
        dialogFormVisible1: false,
        others: {},
      }
    },
    created() {
      this.load()
    },
    watch: {
      $route (to, from) {
        this.$router.go(0)
      }
    },
    methods: {
      load() {
          this.id = this.$route.query.id,
          this.$axios.get("http://localhost:8081/course/getByCourseId/" + this.id).then(res => {
          this.course = res.data.data
          console.log(this.course)
       })
      },
      getOthers() {
        this.$axios.get("http://localhost:8081/course/getByCourseNature/" + this.id).then(res => {
          this.others = res.data.data
          this.dialogFormVisible = true
          console.log(this.others)
        })
      },
      subscribe() {
        this.$axios.post("http://localhost:8081/subscribe/" , {courseId:this.id,memberId:this.user.userId}).then(res =>{
          if (res.data.data.code == 200) {
            console.log(res.data)
            this.$message.success(res.data.data.data)
            this.load()
          }else {
            this.$message.error(res.data.data.message)
            console.log(res.data)
          }
        })
      }

    }
  }
</script>

<style scoped>
  .item1{
    padding: 10px;
    color: #666;
    /*margin-bottom: 4px;*/
  }
  .item1 span :hover{
    color: #409EFF;
  }
</style>
