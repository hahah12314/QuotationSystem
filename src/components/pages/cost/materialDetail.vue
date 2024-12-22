<template>
  <el-card class="box-card">
    <div id="materialDetail">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="dataForm.materialName" placeholder="材料名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog">新增</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="dataList" border style="width: 100%" size="mini">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="55"></el-table-column>
        <el-table-column prop="specification" label="材料规格" width="140"></el-table-column>
        <el-table-column prop="unitWeight" label="单位重量 (公斤)" width="140"></el-table-column>
        <el-table-column prop="netWeight" label="净重 (公斤)" width="120"></el-table-column>
        <el-table-column prop="nakedPrice" label="裸价"></el-table-column>
        <el-table-column prop="profit" label="利润"></el-table-column>
        <el-table-column prop="miscellaneousFees" label="杂费"></el-table-column>
        <el-table-column prop="preTax" label="税前总价" width="100"></el-table-column>
        <el-table-column prop="pricePerkg" label="产品总价" width="100"></el-table-column>
        <el-table-column label="操作" width="240">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="handleDetail(scope.row)">详情</el-button>
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper" style="margin-top: 20px"></el-pagination>
    </div>

    <el-dialog :title="dataDialogForm.detailId ? '编辑材料' : '新增材料'" :visible.sync="dialogFormVisible" width="35%">
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
        size="mini">
        <el-form-item label="材料规格" prop="specification">
          <el-input v-model="dataDialogForm.specification" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单位重量 (公斤)" prop="unitWeight">
          <el-input v-model="dataDialogForm.unitWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="净重 (公斤)" prop="netWeight">
          <el-input v-model="dataDialogForm.netWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="裸价" prop="nakedPrice">
          <el-input v-model="dataDialogForm.nakedPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="利润" prop="profit">
          <el-input v-model="dataDialogForm.profit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="杂费" prop="miscellaneousFees">
          <el-input v-model="dataDialogForm.miscellaneousFees" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="税前总价" prop="preTax">
          <el-input v-model="dataDialogForm.preTax" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品总价" prop="pricePerkg">
          <el-input v-model="dataDialogForm.pricePerkg" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="submitMaterial()">确定</el-button>
      </div>
    </el-dialog>
    <!-- 弹出框显示 MaterialInfo -->
    <el-dialog :visible.sync="dialogVisible" title="原材料明细" width="80%" custom-class="custom-modal">
      <el-card>
        <MaterialInfo :detailId="selectedMaterialInfo.detailId" v-if="selectedMaterialInfo" />

      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCloseDetailsModal">关 闭</el-button>
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
  import MaterialInfo from "@/components/detail/MaterialInfo";
  export default {
    name: "materialDetail",
    components: {
      MaterialInfo
    },
    data() {
      return {
        selectedMaterialInfo: null,
        dialogVisible: false,
        dataForm: {
          materialName: "",
        },
        dataDialogForm: {
          detailId: null,
          specification: "",
          unitWeight: "",
          netWeight: "",
          nakedPrice: "",
          profit: "",
          miscellaneousFees: "",
          preTax: "",
          pricePerkg: "",
        },
        dataList: [],
        pageSize: 10,
        pageIndex: 1,
        totalPage: 0,
        dialogFormVisible: false,
        rules: {
          specification: [{ required: true, message: "材料规格不能为空", trigger: "blur" }],
          unitWeight: [
            { required: true, message: "请输入单位重量", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          netWeight: [
            { required: true, message: "请输入净重", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          nakedPrice: [
            { required: true, message: "请输入裸价", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          profit: [
            { required: true, message: "请输入利润", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          miscellaneousFees: [
            { required: true, message: "请输入杂费", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          preTax: [
            { required: true, message: "请输入税前总价", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          pricePerkg: [
            { required: true, message: "请输入产品总价", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
        },
      };
    },
    methods: {
      async getDataList() {
        try {
          const params = {
            pageSize: this.pageSize,
            pageIndex: this.pageIndex,
            materialName: this.dataForm.materialName,
          };
          const response = await this.$http.get("/raw-materials/list", { params });
          const data = response.data.data;
          this.dataList = data.list;
          this.totalPage = data.totalCount;
        } catch (error) {
          console.error("获取数据失败:", error);
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
        this.$refs.ruleForm?.resetFields(); // 重置表单校验
        this.dataDialogForm = {
          detailId: null,
          specification: "",
          unitWeight: "",
          netWeight: "",
          nakedPrice: "",
          profit: "",
          miscellaneousFees: "",
          preTax: "",
          pricePerkg: "",
        };
      },
      async handleEdit(row) {
        this.dialogFormVisible = true;
        this.$refs.ruleForm?.resetFields(); // 重置表单校验
        this.dataDialogForm = { ...row };
      },
      handleDetail(row) {
        this.selectedMaterialInfo = row
        console.log('row', row)
        this.dialogVisible = true
      },
      handleCloseDetailsModal() {
        this.selectedMaterialInfo = null
        this.dialogVisible = false
      },
      async handleDelete(row) {
        this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(async () => {
            try {
              await this.$http.delete("/raw-materials/delete", { data: { detail_id: row.detailId } });
              this.$message.success("删除成功");
              this.getDataList();
            } catch (error) {
              console.error("删除失败:", error);
              this.$message.error("删除失败");
            }
          })
          .catch(() => {
            this.$message.info("已取消删除");
          });
      },
      async submitMaterial() {
        this.$refs.ruleForm.validate(async (valid) => {
          if (!valid) return;
          const url = this.dataDialogForm.detailId ? "/raw-materials/update" : "/raw-materials/save";
          try {
            await this.$http.post(url, this.dataDialogForm);
            this.$message.success(this.dataDialogForm.detailId ? "编辑成功" : "新增成功");
            this.dialogFormVisible = false;
            this.getDataList();
          } catch (error) {
            console.error("提交失败:", error);
            this.$message.error("提交失败");
          }
        });
      },
      closeDialog() {
        this.dialogFormVisible = false;
        this.$refs.ruleForm?.resetFields(); // 清除表单校验提示
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