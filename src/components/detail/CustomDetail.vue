<template>
  <div class="detail-container">
    <div class="table-container">

      <table class="data-table">
        <thead>
          <tr>
            <th colspan="7">自定义单件加工费用</th>
          </tr>
          <tr>
            <th>项目</th>
            <th>工人</th>
            <th>等级</th>
            <th>每小时工时价格</th>
            <th>工时</th>
            <th>单位</th>
            <th>合计</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, key) in processingCostItems" :key="key">
            <td>{{ item.name }}</td>
            <td>
              <select v-model="processingCost[item.workhourId]"
                @change="updateProcessingCost(item.name, $event.target.value)">
                <option v-for="worker in workers[item.name]" :key="worker.id" :value="worker.id">
                  {{ worker.workerName }}
                </option>
              </select>
            </td>
            <td>{{ getWorkerLevel(processingCost[item.workhourId], item.name) }}</td>
            <td>{{ getWorkerHourlyRate(processingCost[item.workhourId], item.name) }}</td>
            <td>{{ getWorkerTime(processingCost[item.workhourId], item.name) }}</td>
            <td>小时</td>
            <td>{{ processingCost[item.fee] }}</td>
          </tr>
          <tr>
            <td>总加工费用</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>元</td>
            <td>{{ totalProcessCost }}</td>
          </tr>
        </tbody>
      </table>
      <table class="data-table">
        <thead>
          <tr>
            <th colspan="13">成本明细表</th>
          </tr>
          <tr>
            <th colspan="10">原材料费用</th>
            <th colspan="3">数割费用</th>
          </tr>
          <tr>
            <th>长/直径/外径</th>
            <th>宽/直径/壁厚</th>
            <th>高/长/长</th>
            <th>毛坯重量(kg)</th>
            <th>材料单价(元/kg)</th>
            <th>毛坯费(元)</th>
            <th>废料重</th>
            <th>废料单价(元/kg)</th>
            <th>废料费(元)</th>
            <th>原材料费用小计</th>
            <th>数割(M)</th>
            <th>下料费</th>
            <th>费用小计</th>
          </tr>
        </thead>
        <tbody>
          <!-- 原材料费用 -->
          <tr>

            <td><input v-model="materialCost.length" /></td>
            <td><input v-model="materialCost.width" /></td>
            <td><input v-model="materialCost.height" /></td>
            <td>{{blankWeight}}</td>
            <td><input v-model="materialCost.materialUnitPrice" /></td>
            <td>{{blankFee}}</td>
            <td>{{scrapWeight}}</td>
            <td><input v-model="materialCost.scrapUnitPrice" /></td>
            <td>{{scrapFee}}</td>
            <td>{{totalMaterialCost}}</td>
            <td><input v-model="cuttingCost.numCut" /></td>
            <td><input v-model="cuttingCost.cutFee" /></td>
            <td>{{totalCutCost}}</td>
          </tr>
        </tbody>
        <thead>

          <tr>
            <th colspan="13">表面处理单件费用（元）</th>

          </tr>
          <tr>

            <th colspan="2">镀锌</th>
            <th colspan="2">调质</th>
            <th colspan="2">冲砂</th>
            <th colspan="2">QPQ</th>
            <th colspan="2">镀铬</th>
            <th colspan="2">镀镍</th>

            <th rowspan="2">费用小计</th>
          </tr>
          <tr>
            <th>重量</th>
            <th>金额</th>
            <th>重量</th>
            <th>金额</th>
            <th>重量</th>
            <th>金额</th>
            <th>重量</th>
            <th>金额</th>
            <th>面积</th>
            <th>厚度(丝)</th>
            <th>面积(dm2)</th>
            <th>厚度(丝)</th>

          </tr>
        </thead>

        <tbody>
          <!-- 表面处理单件费用 -->
          <tr>
            <td>{{ zincWeight }}</td>
            <td>{{ zincFee }}</td>
            <td>{{ temperingWeight }}</td>
            <td>{{ temperingFee }}</td>
            <td>{{ sandWeight }}</td>
            <td>{{ sandFee }}</td>
            <td>{{ qpqWeight }}</td>
            <td>{{ qpqFee }}</td>
            <td><input v-model.number="surfaceTreatment.chromeArea" /></td>
            <td>{{ chromeFee }}</td>
            <td><input v-model.number="surfaceTreatment.nickArea" /></td>
            <td>{{ nickFee }}</td>
            <td>{{ totalCoatingCost }}</td>
          </tr>
        </tbody>
        <thead>
          <tr>
            <th colspan="13">喷涂单件费用（元）</th>
          </tr>
          <tr>
            <th colspan="2">酸洗/磷化</th>
            <th colspan="2">喷塑</th>
            <th colspan="2">电泳</th>
            <th colspan="2">底漆</th>
            <th colspan="2">面漆</th>
            <th rowspan="2" colspan="3">费用小计</th>
          </tr>
          <tr>
            <th>重量</th>
            <th>金额</th>
            <th>面积</th>
            <th>金额</th>
            <th>面积</th>
            <th>金额</th>
            <th>面积</th>
            <th>金额</th>
            <th>面积</th>
            <th>金额</th>
          </tr>
        </thead>
        <tbody>
          <!-- 喷涂单件费用 -->
          <tr>
            <td><input v-model.number="paintingCost.acidWashWeight" /></td>
            <td>{{ acidWashFee }}</td>
            <td><input v-model.number="paintingCost.sprayPlasticArea" /></td>
            <td>{{ sprayPlasticFee }}</td>
            <td><input v-model.number="paintingCost.electroswimmingArea" /></td>
            <td>{{ electroswimmingFee }}</td>
            <td><input v-model.number="paintingCost.primerArea" /></td>
            <td>{{ primerFee }}</td>
            <td><input v-model.number="paintingCost.topcoatArea" /></td>
            <td>{{ topcoatFee }}</td>
            <td colspan="3">{{ totalPaintingCost }}</td>
          </tr>
        </tbody>



      </table>
      <table class="data-table">
        <thead>
          <tr>
            <th colspan="9">材料详情</th>
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
            <th>产品合计报价</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><input v-model="rawMaterials.specification" /></td>

            <td><input v-model="rawMaterials.unitWeight" /></td>
            <td>{{netWeight}}</td>
            <td>{{nakedPrice}}</td>
            <td>{{profit}}</td>
            <td>{{miscellaneousFees}}</td>
            <td>{{pricePerKg}}</td>
            <td>{{preTax}}</td>
            <td>{{totalProductQuote}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'CustomDetail',
    props: {
      detailId: {
        type: Number,
        required: false // 允许不传递
      },
      quantity: {
        type: Number,
        default: 0 // 设置默认值为 0
      }
    },

    data() {
      return {
        projectMap: {
          '锯': 'saw',
          '弯': 'bend',
          '钻': 'drill',
          '车': 'turn',
          '外磨': 'grind',
          '铣': 'mill',
          '校平': 'calibrate',
          '镗铣': 'boreMill',
          '焊': 'weld',
          '打磨': 'polish',
          '装': 'assemble'
        },
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
        customDetails: {

        }

      }
    },
    computed: {
      processingCostItems() {
        return [
          { name: '锯', workhourId: 'sawWorkhourId', time: 'sawTime', fee: 'sawFee' },
          { name: '弯', workhourId: 'bendWorkhourId', time: 'bendTime', fee: 'bendFee' },
          { name: '钻', workhourId: 'drillWorkhourId', time: 'drillTime', fee: 'drillFee' },
          { name: '车', workhourId: 'turnWorkhourId', time: 'turnTime', fee: 'turnFee' },
          { name: '外磨', workhourId: 'grindWorkhourId', time: 'grindTime', fee: 'grindFee' },
          { name: '铣', workhourId: 'millWorkhourId', time: 'millTime', fee: 'millFee' },
          { name: '校平', workhourId: 'calibrateWorkhourId', time: 'calibrateTime', fee: 'calibrateFee' },
          { name: '镗铣', workhourId: 'boreMillWorkhourId', time: 'boreMillTime', fee: 'boreMillFee' },
          { name: '焊', workhourId: 'weldWorkhourId', time: 'weldTime', fee: 'weldFee' },
          { name: '打磨', workhourId: 'polishWorkhourId', time: 'polishTime', fee: 'polishFee' },
          { name: '装', workhourId: 'assembleWorkhourId', time: 'assembleTime', fee: 'assembleFee' }
        ];
      },
      totalProcessCost: {
        get() {
          return parseFloat(this.processingCostItems.reduce((total, item) => {
            return total + (this.processingCost[item.fee] || 0);
          }, 0).toFixed(2));
        }
      },
      blankWeight: {
        get() {
          return parseFloat((this.materialCost.length * this.materialCost.width * this.materialCost.height * 0.00000785 * this.quantity).toFixed(2));
        }
      },
      blankFee: {
        get() {
          return parseFloat((this.blankWeight * this.materialCost.materialUnitPrice).toFixed(2));
        }
      },
      scrapWeight: {
        get() {
          return parseFloat(((this.blankWeight - this.netWeight) / 1.01).toFixed(2));
        }
      },
      scrapFee: {
        get() {
          return parseFloat((this.scrapWeight * this.materialCost.scrapUnitPrice).toFixed(2));
        }
      },
      totalMaterialCost: {
        get() {
          return parseFloat((this.scrapFee - this.blankFee).toFixed(2));
        }
      },
      totalCutCost: {
        get() {
          return parseFloat((this.cuttingCost.numCut * this.cuttingCost.cutFee * this.quantity).toFixed(2));
        }
      },
      zincWeight: {
        get() {
          return parseFloat(this.netWeight.toFixed(2));
        }
      },
      zincFee: {
        get() {
          return parseFloat((this.zincWeight * 2.4).toFixed(2));
        }
      },
      temperingWeight: {
        get() {
          return parseFloat(this.netWeight.toFixed(2));
        }
      },
      temperingFee: {
        get() {
          return parseFloat((this.temperingWeight * 1.6).toFixed(2));
        }
      },
      sandWeight: {
        get() {
          return parseFloat(this.netWeight.toFixed(2));
        }
      },
      sandFee: {
        get() {
          return parseFloat((this.sandWeight * 0.35).toFixed(2));
        }
      },
      qpqWeight: {
        get() {
          return parseFloat(this.netWeight.toFixed(2));
        }
      },
      qpqFee: {
        get() {
          return parseFloat((this.qpqWeight * 4.5).toFixed(2));
        }
      },
      chromeFee: {
        get() {
          return parseFloat((this.surfaceTreatment.chromeArea * 250).toFixed(2));
        }
      },
      nickFee: {
        get() {
          return parseFloat((this.surfaceTreatment.nickArea * 200).toFixed(2));
        }
      },
      totalCoatingCost: {
        get() {
          return parseFloat((this.zincFee + this.temperingFee + this.sandFee + this.qpqFee + this.chromeFee + this.nickFee).toFixed(2));
        }
      },
      acidWashFee: {
        get() {
          return parseFloat((this.paintingCost.acidWashWeight * 0.4).toFixed(2));
        }
      },
      sprayPlasticFee: {
        get() {
          return parseFloat((this.paintingCost.sprayPlasticArea * this.quantity * 65).toFixed(2));
        }
      },
      electroswimmingFee: {
        get() {
          return parseFloat((this.paintingCost.electroswimmingArea * this.quantity * 22).toFixed(2));
        }
      },
      primerFee: {
        get() {
          return parseFloat((this.paintingCost.primerArea * this.quantity * 35).toFixed(2));
        }
      },
      topcoatFee: {
        get() {
          return parseFloat((this.paintingCost.topcoatArea * this.quantity * 70).toFixed(2));
        }
      },
      totalPaintingCost: {
        get() {
          return parseFloat((this.acidWashFee + this.sprayPlasticFee + this.electroswimmingFee + this.primerFee + this.topcoatFee).toFixed(2));
        }
      },
      netWeight: {
        get() {
          return parseFloat((this.rawMaterials.unitWeight * this.quantity).toFixed(2));
        }
      },
      miscellaneousFees: {
        get() {
          return parseFloat((this.nakedPrice * 0.07 + this.netWeight * 0.17 + this.netWeight * 0.03).toFixed(2));
        }
      },
      profit: {
        get() {
          return parseFloat(((this.totalMaterialCost + this.totalCutCost + this.totalProcessCost + this.totalCoatingCost + this.totalPaintingCost) * 0.05).toFixed(2));
        }
      },
      nakedPrice: {
        get() {
          return parseFloat((this.totalMaterialCost + this.totalCutCost + this.totalProcessCost + this.totalCoatingCost + this.totalPaintingCost).toFixed(2));
        }
      },
      pricePerKg: {
        get() {
          if (this.netWeight === 0) {
            return 0;
          }
          return parseFloat((this.preTax / this.netWeight).toFixed(2));
        }
      },
      preTax: {
        get() {
          return parseFloat((this.totalProductQuote / 1.13).toFixed(2));
        }
      },
      totalProductQuote: {
        get() {
          return parseFloat((this.nakedPrice + this.profit + this.miscellaneousFees).toFixed(2));
        }
      }

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
      },
      scrapFee(newVal) {
        this.materialCost.scrapFee = newVal;
      },
      totalMaterialCost(newVal) {
        this.materialCost.totalMaterialCost = newVal;
      },
      totalCutCost(newVal) {
        this.cuttingCost.totalCutCost = newVal;
      },
      zincWeight(newVal) {
        this.surfaceTreatment.zincWeight = newVal;
      },
      zincFee(newVal) {
        this.surfaceTreatment.zincFee = newVal;
      },
      temperingWeight(newVal) {
        this.surfaceTreatment.temperingWeight = newVal;
      },
      temperingFee(newVal) {
        this.surfaceTreatment.temperingFee = newVal;
      },
      sandWeight(newVal) {
        this.surfaceTreatment.sandWeight = newVal;
      },
      sandFee(newVal) {
        this.surfaceTreatment.sandFee = newVal;
      },
      qpqWeight(newVal) {
        this.surfaceTreatment.qpqWeight = newVal;
      },
      qpqFee(newVal) {
        this.surfaceTreatment.qpqFee = newVal;
      },
      chromeFee(newVal) {
        this.surfaceTreatment.chromeFee = newVal;
      },
      nickFee(newVal) {
        this.surfaceTreatment.nickFee = newVal;
      },
      totalCoatingCost(newVal) {
        this.surfaceTreatment.totalCoatingCost = newVal;
      },
      acidWashFee(newVal) {
        this.paintingCost.acidWashFee = newVal;
      },
      sprayPlasticFee(newVal) {
        this.paintingCost.sprayPlasticFee = newVal;
      },
      electroswimmingFee(newVal) {
        this.paintingCost.electroswimmingFee = newVal;
      },
      primerFee(newVal) {
        this.paintingCost.primerFee = newVal;
      },
      topcoatFee(newVal) {
        this.paintingCost.topcoatFee = newVal;
      },
      totalPaintingCost(newVal) {
        this.paintingCost.totalPaintingCost = newVal;
      },
      netWeight(newVal) {
        this.rawMaterials.netWeight = newVal;
      },
      miscellaneousFees(newVal) {
        this.rawMaterials.miscellaneousFees = newVal;
      },
      profit(newVal) {
        this.rawMaterials.profit = newVal;
      },
      nakedPrice(newVal) {
        this.rawMaterials.nakedPrice = newVal;
      },
      pricePerKg(newVal) {
        this.rawMaterials.pricePerKg = newVal;
      },
      preTax(newVal) {
        this.rawMaterials.preTax = newVal;
      },
      totalProductQuote(newVal) {
        this.rawMaterials.totalProductQuote = newVal;
      }
    },
    created() {
      this.getWorkerInfo()


    },
    methods: {
      async getMaterialInfo() {
        if (!this.detailId) {
          console.error('detailId 未提供');
          return;
        }
        // 设置默认的 workhourId
        this.setDefaultWorkhourIds()

        try {
          const res = await this.$http.get('/raw-materials/getDetail?detailId=' + this.detailId);
          this.rawMaterials = res.data.data.rawMaterials;
          this.materialCost = res.data.data.materialCost;

          this.surfaceTreatment = res.data.data.surfaceTreatment;
          this.cuttingCost = res.data.data.cuttingCost;
          this.paintingCost = res.data.data.paintingCost;
          console.log(res, this.rawMaterials);
        } catch (error) {
          console.error('获取已选菜单时出错:', error);
        }
      },
      async getWorkerInfo() {
        try {
          const res = await this.$http.get('/workers/getAll');
          this.workers = res.data.data;
          // 设置默认的 workhourId
          this.setDefaultWorkhourIds();
          console.log('www', this.workers);
        } catch (error) {
          console.error('获取工人信息时出错:', error);
        }
      },
      setDefaultWorkhourIds() {
        Object.keys(this.projectMap).forEach(project1 => {
          if (this.workers[project1] && this.workers[project1].length > 0) {
            const firstWorker = this.workers[project1][0];
            const project2 = this.projectMap[project1];
            this.$set(this.processingCost, `${project2}WorkhourId`, firstWorker.id);
            console.log(`设置默认 workerId: ${firstWorker.id}`)
            this.updateProcessingCost(project1, firstWorker.id);
          }
        });
        console.log('process', this.processingCost);
      },

      getWorkerLevel(workhourId, project) {
        if (this.workers[project] && this.workers[project].length > 0) {
          const worker = this.workers[project].find(w => w.id == workhourId);
          return worker ? worker.level : '';
        }
        return '';
      },
      getWorkerHourlyRate(workhourId, project) {
        console.log('getWorkerHourlyRate', workhourId, project, this.workers[project]);
        if (this.workers[project] && this.workers[project].length > 0) {
          const worker = this.workers[project].find(w => w.id == workhourId);
          return worker ? worker.cost : 0;
        }
        return 0;
      },
      getWorkerTime(workhourId, project) {
        if (this.workers[project] && this.workers[project].length > 0) {
          const worker = this.workers[project].find(w => w.id == workhourId);
          return worker ? worker.hour : 0;
        }
        return 0;
      },
      updateProcessingCost(name, workhourId) {

        const worker = this.workers[name].find(w => w.id == workhourId);
        console.log('updateProcessingCost', name, workhourId, this.workers[name])
        if (worker) {
          const item = this.processingCostItems.find(i => i.name === name);
          this.processingCost[item.time] = this.getWorkerTime(workhourId, name);
          this.processingCost[item.fee] = this.getWorkerHourlyRate(workhourId, name) * this.getWorkerTime(workhourId, name);
          this.processingCost.totalProcessingCost = this.totalProcessCost;
        }
      },
      getCustomDetails() {
        this.customDetails = {
          rawMaterials: this.rawMaterials,
          paintingCost: this.paintingCost,
          materialCost: this.materialCost,
          cuttingCost: this.cuttingCost,
          processingCost: this.processingCost,
          surfaceTreatment: this.surfaceTreatment
        }
        return this.customDetails;
      }

    }
  }
</script>

<style scoped lang="less">
  .detail-container {

    position: relative;

    table {
      font-size: 16px;
      line-height: 30px;
      height: 30px;
    }

    .table-container {

      display: flex;
      justify-content: center;
      flex-direction: column;
      align-items: center;

      .form-inline {
        display: flex;
        margin-bottom: 20px;
      }

      .input {
        padding: 10px;
        margin-right: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        width: 200px;
      }

      .btn {
        padding: 10px 15px;
        margin-right: 10px;
        border: none;
        background-color: #f0f0f0;
        color: #333;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
      }

      .btn-primary {
        background-color: #007bff;
        color: white;
      }

      .btn-primary:hover {
        background-color: #0056b3;
      }

      .btn-edit {
        background-color: #ffc107;
        color: white;
      }

      .btn-edit:hover {
        background-color: #e0a800;
      }

      .btn-delete {
        background-color: #dc3545;
        color: white;
      }

      .btn-delete:hover {
        background-color: #c82333;
      }

      .data-table {
        width: 100%;
        border-collapse: collapse;

        input,
        select {
          width: 100%;
          padding: 4px;
          border: none;

          /* 下边框颜色 */
          box-sizing: border-box;

          font-size: 20px;
        }

        input:focus,
        select:focus {
          outline: none;
          border-bottom: 2px solid #007bff;
        }
      }

      .data-table th,
      .data-table td {
        border: 1px solid #999;
        /* 较深的边框颜色 */
        padding: 8px;
        text-align: left;
      }

      .data-table th {
        background-color: #f2f2f2;
        /* 浅灰色背景 */
        text-align: center;
        /* 将标题居中 */
      }
    }


    .back-button-top-left {
      position: absolute;
      top: 0px;
      left: 0px;
      padding: 10px 20px;
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
      transition: background-color 0.3s ease;
    }

    .back-button-top-left:hover {
      background-color: #0056b3;
    }

    .back-button-bottom-right {
      position: absolute;
      bottom: -40px;
      right: 0px;
      padding: 10px 20px;
      background-color: #28a745;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
      transition: background-color 0.3s ease;
    }

    .back-button-bottom-right:hover {
      background-color: #218838;
    }





  }
</style>