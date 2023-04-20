<template>
  <div>
    <router-link to = "home" class="shouye"><i class="el-icon-s-home"></i>首页</router-link>
    <el-dropdown style="cursor: pointer; left: 1070px">
      <div style="display: inline-block">
        <el-avatar :src="user.picture" v-if="user.picture"></el-avatar>
        <el-avatar icon="el-icon-user-solid" v-else></el-avatar>
        <span style="
    position: relative ; top: -5px" >{{ user.nickName }}</span>
      </div>
      <el-dropdown-menu slot="dropdown" >
        <el-dropdown-item>
          <span style="text-decoration: none;" @click="logout()">退 出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
  import { Message } from "element-ui";

  export default{
    name:'Header',
    props:{
      user: Object
    },

    data(){
      return {

      }
    },
    methods: {
      logout() {
        console.log("tuichu")
        window.localStorage.clear()
        this.$router.push('/login')
      }
    }

  }
</script>

<style>
  .shouye {
    position: relative;
    left: 47px;
    text-decoration: none;
    color: black;
    font-size: 20px;
   }
  .shouye i{
    position: relative;
    top: 1px;
    padding-right: 5px;
    font-size: 23px;
  }
  .el-avatar{
    position: relative;
    top: 10px;
  }
</style>
