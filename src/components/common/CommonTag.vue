<template>
    <div class="tabs">
        <el-tag v-for="item in tags" :key="item.path" :effect="$route.name===item.name ? 'dark' : 'plain'"
            @click="changeMenu(item)" @close="handleClose(item)" :closable="item.name!=='home'">
            {{ item.name }}
        </el-tag>
    </div>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    export default {
        name: 'commonTag',
        computed: {
            ...mapState(
                {
                    tags: state => state.tab.tabsList
                }
            )
        },
        methods: {
            ...mapMutations(['closeTag']),
            changeMenu(item) {
                if(this.$route.path!==item.path){
                    this.$router.push(item.path)
                }
                
            },
            handleClose(item) {
                this.closeTag(item)
                if (item.name !== this.$route.name) {
                    return
                }
                this.$router.push(this.tags[0].name)
            }
        }

    }
</script>

<style scoped lang="less">
    .tabs {
        position: relative;

        overflow: hidden;
        background: #fff;
        

        box-shadow: 0 5px 10px #ddd;
        padding-bottom: 5px;
        padding-top: 5px;
        padding-left: 15px;

        .el-tag {
            margin-right: 15px;
            cursor: pointer;
            margin-bottom: 5px;
            
        }
    }
</style>