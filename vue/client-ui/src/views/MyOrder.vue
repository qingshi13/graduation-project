<template>
  <div>
    <div style="margin: 10px">
      <el-input style="width: 400px" placeholder="请输入名称" clearable size="medium" suffix-icon="el-icon-search" v-model="name"></el-input>

      <el-select v-model="state" size="medium" @change="load" placeholder="订单状态">
        <el-option label="全部" value=""></el-option>

        <el-option label="待付款" value="待付款"></el-option>
        <el-option label="已支付" value="已支付"></el-option>

      </el-select>

      <el-button class="ml-5" type="primary" @click="load" size="medium"><i class="el-icon-search"></i> 搜 索</eL-button>
    </div>

    <el-table :data="tableData" border stripe size="medium">
      <el-table-column prop="no" label="编号"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="totalPrice" label="总金额"></el-table-column>
      <el-table-column prop="state" label="状态"></el-table-column>
      <el-table-column prop="time" label="下单时间" width="100"></el-table-column>
      <el-table-column prop="payTime" label="付款时间" width="100"></el-table-column>
      <el-table-column prop="userName" label="用户账号"></el-table-column>
      <el-table-column prop="nickName" label="用户昵称"></el-table-column>
      <el-table-column label="查看商品" width="180"align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="viewGoods(scope.row.orderId)"><i class="el-icon-edit"></i> 查看商品</el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="240" align="center">
        <template slot-scope="scope" >
          <el-popconfirm
            v-show="scope.row.state == '待付款'"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定购买吗?"
            @confirm="pay(scope.row.orderId)"
          >
            <el-button type="success" slot="reference"><i class="el-icon-wallet"></i> 付 款</el-button>
          </el-popconfirm>
          <el-popconfirm
            v-show="scope.row.state == '待付款'"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定取消吗?"
            @confirm="del(scope.row.orderId)"
          >
            <el-button type="danger" slot="reference"><i class="el-icon-remove-outline"></i> 取 消</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

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
    name: "MyOrder",
    data() {
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
        state: "",
      }
    },
    created() {
      this.load()
    },
    methods: {
      pay(id){
        this.$axios.post("http://localhost:8081/order/pay/" , {orderId:id}).then(res =>{
          if (res.data.code == 200) {
            console.log(res.data)
            this.$message.success(res.data.data)
            this.load()
          }else {
            this.$message.error(res.data.message)
            console.log(res.data)
          }
        })
      },
      viewGoods(orderId) {
        this.$axios.get("http://localhost:8081/order/getGoodsById/" + orderId).then(res =>{
          this.goodsList = res.data.data
          this.dialogFormVisible1 = true
          console.log(this.goodsList)
        })
      },
      load() {
        this.$axios.get("http://localhost:8081/order/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
            state: this.state,
            Id: this.user.userId
          }
        }).then(res => {
          this.tableData = res.data.data.records
          this.total = res.data.data.total
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
</style>
