
<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">

      <Aside></Aside>
    </el-aside>

    <el-container>
      <el-header>
        <Header :user="user"></Header>
      </el-header>

      <el-main>
        <router-view @refreshUser="getUser"></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>
<script>
  import Aside from "../components/MemberAside"
  import Header from "../components/Header"

  export default {
    name:'Member',
    components:{
      Aside,
      Header
    },
    data(){
      return {
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      }
    },
    created() {
      this.getUser()
    },
    methods:{
      getUser(){
        const memberId = this.user.userId
        if(!memberId){
          this.$message.error("无法获取用户信息")
          return
        }
        this.$axios.get("http://localhost:8081/member/getByMemberId/" + memberId).then(res =>{
          this.user = res.data.data
          this.user.userId = res.data.data.memberId
        })
      }
    }
  };
</script>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
</style>
