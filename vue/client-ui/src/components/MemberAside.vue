<template>
  <div>
    <div slot="title" class="lhjs">
      <img :src="require('../assets/健身.png')">
      <h1>领航健身</h1>
    </div>
    <el-menu router
             :default-active="this.$router.path"
    >

      <el-submenu index="1">
        <template slot="title"><i class="el-icon-user"></i>个人中心</template>
        <el-menu-item-group>
          <el-menu-item index="/member/information">修改信息</el-menu-item>
          <el-menu-item index="/member/rechargemanagement">充值管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title"><i class="el-icon-tickets"></i>私教预约</template>
        <el-menu-item-group>
          <el-menu-item index="/member/allcoachs">全部教练</el-menu-item>
          <el-menu-item index="/member/allcourses">全部课程</el-menu-item>
          <el-menu-item index="/member/mycourse">我的课程</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title"><i class="el-icon-shopping-cart-2"></i>会员商店</template>
        <el-menu-item-group>
          <el-menu-item index="/member/allgoods">全部商品</el-menu-item>
          <el-menu-item index="/member/cart">我的购物车</el-menu-item>
          <el-menu-item index="/member/myorder">我的订单</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>

</template>
<style>
  .lhjs{
    width: 200px;
    height: 61px;
    background-color: white;
  }
  .lhjs img {
    width: 20%;
    position: relative;
    left: 20px;
    top: 12px;
  }
  .lhjs h1{
    position: relative;
    left: 25px;
    font-size: 22px;
    display: inline-block;
  }
</style>
