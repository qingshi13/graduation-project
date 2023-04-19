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
      <el-table-column prop="description" label="课程描述"></el-table-column>
      <el-table-column prop="courseNature" label="课程类型" ></el-table-column>
      <el-table-column prop="num" label="课程节数"></el-table-column>
      <el-table-column prop="recruit" label="学员人数"></el-table-column>

      <el-table-column prop="coachName" label="教练名字"></el-table-column>

      <el-table-column prop="phoneNumber" label="教练手机号"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="100"></el-table-column>
      <el-table-column prop="state" label="课程状态" ></el-table-column>

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
