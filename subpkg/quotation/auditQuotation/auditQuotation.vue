<template>
  <view class="quotation-container">
    <view class="header">
      <text class="title">金威报价单</text>
    </view>

    <view class="card-container">
      <!-- 报价信息 -->
      <view class="card ">
        <view class="card-header">报价信息</view>
        <view class="card-body">

          <text>公司名称: {{ quotationForm.company.name }}</text>
          <text>客户名称: {{ quotationForm.customer.name }}</text>


          <text>负责人: {{ quotationForm.company.responsiblePerson }}</text>
          <text>负责人联系方式: {{ quotationForm.company.contactInfo }}</text>

          <text>邮箱: {{ quotationForm.customer.email }}</text>
          <text>客户联系方式: {{ quotationForm.customer.contactInfo }}</text>

          <text>地址: {{ quotationForm.customer.address }}</text>
          <text>预期价格: {{ quotationForm.customer.minPrice }} - {{ quotationForm.customer.maxPrice }}</text>

          <text>支付方式: {{ quotationForm.customer.paymentMethod }}</text>
          <text>要求: {{ quotationForm.customer.requirements }}</text>

        </view>
      </view>

      <!-- 成本明细 -->
      <view class="card cost-details">
        <view class="card-header">原材料费用</view>
        <view class="card-body">

          <text>长/直径/外径: {{ quotationForm.material.costDetails.materialCost.length }}</text>
          <text>宽/直径/壁厚: {{ quotationForm.material.costDetails.materialCost.width }}</text>
          <text>高/长/长: {{ quotationForm.material.costDetails.materialCost.height }}</text>
          <text>毛坯重量(kg): {{ quotationForm.material.costDetails.materialCost.blankWeight }}</text>
          <text>材料单价(元/kg): {{ quotationForm.material.costDetails.materialCost.materialUnitPrice }}</text>
          <text>毛坯费(元): {{ quotationForm.material.costDetails.materialCost.blankFee }}</text>
          <text>废料重: {{ quotationForm.material.costDetails.materialCost.scrapWeight }}</text>
          <text>废料单价(元/kg): {{ quotationForm.material.costDetails.materialCost.scrapUnitPrice }}</text>
          <text>废料费(元): {{ quotationForm.material.costDetails.materialCost.scrapFee }}</text>
          <text>原材料费用小计: {{ quotationForm.material.costDetails.materialCost.totalMaterialCost }}</text>
        </view>
      </view>


      <view class="card cost-details">
        <view class="card-header">数割费用</view>
        <view class="card-body">

          <text>数割(M): {{ quotationForm.material.costDetails.cuttingCost.numCut }}</text>
          <text>下料费: {{ quotationForm.material.costDetails.cuttingCost.cutFee }}</text>
          <text>费用小计: {{ quotationForm.material.costDetails.cuttingCost.totalCutCost }}</text>
        </view>
      </view>

      <view class="card cost-details">
        <view class="card-header">单件加工费用(元)</view>
        <view class="card-body">

          <text>锯工时: {{ quotationForm.material.costDetails.processingCost.sawTime }}</text>
          <text>锯金额: {{ quotationForm.material.costDetails.processingCost.sawFee }}</text>
          <text>弯工时: {{ quotationForm.material.costDetails.processingCost.bendTime }}</text>
          <text>弯金额: {{ quotationForm.material.costDetails.processingCost.bendFee }}</text>
          <text>钻工时: {{ quotationForm.material.costDetails.processingCost.drillTime }}</text>
          <text>钻金额: {{ quotationForm.material.costDetails.processingCost.drillFee }}</text>
          <text>车工时: {{ quotationForm.material.costDetails.processingCost.turnTime }}</text>
          <text>车金额: {{ quotationForm.material.costDetails.processingCost.turnFee }}</text>
          <text>外磨工时: {{ quotationForm.material.costDetails.processingCost.grindTime }}</text>
          <text>外磨金额: {{ quotationForm.material.costDetails.processingCost.grindFee }}</text>
          <text>铣工时: {{ quotationForm.material.costDetails.processingCost.millTime }}</text>
          <text>铣金额: {{ quotationForm.material.costDetails.processingCost.millFee }}</text>
          <text>校平工时: {{ quotationForm.material.costDetails.processingCost.calibrateTime }}</text>
          <text>校平金额: {{ quotationForm.material.costDetails.processingCost.calibrateFee }}</text>
          <text>镗铣工时: {{ quotationForm.material.costDetails.processingCost.boreMillTime }}</text>
          <text>镗铣金额: {{ quotationForm.material.costDetails.processingCost.boreMillFee }}</text>
          <text>焊工时: {{ quotationForm.material.costDetails.processingCost.weldTime }}</text>
          <text>焊金额: {{ quotationForm.material.costDetails.processingCost.weldFee }}</text>
          <text>打磨工时: {{ quotationForm.material.costDetails.processingCost.polishTime }}</text>
          <text>打磨金额: {{ quotationForm.material.costDetails.processingCost.polishFee }}</text>
          <text>装工时: {{ quotationForm.material.costDetails.processingCost.assembleTime }}</text>
          <text>装金额: {{ quotationForm.material.costDetails.processingCost.assembleFee }}</text>
          <text>费用小计: {{ quotationForm.material.costDetails.processingCost.totalProcessCost }}</text>
        </view>
      </view>


      <view class="card cost-details">
        <view class="card-header">表面处理单件费用（元）</view>
        <view class="card-body">

          <text>镀锌重量: {{ quotationForm.material.costDetails.surfaceTreatment.zincWeight }}</text>
          <text>镀锌金额: {{ quotationForm.material.costDetails.surfaceTreatment.zincFee }}</text>
          <text>调质重量: {{ quotationForm.material.costDetails.surfaceTreatment.temperingWeight }}</text>
          <text>调质金额: {{ quotationForm.material.costDetails.surfaceTreatment.temperingFee }}</text>
          <text>冲砂重量: {{ quotationForm.material.costDetails.surfaceTreatment.sandWeight }}</text>
          <text>冲砂金额: {{ quotationForm.material.costDetails.surfaceTreatment.sandFee }}</text>
          <text>QPQ重量: {{ quotationForm.material.costDetails.surfaceTreatment.qpqWeight }}</text>
          <text>QPQ金额: {{ quotationForm.material.costDetails.surfaceTreatment.qpqFee }}</text>
          <text>镀铬面积: {{ quotationForm.material.costDetails.surfaceTreatment.chromeArea }}</text>
          <text>镀铬金额: {{ quotationForm.material.costDetails.surfaceTreatment.chromeFee }}</text>
          <text>镀镍面积: {{ quotationForm.material.costDetails.surfaceTreatment.nickArea }}</text>
          <text>镀镍金额: {{ quotationForm.material.costDetails.surfaceTreatment.nickFee }}</text>
          <text>费用小计: {{ quotationForm.material.costDetails.surfaceTreatment.totalCoatingCost }}</text>
        </view>
      </view>


      <view class="card cost-details">
        <view class="card-header">喷涂单件费用（元）</view>
        <view class="card-body">

          <text>酸洗/磷化重量: {{ quotationForm.material.costDetails.paintingCost.acidWashWeight }}</text>
          <text>酸洗/磷化金额: {{ quotationForm.material.costDetails.paintingCost.acidWashCost }}</text>
          <text>喷塑面积: {{ quotationForm.material.costDetails.paintingCost.sprayPlasticArea }}</text>
          <text>喷塑金额: {{ quotationForm.material.costDetails.paintingCost.sprayPlasticCost }}</text>
          <text>电泳面积: {{ quotationForm.material.costDetails.paintingCost.electroswimmingArea }}</text>
          <text>电泳金额: {{ quotationForm.material.costDetails.paintingCost.electroswimmingCost }}</text>
          <text>底漆面积: {{ quotationForm.material.costDetails.paintingCost.primerArea }}</text>
          <text>底漆金额: {{ quotationForm.material.costDetails.paintingCost.primerCost }}</text>
          <text>面漆面积: {{ quotationForm.material.costDetails.paintingCost.topcoatArea }}</text>
          <text>面漆金额: {{ quotationForm.material.costDetails.paintingCost.topcoatCost }}</text>
          <text>费用小计: {{ quotationForm.material.costDetails.paintingCost.totalPaintingCost }}</text>
        </view>
      </view>


      <view class="card cost-details">
        <view class="card-header">材料详情</view>
        <view class="card-body">
          <text>材料规格: {{ quotationForm.material.costDetails.rawMaterials.specification }}</text>
          <text>单位重量（公斤）: {{ quotationForm.material.costDetails.rawMaterials.unitWeight }}</text>
          <text>净重（公斤）: {{ quotationForm.material.costDetails.rawMaterials.netWeight }}</text>
          <text>裸价: {{ quotationForm.material.costDetails.rawMaterials.nakedPrice }}</text>
          <text>利润: {{ quotationForm.material.costDetails.rawMaterials.profit }}</text>
          <text>杂费: {{ quotationForm.material.costDetails.rawMaterials.miscellaneousFees }}</text>
          <text>公斤价: {{ quotationForm.material.costDetails.rawMaterials.pricePerkg }}</text>
          <text>税前总价: {{ quotationForm.material.costDetails.rawMaterials.preTax }}</text>
          <text>产品合计报价: {{ quotationForm.material.costDetails.rawMaterials.totalProductQuote }}</text>
        </view>
      </view>
      <view class="button-container">
        <button class="button" @click="returnToEdit">审核通过</button>
        <button class="button submit-button" @click="showAuditFailDialog">审核不通过</button>
      </view>

    </view>
    <view class="footer">
      <text>&copy; 2023 金威. 版权所有.</text>
    </view>
    
  </view>
</template>

<script>
  export default {
    data() {
      return {
        quotationForm: {
          company: {
            name: '',
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
            costDetails: {
              rawMaterials: {
                specification: '',
                nakedPrice: 0,
                profit: 0,
                miscellaneousFees: 0,
                netWeight: 0,
                unitWeight: 0,
                pricePerKg: 0,
                preTax: 0,
                totalProductQuote: 0
              },
              cuttingCost: {
                cutFee: 0,
                numCut: 0,
                totalCutCost: 0
              },
              materialCost: {
                length: 0,
                width: 0,
                height: 0,
                blankWeight: 9,
                materialUnitPrice: 0,
                blankFee: 0,
                scrapUnitPrice: 0,
                scrapWeight: 0,
                scrapFee: 0,
                totalMaterialCost: 0
              },

              processingCost: {
                sawWorkhourId: null,
                sawTime: 0,
                sawFee: 0,
                bendWorkhourId: null,
                bendTime: 0,
                bendFee: 0,
                drillWorkhourId: null,
                drillTime: 0,
                drillFee: 0,
                turnWorkhourId: null,
                turnTime: 0,
                turnFee: 0,
                grindWorkhourId: null,
                grindTime: 0,
                grindFee: 0,
                millWorkhourId: null,
                millTime: 0,
                millFee: 0,
                calibrateWorkhourId: null,
                calibrateTime: 0,
                calibrateFee: 0,
                boreMillWorkhourId: null,
                boreMillTime: 0,
                boreMillFee: 0,
                weldWorkhourId: null,
                weldTime: 0,
                weldFee: 0,
                polishWorkhourId: null,
                polishTime: 0,
                polishFee: 0,
                assembleWorkhourId: null,
                assembleTime: 0,
                assembleFee: 0,
                totalProcessCost: 0

              },
              surfaceTreatment: {
                zincFee: 0,
                zincWeight: 0,
                temperingFee: 0,
                temperingWeight: 0,
                sandFee: 0,
                sandWeight: 0,
                qpqFee: 0,
                qpqWeight: 0,
                chromeArea: 0,
                nickArea: 0,
                chromeFee: 0,
                nickFee: 0,
                totalCoatingCost: 0
              },
              paintingCost: {
                acidWashWeight: 0,
                sprayPlasticArea: 0,
                electroswimmingArea: 0,
                primerArea: 0,
                topcoatArea: 0,
                acidWashFee: 0,
                sprayPlasticFee: 0,
                electroswimmingFee: 0,
                primerFee: 0,
                topcoatFee: 0,
                totalPaintingCost: 0
              }
            },
            quotedMaterials: {
              materialCode: '',
              materialName: '',
              unitPrice: 0,
              quantity: 0,
              specification: '',
              isExternalProcurement: 0
            }
          }
        }
      };
    },
    onLoad() {
      // 从本地缓存中读取数据
      const storedQuotation = uni.getStorageSync('quotation');
      if (storedQuotation) {
        try {
          this.quotationForm = JSON.parse(storedQuotation);
          console.log('ccc', this.quotationForm)
        } catch (e) {
          console.error('Failed to parse quotation data:', e);
        }
      }
      this.determineCostDetailsSource()
    },

    computed: {

    },
    methods: {
      determineCostDetailsSource() {
        if (this.quotationForm.material.customDetails) {
          this.quotationForm.material.costDetails = this.quotationForm.material.customDetails;
        } else if (this.quotationForm.material.historyDetails) {
          this.quotationForm.material.costDetails = this.quotationForm.material.historyDetails;
        } else {
          this.quotationForm.material.costDetails = {};
        }
        console.log('ddd', this.quotationForm.material.costDetails)
      },
      returnToEdit() {
        uni.$http.get('/quotations/auditQuotation?quotationId=' + this.quotationForm.quotationId );

        uni.showToast({
          title: '审核通过',
          icon: 'success',
          duration: 2000
        });
        uni.reLaunch({
          url:'/subpkg/quotation/quotationReview/quotationReview'
        })
        // 这里可以添加跳转到编辑页面的逻辑
      },
      showAuditFailDialog() {
            uni.showModal({
              title: '审核不通过',
              content: '',
              placeholderText: '请输入审核不通过的原因',
              editable: true,
              success: (res) => {
                if (res.confirm) {
                  const auditReason = res.value;
                  if (auditReason && auditReason.trim()) {
                    uni.showToast({
                      title: '已提交审核意见',
                      icon: 'success',
                      duration: 2000
                    });
                    // 这里可以添加提交审核意见的逻辑
                  } else {
                    uni.showToast({
                      title: '审核意见不能为空',
                      icon: 'none',
                      duration: 2000
                    });
                  }
                } else if (res.cancel) {
                  uni.showToast({
                    title: '取消提交',
                    icon: 'none',
                    duration: 2000
                  });
                }
              }
            });
          },
      returnToHome() {
        uni.reLaunch({
          url: '/pages/home/home'
        })
      },
      fetchDataFromQuery() {

      }
    }
  };
</script>

<style scoped lang="less">
  .quotation-container {


    background-color: #e7eaf4;

  }

  .header {
    text-align: center;
    margin-bottom: 20px;
  }

  .title {
    font-size: 24px;
    font-weight: bold;
  }

  .card-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    gap: 15px;
    width: 100%;
  }

  .card {
    width: 80%;
    padding: 15px;
    margin-bottom: 10px;
    background-color: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 10px;
      font-weight: bold;

      .quotation-name {
        font-size: 16px;
        font-weight: bold;
      }

      .quotation-index {
        font-size: 12px;
        color: #888;
      }
    }

    .card-body {
      display: flex;
      flex-direction: column;
      gap: 5px;

      text {
        font-size: 14px;
        color: #555;
      }
    }
  }

  .button-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    width: 100%;
  }

  .button {
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 10px;
    padding: 10px;
    margin: 0 10px;
    cursor: pointer;
    transition: background-color 0.3s;
    width: 180px;
    /* 宽度 */
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    /* 高度 */
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

  .footer {
    text-align: center;
    margin-top: 20px;
    color: #888;
  }
</style>