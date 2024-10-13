<template>
  <div id='id'>
    <el-container>
      <el-aside width="auto">
        <Aside></Aside>
      </el-aside>
      <el-container>
        <el-header>
          <Header></Header>
          
        </el-header>
        <CommonTag></CommonTag>
        <el-main>
          <router-view ></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import Aside from '@/components/common/Aside'
  import Header from '@/components/common/Header'
  import CommonTag from '@/components/common/CommonTag'
  export default {
    name: 'HomeView',
    components: {
      Aside,
      Header,
      CommonTag
    }

  }
</script>

<style scoped lang="less">
  *{
    padding: 0;
    margin: 0;
   }
   .app-container{
    height: 100%;
   }
  .el-main{
    height: 100%;
  }
  .el-aside{
    height: 100%;
  
  }

  

 
</style>