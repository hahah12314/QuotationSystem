<template>
  <el-card class="box-card">
    <div id="materialCost">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline">
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
      <el-table :data="dataList" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="55"></el-table-column>
              <el-table-column prop="specification" label="材料规格" width="130"></el-table-column>
        <el-table-column prop="length" label="原材料长度"></el-table-column>
        <el-table-column prop="width" label="宽度"></el-table-column>
        <el-table-column prop="height" label="高度"></el-table-column>
        <el-table-column prop="blankWeight" label="毛坯重量"></el-table-column>
        <el-table-column prop="materialUnitPrice" label="材料单价"></el-table-column>
        <el-table-column prop="blankFee" label="毛坯费用"></el-table-column>
        <el-table-column prop="scrapWeight" label="废料重量"></el-table-column>
        <el-table-column prop="scrapUnitPrice" label="废料单价"></el-table-column>
        <el-table-column prop="scrapFee" label="废料费用"></el-table-column>
        <el-table-column prop="totalMaterialCost" label="总材料成本"></el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
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

    <el-dialog :title="dataDialogForm.id === 0 ? '新增材料规格' : '编辑材料规格'" :visible.sync="dialogFormVisible" width="35%">
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px">
        <el-form-item label="detail id" prop="detailId">
          <el-input v-model="dataDialogForm.detailId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="材料规格" prop="specification">
          <el-input v-model="dataDialogForm.specification" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="原材料长度" prop="length">
          <el-input v-model="dataDialogForm.length" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="宽度" prop="width">
          <el-input v-model="dataDialogForm.width" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="高度" prop="height">
          <el-input v-model="dataDialogForm.height" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="毛坯重量" prop="blankWeight">
          <el-input v-model="dataDialogForm.blankWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="材料单价" prop="materialUnitPrice">
          <el-input v-model="dataDialogForm.materialUnitPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="毛坯费用" prop="blankFee">
          <el-input v-model="dataDialogForm.blankFee" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="废料重量" prop="scrapWeight">
          <el-input v-model="dataDialogForm.scrapWeight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="废料单价" prop="scrapUnitPrice">
          <el-input v-model="dataDialogForm.scrapUnitPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="废料费用" prop="scrapFee">
          <el-input v-model="dataDialogForm.scrapFee" autocomplete="off"></el-input>
        </el-form-item>
        <!--        <el-form-item label="总材料成本" prop="totalMaterialCost">-->
        <!--          <el-input-->
        <!--              v-model="dataDialogForm.totalMaterialCost"-->
        <!--              autocomplete="off"-->
        <!--          ></el-input>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitMaterial('ruleForm')">
          确定
        </el-button>
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
        pageIndex: 1,
        pageSize: 5,
        totalPage: 0,
        dialogFormVisible: false,
        dataDialogForm: {
          id: 0,
          detailId: "",
          specification: "",
          length: "",
          width: "",
          height: "",
          blankWeight: "",
          materialUnitPrice: "",
          blankFee: "",
          scrapWeight: "",
          scrapUnitPrice: "",
          scrapFee: "",
          totalMaterialCost: "",
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
          const res = await this.$http.get("/material-cost/list", { params });
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
          length: "",
          width: "",
          height: "",
          blankWeight: "",
          materialUnitPrice: "",
          blankFee: "",
          scrapWeight: "",
          scrapUnitPrice: "",
          scrapFee: "",
          totalMaterialCost: "",
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
              await this.$http.delete('/material-cost/delete', { data: { id: row.id } });
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
        try {
          const url =
            this.dataDialogForm.id === 0
              ? "/material-cost/save"
              : "/material-cost/update";
          await this.$http.post(url, this.dataDialogForm);
          this.$message.success("操作成功");
          this.dialogFormVisible = false;
          this.getDataList();
        } catch (error) {
          console.error("操作失败：", error);
          this.$message.error("操作失败");
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