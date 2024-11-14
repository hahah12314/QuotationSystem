<template>
  <div class="quotation-container">
    <header class="header">
      <h1 class="title">金威报价单</h1>
    </header>
   
    <div v-if="quotationForm">
      <table class="data-table">
        <thead>
          <tr>
            <th colspan="4" class="table-title">报价信息</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>公司名称</td>
            <td>{{ quotationForm.company.name }}</td>
            <td>客户名称</td>
            <td>{{ quotationForm.customer.name }}</td>
          </tr>
          <tr>
            <td>负责人</td>
            <td>{{ quotationForm.company.responsiblePerson }}</td>
            <td>负责人联系方式</td>
            <td>{{ quotationForm.company.contactInfo }}</td>
          </tr>
          <tr>
            <td>邮箱</td>
            <td>{{ quotationForm.customer.email }}</td>
            <td>客户联系方式</td>
            <td>{{ quotationForm.customer.contactInfo }}</td>
          </tr>
          <tr>
            <td>地址</td>
            <td>{{ quotationForm.customer.address }}</td>
            <td>预期价格</td>
            <td>{{ quotationForm.customer.minPrice }} - {{ quotationForm.customer.maxPrice }}</td>
          </tr>
          <tr>
            <td>支付方式</td>
            <td>{{ quotationForm.customer.paymentMethod }}</td>
            <td>要求</td>
            <td>{{ quotationForm.customer.requirements }}</td>
          </tr>
        </tbody>
      </table>

      <table class="data-table">
        <thead>
          <tr>
            <th colspan="2" class="table-title">成本信息</th>
          </tr>
          <tr>
            <th>项目</th>
            <th>费用</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>原材料费用</td>
            <td>{{ details.materialCost.totalMaterialCost }}</td>
          </tr>
          <tr>
            <td>数割费用</td>
            <td>{{ details.cuttingCost.totalCutCost }}</td>
          </tr>
          <tr>
            <td>单件加工费用</td>
            <td>{{ details.processingCost.totalProcessingCost }}</td>
          </tr>
          <tr>
            <td>表面处理单件费用</td>
            <td>{{ details.surfaceTreatment.totalCoatingCost }}</td>
          </tr>
          <tr>
            <td>喷涂单件费用</td>
            <td>{{ details.paintingCost.totalPaintingCost }}</td>
          </tr>
        </tbody>
      </table>

      <table class="data-table">
        <thead>
          <tr>
            <th colspan="6" class="table-title">物料信息表</th>
          </tr>
          <tr>
            <th>物料编码</th>
            <th>物料名称</th>
            <th>单价</th>
            <th>数量</th>
            <th>材料规格</th>
            <th>是否外采</th>
          </tr>
        </thead>
        <tbody>
          <!-- 只显示选中的报价物料 -->
          <tr>
            <td>{{ quotationForm.material.quotedMaterials.materialCode }}</td>
            <td>{{ quotationForm.material.quotedMaterials.materialName }}</td>
            <td>{{ quotationForm.material.quotedMaterials.unitPrice }}</td>
            <td>{{ quotationForm.material.quotedMaterials.quantity }}</td>
            <td>{{ details.rawMaterials.specification }}</td>
            <td>{{ quotationForm.material.quotedMaterials.isExternalProcurement ? '是' : '否' }}</td>
          </tr>
        </tbody>
      </table>

      <table class="data-table">
        <thead>
          <tr>
            <th colspan="9" class="table-title">材料详情</th>
          </tr>
          <tr>
            <th>材料规格</th>
            <th>单位重量（公斤）</th>
            <th>净重（公斤）</th>
            <th>裸价</th>
            <th>利润</th>
            <th>杂费</th>
            <th>公斤价</th>
            <th>税前总价</th>
            <th>产品总价</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{{ details.rawMaterials.specification }}</td>
            <td>{{ details.rawMaterials.unitWeight }}</td>
            <td>{{ details.rawMaterials.netWeight }}</td>
            <td>{{ details.rawMaterials.nakedPrice }}</td>
            <td>{{ details.rawMaterials.profit }}</td>
            <td>{{ details.rawMaterials.miscellaneousFees }}</td>
            <td>{{ details.rawMaterials.pricePerkg }}</td>
            <td>{{ details.rawMaterials.preTax }}</td>
            <td>{{ details.rawMaterials.totalProductQuote }}</td>
          </tr>
        </tbody>
      </table>

      <div class="button-container">
        <button class="button" @click="returnToEdit">返回修改</button>
        <button class="button submit-button" @click="submitQuotation">提交报价单</button>
      </div>
    </div>

    <footer class="footer">
      <p>&copy; 2023 金威. 版权所有.</p>
    </footer>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        quotationForm: {}
      };
    },
    created() {
      // 初始化时从查询参数中获取 formData 并解析
      this.fetchDataFromQuery();
    },
    activated() {
      // 每次激活组件时重新获取并解析查询参数
      this.fetchDataFromQuery();
    },
    computed: {
      details() {
        return this.quotationForm.material.type === 'custom' ? this.quotationForm.material.customDetails : this.quotationForm.material.historyDetails;
      }
    },
    methods: {
      returnToEdit() {
        // 返回编辑页面的逻辑
        // 将 quotationForm 转换为 JSON 字符串
        const formData = JSON.stringify(this.quotationForm);

        // 跳转到生成报价单的页面，并传递数据
        this.$router.push({
          path: '/addQuotation',
          query: { formData }
        }).catch(err => {
          console.error('Router push error:', err);
        });
      },
      async submitQuotation() {
        try {
          const response = await this.$http.post('/quotations/save', this.quotationForm);
          if (response.code === 200) {
            this.$message.success('报价单已提交审核！');
          }
          this.$router.push('/firstPage')

        } catch (error) {
          console.error('提交失败:', error);
          this.$message.error('提交失败，请重试！');
        }

      },
      fetchDataFromQuery() {
        const formData = this.$route.query.formData;
        if (formData) {
          try {
            this.quotationForm = JSON.parse(formData);
            console.log(this.quotationForm);
          } catch (error) {
            console.error('解析 formData 失败:', error);
          }
        } else {
          console.warn('没有找到 formData 参数');
        }
      }
    }
  };
</script>

<style scoped>
  .quotation-container {
    font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
    background-color: #ffffff;
    padding: 20px;
    font-size: 14px;
  }

  .header {
    text-align: center;
    margin-bottom: 20px;
  }

  .title {
    font-size: 24px;
    color: #333;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .data-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
    border: 1px solid #e0e0e0;
    font-size: 14px;
  }

  .data-table th,
  .data-table td {
    border: 1px solid #e0e0e0;
    padding: 12px;
    text-align: left;
    font-size: 16px;
  }

  .data-table th {
    background-color: #f5f5f5;
    font-weight: bold;
    color: #333;
    font-size: 18px;
  }

  .data-table .table-title {
    background-color: #f5f5f5;
    color: #333;
    text-align: center;
    font-weight: bold;
    font-size: 18px;
  }

  .footer {
    text-align: center;
    margin-top: 20px;
    color: #777;
    font-size: 14px;
  }

  .button-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }

  .button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    border-radius: 4px;
    margin: 0 10px;
    transition: background-color 0.3s ease;
  }

  .button:hover {
    background-color: #0056b3;
  }

  .submit-button {
    background-color: #28a745;
  }

  .submit-button:hover {
    background-color: #218838;
  }
</style>