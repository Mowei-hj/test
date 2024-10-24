<template>
  <div class="main-content">
    <div style="width: 65%; margin: 20px auto">
      <div style="margin-top: 20px; font-size: 17px; font-weight: bold; color: #666666">{{ coachData.name }} 详细介绍</div>
      <div style="margin-top: 20px" v-html="coachData.content" class="w-e-text w-e-text-container"></div>
      <div style="font-size: 17px; font-weight: bold; margin: 30px 0; color: #9a6d2a">请发布你对本教练的评价：</div>
      <div>
        <el-input type="textarea" :rows="5" v-model="comment" placeholder="请输入您的评价"></el-input>
        <div style="margin-top: 10px; text-align: right">
          <el-button type="primary" @click="submit">提 交</el-button>
        </div>
      </div>
      <div style="font-size: 17px; font-weight: bold; margin: 20px 0; color: #9a6d2a">看看其他小伙伴对教练的评价（4）</div>
      <div style="margin: 40px 0 100px 0">
        <el-row :gutter="10" v-for="item in commentData" style="margin-bottom: 30px">
         <el-col :span="4">
           <div style="display: flex; align-items: center">
             <img :src="item.userAvatar" alt="" style="width: 40px; height: 40px; border-radius: 50%">
             <div style="margin-left: 5px">{{item.userName}}</div>
           </div>
         </el-col>
         <el-col :span="15">
           <div style="line-height: 40px">{{item.content}}</div>
         </el-col>
         <el-col :span="5">
           <div style="line-height: 40px; text-align: right; color: #666666">{{item.time}}</div>
         </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import E from 'wangeditor'
export default {

  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      coachId: this.$route.query.id,
      coachData: {},
      comment: null,
      commentData: []
    }
  },
  mounted() {
    this.loadCoach()
    this.loadComment()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadComment() {
      this.$request.get('/comment/selectAll').then(res => {
        if (res.code === '200') {
          this.commentData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadCoach() {
      this.$request.get('/coach/selectById/' + this.coachId).then(res => {
        if (res.code === '200') {
          this.coachData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    submit() {
      if (this.user.role !== 'USER') {
        this.$message.warning('您的角色暂不支持该操作')
        return
      }
      if (!this.comment) {
        this.$message.warning('请输入评价内容')
        return
      }
      let data = {
        userId: this.user.id,
        coachId: this.coachId,
        content: this.comment
      }
      this.$request.post('/comment/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('评价成功')
          this.comment = null
          this.loadComment()
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>
<style>
  p {
    color: #333333;
  }
</style>