<template>
  <div class="main-content">
    <div>
      <el-carousel height="450px">
        <el-carousel-item v-for="item in carouselData" :key="item">
          <img :src="item" alt="" style="width: 100%; height: 450px">
        </el-carousel-item>
      </el-carousel>
    </div>
    <div style="width: 80%; margin: 30px auto">
      <div style="display: flex">
        <div style="flex: 1; text-align: center; font-size: 25px; color: #737578">专业课程，科学健身</div>
        <div style="width: 80px; text-align: right; color: #666666; cursor:pointer;" @click="$router.push('/front/course')">查看更多</div>
      </div>
      <div style="margin-top: 30px">
        <el-row :gutter="30">
         <el-col :span="6" v-for="item in courseData">
           <img :src="item.img" alt="" style="width: 100%; height: 280px; border-radius: 10px; cursor: pointer" @click="$router.push('/front/courseDetail?id=' + item.id)">
           <div style="font-size: 17px; margin-top: 10px; color: #666666; font-weight: bold">{{item.name}}</div>
           <div style="margin-top: 10px; color: #666666; display: flex">
             <div style="flex: 1">教练：{{ item.coachName }}</div>
             <div style="flex: 1">课程周期：{{ item.time }}</div>
           </div>
         </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>

export default {

  data() {
    return {
      carouselData: [
        require('@/assets/imgs/图1.jpg'),
        require('@/assets/imgs/lun-1.jpg'),
        require('@/assets/imgs/图3.jpg'),
      ],
      courseData: []
    }
  },
  mounted() {
    this.loadCourse()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadCourse() {
      this.$request.get('/course/selectFour').then(res => {
        if (res.code === '200') {
          this.courseData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>
