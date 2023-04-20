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

      <el-select v-model="state" size="medium" @change="load" placeholder="选择状态">
        <el-option label="全部" value=""></el-option>
        <el-option label="未通过" value="未通过"></el-option>
        <el-option label="待审核" value="待审核"></el-option>
        <el-option label="已上线" value="已上线"></el-option>

      </el-select>

      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column label="图片" width="180">
        <template slot-scope="scope">
          <el-image style="width: 150px; height: 150px" :src="scope.row.picture" :preview-src-list="[scope.row.picture]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="课程名字"></el-table-column>
      <el-table-column prop="description" label="课程描述" width="180"></el-table-column>
      <el-table-column prop="courseNature" label="课程类型" ></el-table-column>
      <el-table-column prop="num" label="课程节数"></el-table-column>
      <el-table-column prop="recruit" label="学员人数"></el-table-column>

      <el-table-column prop="coachName" label="教练名字"></el-table-column>

      <el-table-column prop="phoneNumber" label="教练手机号" width="112"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="100"></el-table-column>
      <el-table-column prop="state" label="课程状态" width="77"></el-table-column>
      <el-table-column label="操作" width="172" align="center">
        <template slot-scope="scope" >
          <el-popconfirm
            v-if="scope.row.state == '待审核'"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定通过吗?"
            @confirm="pass(scope.row.courseId)"
          >
            <el-button type="success" slot="reference">通 过</el-button>
          </el-popconfirm>

          <el-popconfirm
            v-if="scope.row.state == '待审核'"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定否决吗?"
            @confirm="fail(scope.row.courseId)"
          >
            <el-button type="danger" slot="reference">否 决</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="this.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  export default {
    name: "AdminCourse",
    data(){
      return{
        imgs: [

        ],
        state:"",
        courseNature: "",
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        name: "",
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      }
    },
    created() {
      this.load()
    },
    methods: {
      pass(courseId){
        this.$axios.post("http://localhost:8081/course/pass" ,{courseId:courseId}).then(res => {
          if (res.data.code == 200) {
            this.$message.success("该课程以上线")
            this.load()
          }else {
            this.$message.error("操作失败")
          }
        })

      },
      fail(courseId){
        this.$axios.post("http://localhost:8081/course/fail" ,{courseId:courseId}).then(res => {
          if (res.data.code == 200) {
            this.$message.success("否决成功")
            this.load()
          }else {
            this.$message.error("操作失败")
          }
        })
      },
      load() {
        this.$axios.get("http://localhost:8081/admin/getallcourses", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
            courseNature: this.courseNature,
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
      },
      handleCurrentChange(pageNum) {
        console.log(pageNum)
        this.pageNum = pageNum
      }
    }
  }

</script>

<style scoped>
</style>
