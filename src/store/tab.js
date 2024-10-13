export default{
    state:{
        isCollapse:false,
        tabsList:[
            {
                path:'/firstPage',
                label:'首页',
                icon:'s-home',
                url:'home/home',
                name:'首页'
            }
        ]
    },
    mutations:{
        ChangeCollapse(state){
            state.isCollapse = !state.isCollapse
        },
        menuChange(state,val){
            console.log(val)
            if(val.label!=='首页'){
                const index =state.tabsList.findIndex((item)=>item.name===val.name)
                if(index===-1){
                    state.tabsList.push(val)
                }
            }
        },
        closeTag(state,val){
            const index =state.tabsList.findIndex((item)=>item.name===val.name)
            state.tabsList.splice(index,1)
        }
    }
}