<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>

      <el-select v-model="position" size="medium" @change="load" placeholder="选择类型">
        <el-option label="全部" value=""></el-option>

        <el-option label="瑜伽教练" value="瑜伽教练"></el-option>
        <el-option label="运动健身教练" value="运动健身教练"></el-option>
        <el-option label="康复健身教练" value="康复健身教练"></el-option>

      </el-select>

      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column prop="nickName" label="教练昵称"></el-table-column>
      <el-table-column prop="name" label="教练名字"></el-table-column>
      <el-table-column prop="phoneNumber" label="教练手机号"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" ></el-table-column>
      <el-table-column prop="remainingSum" label="教练余额" ></el-table-column>
      <el-table-column prop="accumulatePoints" label="教练积分" ></el-table-column>
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
    name: "AdminCoach",
    data(){
      return{
        imgs: [

        ],
        position: "",
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
        this.$axios.get("http://localhost:8081/coach", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
            position: this.position,
          }
        }).then(res => {
          this.tableData = res.data.data.records
          this.total = res.data.data.total
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

<style scoped>
</style>
