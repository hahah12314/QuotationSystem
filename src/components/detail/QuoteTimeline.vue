<template>
  <div class="timeline-container">
    <el-timeline class="horizontal-timeline">
      <el-timeline-item v-for="(activity, index) in filteredActivities" :key="index" :icon="getIcon(activity, index)"
        :type="getType(activity, index)" :color="getColor(activity, index)" :size="activity.size"
        :timestamp="activity.timestamp" placement="top">
        {{ activity.content }}
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
  export default {
    props: {
      currentStage: {
        type: Number,
        required: true
      }
    },
    data() {
      return {
        activities: [
          {
            content: '创建报价单',
            timestamp: '2023-10-01',
            size: 'large',
            type: 'primary',
            icon: 'el-icon-edit'
          },
          {
            content: '提交审核',
            timestamp: '2023-10-02',
            size: 'large',
            type: 'info',
            icon: 'el-icon-check'
          },
          {
            content: '审核中',
            timestamp: '2023-10-04',
            size: 'large',
            type: 'info',
            icon: 'el-icon-check'
          },
          {
            content: '审核通过',
            timestamp: '2023-10-03',
            size: 'large',
            type: 'success',
            icon: 'el-icon-success'
          },
          {
            content: '审核不通过',
            timestamp: '2023-10-04',
            size: 'large',
            type: 'warning',
            icon: 'el-icon-error'
          },
          {
            content: '修改报价单',
            timestamp: '2023-10-05',
            size: 'large',
            type: 'danger',
            icon: 'el-icon-edit-outline'
          },
          {
            content: '重新提交审核',
            timestamp: '2023-10-06',
            size: 'large',
            type: 'info',
            icon: 'el-icon-check'
          },
          {
            content: '最终审核通过',
            timestamp: '2023-10-07',
            size: 'large',
            type: 'success',
            icon: 'el-icon-success'
          }

        ]
      };
    },
    computed: {
      filteredActivities() {
        if (this.currentStage < 2) { // 如果当前阶段是审核通过或之后
          return this.activities.filter((_, index) => [0, 1, 2, 3, 4, 5, 6, 7].includes(index));
        } else if (this.currentStage === 2) {
          return this.activities.filter((_, index) => [0, 1, 2, 7].includes(index));
        }
        else if (this.currentStage === 3) {
          return this.activities.filter((_, index) => [0, 1, 3, 7].includes(index));
        } else {
          return this.activities.filter((_, index) => [0, 1, 4, 5, 6, 7].includes(index));
        }
      }
    },
    methods: {
      getIcon(activity, index) {
        if (index < this.currentStage) {
          return activity.icon;
        } else if (index === this.currentStage) {
          return 'el-icon-loading';
        } else {
          return '';
        }
      },
      getType(activity, index) {
        if (index < this.currentStage) {
          return 'success';
        } else if (index === this.currentStage) {
          return 'primary';
        } else {
          return 'info';
        }
      },
      getColor(activity, index) {
        if (index < this.currentStage) {
          return '#67C23A'; // 绿色
        } else if (index === this.currentStage) {
          return '#409EFF'; // 蓝色
        } else {
          return '#909399'; // 灰色
        }
      }
    }
  };
</script>

<style scoped>
  .timeline-container {
    padding: 20px;
    background-color: #edf3fb;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
  }

  .horizontal-timeline {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
  }

  .horizontal-timeline .el-timeline-item {
    flex: 1;
    margin-right: 20px;
    /* 调整每个时间线项之间的间距 */
    text-align: center;
  }

  .horizontal-timeline .el-timeline-item:last-child {
    margin-right: 0;
    /* 最后一个时间线项不需要右边距 */
  }

  .horizontal-timeline .el-timeline-item__wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .horizontal-timeline .el-timeline-item__node {
    position: relative;
    top: 0;
    left: 0;
    margin-right: 10px;
    /* 调整节点与内容之间的间距 */
    width: 24px;
    height: 24px;
    border: none;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .horizontal-timeline .el-timeline-item__content {
    margin-top: 10px;
    /* 调整内容与节点之间的间距 */
    font-size: 20px;
    color: #303133;
  }

  .horizontal-timeline .el-timeline-item__timestamp {
    font-size: 18px;
    color: #909399;
    margin-top: 5px;
    font-weight: bold;
  }
</style>