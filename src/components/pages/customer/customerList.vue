<template>
    <div>
      <div v-hover-load="loadData" class="hover-box">
        悬停加载内容
      </div>
      <div v-if="loading" class="loading-spinner">加载中...</div>
      <div v-if="data" class="data-content">
        <p>{{ data }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        loading: false, // 控制加载中状态
        data: null, // 存储加载的数据
      };
    },
    methods: {
      async loadData() {
        this.loading = true;
        // 模拟异步请求
        const response = await new Promise((resolve) => {
          setTimeout(() => {
            resolve("这是加载的数据");
          }, 1000);
        });
        this.data = response;
        this.loading = false;
      },
    },
    directives: {
      hoverLoad: {
        bind(el, binding) {
          // 绑定鼠标悬停事件
          const loadData = async () => {
            await binding.value(); // 调用传递的函数，发起 Ajax 请求
          };
  
          el.addEventListener('mouseenter', loadData); // 监听鼠标移入事件
          el._loadData = loadData; // 存储函数以便后续移除事件监听
        },
        unbind(el) {
          // 移除事件监听，防止内存泄漏
          el.removeEventListener('mouseenter', el._loadData);
          delete el._loadData;
        },
      },
    },
  };
  </script>