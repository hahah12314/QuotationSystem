<template>
  <el-card class="box-card">
    <div id="surfaceTreatment">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="dataForm.specification" placeholder="材料规格" clearable></el-input>
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

        <el-table-column prop="specification" label="材料规格" width="130"></el-table-column>
        <el-table-column prop="zincWeight" label="镀锌重量"></el-table-column>
        <el-table-column prop="zincFee" label="镀锌费用"></el-table-column>
        <el-table-column prop="temperingWeight" label="淬火重量"></el-table-column>
        <el-table-column prop="temperingFee" label="淬火费用"></el-table-column>
        <el-table-column prop="sandWeight" label="砂光重量"></el-table-column>
        <el-table-column prop="sandFee" label="砂光费用"></el-table-column>
        <el-table-column prop="qpqWeight" label="QPQ重量"></el-table-column>
        <el-table-column prop="qpqFee" label="QPQ费用"></el-table-column>
        <el-table-column prop="totalCoatingCost" label="总涂层费用"></el-table-column>
        <el-table-column label="操作" width="250">
          <template #default="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper" style="margin-top: 30px">
      </el-pagination>
    </div>

    <el-dialog :title="dataDialogForm.id === 0 ? '新增表面处理费用' : '编辑表面处理费用'" :visible.sync="dialogFormVisible" width="50%"
      @close="closeDialog">
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini">
        <el-form-item label="材料规格" prop="detailId">
          <el-select v-model="dataDialogForm.detailId" class="input-field" placeholder="请选择材料规格">
            <el-option v-for="(material, index) in historyMaterials" :key="material.detailId"
              :label="`${index + 1}. ${material.specification}`" :value="material.detailId">
            </el-option>
          </el-select>


        </el-form-item>
        <el-form-item label="镀锌重量" prop="zincWeight">
          <el-input v-model="dataDialogForm.zincWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="镀锌费用" prop="zincFee">
          <el-input v-model="dataDialogForm.zincFee" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="淬火重量" prop="temperingWeight">
          <el-input v-model="dataDialogForm.temperingWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="淬火费用" prop="temperingFee">
          <el-input v-model="dataDialogForm.temperingFee" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="砂光重量" prop="sandWeight">
          <el-input v-model="dataDialogForm.sandWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="砂光费用" prop="sandFee">
          <el-input v-model="dataDialogForm.sandFee" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="QPQ重量" prop="qpqWeight">
          <el-input v-model="dataDialogForm.qpqWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="QPQ费用" prop="qpqFee">
          <el-input v-model="dataDialogForm.qpqFee" autocomplete="off"></el-input>
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
    name: "SurfaceTreatment",
    data() {
      return {
        dataForm: {
          specification: "",
        },
        historyMaterials: [], // 从后台获取的历史材料数据
        dataList: [],
        dataDialogForm: {
          id: 0,
          detailId: "",
          specification: "",
          zincWeight: "",
          zincFee: "",
          temperingWeight: "",
          temperingFee: "",
          sandWeight: "",
          sandFee: "",
          qpqWeight: "",
          qpqFee: "",
          totalCoatingFee: "",
        },
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dialogFormVisible: false,
        rules: {
          detailId: [{ required: true, message: "请选择材料规格", trigger: "blur" }],
          zincWeight: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          zincFee: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          temperingWeight: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          temperingFee: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          sandWeight: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          sandFee: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          qpqWeight: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          qpqFee: [
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
        },
      };
    },
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
      // 计算总涂层费用
      calculateTotalCoatingCost() {
        const { zincFee, temperingFee, sandFee, qpqFee } = this.dataDialogForm;
        const totalCost = (parseFloat(zincFee) || 0) + (parseFloat(temperingFee) || 0) +
          (parseFloat(sandFee) || 0) + (parseFloat(qpqFee) || 0);
        this.dataDialogForm.totalCoatingCost = totalCost.toFixed(2); // 保留两位小数
      },

      // 获取数据列表
      async getDataList() {
        const params = {
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          specification: this.dataForm.specification,
        };
        try {
          const res = await this.$http.get("/surface-treatment/list", { params });
          this.dataList = res.data.data.list || [];
          this.totalPage = res.data.data.totalCount || 0;
        } catch (error) {
          console.error("获取数据失败：", error);
        }
      },

      // 打开新增表单对话框
      openDialog() {
        this.$refs.ruleForm?.resetFields();
        this.dialogFormVisible = true;
        this.dataDialogForm = {
          id: 0,
          detailId: "",
          specification: "",
          zincWeight: "",
          zincFee: "",
          temperingWeight: "",
          temperingFee: "",
          sandWeight: "",
          sandFee: "",
          qpqWeight: "",
          qpqFee: "",
          totalCoatingCost: "", // 新增字段
        };
      },

      // 关闭表单对话框
      closeDialog() {
        this.dialogFormVisible = false;
        this.$refs.ruleForm?.resetFields();
      },

      // 编辑数据
      async handleEdit(row) {
        this.$refs.ruleForm?.resetFields();
        this.dataDialogForm = { ...row };
        this.calculateTotalCoatingCost(); // 编辑时也计算总涂层费用
        this.dialogFormVisible = true;
      },

      // 提交表单数据
      async submitMaterial() {
        this.$refs.ruleForm.validate(async (valid) => {
          if (!valid) return;

          // 提交前处理空字段为 0
          this.handleEmptyFields();

          // 提交前计算总涂层费用
          this.calculateTotalCoatingCost();

          const url = this.dataDialogForm.id === 0 ? "/surface-treatment/save" : "/surface-treatment/update";
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

      // 处理空字段为 0
      handleEmptyFields() {
        const fields = [
          "zincWeight", "zincFee", "temperingWeight", "temperingFee",
          "sandWeight", "sandFee", "qpqWeight", "qpqFee"
        ];

        fields.forEach(field => {
          if (!this.dataDialogForm[field]) {
            this.$set(this.dataDialogForm, field, "0");
          }
        });
      },

      // 删除数据
      async handleDelete(row) {
        this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(async () => {
            try {
              await this.$http.delete("/surface-treatment/delete", {
                data: { id: row.id },
              });
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

      // 分页大小改变时的处理
      sizeChangeHandle(val) {
        this.pageSize = val;
        this.getDataList();
      },

      // 当前页改变时的处理
      currentChangeHandle(val) {
        this.pageIndex = val;
        this.getDataList();
      },
    },



    mounted() {
      this.getDataList();
      this.fetchHistoryMaterials()
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