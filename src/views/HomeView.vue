<template>
  <div id="id">
    <el-container class="app-container" style="margin: 0;">
      <!-- 左侧菜单 -->
      <el-aside class="aside-container" width="auto">
        <Aside />
      </el-aside>

      <el-container class="main-container">
        <!-- 头部区域 -->
        <el-header class="header-container" style="height: 40px; padding: 0;">
          <Header />
        </el-header>

        <!-- 主要内容区域 -->
        <el-main class="main-content">

          <router-view></router-view>

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import Aside from "@/components/common/Aside";
  import Header from "@/components/common/Header";
  import CommonTag from "@/components/common/CommonTag";

  export default {
    name: "HomeView",
    components: {
      Aside,
      Header,
      CommonTag,
    },
    data() {
      return {
        cachedPages: []
      }
    },
    computed: {
      isCollapse() {
        return this.$store.state.tab.isCollapse

      }
    }

  }
</script>

<style scoped lang="less">
  /* 全局样式重置 */
  * {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
  }

  /* 根容器占满整个页面 */
  #id {
    height: 100vh;
    display: flex;
  }

  /* 主体部分的容器，保证左右布局正常显示 */
  .app-container {
    width: 100%;
  }

  /* 左侧 Aside 区域 */
  .aside-container {
    height: 100vh;
    overflow-y: auto;
    /* 独立滚动条 */

    border-right: 1px solid #e0e0e0;
  }

  /* 主体部分容器 */
  .main-container {
    display: flex;
    flex-direction: column;
    width: 100%;
  }

  /* 头部区域 */
  .header-container {
    height: 40px;

    background-color: #fff;
    border-bottom: 1px solid #e0e0e0;
    padding: 0 10px;
    overflow: hidden;
  }

  /* 标签导航 */
  .common-tag {
    height: 20px;
    line-height: 20px;
    background-color: #fafafa;
    border-bottom: 1px solid #e0e0e0;
    overflow: hidden;
  }

  /* 主要内容区域 */
  .main-content {
    flex: 1;
    /* 填充剩余空间 */
    height: 100vh;
    overflow-y: auto;
    /* 独立滚动条 */
    padding: 0px;
    background-color: #fff;
  }
</style>