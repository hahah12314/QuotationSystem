<template>
  <el-card class="box-card">
    <div id="paintingCost">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="dataForm.specification" placeholder="材料规格" clearable />
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog()">新增</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="dataList" style="width: 100%" @selection-change="handleSelectionChange" size="mini">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="55"></el-table-column>

        <el-table-column prop="specification" label="材料规格" width="130" />
        <el-table-column prop="acidWashWeight" label="酸洗/磷化重量" />
        <el-table-column prop="acidWashCost" label="酸洗/磷化金额" />
        <el-table-column prop="sprayPlasticArea" label="喷塑面积" />
        <el-table-column prop="sprayPlasticCost" label="喷塑金额" />
        <el-table-column prop="electroswimmingArea" label="电泳面积" />
        <el-table-column prop="electroswimmingCost" label="电泳金额" />
        <el-table-column prop="primerArea" label="底漆面积" />
        <el-table-column prop="primerCost" label="底漆金额" />
        <el-table-column prop="topcoatArea" label="面漆面积" />
        <el-table-column prop="topcoatCost" label="面漆金额" />
        <el-table-column prop="totalPaintingCost" label="总喷涂费用" />
        <el-table-column label="操作" width="250">
          <template #default="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper" style="margin-top: 30px"></el-pagination>
    </div>

    <el-dialog :title="dataDialogForm.id === 0 ? '新增喷涂记录' : '编辑喷涂记录'" :visible.sync="dialogFormVisible" width="50%">
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini">
        <el-form-item label="材料规格" prop="detailId">
          <el-select v-model="dataDialogForm.detailId" class="input-field" placeholder="请选择材料规格" >
            <el-option v-for="(material, index) in historyMaterials" :key="material.detailId"
              :label="`${index + 1}. ${material.specification}`" :value="material.detailId">
            </el-option>
          </el-select>


        </el-form-item>
        <el-form-item label="酸洗/磷化重量" prop="acidWashWeight">
          <el-input v-model="dataDialogForm.acidWashWeight" autocomplete="off" />
        </el-form-item>
        <el-form-item label="酸洗/磷化金额" prop="acidWashCost">
          <el-input v-model="dataDialogForm.acidWashCost" autocomplete="off" />
        </el-form-item>
        <el-form-item label="喷塑面积" prop="sprayPlasticArea">
          <el-input v-model="dataDialogForm.sprayPlasticArea" autocomplete="off" />
        </el-form-item>
        <el-form-item label="喷塑金额" prop="sprayPlasticCost">
          <el-input v-model="dataDialogForm.sprayPlasticCost" autocomplete="off" />
        </el-form-item>
        <el-form-item label="电泳面积" prop="electroswimmingArea">
          <el-input v-model="dataDialogForm.electroswimmingArea" autocomplete="off" />
        </el-form-item>
        <el-form-item label="电泳金额" prop="electroswimmingCost">
          <el-input v-model="dataDialogForm.electroswimmingCost" autocomplete="off" />
        </el-form-item>
        <el-form-item label="底漆面积" prop="primerArea">
          <el-input v-model="dataDialogForm.primerArea" autocomplete="off" />
        </el-form-item>
        <el-form-item label="底漆金额" prop="primerCost">
          <el-input v-model="dataDialogForm.primerCost" autocomplete="off" />
        </el-form-item>
        <el-form-item label="面漆面积" prop="topcoatArea">
          <el-input v-model="dataDialogForm.topcoatArea" autocomplete="off" />
        </el-form-item>
        <el-form-item label="面漆金额" prop="topcoatCost">
          <el-input v-model="dataDialogForm.topcoatCost" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取消</el-button>
        <el-button type="primary" @click="submitMaterial()">保存</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  export default {
    data() {
      return {
        dataForm: {
          specification: "",
        },
        dataList: [],
        dataDialogForm: {
          id: 0,
          detailId: "",
          specification: "",
          acidWashWeight: "",
          acidWashCost: "",
          sprayPlasticArea: "",
          sprayPlasticCost: "",
          electroswimmingArea: "",
          electroswimmingCost: "",
          primerArea: "",
          primerCost: "",
          topcoatArea: "",
          topcoatCost: "",
          totalPaintingCost: "",
        },
        historyMaterials: [], // 从后台获取的历史材料数据
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dialogFormVisible: false,
        rules: {
          detailId: [{ required: true, message: "请选择材料规格", trigger: "blur" }],
          acidWashWeight: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          acidWashCost: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          sprayPlasticArea: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          sprayPlasticCost: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          electroswimmingArea: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          electroswimmingCost: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          primerArea: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          primerCost: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          topcoatArea: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          topcoatCost: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
        },
      };
    },
    // methods: {
    //   async getDataList() {
    //     const params = {
    //       pageIndex: this.pageIndex,
    //       pageSize: this.pageSize,
    //       specification: this.dataForm.specification,
    //     };
    //     try {
    //       const res = await this.$http.get("/painting-cost/list", { params });
    //       this.dataList = res.data.data.list || [];
    //       this.totalPage = res.data.data.totalCount || 0;
    //     } catch (error) {
    //       console.error("获取数据失败：", error);
    //     }
    //   },
    //   openDialog() {
    //     this.$refs.ruleForm?.resetFields();
    //     this.dialogFormVisible = true;
    //     this.dataDialogForm = {
    //       id: 0,
    //       detailId: "",
    //       specification: "",
    //       acidWashWeight: "",
    //       acidWashCost: "",
    //       sprayPlasticArea: "",
    //       sprayPlasticCost: "",
    //       electroswimmingArea: "",
    //       electroswimmingCost: "",
    //       primerArea: "",
    //       primerCost: "",
    //       topcoatArea: "",
    //       topcoatCost: "",
    //       totalPaintingCost: "",
    //     };
    //   },
    //   async handleEdit(row) {
    //     this.$refs.ruleForm?.resetFields();
    //     this.dataDialogForm = { ...row };
    //     this.dialogFormVisible = true;
    //   },
    //   closeDialog() {
    //     this.dialogFormVisible = false;
    //     this.$refs.ruleForm?.resetFields();
    //   },
    //   calculateTotalCost() {
    //     // 计算总喷涂费用
    //     const { acidWashCost, sprayPlasticCost, electroswimmingCost, primerCost, topcoatCost } = this.dataDialogForm;
    //     this.dataDialogForm.totalPaintingCost = (
    //         parseFloat(acidWashCost || 0) +
    //         parseFloat(sprayPlasticCost || 0) +
    //         parseFloat(electroswimmingCost || 0) +
    //         parseFloat(primerCost || 0) +
    //         parseFloat(topcoatCost || 0)
    //     ).toFixed(2); // 保留两位小数
    //   },
    //   async submitMaterial() {
    //     this.$refs.ruleForm.validate(async (valid) => {
    //       if (!valid) return;
    //       this.calculateTotalCost(); // 自动计算总费用
    //       const url = this.dataDialogForm.id === 0 ? "/painting-cost/save" : "/painting-cost/update";
    //       try {
    //         await this.$http.post(url, this.dataDialogForm);
    //         this.$message.success("保存成功");
    //         this.dialogFormVisible = false;
    //         this.getDataList();
    //       } catch (error) {
    //         console.error("保存失败：", error);
    //         this.$message.error("保存失败");
    //       }
    //     });
    //   },
    //   sizeChangeHandle(val) {
    //     this.pageSize = val;
    //     this.getDataList();
    //   },
    //   currentChangeHandle(val) {
    //     this.pageIndex = val;
    //     this.getDataList();
    //   },
    // },
    methods: {
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
      async getDataList() {
        const params = {
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          specification: this.dataForm.specification,
        };
        try {
          const res = await this.$http.get("/painting-cost/list", { params });
          this.dataList = res.data.data.list || [];
          this.totalPage = res.data.data.totalCount || 0;
        } catch (error) {
          console.error("获取数据失败：", error);
        }
      },
      openDialog() {
        this.$refs.ruleForm?.resetFields();
        this.dialogFormVisible = true;
        this.dataDialogForm = {
          id: 0,
          detailId: "",
          specification: "",
          acidWashWeight: "",
          acidWashCost: "",
          sprayPlasticArea: "",
          sprayPlasticCost: "",
          electroswimmingArea: "",
          electroswimmingCost: "",
          primerArea: "",
          primerCost: "",
          topcoatArea: "",
          topcoatCost: "",
          totalPaintingCost: "",
        };
      },
      async handleEdit(row) {
        this.$refs.ruleForm?.resetFields();
        this.dataDialogForm = { ...row };
        this.dialogFormVisible = true;
      },
      closeDialog() {
        this.dialogFormVisible = false;
        this.$refs.ruleForm?.resetFields();
      },
      // 计算总喷涂费用
      calculateTotalCost() {
        const { acidWashCost, sprayPlasticCost, electroswimmingCost, primerCost, topcoatCost } = this.dataDialogForm;
        this.dataDialogForm.totalPaintingCost = (
          parseFloat(acidWashCost || 0) +
          parseFloat(sprayPlasticCost || 0) +
          parseFloat(electroswimmingCost || 0) +
          parseFloat(primerCost || 0) +
          parseFloat(topcoatCost || 0)
        ).toFixed(2); // 保留两位小数
      },
      async submitMaterial() {
        this.$refs.ruleForm.validate(async (valid) => {
          if (!valid) return;

          // 计算总喷涂费用
          this.calculateTotalCost();

          // 自动将空字段设置为 0
          for (const key in this.dataDialogForm) {
            if (this.dataDialogForm.hasOwnProperty(key) && (this.dataDialogForm[key] === "" || this.dataDialogForm[key] === null)) {
              this.dataDialogForm[key] = "0"; // 设置为空的字段为 0
            }
          }

          const url = this.dataDialogForm.id === 0 ? "/painting-cost/save" : "/painting-cost/update";
          try {
            await this.$http.post(url, this.dataDialogForm);
            this.$message.success("保存成功");
            this.dialogFormVisible = false;
            this.getDataList();
          } catch (error) {
            console.error("保存失败：", error);
            this.$message.error("保存失败");
          }
        });
      },
      sizeChangeHandle(val) {
        this.pageSize = val;
        this.getDataList();
      },
      currentChangeHandle(val) {
        this.pageIndex = val;
        this.getDataList();
      },
    },

    mounted() {
      this.getDataList();
      this.fetchHistoryMaterials();
    },
  };
</script>

<style scoped>
  .dialog-footer {
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>