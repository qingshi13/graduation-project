<template>
  <div style="padding: 10px">
    <el-card>
      <div style="display: flex;">
        <div style="width: 300px">
          <el-image :src="coach.picture" :preview-src-list="[coach.picture]" style="width: 100%"></el-image>
        </div>
        <div style="flex: 1; padding-left: 50px">
          <div class="item1"><h1 style="font-size: 24px">{{ coach.name }}</h1></div>
          <div class="item1" style="font-size: 16px; margin-top: -10px;">{{ coach.sex }}</div>
          <div class="item1" style="font-size: 16px; ">{{ coach.description }}</div>
          <div class="item1" style="font-size: 16px; ">{{ coach.phoneNumber }}</div>

          <div class="item1">
            <el-button type="primary" @click="viewCourses()"><i class="el-icon-edit"></i> 查看课程</el-button>
          </div>
        </div>
      </div>
    </el-card>
    <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="50%" :close-on-click-modal="false">
      <el-table :data="coursesList" border stripe>
        <el-table-column prop="name" label="课程名称"></el-table-column>
        <el-table-column label="图片" width="180">
          <template slot-scope="scope">
            <el-image style="width: 150px; height: 150px" :src="scope.row.picture" :preview-src-list="[scope.row.picture]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="课程描述" width="150"></el-table-column>
        <el-table-column prop="num" label="课节数" width="70"></el-table-column>
        <el-table-column prop="courseFees" label="课程价格" width="80"></el-table-column>
<!--        <el-table-column prop="openTime" label="开课时间" width="100"></el-table-column>-->
        <el-table-column label="查看详情" align="center" width="130">
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
    name: "CoachDetail",
    data() {
      return {
        id: this.$route.query.id,
        coach: {},
        form: { num: 1},
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        dialogFormVisible: false,
        coursesList: {},
      }
    },
    created() {
      this.load()
    },
    methods: {
      load() {
        this.$axios.get("http://localhost:8081/coach/getByCoachId/" + this.id).then(res => {
          this.coach = res.data.data
        })
      },
      viewCourses() {
        this.$axios.get("http://localhost:8081/course/getByCoachId/" + this.id).then(res =>{
          this.coursesList = res.data.data
          this.dialogFormVisible = true
          console.log(this.goodsList)
        })
      },
    }
  }
</script>

<style scoped>
  .item1{
    padding: 10px;
    color: #666;
    margin-bottom: 10px;
  }
</style>
