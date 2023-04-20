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
      <el-table-column prop="state" label="状态" width="100"></el-table-column>

    </el-table>

    <el-dialog title="课程设置" :visible.sync="dialogFormVisible" width="42%" @close="handleClose">
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
          <el-input v-model.number="addCourse.recruit" type="number" autocomplete="off"></el-input>
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
          <el-input v-model="addCourse.courseFees" @input="limitInput($event,'courseFees')" autocomplete="off"></el-input>
        </el-form-item>


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
        if (value > this.recruitNum) {
          callback(new Error('人数不能超过'+ this.recruitNum +'人'));
        } else {
          callback();
        }


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
      limitInput(value, name) {
        this.addCourse[name] =
          ("" + value) // 第一步：转成字符串
            .replace(/[^\d^\.]+/g, "") // 第二步：把不是数字，不是小数点的过滤掉
            .replace(/^0+(\d)/, "$1") // 第三步：第一位0开头，0后面为数字，则过滤掉，取后面的数字
            .replace(/^\./, "0.") // 第四步：如果输入的第一位为小数点，则替换成 0. 实现自动补全
            .match(/^\d*(\.?\d{0,2})/g)[0] || ""; // 第五步：最终匹配得到结果 以数字开头，只有一个小数点，而且小数点后面只能有0到2位小数
      },
      handleClose() {
        for (let key in this.addCourse) {
          this.addCourse[key] = '';
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
        this.addCourse.picture = res
        this.load()
      },
      add(addCourse) {
        this.$refs[addCourse].validate((valid) => {
          if (valid) {
            this.addCourse.coachId = this.user.userId
            this.$axios.post("http://localhost:8081/course/save", this.addCourse).then(res =>{
              if (res.data.code == 200) {
                this.dialogFormVisible = false
                this.$message.success("提交审核")
                this.load()
              }else {
                this.$message.error(res.data.message)
              }

            })
          }
        });
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
    margin: 20px;
    display: inline-block;
    position: relative;
    left: 30px;
    top: -20px;
  }
  .img .avatar-uploader{
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    overflow: hidden;
    width: 200px;
    height: 200px;
    margin-top: 20px;
    display: block;
  }
  .img .avatar-uploader:hover {
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
