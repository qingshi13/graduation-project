<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <div style="margin: 10px">
      <el-button class="ml-5" type="success" @click="dialogFormVisible = true" size="medium"><i class="el-icon-circle-plus-outline"></i> 添 加</eL-button>
      <span style="position: relative;left: 800px;">最多可收 {{this.limit.maxPeople}}名学员，已收{{this.limit.subscribedNum}}名</span>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column label="图片">
        <template v-slot="scope">
          <el-image style="height: 100px" :src="scope.row.picture" :preview-src-list="[scope.row.picture]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="课程名"></el-table-column>
      <el-table-column prop="description" label="简介" width="200"></el-table-column>
      <el-table-column prop="courseNature" label="性质"></el-table-column>
      <el-table-column prop="num" label="课程规划" width="100"></el-table-column>
      <el-table-column prop="courseFees" label="课程收费" width="100"></el-table-column>
      <el-table-column prop="recruit" label="人数" width="60"></el-table-column>

      <el-table-column prop="createTime" label="申请时间" width="100"></el-table-column>
<!--      <el-table-column prop="openTime" label="开课时间" width="100"></el-table-column>-->
      <el-table-column prop="state" label="状态" width="100"></el-table-column>

      <el-table-column label="操作" align="center" width="120">
        <template slot-scope="scope">
          <el-button type="success" @click="pay(scope.row)"><i class="el-icon-wallet"></i> 编 辑</el-button>

        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="课程设置" :visible.sync="dialogFormVisible" width="42%" :close-on-click-modal="false">
      <el-form label-width="110px" :model="addCourse" ref="addCourse" status-icon :rules="rules"
                   size="small" style="display: inline-block">

        <el-form-item label="课程名称">
          <el-input v-model="addCourse.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="课程描述">
          <el-input v-model="addCourse.description" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="课程性质" >
          <el-select v-model="addCourse.courseNature" placeholder="">
            <el-option label="健身操" value="健身操"></el-option>
            <el-option label="搏击" value="搏击"></el-option>
            <el-option label="瑜伽" value="瑜伽"></el-option>
            <el-option label="综合有氧" value="综合有氧"></el-option>
            <el-option label="增肌" value="增肌"></el-option>
            <el-option label="塑形" value="塑形"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程人数" prop="recruit">
          <el-input v-model.number="addCourse.recruit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程规划" >
          <el-select v-model="addCourse.num" placeholder="">
            <el-option label="8节" value="8"></el-option>
            <el-option label="12节" value="12"></el-option>
            <el-option label="16节" value="16"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="课程收费"
                      prop="courseFees"
        >
          <el-input v-model="addCourse.courseFees" oninput="value = value.toString().match(/^\d+(?:\.\d{0,2})?/)"
                    autocomplete="off"></el-input>
        </el-form-item>


<!--        <el-form-item label="开课时间">-->
<!--          <el-date-picker v-model="addCourse.openTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="日期"></el-date-picker>-->
<!--        </el-form-item>-->
      </el-form>


      <div class="img">
        课程图片
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8081/file/upload"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload"
          :on-success="handleAvatarSuccess"
        >
          <img v-if="this.addCourse.picture" :src="this.addCourse.picture">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add('addCourse')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="商品信息" :visible.sync="dialogFormVisible1" width="50%" :close-on-click-modal="false">
      <el-table :data="goodsList" border stripe>
        <el-table-column prop="name" label="商品名称"></el-table-column>
        <el-table-column prop="price" label="价格" width="80"></el-table-column>
        <el-table-column prop="description" label="商品描述" width="150"></el-table-column>
        <el-table-column label="图片" width="180">
          <template slot-scope="scope">
            <el-image style="width: 150px; height: 150px" :src="scope.row.picture" :preview-src-list="[scope.row.picture]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="下单时间" width="100"></el-table-column>
        <el-table-column prop="num" label="购买数量" width="80"></el-table-column>

      </el-table>
    </el-dialog>

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
    name: "CoachCourse",
    data() {
      var checkCourseFees = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('课程收费不能为空'));
        }
        setTimeout(() => {
            if (value < this.midValue * 0.8 || value > this.midValue * 1.2) {
              callback(new Error('收费须在给定区间内'));
            } else {
              callback();
            }
        }, 1000);
      };
      var checkRecruit = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('课程人数不能为空'));
        }
        setTimeout(() => {
          if (value > 10) {
            callback(new Error('人数不能超过'+ this.recruitNum +'人'));
          } else {
            callback();
          }
        }, 1000);
      };
      return{
        goodsList: [],
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        name: "",
        form: {},
        dialogFormVisible: false,
        dialogFormVisible1: false,
        multipleSelection: [],
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        totalPrice: 0,
        recruitNum:'',
        limit: {},
        midValue: 4000,

        addCourse: {courseFees:"",recruit:""},
        rules: {
          courseFees: [
            { validator: checkCourseFees, trigger: 'blur' }
          ],
          recruit: [
            { validator: checkRecruit, trigger: 'blur' }
          ]
        }

      }

    },
    created() {
      this.load()

    },

    methods: {
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isLt2M;
      },
      handleAvatarSuccess(res) {
        // res就是文件的路径
        this.addCourse.picture = res
        this.load()
      },
      add(addCourse) {
        this.$refs[addCourse].validate((valid) => {
          if (valid) {
            this.addCourse.coachId = this.user.userId
            this.$axios.post("http://localhost:8081/course/save", this.addCourse).then(res =>{

              this.dialogFormVisible = false
              this.load()
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      viewGoods(orderId) {
        this.$axios.get("http://localhost:8081/order/getGoodsById/" + orderId).then(res =>{
          this.goodsList = res.data.data
          this.dialogFormVisible1 = true
          console.log(this.goodsList)
        })
      },
      load() {
        this.$axios.get("http://localhost:8081/course/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
            Id: this.user.userId
          }
        }).then(res => {
          this.tableData = res.data.data.records
          this.total = res.data.data.total
        })
        this.$axios.get("http://localhost:8081/coach/limit/" + this.user.userId).then(res => {
          this.limit = res.data.data
          this.recruitNum = this.limit.maxPeople - this.limit.recruit

        })
      },
      del(id){
        console.log(id)
        this.$axios.delete("http://localhost:8081/order/" + id).then(res => {
          if (res.data.code == 200) {
            this.$message.success("取消成功")
            this.load()
          }else {
            this.$message.error("取消失败")
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
  .img{
    text-align: center;
    margin: 10px;
    display: inline-block;
    position: relative;
    left: 30px;
    top: -30px;
  }
  .img .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 200px;
    height: 200px;
    margin-top: 10px;
    display: block;
  }
  .img .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .img .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .img img{
    width: 200px;
    height: 200px;
    object-fit: cover;
  }
</style>
