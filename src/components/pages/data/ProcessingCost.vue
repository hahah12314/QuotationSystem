<template>
  <el-card class="box-card">
    <div id="processingCost">
      <!-- 查询和操作按钮 -->
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="dataForm.specification" placeholder="材料规格" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog">新增</el-button>
        </el-form-item>
      </el-form>

      <!-- 数据表格 -->
      <el-table :data="dataList" style="width: 100%" @selection-change="handleSelectionChange" size="mini">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="55"></el-table-column>
        <el-table-column prop="specification" label="材料规格" width="130"></el-table-column>
        <el-table-column prop="sawTime" label="锯切时间"></el-table-column>
        <el-table-column prop="sawFee" label="锯切费用"></el-table-column>
        <el-table-column prop="bendTime" label="弯曲时间"></el-table-column>
        <el-table-column prop="bendFee" label="弯曲费用"></el-table-column>
        <el-table-column prop="drillTime" label="钻孔时间"></el-table-column>
        <el-table-column prop="drillFee" label="钻孔费用"></el-table-column>
        <el-table-column prop="turnTime" label="车削时间"></el-table-column>
        <el-table-column prop="turnFee" label="车削费用"></el-table-column>
        <el-table-column prop="grindTime" label="磨削时间"></el-table-column>
        <el-table-column prop="grindFee" label="磨削费用"></el-table-column>
        <el-table-column prop="millTime" label="铣削时间"></el-table-column>
        <el-table-column prop="millFee" label="铣削费用"></el-table-column>
        <el-table-column prop="calibrateTime" label="校正时间"></el-table-column>
        <el-table-column prop="calibrateFee" label="校正费用"></el-table-column>
        <el-table-column prop="boreMillTime" label="镗铣时间"></el-table-column>
        <el-table-column prop="boreMillFee" label="镗铣费用"></el-table-column>
        <el-table-column prop="weldTime" label="焊接时间"></el-table-column>
        <el-table-column prop="weldFee" label="焊接费用"></el-table-column>
        <el-table-column prop="polishTime" label="抛光时间"></el-table-column>
        <el-table-column prop="polishFee" label="抛光费用"></el-table-column>
        <el-table-column prop="assembleTime" label="组装时间"></el-table-column>
        <el-table-column prop="assembleFee" label="组装费用"></el-table-column>
        <el-table-column prop="totalProcessCost" label="总加工费用"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper" style="margin-top: 20px">
      </el-pagination>
    </div>

    <!-- 弹出框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="60%" @close="closeDialog">
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="材料规格" prop="detailId">
              <el-select v-model="dataDialogForm.detailId" class="input-field" placeholder="请选择材料规格">
                <el-option v-for="(material, index) in historyMaterials" :key="material.detailId"
                  :label="`${index + 1}. ${material.specification}`" :value="material.detailId">
                </el-option>
              </el-select>


            </el-form-item>
          </el-col>
          <!-- 循环渲染字段 -->
          <el-col v-for="(label, key) in fields" :key="key" :span="12">
            <el-form-item :label="label" :prop="key">
              <el-input v-model="dataDialogForm[key]"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取消</el-button>
        <el-button type="primary" @click="submitForm()">保存</el-button>
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
        historyMaterials: [], // 从后台获取的历史材料数据
        dataDialogForm: {
          detailId: "",
          specification: "",
          sawTime: "",
          sawFee: "",
          bendTime: "",
          bendFee: "",
          drillTime: "",
          drillFee: "",
          turnTime: "",
          turnFee: "",
          grindTime: "",
          grindFee: "",
          millTime: "",
          millFee: "",
          calibrateTime: "",
          calibrateFee: "",
          boreMillTime: "",
          boreMillFee: "",
          weldTime: "",
          weldFee: "",
          polishTime: "",
          polishFee: "",
          assembleTime: "",
          assembleFee: "",
          totalProcessCost: 0, // 添加此字段，用于计算总加工费用
        },
        fields: {
          sawTime: "锯切时间",
          sawFee: "锯切费用",
          bendTime: "弯曲时间",
          bendFee: "弯曲费用",
          drillTime: "钻孔时间",
          drillFee: "钻孔费用",
          turnTime: "车削时间",
          turnFee: "车削费用",
          grindTime: "磨削时间",
          grindFee: "磨削费用",
          millTime: "铣削时间",
          millFee: "铣削费用",
          calibrateTime: "校正时间",
          calibrateFee: "校正费用",
          boreMillTime: "镗铣时间",
          boreMillFee: "镗铣费用",
          weldTime: "焊接时间",
          weldFee: "焊接费用",
          polishTime: "抛光时间",
          polishFee: "抛光费用",
          assembleTime: "组装时间",
          assembleFee: "组装费用",
        },
        dataList: [],
        dialogFormVisible: false,
        pageSize: 5,
        pageIndex: 1,
        totalPage: 0,
        dialogTitle: "",
        rules: {
          detailId: [{ required: true, message: "请选择材料规格", trigger: "blur" }],
          specification: [{ required: true, message: "材料规格不能为空", trigger: "blur" }],
          sawTime: [
            // { required: true, message: "锯切时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          sawFee: [
            //{ required: true, message: "锯切费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          bendTime: [
            //{ required: true, message: "弯曲时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          bendFee: [
            //{ required: true, message: "弯曲费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          drillTime: [
            //{ required: true, message: "钻孔时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          drillFee: [
            //  { required: true, message: "钻孔费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          turnTime: [
            // { required: true, message: "车削时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          turnFee: [
            // { required: true, message: "车削费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          grindTime: [
            //{ required: true, message: "磨削时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          grindFee: [
            // { required: true, message: "磨削费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          millTime: [
            //{ required: true, message: "铣削时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          millFee: [
            //{ required: true, message: "铣削费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          calibrateTime: [
            //{ required: true, message: "校正时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          calibrateFee: [
            //{ required: true, message: "校正费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          boreMillTime: [
            // { required: true, message: "镗铣时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          boreMillFee: [
            //{ required: true, message: "镗铣费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          weldTime: [
            //{ required: true, message: "焊接时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          weldFee: [
            //{ required: true, message: "焊接费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          polishTime: [
            //{ required: true, message: "抛光时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          polishFee: [
            //{ required: true, message: "抛光费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          assembleTime: [
            //{ required: true, message: "组装时间不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          assembleFee: [
            //{ required: true, message: "组装费用不能为空", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
        },
      };
    },
    // methods: {
    //   async getDataList() {
    //     const params = { pageSize: this.pageSize, pageIndex: this.pageIndex, specification: this.dataForm.specification };
    //     try {
    //       const res = await this.$http.get("/processing-cost/list", { params });
    //       this.dataList = res.data.data.list || [];
    //       this.totalPage = res.data.data.totalCount || 0;
    //     } catch (error) {
    //       console.error("获取数据失败：", error);
    //     }
    //   },
    //   openDialog() {
    //     this.dialogFormVisible = true;
    //     this.dialogTitle = "新增记录";
    //     this.$refs.ruleForm?.resetFields();
    //     this.dataDialogForm = {
    //       detailId: "",
    //       specification: "",
    //       sawTime: "",
    //       sawFee: "",
    //       bendTime: "",
    //       bendFee: "",
    //       drillTime: "",
    //       drillFee: "",
    //       turnTime: "",
    //       turnFee: "",
    //       grindTime: "",
    //       grindFee: "",
    //       millTime: "",
    //       millFee: "",
    //       calibrateTime: "",
    //       calibrateFee: "",
    //       boreMillTime: "",
    //       boreMillFee: "",
    //       weldTime: "",
    //       weldFee: "",
    //       polishTime: "",
    //       polishFee: "",
    //       assembleTime: "",
    //       assembleFee: "",
    //       totalProcessCost: 0, // 重置时确保totalProcessCost为0
    //     };
    //   },
    //   closeDialog() {
    //     this.dialogFormVisible = false;
    //     this.$refs.ruleForm?.resetFields();
    //   },
    //   handleEdit(row) {
    //     this.dialogFormVisible = true;
    //     this.dialogTitle = "编辑记录";
    //     this.$refs.ruleForm?.resetFields();
    //     this.dataDialogForm = { ...row };
    //   },
    //   calculateTotalProcessCost() {
    //     // 自动计算总加工费用
    //     const fieldsToCalculate = [
    //       'sawFee', 'bendFee', 'drillFee', 'turnFee', 'grindFee', 'millFee',
    //       'calibrateFee', 'boreMillFee', 'weldFee', 'polishFee', 'assembleFee'
    //     ];
    //
    //     let totalCost = 0;
    //
    //     // 累加所有费用字段的值
    //     fieldsToCalculate.forEach(field => {
    //       const fee = parseFloat(this.dataDialogForm[field]) || 0; // 如果是空值则视为0
    //       totalCost += fee;
    //     });
    //
    //     // 更新总加工费用字段
    //     this.dataDialogForm.totalProcessCost = totalCost.toFixed(2); // 保留两位小数
    //   },
    //   async submitForm() {
    //     // 在提交之前先计算总加工费用
    //     this.calculateTotalProcessCost();
    //
    //     this.$refs.ruleForm.validate(async (valid) => {
    //       if (!valid) return;
    //       const url = this.dialogTitle === "新增记录" ? "/processing-cost/save" : "/processing-cost/update";
    //       try {
    //         await this.$http.post(url, this.dataDialogForm);
    //         this.$message.success("保存成功");
    //         this.dialogFormVisible = false;
    //         this.getDataList();
    //       } catch (error) {
    //         console.error("保存失败：", error);
    //       }
    //     });
    //   },
    //   async handleDelete(id) {
    //     this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
    //       confirmButtonText: "确定",
    //       cancelButtonText: "取消",
    //       type: "warning",
    //     })
    //         .then(async () => {
    //           try {
    //             await this.$http.delete("/processing-cost/delete", { data: { id } });
    //             this.$message.success("删除成功");
    //             this.getDataList();
    //           } catch (error) {
    //             console.error("删除失败：", error);
    //             this.$message.error("删除失败");
    //           }
    //         })
    //         .catch(() => {
    //           this.$message.info("已取消删除");
    //         });
    //   },
    //   sizeChangeHandle(size) {
    //     this.pageSize = size;
    //     this.getDataList();
    //   },
    //   currentChangeHandle(page) {
    //     this.pageIndex = page;
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
        const params = { pageSize: this.pageSize, pageIndex: this.pageIndex, specification: this.dataForm.specification };
        try {
          const res = await this.$http.get("/processing-cost/list", { params });
          this.dataList = res.data.data.list || [];
          this.totalPage = res.data.data.totalCount || 0;
        } catch (error) {
          console.error("获取数据失败：", error);
        }
      },
      openDialog() {
        this.dialogFormVisible = true;
        this.dialogTitle = "新增记录";
        this.$refs.ruleForm?.resetFields();
        this.dataDialogForm = {
          detailId: "",
          specification: "",
          sawTime: "",
          sawFee: "",
          bendTime: "",
          bendFee: "",
          drillTime: "",
          drillFee: "",
          turnTime: "",
          turnFee: "",
          grindTime: "",
          grindFee: "",
          millTime: "",
          millFee: "",
          calibrateTime: "",
          calibrateFee: "",
          boreMillTime: "",
          boreMillFee: "",
          weldTime: "",
          weldFee: "",
          polishTime: "",
          polishFee: "",
          assembleTime: "",
          assembleFee: "",
          totalProcessCost: 0, // 重置时确保totalProcessCost为0
        };
      },
      closeDialog() {
        this.dialogFormVisible = false;
        this.$refs.ruleForm?.resetFields();
      },
      handleEdit(row) {
        this.dialogFormVisible = true;
        this.dialogTitle = "编辑记录";
        this.$refs.ruleForm?.resetFields();
        this.dataDialogForm = { ...row };
        this.calculateTotalProcessCost(); // 编辑时自动计算总加工费用
      },
      calculateTotalProcessCost() {
        // 自动计算总加工费用
        const fieldsToCalculate = [
          'sawFee', 'bendFee', 'drillFee', 'turnFee', 'grindFee', 'millFee',
          'calibrateFee', 'boreMillFee', 'weldFee', 'polishFee', 'assembleFee'
        ];

        let totalCost = 0;

        // 累加所有费用字段的值
        fieldsToCalculate.forEach(field => {
          const fee = parseFloat(this.dataDialogForm[field]) || 0; // 如果是空值则视为0
          totalCost += fee;
        });

        // 更新总加工费用字段
        this.dataDialogForm.totalProcessCost = totalCost.toFixed(2); // 保留两位小数
      },
      handleEmptyFields() {
        // 遍历所有字段，如果为空则设置为0
        Object.keys(this.dataDialogForm).forEach(key => {
          if (this.dataDialogForm[key] === "" || this.dataDialogForm[key] === null) {
            this.dataDialogForm[key] = "0"; // 设置为字符串 "0"，后端可以根据需要转换成数字
          }
        });
      },
      async submitForm() {
        // 在提交之前先处理空字段
        this.handleEmptyFields();

        // 在提交之前计算总加工费用
        this.calculateTotalProcessCost();

        this.$refs.ruleForm.validate(async (valid) => {
          if (!valid) return;
          const url = this.dialogTitle === "新增记录" ? "/processing-cost/save" : "/processing-cost/update";
          try {
            await this.$http.post(url, this.dataDialogForm);
            this.$message.success("保存成功");
            this.dialogFormVisible = false;
            this.getDataList();
          } catch (error) {
            console.error("保存失败：", error);
          }
        });
      },
      async handleDelete(id) {
        this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(async () => {
            try {
              await this.$http.delete("/processing-cost/delete", { data: { id } });
              this.$message.success("删除成功");
              this.getDataList();
            } catch (error) {
              console.error("删除失败：", error);
              this.$message.error("删除失败");
            }
          })
          .catch(() => {
            this.$message.info("已取消删除");
          });
      },
      sizeChangeHandle(size) {
        this.pageSize = size;
        this.getDataList();
      },
      currentChangeHandle(page) {
        this.pageIndex = page;
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