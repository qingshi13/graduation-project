<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>
    <div style="margin: 10px">
      <el-button class="ml-5" type="success" @click="dialogFormVisible = true" size="medium"><i class="el-icon-circle-plus-outline"></i> 添 加</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column label="图片" width="180">
        <template slot-scope="scope">
          <el-image style="width: 150px; height: 150px" :src="scope.row.picture" :preview-src-list="[scope.row.picture]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品名字" ></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170"></el-table-column>
      <el-table-column prop="description" label="商品简介" ></el-table-column>
      <el-table-column prop="store" label="商品库存" width="100"></el-table-column>
      <el-table-column prop="price" label="商品价格" width="100"></el-table-column>
      <el-table-column label="操作" align="center" width="120">
        <template slot-scope="scope">
          <el-button type="success" @click="edit(scope.row.goodId)"><i class="el-icon-edit"></i> 编 辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="商品添加" :visible.sync="dialogFormVisible" width="42%" @close="handleClose">
      <el-form label-width="110px" :model="addGood" ref="addGood"
               size="small" style="display: inline-block;position:relative;top:-20px">

        <el-form-item label="商品名称">
          <el-input v-model="addGood.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品描述">
          <el-input v-model="addGood.description" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品数量" prop="recruit">
          <el-input v-model.number="addGood.store" @input="limitInput($event,'store')" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品价格"
                      prop="price"
        >
          <el-input v-model="addGood.price" @input="limitInput($event,'price')" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div class="img1">
        商品图片
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8081/file/upload"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload"
          :on-success="handleAvatarSuccess"
        >
          <img v-if="this.addGood.picture" :src="this.addGood.picture">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="商品设置" :visible.sync="dialogFormVisible1" width="42%" >
      <el-form label-width="110px" :model="form" ref="form"
               size="small" style="display: inline-block;position:relative;top:-20px">

        <el-form-item label="商品名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品数量" prop="recruit">
          <el-input v-model.number="form.store" @input="limitInput($event,'store')" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品价格"
                      prop="price"
        >
          <el-input v-model="form.price" @input="limitInput($event,'price')" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div class="img1">
        商品图片
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8081/file/upload"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload"
          :on-success="handleAvatarSuccess"
        >
          <img v-if="this.form.picture" :src="this.form.picture">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="save(form)">修 改</el-button>
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
    name: "AdminGood",
    data(){
      return{
        addGood: {},
        form: {},
        position: "",
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        dialogFormVisible: false,
        dialogFormVisible1: false,

        name: "",
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      }
    },
    created() {
      this.load()
    },
    methods: {
      limitInput(value, name) {
        this.addGood[name] =
          ("" + value) // 第一步：转成字符串
            .replace(/[^\d^\.]+/g, "") // 第二步：把不是数字，不是小数点的过滤掉
            .replace(/^0+(\d)/, "$1") // 第三步：第一位0开头，0后面为数字，则过滤掉，取后面的数字
            .replace(/^\./, "0.") // 第四步：如果输入的第一位为小数点，则替换成 0. 实现自动补全
            .match(/^\d*(\.?\d{0,2})/g)[0] || ""; // 第五步：最终匹配得到结果 以数字开头，只有一个小数点，而且小数点后面只能有0到2位小数
      },
      handleClose() {
        for (let key in this.addGood) {
          this.addGood[key] = '';
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
        this.addGood.picture = res
        this.load()
      },
      load() {
        this.$axios.get("http://localhost:8081/good/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
          }
        }).then(res => {
          this.tableData = res.data.data.records
          this.total = res.data.data.total
          console.log(this.tableData)
        })
      },
      add() {
        this.$axios.post("http://localhost:8081/good/save", this.addGood).then(res =>{
          if (res.data.code == 200){
            this.dialogFormVisible = false
            this.load()
            this.$message.success("添加成功")
          }else {
            this.$message.error(res.data.message)
          }
        })
      },
      edit(goodId) {

        this.$axios.get("http://localhost:8081/good/getByGoodId/" + goodId).then(res =>{
          if (res.data.code == 200){
            this.form = res.data.data
            this.dialogFormVisible1 = true
          }else {
            this.$message.error("获取失败")
          }
        })
      },
      save(good){
        this.$axios.post("http://localhost:8081/good/save",good).then(res =>{
          if (res.data.code == 200){
            this.dialogFormVisible1 = false
            this.load()
            this.$message.success("修改成功")
          }else {
            this.$message.error(res.data.message)
          }
        })
        console.log(good)
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
  .img1{
    text-align: center;
    margin: 20px;
    display: inline-block;
    position: relative;
    left: 30px;

  }
  .img1 .avatar-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 200px;
    height: 200px;
    margin-top: 20px;
    display: block;
  }
  .img1 .avatar-uploader:hover {
    border-color: #409EFF;
  }
  .img1 .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .img1 img{
    width: 200px;
    height: 200px;
    object-fit: cover;
  }
</style>
