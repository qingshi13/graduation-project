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
        <template slot="title"><i class="el-icon-user"></i>信息管理</template>
        <el-menu-item-group>
          <el-menu-item index="/coach/information">修改信息</el-menu-item>
          <el-menu-item index="/coach/rechargemanagement">充值管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title"><i class="el-icon-tickets"></i>课程管理</template>
        <el-menu-item-group>
          <el-menu-item index="/coach/mycourse">我的课程</el-menu-item>
          <el-menu-item index="/coach/mystudent">我的学员</el-menu-item>
          <el-menu-item index="/coach/myappointment">我的预约</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title"><i class="el-icon-shopping-cart-2"></i>会员商店</template>
        <el-menu-item-group>
          <el-menu-item index="/coach/allgoods">全部商品</el-menu-item>
          <el-menu-item index="/coach/cart">我的购物车</el-menu-item>
          <el-menu-item index="/coach/myorder">我的订单</el-menu-item>
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
