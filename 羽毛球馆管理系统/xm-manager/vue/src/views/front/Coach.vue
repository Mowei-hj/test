<template>
  <div class="main-content">
    <img src="@/assets/imgs/coach.jpg" alt="" style="width: 100%; height: 400px">
    <div style="width: 80%; margin: 30px auto">
      <div style="text-align: center; font-size: 28px; color: #737578">预约教练</div>
      <div style="text-align: center; font-size: 22px; color: #737578; margin: 15px 0">专业负责的私人教练，一对一定制化教学</div>
      <div>
        <el-row :gutter="30">
         <el-col :span="6" v-for="item in coachData" style="margin-bottom: 10px; padding: 10px">
           <img @click="$router.push('/front/coachDetail?id=' + item.id)" :src="item.avatar" alt="" style="width: 100%; height: 280px; border-radius: 10px; cursor: pointer">
           <div style="margin-top: 10px; font-size: 17px; font-weight: bold; color: #666666">姓名：{{item.name}}</div>
           <div style="display: flex; color: #666666; margin-top: 10px; text-align: center; align-items: center">
             <div style="flex: 1">电话：{{ item.phone }}</div>
             <div style="flex: 1">
               <el-button type="info" @click="reserveInit(item.id)">我要预约</el-button>
             </div>
           </div>
         </el-col>
        </el-row>
      </div>
    </div>
    <el-dialog title="预约信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px">
        <el-form-item prop="content" label="预约说明">
          <el-input type="textarea" :rows="5" v-model="content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {

  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      coachData: [],
      content: null,
      coachId: null,
      fromVisible: false,
    }
  },
  mounted() {
    this.loadCoach()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadCoach() {
      this.$request.get('/coach/selectAll').then(res => {
        if (res.code === '200') {
          this.coachData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reserveInit(coachId) {
      if (this.user.role !== 'USER') {
        this.$message.warning('您的角色暂不支持该操作')
        return
      }
      this.coachId = coachId
      this.fromVisible = true
    },
    submit() {
      let data = {
        userId: this.user.id,
        coachId: this.coachId,
        content: this.content,
      }
      this.$request.post('/reserve/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('恭喜您预约成功，等待教练联系')
          this.coachId = null
          this.content = null
          this.fromVisible = false
        }
      })
    }
  }
}
</script>
