<template>
  <el-card class="box-card">
    <div id="cuttingCost">
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
      <el-table :data="dataList" style="width: 100%" size="mini">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="55"></el-table-column>
        <el-table-column prop="specification" label="材料规格" width="130"></el-table-column>
        <el-table-column prop="numCut" label="数割(M)" width="180"></el-table-column>
        <el-table-column prop="cutFee" label="下料费"></el-table-column>
        <el-table-column prop="totalCutCost" label="费用小计"></el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper" style="margin-top: 30px;"></el-pagination>
    </div>

    <el-dialog :title="dataDialogForm.id === 0 ? '新增记录' : '编辑记录'" :visible.sync="dialogFormVisible" width="35%"
      @close="closeDialog">
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini">

        <el-form-item label="材料规格" prop="detailId">
          <el-select v-model="dataDialogForm.detailId" class="input-field" placeholder="请选择材料规格" style="width: 240px;">
            <el-option v-for="(material, index) in historyMaterials" :key="material.detailId"
              :label="`${index + 1}. ${material.specification}`" :value="material.detailId">
            </el-option>
          </el-select>


        </el-form-item>
        <el-form-item label="数割(M)" prop="numCut">
          <el-input v-model="dataDialogForm.numCut" autocomplete="off" style="width: 240px;"></el-input>
        </el-form-item>
        <el-form-item label="下料费" prop="cutFee">
          <el-input v-model="dataDialogForm.cutFee" autocomplete="off" style="width: 240px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="submitData()">确定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  export default {
    name: "cuttingCost",
    data() {
      return {
        dataForm: {
          specification: "",
        },
        historyMaterials: [], // 从后台获取的历史材料数据
        dataDialogForm: {
          id: "",
          detailId: "",
          specification: "",
          numCut: "",
          cutFee: "",
          totalCutCost: "",
        },
        dataList: [],
        pageSize: 5,
        pageIndex: 1,
        totalPage: 0,
        dialogFormVisible: false,
        rules: {
          detailId: [{ required: true, message: "请选择材料规格", trigger: "blur" }],
          numCut: [
            //{ required: false, message: "请输入数割(M)", trigger: "blur" },
            // {
            //   validator: (rule, value, callback) => {
            //     if (!/^\d+$/.test(value)) {
            //       callback(new Error("数割必须是正整数"));
            //     } else {
            //       callback();
            //     }
            //   },
            //   trigger: "blur",
            // },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },

          ],
          cutFee: [
            //{ required: false, message: "请输入下料费", trigger: "blur" },
            // {
            //   validator: (rule, value, callback) => {
            //     if (!/^-?\d{1,8}(\.\d{1,2})?$/.test(value)) {
            //       callback(new Error("格式为数字，最多保留两位小数"));
            //     } else {
            //       callback();
            //     }
            //   },
            //   trigger: "blur",
            // },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },

          ],
        },
      };
    },
    // methods: {
    //   closeDialog() {
    //     this.dialogFormVisible = false; // 关闭弹窗
    //     this.$nextTick(() => {
    //       if (this.$refs.ruleForm) {
    //         this.$refs.ruleForm.resetFields(); // 重置表单验证状态和内容
    //       }
    //     });
    //   },
    //   async getDataList() {
    //     const params = {
    //       pageSize: this.pageSize,
    //       pageIndex: this.pageIndex,
    //       specification: this.dataForm.specification,
    //     };
    //     try {
    //       const response = await this.$http.get("/cutting-cost/list", { params });
    //       const data = response.data.data;
    //       this.dataList = data.list;
    //       this.totalPage = data.totalCount;
    //     } catch (error) {
    //       this.$message.error("获取数据失败");
    //     }
    //   },
    //   sizeChangeHandle(val) {
    //     this.pageSize = val;
    //     this.getDataList();
    //   },
    //   currentChangeHandle(val) {
    //     this.pageIndex = val;
    //     this.getDataList();
    //   },
    //   openDialog() {
    //     this.dialogFormVisible = true;
    //     this.dataDialogForm = {
    //       id: "",
    //       detailId: "",
    //       specification: "",
    //       numCut: "",
    //       cutFee: "",
    //       totalCutCost: "",
    //     };
    //   },
    //   handleEdit(row) {
    //     this.dataDialogForm = { ...row };
    //     this.dialogFormVisible = true;
    //   },
    //   async handleDelete(row) {
    //     this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
    //       confirmButtonText: "确定",
    //       cancelButtonText: "取消",
    //       type: "warning",
    //     })
    //         .then(async () => {
    //           try {
    //             await this.$http.delete("/cutting-cost/delete", { data: { id: row.id } });
    //             this.$message.success("删除成功");
    //             this.getDataList();
    //           } catch (error) {
    //             this.$message.error("删除失败");
    //           }
    //         })
    //         .catch(() => {
    //           this.$message.info("已取消删除");
    //         });
    //   },
    //   calculateTotalCutCost() {
    //     // 计算费用小计
    //     if (this.dataDialogForm.numCut && this.dataDialogForm.cutFee) {
    //       const numCut = parseFloat(this.dataDialogForm.numCut);
    //       const cutFee = parseFloat(this.dataDialogForm.cutFee);
    //       this.dataDialogForm.totalCutCost = (numCut * cutFee).toFixed(2); // 保留两位小数
    //     }
    //   },
    //   async submitData() {
    //     this.$refs.ruleForm.validate(async (valid) => {
    //       if (!valid) return;
    //
    //       // 计算费用小计
    //       this.calculateTotalCutCost();
    //
    //       const url = this.dataDialogForm.id ? "/cutting-cost/update" : "/cutting-cost/save";
    //       try {
    //         await this.$http.post(url, this.dataDialogForm);
    //         this.$message.success(this.dataDialogForm.id ? "编辑成功" : "新增成功");
    //         this.dialogFormVisible = false;
    //         this.getDataList();
    //       } catch (error) {
    //         this.$message.error("提交失败");
    //       }
    //     });
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
      closeDialog() {
        this.dialogFormVisible = false; // 关闭弹窗
        this.$nextTick(() => {
          if (this.$refs.ruleForm) {
            this.$refs.ruleForm.resetFields(); // 重置表单验证状态和内容
          }
        });
      },
      async getDataList() {
        const params = {
          pageSize: this.pageSize,
          pageIndex: this.pageIndex,
          specification: this.dataForm.specification,
        };
        try {
          const response = await this.$http.get("/cutting-cost/list", { params });
          const data = response.data.data;
          this.dataList = data.list;
          this.totalPage = data.totalCount;
        } catch (error) {
          this.$message.error("获取数据失败");
        }
      },
      sizeChangeHandle(val) {
        this.pageSize = val;
        this.getDataList();
      },
      currentChangeHandle(val) {
        this.pageIndex = val;
        this.getDataList();
      },
      openDialog() {
        this.dialogFormVisible = true;
        this.dataDialogForm = {
          id: "",
          detailId: "",
          specification: "",
          numCut: "",
          cutFee: "",
          totalCutCost: "",
        };
      },
      handleEdit(row) {
        this.dataDialogForm = { ...row };
        this.dialogFormVisible = true;
      },
      async handleDelete(row) {
        this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(async () => {
            try {
              await this.$http.delete("/cutting-cost/delete", { data: { id: row.id } });
              this.$message.success("删除成功");
              this.getDataList();
            } catch (error) {
              this.$message.error("删除失败");
            }
          })
          .catch(() => {
            this.$message.info("已取消删除");
          });
      },
      calculateTotalCutCost() {
        // 计算费用小计
        if (this.dataDialogForm.numCut && this.dataDialogForm.cutFee) {
          const numCut = parseFloat(this.dataDialogForm.numCut);
          const cutFee = parseFloat(this.dataDialogForm.cutFee);
          this.dataDialogForm.totalCutCost = (numCut * cutFee).toFixed(2); // 保留两位小数
        }
      },
      // 新增或编辑时，自动填充空字段
      handleEmptyFields() {
        if (!this.dataDialogForm.numCut) {
          this.dataDialogForm.numCut = "0";
        }
        if (!this.dataDialogForm.cutFee) {
          this.dataDialogForm.cutFee = "0";
        }
        if (!this.dataDialogForm.totalCutCost) {
          this.dataDialogForm.totalCutCost = "0";
        }
      },
      async submitData() {
        this.$refs.ruleForm.validate(async (valid) => {
          if (!valid) return;

          // 处理空字段
          this.handleEmptyFields();

          // 计算费用小计
          this.calculateTotalCutCost();

          const url = this.dataDialogForm.id ? "/cutting-cost/update" : "/cutting-cost/save";
          try {
            await this.$http.post(url, this.dataDialogForm);
            this.$message.success(this.dataDialogForm.id ? "编辑成功" : "新增成功");
            this.dialogFormVisible = false;
            this.getDataList();
          } catch (error) {
            this.$message.error("提交失败");
          }
        });
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