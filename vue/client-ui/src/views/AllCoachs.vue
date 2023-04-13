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
    <div style="margin: 20px 0">
      <el-row :gutter="10">
        <el-col :span="6" v-for="item in tableData" :key="item.id" style="margin-bottom: 35px;position:relative;left: 15px">
          <div style="width: 280px; border: 1px solid #ccc; padding-bottom: 10px; border-radius: 10px; overflow: hidden">
            <img :src="item.picture" alt="" style="width: 280px;height: 280px;object-fit: cover; cursor: pointer"
                 @click="$router.push('/'+ user.role + '/coachdetail?id=' + item.coachId)">
            <div style="color: #666; padding: 5px; font-size: 18px; cursor: pointer"><b @click="$router.push('/'+ user.role + '/coachdetail?id=' + item.coachId)">{{ item.name }}</b></div>
            <div style="color: #666; padding: 5px; font-size: 14px;height:24px">{{ item.description | ellipsis }}</div>
          </div>
        </el-col>
      </el-row>
    </div>
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
    name: "AllCoachs",
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
    filters: {
      // 当标题字数超出时，超出部分显示’...‘。此处限制超出8位即触发隐藏效果
      ellipsis (value) {
        if (!value) return ''
        if (value.length > 28) {
          return value.slice(0, 28) + '...'
        }
        return value
      }
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
