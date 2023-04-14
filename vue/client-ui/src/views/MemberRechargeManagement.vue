<template>
  <div>
    <el-card class="recharge">
      <el-descriptions title="用户充值" :column=1>
        <el-descriptions-item label="余额">{{ member.remainingSum }}</el-descriptions-item>

        <el-descriptions-item v-if="this.user.role == 'member'" label="会员到期时间">{{ member.expirationTime }}</el-descriptions-item>

        <el-descriptions-item v-if="this.user.role == 'member'" label="会员状态">{{ member.state }}</el-descriptions-item>

      </el-descriptions>
      <el-button type="primary" @click="dialogFormVisible1 = false,dialogFormVisible = true">充 值</el-button>
      <el-button v-if="this.user.role == 'member'" type="primary" @click="dialogFormVisible1 = true,dialogFormVisible = false">续 费</el-button>

    </el-card>

    <el-dialog title="充值" :visible.sync="dialogFormVisible" class="dialog" :modal="false" :close-on-click-modal="false">
      <el-form label-width="80px" :model="recharge" ref="recharge" status-icon size="small">

        <el-form-item label="余额充值">
          <el-input v-model.number="recharge.account" placeholder="充值金额"></el-input>
        </el-form-item>

        <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定充值吗?"
          @confirm="Recharge"
        >
          <el-button size="medium" style="background-color: coral; color: white" slot="reference"><i class="el-icon-wallet"></i> 充 值 </el-button>
        </el-popconfirm>
      </el-form>
    </el-dialog>
    <el-dialog title="续费" :visible.sync="dialogFormVisible1" class="renew" :modal="false" :close-on-click-modal="false">

      <el-select v-model="renew" size="medium" placeholder="选择续费时长">
        <el-option label="一个月" value="1"></el-option>
        <el-option label="三个月" value="3"></el-option>
        <el-option label="六个月" value="6"></el-option>
        <el-option label="一年" value="12"></el-option>
      </el-select>

      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定续费吗?"
          @confirm="Renew"
        >
        <el-button size="medium" style="background-color: coral; color: white" slot="reference"><i class="el-icon-wallet"></i> 续 费 </el-button>
      </el-popconfirm>

    </el-dialog>

  </div>


</template>
<script>
  export default {
    name: 'RechargeManagement',
    data() {
      return {
        form: {},
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        member: {},
        dialogFormVisible: false,
        dialogFormVisible1: false,

        recharge: {},
        renew: "",
      }
    },
    created() {
      this.load()
    },
    methods: {
      Recharge() {
        console.log(this.recharge.account)
      },
      Renew() {
        console.log(this.renew)
      },
      load() {
        if(!this.user.userId){
          this.$message.error("无法获取用户信息")
          return
        }
        if (this.user.role == 'member'){
          this.$axios.get("http://localhost:8081/member/getByMemberId/" + this.user.userId).then(res =>{
            this.member=res.data.data

            console.log(res.data.data)
          })
        }else {
          this.$axios.get("http://localhost:8081/coach/getByCoachId/" + this.user.userId).then(res =>{
            this.member = res.data.data

          })
        }

      },
    }
  };
</script>

<style>
  .recharge{
    width: 300px;
    position: relative;
    left: 90px;
    top: 130px;
    display: inline-block;
  }
  .dialog{
    position: absolute;
    left: 450px;
    width: 1200px;
    height: 600px;
  }
  .renew{
    position: absolute;
    left: 450px;
    top: 300px;
    width: 1200px;
    height: 600px;
  }
</style>
