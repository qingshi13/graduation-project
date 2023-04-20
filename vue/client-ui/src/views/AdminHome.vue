<template>
  <div style="width: 1200px">
    <el-carousel :interval="4000" type="card" height="300px" >
      <el-carousel-item v-for="(item, index) in dataList" :key="index">
        <h3 class="medium" style="position: relative;top:-60px;left: 40px;font-size:24px">{{ item.title }}</h3>
        <p style="position: relative;top:-120px;left: 40px;font-size:18px">{{ item.content }}</p>
        <span class="time" style="position: relative;top:40px;left: 400px">{{ item.time }}</span>
      </el-carousel-item>
    </el-carousel>

    <el-card class="revise">
      <el-form label-width="40px">

        <el-form-item label="标题">
          <el-input v-model="form.title" size="small" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea"
                    :rows="3"
                    placeholder="请输入内容" autocomplete="off"></el-input>
        </el-form-item>


        <el-button style="position: relative;left: 386px" type="primary" @click="release">发 布</el-button>

      </el-form>
    </el-card>
  </div>

</template>

<script>
  export default {
    name: "AdminHome",
    data(){
      return{
        form: {},
        data: {},
        dataList: [
          {
            title: "标题1",
            content: "内容1",
            time: "2023-04-20 10:00:00"
          },
          {
            title: "标题2",
            content: "内容2",
            time: "2023-04-20 12:00:00"
          },
          {
            title: "标题3",
            content: "内容3",
            time: "2023-04-20 14:00:00"
          },
          {
            title: "标题4",
            content: "内容4",
            time: "2023-04-20 16:00:00"
          },
          {
            title: "标题5",
            content: "内容5",
            time: "2023-04-20 18:00:00"
          },
          {
            title: "标题6",
            content: "内容6",
            time: "2023-04-20 20:00:00"
          }
        ],
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      }
    },

    created(){
      this.load()
    },
    methods: {
      load() {
        const AdminId = this.user.userId
        if(!AdminId){
          this.$message.error("无法获取用户信息")
          this.$router.push('/login')
          return
        }

      },
      release() {
        this.$axios.post("http://localhost:8081/message/release", this.form).then(res => {
          if (res.data.code == 200) {
            this.$message.success("发布成功")
            this.load()
          } else {
            this.$message.error("保存失败")
          }
        })
      }
    }
  }
</script>
<style>
  .revise{
    position: relative;
    left: 400px;
    top: 30px;
    width: 500px;
    display: inline-block;
  }
  .el-carousel {
    position: relative;
    left: 47px;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
