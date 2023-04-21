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
    <div style="margin: 10px">
      <el-button class="ml-5" type="success" @click="dialogFormVisible = true" size="medium"><i class="el-icon-circle-plus-outline"></i> 添 加</eL-button>
    </div>
    <el-table :data="tableData" border stripe size="medium">
      <el-table-column prop="nickName" label="教练昵称"></el-table-column>
      <el-table-column prop="name" label="教练名字"></el-table-column>
      <el-table-column prop="phoneNumber" label="教练手机号"></el-table-column>
      <el-table-column prop="description" label="教练简介"></el-table-column>

      <el-table-column prop="createTime" label="创建时间" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" ></el-table-column>
      <el-table-column prop="address" label="地址" ></el-table-column>
      <el-table-column prop="position" label="教练类型" ></el-table-column>
      <el-table-column prop="recruitNumber" label="已收学员" ></el-table-column>

      <el-table-column prop="remainingSum" label="教练余额" ></el-table-column>
      <el-table-column prop="accumulatePoints" label="教练积分" ></el-table-column>
    </el-table>

    <el-dialog title="教练设置" :visible.sync="dialogFormVisible" width="42%" @close="handleClose">
      <el-form label-width="110px" :model="addCoach" ref="addCoach" status-icon
               size="small" style="display: inline-block;">

        <el-form-item label="教练昵称">
          <el-input v-model="addCoach.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="教练名字">
          <el-input v-model="addCoach.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="教练性别">
          <el-input v-model="addCoach.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="教练手机号">
          <el-input v-model="addCoach.phoneNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="教练专职">
          <el-select v-model="addCoach.position" size="medium" placeholder="选择类型">
            <el-option label="瑜伽教练" value="瑜伽教练"></el-option>
            <el-option label="运动健身教练" value="运动健身教练"></el-option>
            <el-option label="康复健身教练" value="康复健身教练"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="教练简介">
          <el-input v-model="addCoach.description" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div class="img2">
        教练图片
        <el-upload
          class="avatar-uploader2"
          action="http://localhost:8081/file/upload"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload"
          :on-success="handleAvatarSuccess"
        >
          <img v-if="this.addCoach.picture" :src="this.addCoach.picture">
          <i v-else class="el-icon-plus avatar-uploader-icon2"></i>
        </el-upload>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
      </div>
    </el-dialog>


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
        addCoach: {},
        dialogFormVisible: false,
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
      handleClose() {
        for (let key in this.addCoach) {
          this.addCoach[key] = '';
        }
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isLt2M;
      },
      handleAvatarSuccess(res) {
        // res就是文件的路径
        this.addCoach.picture = res
        this.load()
      },
      add() {
        this.$axios.post("http://localhost:8081/coach/save", this.addCoach).then(res =>{
          if (res.data.code == 200){
            this.dialogFormVisible = false
            this.load()
            this.$message.success("添加成功")
          }else {
            this.$message.error(res.data.message)
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

<style scoped>
  .img2 {
    text-align: center;
    margin-left: 20px;
    display: inline-block;
    position: relative;
    left: 30px;
    top: -20px;
  }
  .img2 .avatar-uploader2 {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    overflow: hidden;
    margin-top: 20px;
    width: 200px;
    height: 200px;
    display: block;
  }
  .img2 .avatar-uploader2:hover {
    border-color: #409EFF;
  }
  .img2 .avatar-uploader-icon2 {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .img2 img{
    width: 200px;
    height: 200px;
    object-fit: cover;
  }
</style>
