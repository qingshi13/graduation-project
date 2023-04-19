<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入会员手机号" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>

      <el-select v-model="state" size="medium" @change="load" placeholder="会员状态">
        <el-option label="全部" value=""></el-option>

        <el-option label="未开通" value="未开通"></el-option>
        <el-option label="已生效" value="已生效"></el-option>
        <el-option label="已过期" value="已过期"></el-option>

      </el-select>

      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column prop="nickName" label="会员昵称"></el-table-column>
      <el-table-column prop="name" label="会员名字"></el-table-column>
      <el-table-column prop="phoneNumber" label="会员手机号"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="100"></el-table-column>
      <el-table-column prop="state" label="会员状态" ></el-table-column>
      <el-table-column prop="expirationTime" label="过期时间" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" ></el-table-column>
      <el-table-column prop="remainingSum" label="会员余额" ></el-table-column>
      <el-table-column prop="accumulatePoints" label="会员积分" ></el-table-column>

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
    name: "AdminMember",
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
        this.$axios.get("http://localhost:8081/admin/getallmembers", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
            state: this.state,
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
