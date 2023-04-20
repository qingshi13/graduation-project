<template>
  <div>
    <el-card class="information">
      <el-descriptions title="个人信息" :column=1>
        <br>
        <el-descriptions-item label="用户名">{{ member.name }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ member.phoneNumber }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ member.sex }}</el-descriptions-item>
        <el-descriptions-item label="余额">{{ member.remainingSum }}</el-descriptions-item>
        <el-descriptions-item label="积分">{{ member.accumulatePoints }}</el-descriptions-item>
        <el-descriptions-item label="会员到期时间">{{ member.expirationTime }}</el-descriptions-item>
        <el-descriptions-item label="会员状态">{{ member.state }}</el-descriptions-item>
        <el-descriptions-item label="配送地址">{{ member.address }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card class="revise">
      <el-form label-width="40px">
        <div style="text-align: center; margin: 10px;">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8081/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="form.picture" :src="form.picture" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <br>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名字">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phoneNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center;display: block">
          <el-button type="primary" @click="save">修 改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>
<script>
  export default {
    name: 'Information',
    data() {
      return {
        form:{},
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        member:{}
      }
    },
    created() {
      this.load()
    },
    methods: {
      load() {
        const memberId = this.user.userId
        if(!memberId){
          this.$message.error("无法获取用户信息")
          return
        }
        this.$axios.get("http://localhost:8081/member/getByMemberId/" + memberId).then(res =>{
          this.form = res.data.data
          this.member.name = res.data.data.name
          this.member.phoneNumber = res.data.data.phoneNumber
          this.member.sex = res.data.data.sex
          this.member.address = res.data.data.address
          this.member.remainingSum = res.data.data.remainingSum
          this.member.accumulatePoints = res.data.data.accumulatePoints
          this.member.expirationTime = res.data.data.expirationTime
          this.member.state = res.data.data.state
          console.log(res.data.data)
        })
      },
      save() {
        this.$axios.post("http://localhost:8081/member/save", this.form).then(res => {
          if (res.data.code == 200) {
            this.$message.success("保存成功")
            this.load()
            this.$emit('refreshUser')
          } else {
            this.$message.error("保存失败")
          }
        })
      },
      handleAvatarSuccess(res) {
        // res就是文件的路径
        this.form.picture = res
      }
    }
  }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 128px;
    height: 128px;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 128px;
    height: 128px;
    line-height: 128px;
    text-align: center;
  }
  .avatar {
    width: 128px;
    height: 128px;
    display: block;
  }
  .information{
    width: 400px;
    position: relative;
    left: 90px;
    top: -130px;
    display: inline-block;

  }
  .revise{
    position: relative;
    left: 230px;
    top: 30px;
    width: 500px;
    display: inline-block;
  }
</style>
