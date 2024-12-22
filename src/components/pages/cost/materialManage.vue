<template>
  <el-card class="box-card">
    <div id="materialManage">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="dataForm.materialName" placeholder="物料名称/物料编码" clearable></el-input>
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
        <el-table-column prop="materialCode" label="物料编码"></el-table-column>
        <el-table-column prop="materialName" label="物料名称" width="180"></el-table-column>
        <el-table-column prop="unitPrice" label="物料单价"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间"></el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="mini" type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[5, 10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          style="margin-top: 30px;"
      ></el-pagination>
    </div>

    <el-dialog
        :title="dataDialogForm.materialId ? '更新物料' : '新增物料'"
        :visible.sync="dialogFormVisible"
        width="35%"
        @close="closeDialog"
    >
   
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini">
        <el-form-item label="物料编码" prop="materialCode">
          <el-input v-model="dataDialogForm.materialCode" autocomplete="off" style="width: 240px;"></el-input>
        </el-form-item>
        <el-form-item label="物料名称" prop="materialName">
          <el-input v-model="dataDialogForm.materialName" autocomplete="off" style="width: 240px;"></el-input>
        </el-form-item>
        <el-form-item label="物料单价" prop="unitPrice">
          <el-input v-model="dataDialogForm.unitPrice" autocomplete="off" style="width: 240px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取消</el-button>
        <el-button type="primary" @click="submitMaterial()">确定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  
export default {
  name: "materialManage",
  data() {
    return {
      dataForm: {
        materialName: "",
      },
      dataDialogForm: {
        materialId: null,
        materialCode: "",
        materialName: "",
        unitPrice: "",
      },
      dataList: [],
      pageSize: 10,
      pageIndex: 1,
      totalPage: 0,
    
      dialogFormVisible: false,
      rules: {
        materialCode: [{ required: true, message: "物料编码不能为空", trigger: "blur" }],
        materialName: [{ required: true, message: "物料名称不能为空", trigger: "blur" }],
        unitPrice: [
          {
            required: true,
            message: "物料单价不能为空",
            trigger: "blur",
          },
          {
            pattern: /^-?\d{1,8}(\.\d{1,2})?$/,
            message: "格式为非负数，最多保留两位小数",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    // 获取物料列表
    async getDataList() {
      const params = {
        pageSize: this.pageSize,
        pageIndex: this.pageIndex,
        materialName: this.dataForm.materialName,
      };
      try {
        const res = await this.$http.get("/materials/list", { params });
        this.dataList = res.data.data.list;
        this.totalPage = res.data.data.totalCount;
      } catch (error) {
        this.$message.error("获取数据失败");
      }
    },

    // 分页
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.getDataList();
    },
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },

    // 打开对话框
    openDialog() {
      this.dialogFormVisible = true;
      this.dataDialogForm = {
        materialId: null,
        materialCode: "",
        materialName: "",
        unitPrice: "",
      };
    },

    // 编辑
    handleEdit(row) {
      this.dialogFormVisible = true;
      this.dataDialogForm = { ...row };
    },

    // 删除
    async handleDelete(row) {
      this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(async () => {
            try {
              await this.$http.delete("/materials/delete", {
                data: { material_id: row.materialId },
              });
              this.$message.success("删除成功");
              this.getDataList(); // 重新获取数据列表
            } catch (error) {
              this.$message.error("删除失败");
            }
          })
          .catch(() => {
            this.$message.info("已取消删除");
          });
    },

    // 提交新增或编辑
    async submitMaterial() {
      this.$refs.ruleForm.validate(async (valid) => {
        if (!valid) return;
        const url = this.dataDialogForm.materialId
            ? "/materials/update"
            : "/materials/save";
        try {
          await this.$http.post(url, this.dataDialogForm);
          this.$message.success(
              this.dataDialogForm.materialId ? "更新成功" : "新增成功"
          );
          this.dialogFormVisible = false;
          this.getDataList();
        } catch (error) {
          this.$message.error("提交失败");
        }
      });
    },

    // 关闭对话框
    closeDialog() {
      this.dialogFormVisible = false;
      this.$refs.ruleForm.resetFields();
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
