<template>
  <div style="padding: 10px">
    <el-card>
      <div style="display: flex;">
        <div style="width: 300px">
          <el-image :src="good.picture" :preview-src-list="[good.picture]" style="width: 100%"></el-image>
        </div>
        <div style="flex: 1; padding-left: 50px">
          <div class="item1"><h1 style="font-size: 24px">{{ good.name }}</h1></div>
          <div class="item1" style="font-size: 14px; ">{{ good.description }}</div>
          <div class="item1" style="color: orangered">价格 ￥ {{ good.price }}</div>
          <div class="item1" style="font-size: 14px; ">库存 {{ good.store }}<span style="margin-left: 10px">件</span></div>
          <div class="item1">
            <el-input-number v-model="form.num" :min="1" :max="100" label="描达文字"></el-input-number>
            <span style="margin-left: 10px">件</span>
          </div>
          <div class="item1" style="margin-top: 25px">
            <el-button size="medium" style="background-color: red; color: white" v-on:click="addorder"><i class="el-icon-wallet"></i> 直接购买</el-button>
            <el-button size="medium" style="background-color: red; color: white" v-on:click="addcart"><i class="el-icon-shopping-cart-2" ></i> 加入购物车</el-button>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "Detail",
    data() {
      return {
        id: this.$route.query.id,
        good: {},
        form: { num: 1},
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

      }
    },
    created() {
      this.load()
    },
    methods: {
      load() {
        this.$axios.get("http://localhost:8081/good/getByGoodId/" + this.id).then(res => {
          this.good = res.data.data

        })
      },
      addcart() {
        if (!this.user.nickName){
          this.$message.warning("请登录后操作")
          return
        }
        this.form.userId = this.user.userId
        this.form.goodId = this.id
        this.$axios.post("http://localhost:8081/cart/" , this.form).then(res => {
          if (res.data.code == 200){
            this.$message.success("加入购物车成功")
          } else{
            this.$message.error(res.data.message)
          }
        })
      },
      addorder() {
        let data = {name: this.good.name, totalPrice: this.good.price * this.form.num,
          goodId: this.good.goodId, num: this.form.num, userId: this.user.userId}
        this.$axios.post("http://localhost:8081/order", data).then(res => {
          if (res.data.code == 200 ) {
            this.$message.success("结算成功")
            this.load()
          } else {
            this.$message.error(res.data.message)
          }
        })
      }
    }
  }
</script>

<style scoped>
  .item1{
    padding: 10px;
    color: #666;
    margin-bottom: 5px;
  }
</style>
