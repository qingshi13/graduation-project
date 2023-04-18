<template>
  <div class="loginregister">
    <div class="contain">
      <div class="big-box" :class="{active:isLogin}">
        <div class="big-contain" v-if="isLogin">
          <div class="btitle">账户登录</div>
          <el-form class="bform" :model="form" ref="form" @submit.native.prevent="login">
              <el-input class="input" v-model="form.userphone" placeholder="请输入手机号"></el-input>
              <el-input class="input" placeholder="请输入验证码" v-model="form.userpwd"></el-input>
            <el-button class="code" type="primary" @click="code">获取验证码</el-button>
            <el-button class="button" type="primary" native-type="submit" round>登录</el-button>
          </el-form>
        </div>
        <div class="big-contain" v-else>
          <div class="btitle" style="color: rgb(56,183,145);">创建账户</div>

            <el-form class="bform" :model="form" ref="form" @submit.native.prevent="register">
              <el-input class="input" v-model="form.useraccount" placeholder="请输入用户名"></el-input>

              <el-input class="input" v-model="form.userphone" placeholder="请输入手机号"></el-input>

              <el-input class="input" placeholder="请输入验证码" v-model="form.userpwd"></el-input>

              <el-button class="code" style="top:244px;background-color: rgb(56,183,145);" type="primary" @click="code">获取验证码</el-button>

              <el-button class="button" style="background-color: rgb(56,183,145);position:relative;top:30px"
                         type="primary" round native-type="submit">注册</el-button>
            </el-form>
<!--            <el-input class="input" placeholder="请确认密码" v-model="form.userpwd" show-password></el-input>-->


        </div>
      </div>

      <div class="small-box" :class="{active:isLogin}">
        <div class="small-contain" v-if="isLogin">
          <div class="stitle">你好，朋友!</div>
          <p class="scontent">开始注册，和我们一起运动</p>
          <el-button class="button" style="background-color: transparent;"
                     type="primary" round @click="changeType">注册</el-button>
        </div>
        <div class="small-contain" v-else>
          <div class="stitle">欢迎回来!</div>
          <p class="scontent">从这里登录你的账户</p>
          <el-button class="button" style="background-color: transparent;"
                     type="primary" round @click="changeType">登录</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { Message } from "element-ui";

  export default{
    name:'LoginRegister',
    data(){
      return {
        isLogin:true,
        form:{
          useraccount:'',
          userphone:'',
          userpwd:''
        },
        loading:false,
      }
    },
    methods:{
      changeType() {
        this.isLogin = !this.isLogin
        this.form.useraccount = ''
        this.form.userphone = ''
        this.form.userpwd = ''
      },

      code() {
        const param = new URLSearchParams();
        param.append("phone", this.form.userphone);
        this.$axios.post("http://localhost:8081/code",param).then(res =>{
          if (res.data.code == 200){
            this.$message.success(res.data.message)
          }else {
            this.$message.error(res.data.message)
          }
        })
      },
      login() {
        this.$axios.post("http://localhost:8081/login/",{phone:this.form.userphone,code:this.form.userpwd}).then(res =>{
          if(res.data.code == 200){
            console.log(res.data)
            localStorage.setItem('user', JSON.stringify(res.data.data));
            if (res.data.data.role == "member") {
              this.$router.push({
                path: "/member/home",
              });
            }
            else if (res.data.data.role == "coach") {
              this.$router.push({
                path: "/coach/home",
              });
            }
            else if (res.data.data.role == "admin") {
              this.$router.push({
                path: "/admin/home",
              });
            }
          }
          else {
            this.$message.error(res.data.message)
          }
        })
      },
      register(){
        this.$axios.post("http://localhost:8081/register/",{nickName:this.form.useraccount,phone:this.form.userphone,code:this.form.userpwd}).then(res =>{
          if(res.data.code == 200){
            console.log(res.data)
            localStorage.setItem('user', JSON.stringify(res.data.data));
            this.$router.push({
              path: "/member/home",
            });
          }
          else {
            this.$message.error(res.data.message)
          }
        })
      }
    }
  }
</script>

<style scoped="scoped">
  .input{
    width: 50%;
  }
  .loginregister{
    width: 1500px;
    height: 750px;
    box-sizing: border-box;
  }
  .contain{
    width: 60%;
    height: 60%;
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    background-color: #fff;
    border-radius: 20px;
    box-shadow: 0 0 3px #f0f0f0,
    0 0 6px #f0f0f0;
  }
  .big-box{
    width: 70%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 30%;
    transform: translateX(0%);
    transition: all 1s;
  }
  .big-contain{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .btitle{
    font-size: 1.5em;
    font-weight: bold;
    color: rgb(57,167,176);
  }
  .bform{
    width: 100%;
    height: 50%;
    padding: 2em 0;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }
  .bform .errTips{
    display: block;
    width: 50%;
    text-align: left;
    color: red;
    font-size: 0.7em;
    margin-left: 1em;
  }
  .button{
    width: 130px;
    height: 40px;
    background-color: rgb(57,167,176);
    font-size: 1em;
    border: 1px solid #fff;

  }
  .code{
    width: 120px;
    height: 40px;
    background-color: rgb(57,167,176);
    font-size: 1em;
    border: 1px solid #fff;
    position: absolute;
    left: 480px;
  }
  .small-box{
    width: 30%;
    height: 100%;
    background: linear-gradient(135deg,rgb(57,167,176),rgb(56,183,145));
    position: absolute;
    top: 0;
    left: 0;
    transform: translateX(0%);
    transition: all 1s;
    border-top-left-radius: inherit;
    border-bottom-left-radius: inherit;
  }
  .small-contain{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .stitle{
    font-size: 1.5em;
    font-weight: bold;
    color: #fff;
  }
  .scontent{
    font-size: 0.8em;
    color: #fff;
    text-align: center;
    padding: 2em 4em;
    line-height: 1.7em;
  }
  .big-box.active{
    left: 0;
    transition: all 0.5s;
  }
  .small-box.active{
    left: 100%;
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    border-top-right-radius: inherit;
    border-bottom-right-radius: inherit;
    transform: translateX(-100%);
    transition: all 1s;
  }
</style>
