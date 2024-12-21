<template>
  <el-card class="box-card">
    <div id="workHours">
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="dataForm.processName" placeholder="工序名称" clearable></el-input>
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
        <el-table-column prop="processName" label="工序名称"></el-table-column>
        <el-table-column prop="workerName" label="工人姓名"></el-table-column>
        <el-table-column prop="cost" label="成本"></el-table-column>
        <el-table-column prop="hour" label="工时"></el-table-column>
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

    <el-dialog :title="dataDialogForm.id === 0 ? '新增工时' : '编辑工时'" :visible.sync="dialogFormVisible" width="35%"
      @close="handleClose">
      <el-form :model="dataDialogForm" ref="ruleForm" :rules="rules" label-width="100px" size="mini">
        <el-form-item label="工序名称" prop="processName">
          <el-select v-model="dataDialogForm.processName" filterable placeholder="请选择" allow-create>
            <el-option v-for="item in processOptions" :key="item" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工人姓名" prop="workerId">
          <el-select v-model="dataDialogForm.workerId" filterable placeholder="请选择" allow-create>
            <el-option v-for="item in workerOptions" :key="item.id" :label="item.workerName"
              :value="item.id"></el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="成本" prop="cost">
          <el-input v-model="dataDialogForm.cost" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="工时" prop="hour">
          <el-input v-model="dataDialogForm.hour" autocomplete="off" type="text" @input="sanitizeHourInput">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
  export default {
    name: "workHours",
    data() {
      return {
        dataForm: {
          processName: "",
        },
        dataDialogForm: {
          id: 0,
          processName: "",
          workerId: "",
          cost: "",
          hour: "",
        },
        dataList: [],
        pageSize: 5,
        pageIndex: 1,
        totalPage: 0,
        dialogFormVisible: false,
        workerOptions: [],
        processOptions: ["锯", "弯", "钻", "车", "外磨", "铣", "校平", "镗铣", "焊", "打磨", "装"],
        rules: {
          processName: [
            { required: true, message: "请选择或输入工序名称", trigger: "change" },
          ],
          workerId: [
            { required: true, message: "请选择工人姓名", trigger: "change" },

          ],
          cost: [
            { required: true, message: "请输入成本", trigger: "blur" },
            { pattern: /^-?\d{1,8}(\.\d{1,2})?$/, message: "格式为数字，最多保留两位小数", trigger: "blur" },
          ],
          hour: [
            { required: true, message: "请输入工时", trigger: "blur" },
            {
              validator: (rule, value, callback) => {
                if (!value) {
                  callback(new Error("请输入工时"));
                } else if (!/^\d+$/.test(value)) {
                  callback(new Error("工时必须是整数"));
                } else {
                  callback();
                }
              },
              trigger: "blur",
            },
          ],
        },
      };
    },
    methods: {

      sanitizeHourInput() {
        this.dataDialogForm.hour = this.dataDialogForm.hour.replace(/[^0-9]/g, "");
      },
      async getDataList() {
        const params = {
          pageSize: this.pageSize,
          pageIndex: this.pageIndex,
          processName: this.dataForm.processName || "",
        };

        try {
          const response = await this.$http.get("/workhours/list", { params });
          const response2 = await this.$http.get("/workers/getWorkerAll");
          const data = response.data.data;
          this.workerOptions = response2.data.data || [];
          this.dataList = data.list || [];
          this.totalPage = data.totalCount || 0;
          console.log('worker', this.workerOptions);

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
          id: 0,
          processName: "",
          workerId: "",
          cost: "",
          hour: "",
        };
      },
      handleClose() {
        this.$refs.ruleForm.resetFields();
        this.dataDialogForm = {
          id: 0,
          processName: "",
          workerId: "",
          cost: "",
          hour: "",
        };
      },
      async handleEdit(row) {
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
              await this.$http.delete("/workhours/delete", { data: { id: row.id } });
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
      async submitForm(formName) {
        this.$refs[formName].validate(async (valid) => {
          if (!valid) return;

          const url = this.dataDialogForm.id ? "/workhours/update" : "/workhours/save";
          try {
            await this.$http.post(url, this.dataDialogForm);
            this.$message.success(this.dataDialogForm.id ? "更新成功" : "新增成功");
            this.dialogFormVisible = false;
            this.getDataList();
          } catch (error) {
            this.$message.error("保存失败");
          }
        });
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