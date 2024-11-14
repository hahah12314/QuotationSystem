<template>

    <el-card class="box-card">
      <div id='sysRole'>
        <el-form :inline="true" :model="dataForm" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="dataForm.specification" placeholder="材料规格" clearable></el-input>
          </el-form-item>
  
          <el-form-item>
            <el-button @click="getDataList()">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="openDialog">新增</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" @click="deleteBatch()">批量删除</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="dataList" style="width: 100%">
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="specification" label="材料规格"></el-table-column>
            <el-table-column prop="zincWeight" label="镀锌重量" width="auto"></el-table-column>
            <el-table-column prop="zincFee" label="镀锌费用" width="auto"></el-table-column>
            <el-table-column prop="temperingWeight" label="淬火重量" width="auto"></el-table-column>
            <el-table-column prop="temperingFee" label="淬火费用" width="auto"></el-table-column>
            <el-table-column prop="sandWeight" label="砂光重量" width="auto"></el-table-column>
            <el-table-column prop="sandFee" label="砂光费用" width="auto"></el-table-column>
            <el-table-column prop="qpqWeight" label="QPQ重量" width="auto"></el-table-column>
            <el-table-column prop="qpqFee" label="QPQ费用" width="auto"></el-table-column>
            <el-table-column prop="totalCoatingFee" label="总涂层费用" width="auto"></el-table-column>
            <el-table-column label="操作" width="250">
              <template slot-scope="scope">
                  <el-button size="mini" type="primary"
                      @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
               
                  <el-button size="mini" type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
          </el-table-column>
        </el-table>
        <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
          :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
        </el-pagination>
      </div>
  
      <el-dialog :title="this.dataDialogForm.id === 0 ? '新增材料规格' : '更新材料规格'" :visible.sync="dialogFormVisible" width="35%"
      @close="closeDialog">
<el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px">
<el-form-item label="材料规格" prop="specification">
 <el-input v-model="dataDialogForm.specification" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="镀锌重量" prop="zincWeight">
 <el-input v-model="dataDialogForm.zincWeight" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="镀锌费用" prop="zincFee">
 <el-input v-model="dataDialogForm.zincFee" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="淬火重量" prop="temperingWeight">
 <el-input v-model="dataDialogForm.tempteringWeight" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="淬火费用" prop="temperingFee">
 <el-input v-model="dataDialogForm.tempteringFee" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="砂光重量" prop="sandWeight">
 <el-input v-model="dataDialogForm.sandWeight" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="砂光费用" prop="sandFee">
 <el-input v-model="dataDialogForm.sandFee" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="QPQ重量" prop="qpqWeight">
 <el-input v-model="dataDialogForm.qpqWeight" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="QPQ费用" prop="qpqFee">
 <el-input v-model="dataDialogForm.qpqFee" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
<el-form-item label="总涂层费用" prop="totalCoatingFee">
 <el-input v-model="dataDialogForm.totalCoatingFee" autocomplete="off" style="width: 300px;"></el-input>
</el-form-item>
</el-form>
<div slot="footer" class="dialog-footer">
<el-button @click="dialogFormVisible = false">取 消</el-button>
<el-button type="primary" @click="submitMaterial('ruleForm')">确 定</el-button>
</div>
</el-dialog>
    </el-card>
  
  </template>
  
  <script>
    export default {
      name: 'sufaceTreatment',
      data() {
        var validateName = (rule, value, callback) => {
          if (this.dataDialogForm.userId === 0) {
            if (value === '') {
              callback(new Error('请输入用户名称'));
            } else {
              this.$http.get('/sys/sysUser/checkuserName?username=' + value).then(res => {
                console.log(res.data.data);
                if (res.data.data === 'success') {
                  callback();
                } else {
                  callback(new Error('用户名称已存在'));
                }
              });
            }
          } else {
            callback();
          }
        };
  
        return {
          dataForm: {
            username: '',
          },
          options: [
            {
              label: '启用',
              value: 1
            },
            {
              label: '禁用',
              value: 0
            }
          ],
          dataDialogForm: {
            username: '',
            email: '',
            mobile: '',
            status: 1,
            password: '',
            userId: 0,
            roleList: []
          },
          dataList: [],
          roleAll: [],
          checkList: [],
          multipleSelection:[],
          pageSize: 5,
          pageIndex: 1,
          totalPage: 0,
          dataListLoading: false,
          formLabelWidth: '120px',
          dialogFormVisible: false,
          dialogSubmitForm: false,
          rules: {
            username: [
              { validator: validateName, trigger: 'blur', required: true }
            ],
            password: [
              { required: true, message: '请输入密码', trigger: 'blur' }
            ]
          }
        }
      },
      methods: {
        handleEdit(index, item){
          this.dialogFormVisible = true;
        },
        handleSelectionChange(val) {
          this.multipleSelection=val
        },
        async deleteBatch(){
          const params = 
            {
              ids: this.multipleSelection.map(item => item.userId)
            }
          
          console.log(params)
          try {
            const res = await this.$http.post('/sys/sysUser/deleteBatch',params.ids)
            console.log(res);
            if(res.data.data==='success'){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            }
           
  
            this.getDataList()
          } catch (error) {
            console.error('删除用户时出错:', error);
          }
  
        },
        async getDataList() {
          if (this.dataListLoading) {
            return;
          }
          this.dataListLoading = true;
  
          const params = {
            params: {
              pageSize: this.pageSize,
              pageIndex: this.pageIndex,
              specification: this.dataForm.specification
            }
          };
  
          try {
            const res = await this.$http.get('/surface-treatment/list', params);
            console.log(res);
            this.dataList = res.data.data.list;
            this.totalPage = res.data.data.totalCount;
            this.dataListLoading = false;
          } catch (error) {
            console.error('获取用户列表时出错:', error);
            this.dataListLoading = false;
          }
  
          try {
            const res = await this.$http.get('/sys/sysRole/roleAll');
            console.log(res);
            this.roleAll = res.data.data;
          } catch (error) {
            console.error('获取角色列表时出错:', error);
          }
        },
        sizeChangeHandle(val) {
          this.pageIndex = 1;
          this.pageSize = val;
          this.getDataList();
        },
        currentChangeHandle(val) {
          this.pageIndex = val;
          this.getDataList();
        },
        async handleEdit(index, item) {
          try {
            const res = await this.$http.get('/sys/sysRole/getRoleChecked?userId=' + item.userId);
            this.checkList = res.data.data.flatMap(item => item.roleId);
            console.log(this.checkList);
          } catch (error) {
            console.error('获取角色列表时出错:', error);
          }
  
          this.dialogFormVisible = true;
          this.dataDialogForm.userId = item.userId;
          this.dataDialogForm.username = item.username;
          this.dataDialogForm.email = item.email;
          this.dataDialogForm.mobile = item.mobile;
          this.dataDialogForm.status = item.status;
        },
        async handleStatus(index, item) {
          var s = item.status === 1 ? '禁用' : '启用';
          this.$confirm('是否要' + s + '该用户?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            item.status = item.status === 1 ? 0 : 1;
  
            try {
              const res = await this.$http.post('/sys/sysUser/update', item);
              console.log(res);
              await this.getDataList();
              this.dataForm = {
                username: '',
                email: '',
                mobile: '',
                status: 1,
                password: '',
                userId: 0
              };
            } catch (error) {
              console.error('更新用户状态时出错:', error);
            }
  
            this.$message({
              type: 'success',
              message: '已修改状态为' + s
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消修改'
            });
          });
        },
        openDialog() {
          this.dialogFormVisible = true;
          this.dataDialogForm = {
            username: '',
            email: '',
            mobile: '',
            status: 1,
            password: '',
            userId: 0
          };
          this.checkList = [];
        },
        closeDialog() {
          this.dialogFormVisible = false;
          this.dataDialogForm = {
            username: '',
            email: '',
            mobile: '',
            status: 1,
            password: '',
            userId: 0
          };
        },
        async submitUser(ruleForm) {
          await this.updateUser(ruleForm);
        },
        async updateUser(formName) {
          const valid = await new Promise(resolve => {
            this.$refs[formName].validate(valid => resolve(valid));
          });
  
          if (!valid) {
            console.log('error submit!!');
            return;
          }
  
          if (this.dialogSubmitForm) {
            return;
          }
  
          try {
            this.dialogSubmitForm = true;
            this.dataDialogForm.roleList = this.checkList;
            console.log(this.dataDialogForm);
            
            const url = this.dataDialogForm.userId !== 0 ? '/sys/sysUser/update' : '/sys/sysUser/save';
            const response = await this.$http.post(url, this.dataDialogForm);
            console.log(response);
  
            this.dialogFormVisible = false;
            this.dataDialogForm = {
              username: '',
              email: '',
              mobile: '',
              status: 1,
              password: '',
              userId: 0
            };
            this.dialogSubmitForm = false;
            await this.getDataList();
          } catch (error) {
            console.error('更新用户时出错:', error);
          }
        },
      },
      mounted() {
        this.getDataList();
      }
    }
  </script>
  
  <style scoped>
  
  </style>