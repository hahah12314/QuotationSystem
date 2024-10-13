<template>
    <div class='header-container'>
        <div class="l-content" style="margin-left:-5px">
            <el-button icon="el-icon-menu" size="medium" @click="handleMenu"></el-button>
            <el-breadcrumb separator="/" style="margin-left: 20px;">
                <el-breadcrumb-item v-for="item in tags" :key="item.path" :to="item.path !== $route.path ? item.path : null" >
                    {{item.name}}
                </el-breadcrumb-item>

            </el-breadcrumb>
        </div>
        <div class="r-content">
            <el-dropdown @command="handleCommand">
                <span class="el-dropdown-link">
                    <img src="@/assets/images/user1.png" class="user-img">
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="a">个人信息</el-dropdown-item>
                    <el-dropdown-item command="b">退出</el-dropdown-item>

                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: 'header',
        methods: {
            handleMenu() {
                this.$store.commit('ChangeCollapse')
            },
            handleCommand(command){
                console.log(command)
                if(command==='b'){
                    sessionStorage.clear()
                    
                    this.$router.push('/login')
                }else{
                    if(this.$route.path!=='/' && this.$route.path!=='/firstPage'){
                        this.$router.push('/')
                    }
                    
                }
            }

        },
        computed: {
            ...mapState(
                {
                    tags: state => state.tab.tabsList
                }
            )
        }

    }
</script>

<style lang="less" scoped>
    .header-container {

        height: 60px;
        display: flex;
        justify-content: space-between;
        align-items: center;

        .firstPage {
            color: #fff;
            font-size: 18px;
            margin-left: 10px;
        }

        .r-content {
            padding-right: 20px;

            .user-img {
                width: 40px;
                height: 40px;
                border-radius: 50%;

            }
        }

        .l-content {
            display: flex;
            align-items: center;

            /deep/.el-breadcrumb__item {
                .el-breadcrumb__inner {
                    font-weight: normal;

                    &.is-link {
                        color: #666;
                    }
                }

                &:last-child {
                    .el-breadcrumb__inner {
                        color: #fff;
                    }
                }
            }
        }
    }
</style>