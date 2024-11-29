<template>
  <el-card class="box-card">
    <div id="surfaceTreatment">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input
              v-model="dataForm.specification"
              placeholder="材料规格"
              clearable
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog()">新增</el-button>
        </el-form-item>
      </el-form>

      <el-table
          :data="dataList"
          style="width: 100%"
          @selection-change="handleSelectionChange" size="mini"
      >
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

      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          style="margin-top: 30px"
      >
      </el-pagination>
    </div>

    <el-dialog
        :title="dataDialogForm.id === 0 ? '新增表面处理费用' : '编辑表面处理费用'"
        :visible.sync="dialogFormVisible"
        width="50%"
        @close="closeDialog"
    >
      <el-form
          :model="dataDialogForm"
          :rules="rules"
          ref="ruleForm"
          label-width="120px" size="mini"
      >
        <el-form-item label="detail id" prop="detailId">
          <el-input
              v-model="dataDialogForm.detailId"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="材料规格" prop="specification">
          <el-input
              v-model="dataDialogForm.specification"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="镀锌重量" prop="zincWeight">
          <el-input
              v-model="dataDialogForm.zincWeight"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="镀锌费用" prop="zincFee">
          <el-input
              v-model="dataDialogForm.zincFee"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="淬火重量" prop="temperingWeight">
          <el-input
              v-model="dataDialogForm.temperingWeight"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="淬火费用" prop="temperingFee">
          <el-input
              v-model="dataDialogForm.temperingFee"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="砂光重量" prop="sandWeight">
          <el-input
              v-model="dataDialogForm.sandWeight"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="砂光费用" prop="sandFee">
          <el-input
              v-model="dataDialogForm.sandFee"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="QPQ重量" prop="qpqWeight">
          <el-input
              v-model="dataDialogForm.qpqWeight"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="QPQ费用" prop="qpqFee">
          <el-input
              v-model="dataDialogForm.qpqFee"
              autocomplete="off"
          ></el-input>
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
      pageSize: 5,
      totalPage: 0,
      dialogFormVisible: false,
      rules: {
        specification: [{ required: true, message: "材料规格不能为空", trigger: "blur" }],
        zincWeight: [
          { required: true, message: "镀锌重量不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
        zincFee: [
          { required: true, message: "镀锌费用不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
        temperingWeight: [
          { required: true, message: "淬火重量不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
        temperingFee: [
          { required: true, message: "淬火费用不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
        sandWeight: [
          { required: true, message: "砂光重量不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
        sandFee: [
          { required: true, message: "砂光费用不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
        qpqWeight: [
          { required: true, message: "QPQ重量不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
        qpqFee: [
          { required: true, message: "QPQ费用不能为空", trigger: "blur" },
          { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
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
        totalCoatingFee: "",
      };
    },
    closeDialog() {
      this.dialogFormVisible = false;
      this.$refs.ruleForm?.resetFields();
    },
    async handleEdit(row) {
      this.$refs.ruleForm?.resetFields();
      this.dataDialogForm = { ...row };
      this.dialogFormVisible = true;
    },
    async submitMaterial() {
      this.$refs.ruleForm.validate(async (valid) => {
        if (!valid) return;
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
