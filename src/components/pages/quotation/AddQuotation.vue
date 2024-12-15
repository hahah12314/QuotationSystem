<template>
  <div class="add-quotation">
    <div class="quotation-form">
      <h1 class="main-title" v-if="!isReadOnly">新增报价单</h1>

      <table class="data-table">
        <thead>
          <tr>
            <th class="table-title" colspan="4">信息录入</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>公司名称<span class="required">*</span>:</td>
            <td><input v-model="quotationForm.company.name" class="input-field" :readonly="isReadOnly" /></td>
            <td>客户名称<span class="required">*</span>:</td>
            <td><input v-model="quotationForm.customer.name" class="input-field" :readonly="isReadOnly" /></td>
          </tr>
          <tr>
            <td>负责人<span class="required">*</span>:</td>
            <td><input v-model="quotationForm.company.responsiblePerson" class="input-field" :readonly="isReadOnly" />
            </td>
            <td>负责人联系方式<span class="required">*</span>:</td>
            <td><input v-model="quotationForm.company.contactInfo" class="input-field" :readonly="isReadOnly" /></td>
          </tr>
          <tr>
            <td>客户邮箱:</td>
            <td><input v-model="quotationForm.customer.email" class="input-field" :readonly="isReadOnly" /></td>
            <td>客户联系方式<span class="required">*</span>:</td>
            <td><input v-model="quotationForm.customer.contactInfo" class="input-field" :readonly="isReadOnly" /></td>
          </tr>
          <tr>
            <td>客户地址:</td>
            <td><input v-model="quotationForm.customer.address" class="input-field" :readonly="isReadOnly" /></td>
            <td>预期价格:</td>
            <td>
              <div class="price-inputs">
                <input v-model="quotationForm.customer.minPrice" placeholder="最低价格" class="input-field small-input"
                  :readonly="isReadOnly" />
                <span class="separator">至</span>
                <input v-model="quotationForm.customer.maxPrice" placeholder="最高价格" class="input-field small-input"
                  :readonly="isReadOnly" />
              </div>
            </td>
          </tr>
          <tr>
            <td>付款方式<span class="required">*</span>:</td>
            <td>
              <select v-model="quotationForm.customer.paymentMethod" class="input-field" :disabled="isReadOnly">
                <option value="现金">现金</option>
                <option value="信用卡">信用卡</option>
                <option value="银行转账">银行转账</option>
                <option value="支付宝">支付宝</option>
                <option value="微信支付">微信支付</option>
              </select>
            </td>
            <td>客户要求:</td>
            <td colspan="2">
              <textarea v-model="quotationForm.customer.requirements" class="textarea-field"
                :readonly="isReadOnly"></textarea>
            </td>
          </tr>
          <tr>
            <td>报价物料<span class="required">*</span>:</td>
            <td>
              <select v-model="quotationForm.material.quotedMaterialId" class="input-field" :disabled="isReadOnly">
                <option v-for="(material,index) in quotedMaterials" :key="material.materialId"
                  :value="material.materialId">
                  {{material.materialName }}
                </option>
              </select>
            </td>
            <td>原材料<span class="required">*</span>:</td>
            <td>
              <div>
                <label>
                  <input type="radio" v-model="quotationForm.material.type" value="custom" :disabled="isReadOnly" />
                  自定义明细
                </label>
                <label>
                  <input type="radio" v-model="quotationForm.material.type" value="history" :disabled="isReadOnly" />
                  历史明细
                </label>
                <select v-model="quotationForm.material.historyMaterialId" class="input-field" :disabled="isReadOnly">
                  <option v-for="(material,index) in historyMaterials" :key="material.detailId"
                    :value="material.detailId">
                    {{index+1}}. {{ material.specification}}
                  </option>
                </select>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <hr class="divider" />

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
          <tr v-for="(item, index) in selectedQuotedMaterials" :key="index">
            <td>{{ item.materialCode }}</td>
            <td>{{ item.materialName }}</td>
            <td><input v-model.number="item.unitPrice" class="input-field" :readonly="isReadOnly" /></td>
            <td><input v-model.number="item.quantity" class="input-field" :readonly="isReadOnly" /></td>
            <td>
              <select v-model="quotationForm.material.historyMaterialId" class="input-field" :disabled="isReadOnly">
                <option v-for="(material,index) in historyMaterials" :key="material.detailId"
                  :value="material.detailId">
                  {{ material.specification}}
                </option>
              </select>
            </td>
            <td>
              <select v-model="quotationForm.material.quotedMaterials.isExternalProcurement" class="input-field"
                :disabled="isReadOnly">
                <option value="1">是</option>
                <option value="0">否</option>
              </select>
            </td>
          </tr>
        </tbody>
      </table>
      <hr class="divider" />
      <!-- 条件渲染部分 -->
      <div v-if="quotationForm.material.type === 'history'">
        <MaterialInfo ref="historyDetail" :detailId="quotationForm.material.historyMaterialId"
          :quantity="selectedQuotedMaterials[0]?.quantity ?? 1" />
        <hr class="divider" />
      </div>
      <div v-else>
        <CustomDetail ref="customDetail" :detailId="quotationForm.material.historyMaterialId"
          :quantity="selectedQuotedMaterials[0]?.quantity ?? 1" />
      </div>

      <button class="btn-submit" @click="generateQuote" v-show="!isReadOnly">生成报价单</button>
    </div>
  </div>
</template>

<script>
  import MaterialInfo from '@/components/detail/MaterialInfo';
  import CustomDetail from '@/components/detail/CustomDetail';
  export default {
    components: {
      MaterialInfo,
      CustomDetail

    },
    props: {
      outQuotationForm: {
        type: Object,
        required: false
      },
      ReadOnly: {
        type: Boolean,
        required: false
      },

    },

    data() {
      return {
        isReadOnly: false,
        quotationForm: {
          company: {
            name: '金威',
            responsiblePerson: '',
            contactInfo: '',

          },
          customer: {
            name: '',
            contactInfo: '',
            email: '',
            minPrice: 0,
            maxPrice: 0,
            requirements: '',
            address: '',
            paymentMethod: ''
          },
          material: {
            quotedMaterialId: null,
            type: 'custom',
            historyMaterialId: null,
            customDetails: {}, // 存储自定义明细的数据
            historyDetails: {}, // 存储历史明细的数据
            quotedMaterials: {
              materialCode: '',
              materialName: '',
              unitPrice: 0,
              quantity: 1,
              specification: '',
              isExternalProcurement: 0
            }
          }

        },
        userInfo: null,
        fromOut: false,
        quotedMaterials: [], // 从后台获取的报价物料数据
        historyMaterials: [], // 从后台获取的历史物料数据
        materialCosts: [
          { project: '原材料费用', detail: '钢材', unitPrice: 0, quantity: 0, unit: '吨' },
          { project: '原材料费用', detail: '铝材', unitPrice: 0, quantity: 0, unit: '吨' },
          // 可以添加更多项目
        ],
      };
    },

    computed: {
      selectedQuotedMaterials() {
        // 返回选中的报价物料，并将数量设置为1
        return this.quotedMaterials
          .filter(material => material.materialId === this.quotationForm.material.quotedMaterialId)
          .map(material => ({ ...material, quantity: 1 }));
      },
      subtotal() {
        // 计算选中的报价物料的小计
        return this.selectedQuotedMaterials.reduce((sum, item) => sum + (item.unitPrice * item.quantity), 0);
      },
      totalCost() {
        // 计算选中的报价物料的总计
        return this.subtotal;
      }
    },
    created() {
      this.fetchUserInfo()
      this.fetchQuotedMaterials();
      this.fetchHistoryMaterials();
      this.fetchDataFromQuery()
      this.fetchQuotationInfo()

    },
    methods: {
      async fetchUserInfo() {
        const res = await this.$http.get('/sys/sysUser/getNowUser');
        this.userInfo = res.data.data;
        this.quotationForm.company.responsiblePerson = this.userInfo.name || this.quotationForm.company.responsiblePerson
        console.log(this.userInfo, 'uuuu')
      },
      fetchQuotationInfo() {
        this.isReadOnly = !!this.outQuotationForm;
        if (this.isReadOnly) {
          this.quotationForm = this.outQuotationForm
          console.log('readonly', this.quotationForm)
        }
        if (!!!this.ReadOnly) {
          this.fromOut = true
        }
        this.isReadOnly = !!this.ReadOnly
        console.log('readonly', this.ReadOnly)

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
      },
      async fetchQuotedMaterials() {
        try {

          const res = await this.$http.get('/materials/getAll');

          this.quotedMaterials = res.data.data.map(item => ({
            ...item

          }));
          console.log(this.quotedMaterials)

        } catch (error) {
          console.error('获取物料时出错:', error);
        }

      },
      async fetchHistoryMaterials() {
        try {

          const res = await this.$http.get('/raw-materials/getAll');
          // 修改 historyMaterials 中的 specification 属性
          this.historyMaterials = res.data.data
          console.log(this.historyMaterials)

        } catch (error) {
          console.error('获取历史原材料时出错:', error);
        }

      },
      generateQuote() {
        if (!this.quotationForm.company.name) {
          this.$message.error('请输入公司名称');
          return;
        }

        if (!this.quotationForm.customer.name) {
          this.$message.error('请输入客户名称');
          return;
        }
        if (!this.quotationForm.company.responsiblePerson) {
          this.$message.error('请输入负责人');
          return;
        }

        if (!this.quotationForm.company.contactInfo) {
          this.$message.error('请输入负责人联系方式');
          return;
        }
        if (!this.quotationForm.customer.contactInfo) {
          this.$message.error('请输入客户联系方式');
          return;
        }

        if (!this.quotationForm.customer.paymentMethod) {
          this.$message.error('请输入付款方式');
          return;
        }
        if (!this.quotationForm.material.quotedMaterialId) {
          this.$message.error('请选择报价物料');
          return;
        }

        if (!this.quotationForm.material.historyMaterialId) {
          this.$message.error('请选择原材料类型');
          return;
        }
        // 获取子组件的 customDetails
        if (this.quotationForm.material.type === 'custom' && this.quotationForm.material.historyMaterialId) {
          const customDetails = this.$refs.customDetail.getCustomDetails();
          this.quotationForm.material.customDetails = customDetails;
          this.quotationForm.material.historyDetails = null;
          console.log(customDetails);
        } else {
          const historyDetails = this.$refs.historyDetail.getHistoryDetails();
          this.quotationForm.material.historyDetails = historyDetails;
          this.quotationForm.material.customDetails = null;
          console.log(historyDetails);

        }
        if (this.selectedQuotedMaterials.length > 0) {
          this.quotationForm.material.quotedMaterials = this.selectedQuotedMaterials[0]
        }
        this.quotationForm.material.quotedMaterials.isExternalProcurement = this.quotationForm.material.quotedMaterials.isExternalProcurement ? 1 : 0
        // 将 quotationForm 转换为 JSON 字符串
        const formData = JSON.stringify(this.quotationForm);
        console.log('fff', formData, 'aaa', this.quotationForm)

        // 跳转到生成报价单的页面，并传递数据
        this.$router.push({
          path: '/quoteGeneration',
          query: { formData, fromOut: this.fromOut }
        }).catch(err => {
          console.error('Router push error:', err);
        });




        // 跳转到生成报价单的页面
        //this.$router.push('/quote-generation');
      },
    },
  };
</script>

<style scoped>
  .quotation-form {
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
  }

  .main-title {
    text-align: center;
    margin-bottom: 20px;
    font-size: 24px;
    font-weight: bold;
  }

  .data-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
  }

  .data-table th,
  .data-table td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: center;
  }

  .data-table th {
    background-color: #f2f2f2;
    font-weight: bold;
  }

  .column-1 {
    width: 30%;
  }

  .column-2 {
    width: 70%;
  }

  .input-field {
    width: 100%;
    padding: 8px;
    border: none;
    border-bottom: 1px solid #555;
    /* 下边框颜色 */
    box-sizing: border-box;
    margin-bottom: 10px;
    font-size: 20px;
  }

  .input-field:focus {
    outline: none;
    border-bottom: 2px solid #007bff;
  }

  .price-inputs {
    display: flex;
    align-items: center;
  }

  .small-input {
    width: 80px;
    /* 调整输入框宽度 */
    margin-right: 5px;
  }

  .separator {
    margin: 0 5px;
  }

  .textarea-field {
    width: 100%;
    padding: 8px;
    border: 1px solid #555;
    /* 可选的边框 */
    box-sizing: border-box;
    height: 60px;
    resize: none;
    /* 禁止调整大小 */
  }

  .textarea-field:focus {
    outline: none;
    border: 2px solid #007bff;
  }

  .btn-submit {
    margin-top: 20px;
    padding: 10px 20px;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .btn-submit:hover {
    background-color: #218838;
  }

  .divider {
    border: 1px solid #bcbfc2;
    margin: 20px 0;
  }

  table {
    font-size: 16px;
    line-height: 30px;
    height: 30px;
  }

  .required {
    color: red;
  }
</style>