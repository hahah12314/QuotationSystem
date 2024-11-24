<template>
    <div id="audit-quotation">
      <h1 class="main-title">审核报价单</h1>
      <add-quotation :outQuotationForm="outQuotationForm" :isReadOnly="true"></add-quotation>
      <div class="audit-actions">
        <el-button type="success" @click="handleApprove">审核通过</el-button>
        <el-button type="danger" @click="handleShowRejectModal">审核不通过</el-button>
      </div>
      <el-dialog
        title="审核不通过"
        :visible.sync="isRejectModalVisible"
        width="50%"
        custom-class="custom-modal"
      >
        <el-input
          type="textarea"
          :rows="6"
          placeholder="请输入审核不通过的原因"
          v-model="rejectionReason"
          class="rejection-textarea"
        ></el-input>
        <div slot="footer" class="dialog-footer">
          <el-button @click="handleCloseRejectModal">取 消</el-button>
          <el-button type="primary" @click="handleReject">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import AddQuotation from '@/components/pages/quotation/AddQuotation';
  
  export default {
    name: 'auditQuotation',
    components: {
      AddQuotation
    },
    data() {
      return {
        outQuotationForm: null,
        isRejectModalVisible: false,
        rejectionReason: ''
      };
    },
    created() {
      this.fetchDataFromQuery();
    },
    methods: {
      fetchDataFromQuery() {
        const formData = this.$route.query.formData;
        if (formData) {
          try {
            this.outQuotationForm = JSON.parse(formData);
            console.log(this.outQuotationForm);
          } catch (error) {
            console.error('解析 formData 失败:', error);
          }
        } else {
          console.warn('没有找到 formData 参数');
        }
      },
      async submitQuotation() {
        const res = await this.$http.get('/quotations/auditQuotation?quotationId=' + this.outQuotationForm.quotationId );
        this.$message({
            message: '审核通过成功',
            type: 'success'
          });
          // 可以在这里发送请求更新服务器状态
          this.$router.go(-1)
      },
      handleApprove() {
        // 处理审核通过的逻辑
        this.submitQuotation()
        
      },
      handleShowRejectModal() {
        this.isRejectModalVisible = true;
      },
      handleCloseRejectModal() {
        this.isRejectModalVisible = false;
        this.rejectionReason = '';
        this.$router.go(-1)
      },
      handleReject() {
        if (!this.rejectionReason) {
          this.$message({
            message: '请填写审核不通过的原因',
            type: 'warning'
          });
          return;
        }
        // 处理审核不通过的逻辑
        this.$message({
          message: '审核不通过成功，原因: ' + this.rejectionReason,
          type: 'info'
        });
        // 可以在这里发送请求更新服务器状态
        this.handleCloseRejectModal();
      }
    }
  };
  </script>
  
  <style scoped>
  .audit-actions {
    margin-top: 20px;
  }
  
  .custom-modal {
    border-radius: 12px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  .dialog-footer{
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .rejection-textarea {
    width: 100%;
    resize: none;
    font-size: 16px;
    padding: 12px;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    outline: none;
    transition: border-color 0.3s;
  }
  
  .rejection-textarea:focus {
    border-color: #409eff;
  }
  .el-textarea{
    padding: 0;
  }
  </style>