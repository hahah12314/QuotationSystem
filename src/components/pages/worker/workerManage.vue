<template>
  <el-card class="box-card">
    <div id='workers'>
      <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="dataForm.workerName" placeholder="工人姓名" clearable></el-input>
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
        <el-table-column prop="workerName" label="工人姓名"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="level" label="级别"></el-table-column>
        <el-table-column prop="hireDate" label="入职日期"></el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
      </el-pagination>
    </div>

    <el-dialog :title="dataDialogForm.id === 0 ? '新增工人' : '编辑工人'" :visible.sync="dialogFormVisible" width="35%"
      @close="handleClose">
      <el-form :model="dataDialogForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="mini">
        <el-form-item label="工人姓名" prop="workerName" :rules="rules.workerName">
          <el-input v-model="dataDialogForm.workerName" autocomplete="off" style="width: 240px;"></el-input>
        </el-form-item>

        <el-form-item label="年龄" prop="age" :rules="rules.age">
          <el-input v-model="dataDialogForm.age" autocomplete="off" style="width: 240px;" type="number"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="gender" :rules="rules.gender">
          <el-select v-model="dataDialogForm.gender" placeholder="请选择">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="级别" prop="level" :rules="rules.level">
          <el-select v-model="dataDialogForm.level" filterable placeholder="请选择">
            <el-option v-for="item in levels" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="入职日期" prop="hireDate" :rules="rules.hireDate">
          <el-date-picker v-model="dataDialogForm.hireDate" type="date" placeholder="选择日期"
            style="width: 240px;"></el-date-picker>
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
    name: 'workers',
    data() {
      return {
        dataForm: {
          workerName: ''
        },
        dataDialogForm: {
          id: 0,
          workerName: '',
          age: '',
          gender: '',
          level: '',
          hireDate: ''
        },
        dataList: [],
        pageSize: 10,
        pageIndex: 1,
        totalPage: 0,
        dialogFormVisible: false,
        dialogSubmitForm: false,

        // 提取规则到data部分
        rules: {
          workerName: [
            { required: true, message: '请输入工人姓名', trigger: 'blur' },
            { min: 1, max: 10, message: '工人姓名最多为10个字符', trigger: 'blur' }
          ],
          age: [
            { required: false, message: '请输入年龄', trigger: 'blur' }
          ],
          gender: [
            { required: false, message: '请选择性别', trigger: 'change' }
          ],
          level: [
            { required: false, message: '请选择级别', trigger: 'change' }
          ],
          hireDate: [
            { required: false, message: '请选择入职日期', trigger: 'change' }
          ]
        },

        // 级别选项
        levels: [
          { label: '低级', value: '低级' },
          { label: '中级', value: '中级' },
          { label: '高级', value: '高级' }
        ]
      };
    },
    methods: {
      async getDataList() {
        const params = {
          pageSize: this.pageSize,
          pageIndex: this.pageIndex,
          workerName: this.dataForm.workerName,
        };
        try {
          const res = await this.$http.get('/workers/list', { params });
          this.dataList = res.data.data.list;
          this.totalPage = res.data.data.totalCount;
        } catch (error) {
          this.$message.error('获取数据失败');
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
        // 重置表单数据，确保每次打开时都是空的
        this.dataDialogForm = {
          id: 0,
          workerName: '',
          age: '',
          gender: '',
          level: '',
          hireDate: ''
        };
        this.dialogFormVisible = true;
      },
      async handleEdit(row) {
        // 填充表单数据
        this.dataDialogForm = { ...row };
        this.dialogFormVisible = true;
      },
      async handleDelete(row) {
        this.$confirm('此操作将永久删除该工人信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(async () => {
            try {
              await this.$http.delete('/workers/delete', { data: { id: row.id } });
              this.$message.success('删除成功');
              this.getDataList(); // 重新获取数据列表
            } catch (error) {
              this.$message.error('删除失败');
            }
          })
          .catch(() => {
            this.$message.info('已取消删除');
          });
      },
      async submitForm(formName) {
        this.$refs[formName].validate(async (valid) => {
          if (!valid) return;

          const url = this.dataDialogForm.id === 0 ? '/workers/save' : '/workers/update';
          try {
            await this.$http.post(url, this.dataDialogForm);
            this.$message.success(this.dataDialogForm.id === 0 ? '新增成功' : '更新成功');
            this.dialogFormVisible = false;
            this.getDataList();
          } catch (error) {
            this.$message.error('保存失败');
          }
        });
      },
      handleClose() {
        // 关闭时清空表单和验证状态
        this.$refs.ruleForm.resetFields(); // 重置验证状态
        this.dataDialogForm = {
          id: 0,
          workerName: '',
          age: '',
          gender: '',
          level: '',
          hireDate: ''
        };
      }
    },
    mounted() {
      this.getDataList();
    }
  };
</script>

<style scoped>
  .dialog-footer {
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>