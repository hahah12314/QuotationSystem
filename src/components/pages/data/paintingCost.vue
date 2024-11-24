<template>
  <el-card class="box-card">
    <div id="paintingCost">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline">
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

      <el-table :data="dataList" style="width: 100%" @selection-change="handleSelectionChange">
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

      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          style="margin-top: 30px"
      ></el-pagination>
    </div>

    <el-dialog :title="dataDialogForm.id === 0 ? '新增喷涂记录' : '编辑喷涂记录'" :visible.sync="dialogFormVisible" width="50%">
      <el-form :model="dataDialogForm" ref="ruleForm" label-width="120px">
        <el-form-item label="detail id" prop="detailId">
          <el-input v-model="dataDialogForm.detailId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="材料规格" prop="specification">
          <el-input v-model="dataDialogForm.specification" autocomplete="off" />
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
<!--        <el-form-item label="总喷涂费用" prop="totalPaintingCost">-->
<!--          <el-input v-model="dataDialogForm.totalPaintingCost" autocomplete="off" />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
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
      pageIndex: 1,
      pageSize: 5,
      totalPage: 0,
      dialogFormVisible: false,
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
        const res = await this.$http.get("/painting-cost/list", { params });
        this.dataList = res.data.data.list || [];
        this.totalPage = res.data.data.totalCount || 0;
      } catch (error) {
        console.error("获取数据失败：", error);
      }
    },
    openDialog() {
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
      this.dataDialogForm = { ...row };
      this.dialogFormVisible = true;
    },
    async handleDelete(row) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
          .then(async () => {
            try {
              await this.$http.delete('/painting-cost/delete', { data: { id: row.id } });
              this.$message.success('删除成功');
              this.getDataList(); // 重新获取数据列表
            } catch (error) {
              console.error('删除失败:', error);
              this.$message.error('删除失败');
            }
          })
          .catch(() => {
            this.$message.info('已取消删除');
          });
    },
    async submitMaterial() {
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
  .dialog-footer{
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
