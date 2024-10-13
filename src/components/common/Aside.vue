
 <template>
  <div class='aside'>
    <h3>
      <i class="el-icon-takeaway-box"></i>
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
        temList: [
          {
            menuId: 1,
            name: '系统管理',
            icon: 'setting',
            path: '/system-management',
            children: [
              {
                menuId: 11,
                name: '用户管理',
                icon: 'user',
                path: '/system-management/users'
              },
              {
                menuId: 12,
                name: '权限管理',
                icon: 'lock',
                path: '/system-management/permissions'
              },
              {
                menuId: 13,
                name: '操作日志',
                icon: 'document',
                path: '/system-management/logs'
              },
              {
                menuId: 14,
                name: '菜单管理',
                icon: 'document',
                path: '/system-management/menus'
              }
            ]
          },
          {
            menuId: 2,
            name: '内容管理',
            icon: 'tickets',
            path: '/document',
            children: [
              {
                menuId: 21,
                name: '文档管理',
                icon: 'notebook-2',
                path: '/content-management/documents'
              },
              {
                menuId: 22,
                name: '知识库维护',
                icon: 'database',
                path: '/content-management/knowledge-base'
              },
              {
                menuId: 23,
                name: '内容审核',
                icon: 'check',
                path: '/content-management/content-review'
              }
            ]
          },
          {
            menuId: 3,
            name: '客服管理',
            icon: 'chat-dot-square',
            path: '/customer-service',
            children: [
              {
                menuId: 31,
                name: '对话记录',
                icon: 'chat-dot-round',
                path: '/customer-service/conversation-history'
              },
              {
                menuId: 32,
                name: '会话监控',
                icon: 'monitor',
                path: '/customer-service/session-monitoring'
              },
              {
                menuId: 33,
                name: '客服绩效',
                icon: 'star-on',
                path: '/customer-service/performance'
              }
            ]
          },
          {
            menuId: 4,
            name: '数据分析',
            icon: 'pie-chart',
            path: '/data-analysis',
            children: [
              {
                menuId: 41,
                name: '用户行为分析',
                icon: 'bar-chart',
                path: '/data-analysis/user-behavior'
              },
              {
                menuId: 42,
                name: '会话统计',
                icon: 'line-chart',
                path: '/data-analysis/session-stats'
              },
              {
                menuId: 43,
                name: '问题分类统计',
                icon: 'area',
                path: '/data-analysis/question-stats'
              }
            ]
          },
          {
            menuId: 5,
            name: '系统设置',
            icon: 'edit',
            path: '/system-settings',
            children: [
              {
                menuId: 51,
                name: '系统配置',
                icon: 'setting',
                path: '/system-settings/system-config'
              },
              {
                menuId: 52,
                name: '接口管理',
                icon: 'link',
                path: '/system-settings/api-management'
              },
              {
                menuId: 53,
                name: '版本更新',
                icon: 'refresh-right',
                path: '/system-settings/version-update'
              }
            ]
          },
          {
            menuId: 6,
            name: '问答管理',
            icon: 'question',
            path: '/qa-management',
            children: [
              {
                menuId: 61,
                name: '问答中心',
                icon: 'message',
                path: '/qa-management/chat-center'
              },
              {
                menuId: 62,
                name: '个人中心',
                icon: 'user-solid',
                path: '/qa-management/personal-center'
              },
              {
                menuId: 63,
                name: '帮助与支持',
                icon: 'help',
                path: '/qa-management/help-support'
              },

            ]
          },
          {
            menuId: 7,
            name: '首页',
            icon: 'crop',
            path: '/firstPage'
          }
        ],
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
    padding-top: 20px;
    width: auto;
    height: 100vh;
    text-align: center;

    .title {
      margin-left: 10px;
    }

    .menu-container {
      margin-top: 10px;
    }
  }
</style>