<template>
  <view class="detail-container">
    <view class="card-container">
      <!-- 成本明细表 -->
      <view class="card">
        <view class="card-header">成本明细表</view>
        <view class="card-body">
          <view class="section">
            <view class="section-header">原材料费用</view>
            <view class="section-content">
              <view class="row">
                <text>长/直径/外径: {{ materialCost.length }}</text>
                <text>宽/直径/壁厚: {{ materialCost.width }}</text>
                <text>高/长/长: {{ materialCost.height }}</text>
                <text>毛坯重量(kg): {{ blankWeight }}</text>
                <text>材料单价(元/kg): {{ materialCost.materialUnitPrice }}</text>
                <text>毛坯费(元): {{ blankFee }}</text>
                <text>废料重: {{ scrapWeight }}</text>
                <text>废料单价(元/kg): {{ materialCost.scrapUnitPrice }}</text>
                <text>废料费(元): {{ scrapFee }}</text>
                <text>原材料费用小计: {{ totalMaterialCost }}</text>
              </view>
            </view>
          </view>

          <view class="section">
            <view class="section-header">数割费用</view>
            <view class="section-content">
              <view class="row">
                <text>数割(M): {{ cuttingCost.numCut }}</text>
                <text>下料费: {{ cuttingCost.cutFee }}</text>
                <text>费用小计: {{ totalCutCost }}</text>
              </view>
            </view>
          </view>
        </view>
      </view>

      <!-- 单件加工费用 -->
      <view class="card">
        <view class="card-header">单件加工费用(元)</view>
        <view class="card-body">
          <view class="row">
            <text>锯工时: {{ processingCost.sawTime }}</text>
            <text>锯金额: {{ processingCost.sawFee }}</text>
            <text>弯工时: {{ processingCost.bendTime }}</text>
            <text>弯金额: {{ processingCost.bendFee }}</text>
            <text>钻工时: {{ processingCost.drillTime }}</text>
            <text>钻金额: {{ processingCost.drillFee }}</text>
            <text>车工时: {{ processingCost.turnTime }}</text>
            <text>车金额: {{ processingCost.turnFee }}</text>
            <text>外磨工时: {{ processingCost.grindTime }}</text>
            <text>外磨金额: {{ processingCost.grindFee }}</text>
            <text>铣工时: {{ processingCost.millTime }}</text>
            <text>铣金额: {{ processingCost.millFee }}</text>
            <text>校平工时: {{ processingCost.calibrateTime }}</text>
            <text>校平金额: {{ processingCost.calibrateFee }}</text>
            <text>镗铣工时: {{ processingCost.boreMillTime }}</text>
            <text>镗铣金额: {{ processingCost.boreMillFee }}</text>
            <text>焊工时: {{ processingCost.weldTime }}</text>
            <text>焊金额: {{ processingCost.weldFee }}</text>
            <text>打磨工时: {{ processingCost.polishTime }}</text>
            <text>打磨金额: {{ processingCost.polishFee }}</text>
            <text>装工时: {{ processingCost.assembleTime }}</text>
            <text>装金额: {{ processingCost.assembleFee }}</text>
            <text>费用小计: {{ processingCost.totalProcessCost }}</text>
          </view>
        </view>
      </view>

      <!-- 表面处理单件费用 -->
      <view class="card">
        <view class="card-header">表面处理单件费用（元）</view>
        <view class="card-body">
          <view class="row">
            <text>镀锌重量: {{ zincWeight }}</text>
            <text>镀锌金额: {{ zincFee }}</text>
            <text>调质重量: {{ temperingWeight }}</text>
            <text>调质金额: {{ temperingFee }}</text>
            <text>冲砂重量: {{ sandWeight }}</text>
            <text>冲砂金额: {{ sandFee }}</text>
            <text>QPQ重量: {{ qpqWeight }}</text>
            <text>QPQ金额: {{ qpqFee }}</text>
            <text>镀铬面积: {{ surfaceTreatment.chromeArea }}</text>
            <text>镀铬金额: {{ chromeFee }}</text>
            <text>镀镍面积: {{ surfaceTreatment.nickArea }}</text>
            <text>镀镍金额: {{ nickFee }}</text>
            <text>费用小计: {{ totalCoatingCost }}</text>
          </view>
        </view>
      </view>

      <!-- 喷涂单件费用 -->
      <view class="card">
        <view class="card-header">喷涂单件费用（元）</view>
        <view class="card-body">
          <view class="row">
            <text>酸洗/磷化重量: {{ paintingCost.acidWashWeight }}</text>
            <text>酸洗/磷化金额: {{ acidWashFee }}</text>
            <text>喷塑面积: {{ paintingCost.sprayPlasticArea }}</text>
            <text>喷塑金额: {{ sprayPlasticFee }}</text>
            <text>电泳面积: {{ paintingCost.electroswimmingArea }}</text>
            <text>电泳金额: {{ electroswimmingFee }}</text>
            <text>底漆面积: {{ paintingCost.primerArea }}</text>
            <text>底漆金额: {{ primerFee }}</text>
            <text>面漆面积: {{ paintingCost.topcoatArea }}</text>
            <text>面漆金额: {{ topcoatFee }}</text>
            <text>费用小计: {{ totalPaintingCost }}</text>
          </view>
        </view>
      </view>

      <!-- 材料详情 -->
      <view class="card">
        <view class="card-header">材料详情</view>
        <view class="card-body">
          <view class="row">
            <text>材料规格: {{ rawMaterials.specification }}</text>
            <text>单位重量（公斤）: {{ rawMaterials.unitWeight }}</text>
            <text>净重（公斤）: {{ netWeight }}</text>
            <text>裸价: {{ nakedPrice }}</text>
            <text>利润: {{ profit }}</text>
            <text>杂费: {{ miscellaneousFees }}</text>
            <text>公斤价: {{ pricePerKg }}</text>
            <text>税前总价: {{ preTax }}</text>
            <text>产品合计报价: {{ totalProductQuote }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: 'MaterialInfo',
  props: {
    detailId: {
      type: String,
      required: false // 允许不传递
    },
    quantity: {
      type: Number,
      default: 0 // 设置默认值为 0
    }
  },
  created() {
    this.getMaterialInfo();
  },
  data() {
    return {
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
      workers: {}, // 初始化为对象
      workerInfo: {}, // 初始化为对象
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
      },
      historyDetails: {},
      detailId: null,
      isComponent: true
    };
  },
  watch: {
    detailId: {
      immediate: true,
      handler(newVal) {
        if (newVal) {
          this.getMaterialInfo();
        }
      }
    },
    scrapWeight(newVal) {
      this.materialCost.scrapWeight = newVal;
    },
    blankWeight(newVal) {
      this.materialCost.blankWeight = newVal;
    },
    blankFee(newVal) {
      this.materialCost.blankFee = newVal;
    }
  },
  methods: {
    getMaterialInfo() {
      // 获取材料信息的逻辑
    }
  }
};
</script>

<style scoped>
.detail-container {
  padding: 20px;
}

.card-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-header {
  background-color: #007bff;
  color: #fff;
  padding: 10px;
  text-align: center;
  font-size: 18px;
}

.card-body {
  padding: 15px;
}

.section {
  margin-bottom: 10px;
}

.section-header {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.section-content {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.row text {
  flex-basis: 50%;
  font-size: 14px;
}
</style>