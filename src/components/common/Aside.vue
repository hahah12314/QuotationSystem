<template>
  <div class='aside'>
    <h3 class="title-container">
      <img :src="require('@/assets/icon/首页图标.png')" alt="" />
      <span class="title" v-if="!isCollapse">{{isCollapse ? '金威':'金威报价系统'}}</span>
    </h3>
    <div class="menu-container">

      <el-menu class="el-menu-vertical-demo" :collapse="isCollapse">
        <el-menu-item v-for="item in noChild" :key="item.menuId" :index="item.menuId" @click="handleClick(item)">
          <i :class="`el-icon-${item.icon}`"></i>
          <span slot="title">{{item.name}}</span>
        </el-menu-item>
        <el-submenu v-for="item in hasChild" :key="item.menuId" :index="item.menuId">
          <template slot="title">
            <i :class="`el-icon-${item.icon}`"></i>
            <span>{{item.name}}</span>
          </template>
          <el-menu-item-group>

            <el-menu-item v-for="sub in item.children" :key="sub.menuId" :index="sub.menuId"
              @click="handleClick(sub)">{{sub.name}}</el-menu-item>

          </el-menu-item-group>

        </el-submenu>

      </el-menu>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'AsideComponent',
    computed: {
      isCollapse() {
        return this.$store.state.tab.isCollapse

      },
      hasChild() {
        return this.menuList.filter(item => item.children && item.children.length > 0)
      },
      noChild() {
        return this.menuList.filter(item => !item.children || item.children.length == 0)
      }
    },
    data() {
      return {

        menuList: [

        ]
      }
    },
    methods: {
      handleClick(item) {
        if (this.$route.path != item.path) {
          this.$router.push(item.path)
        }
        this.$store.commit('menuChange', item)
      },
      async getDataList() {

        try {
          const res = await this.$http.get('/sys/sysMenu/getNowMenuChecked');
          console.log(res);
          this.menuList = res.data.data
          // 定义排序顺序
          const order = ['首页', '报价管理', '资源管理', '工种管理', '费用管理', '系统管理', '统计分析'];

          // 对 menuList 进行排序
          this.menuList = this.menuList.sort((a, b) => {
            return order.indexOf(a.name) - order.indexOf(b.name);
          });

          // 将排序后的 menuList 存储到 localStorage
          localStorage.setItem('menuList', JSON.stringify(this.menuList));


          console.log(this.menuList)

        } catch (error) {
          console.error('获取数据列表时出错:', error);
          this.$message({
            message: '获取数据列表时发生错误',
            type: 'error'
          });
        }
      }
      ,

    },
    mounted() {
      this.getDataList()
    }

  }
</script>

<style scoped lang="less">
  .aside {
    padding-top: 10px;

    height: 100vh;

    .title-container {
      display: flex;
      align-items: center;

      img {
        width: 18px;
        height: 18px;
        margin-left: 24px;
      }

      .title {
        margin-left: 5px;
        margin-right: 6px;
        font-size: 16px;
      }
    }


    .menu-container {
      margin-top: 10px;




    }
  }
</style>