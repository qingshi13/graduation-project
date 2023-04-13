<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium" @selection-change="handleSelectionChange">

      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="" label="商品图片">
        <template v-slot="scope">
          <el-image style="width: 100px; height: 100px" :src="scope.row.goodPicture" :preview-src-list="[scope.row.goodPicture]"></el-image>
        </template>
      </el-table-column>

      <el-table-column prop="goodName" label="商品"></el-table-column>

      <el-table-column prop="price" label="价格" width="100">
        <template v-slot="scope">
          <span style="color: orangered">￥ {{ scope.row.price }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="num" label="商品数量">
        <template v-slot="scope">
          <el-input-number v-model="scope.row.num" :min="1" :max="100" label="数量" @change="changeNum(scope.row)"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="添加时间"></el-table-column>

      <el-table-column label="操作" width="180" align="center">
        <template slot-scope="scope">
          <el-popconfirm
            class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗?"
            @confirm="del(scope.row.cartId)"
          >
            <el-button type="danger" slot="reference"><i class="el-icon-remove-outline"></i> 删 除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
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

    <div style="margin: 10px ; text-align: right">
      <div style="padding: 10px 0">当前已选商品总价: <span style="color: orangered">￥ {{ totalPrice }}</span></div>
      <el-button style="color: white; background-color: orangered" size="medium" @click="settleAccount"><i class="el-icon-coin"></i> 结 算</el-button>
    </div>

<!--    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">-->
<!--      <el-form label-width="100px" size="small" style="">-->
<!--        <el-form-item label="商品id">-->
<!--          <el-input v-model="form.goodId" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="用户id">-->
<!--          <el-input v-model="form.userId" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="商品数量">-->
<!--          <el-input v-model="form.num" autocomplete="off"></el-input></el-form-item>-->
<!--        <el-form-item label="添加时间">-->
<!--          <el-date-picker v-model="form.time" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="日期"></el-date-picker>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--        <el-button type="primary" @click="save">确 定</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
  </div>
</template>
<script>
  export default {
    name:"Cart",
    data() {
      return{
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        name: "",
        form: {},
        dialogFormVisible: false,
        multipleSelection: [],
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        totalPrice: 0
      }
    },
    created() {
      this.load()
    },
    methods: {
      settleAccount (){
        if (!this.multipleSelection || !this.multipleSelection.length){
          this.$message.error("请选择需要结算的商品")
          return
        }

        let data = {name: this.multipleSelection[0].goodName, totalPrice: this.totalPrice,
          cart: this.multipleSelection, userId: this.user.userId}
        this.$axios.post("http://localhost:8081/order", data).then(res => {
          if (res.data.code == 200 ) {
            this.$message.success("结算成功")
            this.load()
          } else {
            this.$message.error(res.data.message)
          }
        })
        console.log(data)
      },
      changeNum (row){
        this.$axios.post("http://localhost:8081/cart/num/" + row.cartId + "/" + row.num).then(res =>{
          this.totalPrice = 0

          // 计算总价格
          this.multipleSelection.forEach(item => {
            this.totalPrice += item.price * item.num
          })
          this.totalPrice = this.totalPrice.toFixed( 2)
        })
      },
      load() {
        this.$axios.get("http://localhost:8081/cart/page", {
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
      },
      // save()  {
      //   this.$axios.post("http://localhost:8081/cart", this.form).then(res => {
      //     if (res.code == 200 ) {
      //       this.$message.success("保存成功")
      //       this.dialogFormVisible = false
      //       this.load()
      //     } else {
      //       this.$message.error("保存失败")
      //     }
      //   })
      // },
      // handleAdd() {
      //   this.dialogFormVisible = true
      //   this.form = {}
      //   this.$nextTick(() => {
      //     if (this.$refs.img) {
      //       this.$refs.img.clearFiles();
      //     }
      //     if (this.$refs.file) {
      //       this.$refs.file.clearFiles();
      //     }
      //   })
      // },
      // handleEdit(row) {
      //   this.form = JSON.parse(JSON.stringify(row))
      //   this.dialogFormVisible = true
      //   this.$nextTick(() => {
      //     if (this.$refs.img) {
      //       this.$refs.img.clearFiles();
      //     }
      //     if (this.$refs.file) {
      //       this.$refs.file.clearFiles();
      //     }
      //   })
      // },
      del(id){
        this.$axios.delete("http://localhost:8081/cart/" + id).then(res => {
          if (res.data.code == 200) {
            this.$message.success("删除成功")
            this.load()
          }else {
            this.$message.error("删除失败")
          }
        })
      },
      handleSelectionChange(val) {
        this.totalPrice = 0
        this.multipleSelection = val
        // 进行计算操作
        if (val && val.length) {
          val.forEach(item => {
            this.totalPrice += item.num * item.price
          })
          this.totalPrice = this.totalPrice.toFixed( 2)

        }
      },
      // delBatch() {
      //   if (!this.multipleSelection.length) {
      //     this.$message.error("请选择需要删除的数据")
      //     return
      //   }
      //   let ids = this.multipleSelection.map(v => v.id) // [小，，] => [1,2,3]
      //   this.request.post("http://localhost:8081/cart/del/batch", ids).then(res => {
      //     if (res.code == 200) {
      //       this.$message.success("批量删除成功")
      //       this.load()
      //     } else {
      //       this.$message.error("批量删除失败")
      //     }
      //   })
      // },
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
