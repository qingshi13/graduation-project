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

    <el-card class="record">
      <el-table :data="tableData" border stripe size="medium">
        <el-table-column prop="amount" label="充值金额" width="170px"></el-table-column>
        <el-table-column prop="time" label="充值时间" width="180px"></el-table-column>

      </el-table>

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

    </el-card>

    <el-dialog title="充值" :visible.sync="dialogFormVisible" class="dialog" width="31%"  @close="handleClose">

      <el-input v-model="amount"
                @input="limitInput($event)"
                placeholder="充值金额"></el-input>
      <br><br><br><br>

      <el-button size="medium" style="position:relative;left: 230px" @click="dialogFormVisible = false">
          <i class="el-icon-remove-outline"></i> 取 消</el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定充值吗?"
          @confirm="Recharge"
        >
        <el-button size="medium" type="success" style=" position:relative;left: 240px" slot="reference"><i class="el-icon-wallet"></i> 充 值 </el-button>
      </el-popconfirm>



    </el-dialog>
    <el-dialog title="续费" :visible.sync="dialogFormVisible1" class="dialog" width="30%"  @close="handleClose">

      <el-select v-model="renew" size="medium" placeholder="选择续费时长" style="display: block">
        <el-option label="一个月   208" value="1"></el-option>
        <el-option label="三个月   536" value="3"></el-option>
        <el-option label="六个月   888" value="6"></el-option>
        <el-option label="一年    1499" value="12"></el-option>
      </el-select>
      <br><br><br>
      <el-button size="medium" style="position:relative;left: 222px" @click="dialogFormVisible1 = false">
        <i class="el-icon-remove-outline"></i> 取 消</el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定续费吗?"
          @confirm="Renew"
        >
        <el-button size="medium" style="position:relative;left:235px;background-color: coral; color: white" slot="reference"><i class="el-icon-wallet"></i> 续 费 </el-button>
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
        tableData: [],
        recharge: {},
        amount: "",
        pageNum: 1,
        pageSize: 8,
        total: 0,
        renew: "",
      }
    },
    created() {
      this.load()
    },
    methods: {
      limitInput(value) {
        this.amount =
          ("" + value) // 第一步：转成字符串
            .replace(/[^\d^\.]+/g, "") // 第二步：把不是数字，不是小数点的过滤掉
            .replace(/^0+(\d)/, "$1") // 第三步：第一位0开头，0后面为数字，则过滤掉，取后面的数字
            .replace(/^\./, "0.") // 第四步：如果输入的第一位为小数点，则替换成 0. 实现自动补全
            .match(/^\d*(\.?\d{0,2})/g)[0] || ""; // 第五步：最终匹配得到结果 以数字开头，只有一个小数点，而且小数点后面只能有0到2位小数
      },
      handleClose() {
        this.amount=''
        this.renew=''
        dialogFormVisible = false
        dialogFormVisible1 = false

      },
      Recharge() {

        console.log(this.amount)
        if (this.user.role == 'member'){
          this.$axios.post("http://localhost:8081/member/recharge/" , {
            userId:this.user.userId,amount:this.amount}).then(res =>{
            if (res.data.code == 200) {
              this.amount = ''
              this.$message.success(res.data.message)
              this.load()
            }else {
              this.$message.error(res.data.message)
            }

          })
        }else {
          this.$axios.post("http://localhost:8081/coach/recharge/" , {
            userId:this.user.userId,amount:this.amount}).then(res =>{
            if (res.data.code == 200) {
              this.amount = ''
              this.$message.success(res.data.message)
              this.load()
            }else {
              this.$message.error(res.data.message)
            }
          })
        }
      },
      Renew() {
        console.log(this.renew)
        this.$axios.post("http://localhost:8081/member/renew/" , {
          memberId:this.user.userId,renew:this.renew}).then(res =>{
          if (res.data.code == 200) {

            this.$message.success(res.data.message)
            this.load()
          }else {
            this.$message.error(res.data.message)
          }

        })
      },
      load() {
        if(!this.user.userId){
          this.$message.error("无法获取用户信息")
          return
        }
        if (this.user.role == 'member'){
          this.$axios.get("http://localhost:8081/member/getByMemberId/" + this.user.userId).then(res =>{
            this.member=res.data.data

          })

        }else {
          this.$axios.get("http://localhost:8081/coach/getByCoachId/" + this.user.userId).then(res =>{
            this.member = res.data.data

          })
        }
        this.$axios.get("http://localhost:8081/recharge/member/" , {
          params: {
            memberId: this.user.userId,
            pageNum: this.pageNum,
            pageSize: this.pageSize,

          }
        }).then(res => {
          this.tableData = res.data.data.records
          this.total = res.data.data.total
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
  .record{
    width: 391px;
    position: absolute;
    top: 150px;
    left: 800px;

    display: inline-block;
  }

</style>
