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
            <el-table-column prop="sawTime" label="锯切时间" width="auto"></el-table-column>
            <el-table-column prop="sawFee" label="锯切费用" width="auto"></el-table-column>
            <el-table-column prop="bendTime" label="弯曲时间" width="auto"></el-table-column>
            <el-table-column prop="bendFee" label="弯曲费用" width="auto"></el-table-column>
            <el-table-column prop="drillTime" label="钻孔时间" width="auto"></el-table-column>
            <el-table-column prop="drillFee" label="钻孔费用" width="auto"></el-table-column>
            <el-table-column prop="turnTime" label="车削时间" width="auto"></el-table-column>
            <el-table-column prop="turnFee" label="车削费用" width="auto"></el-table-column>
            <el-table-column prop="grindTime" label="磨削时间" width="auto"></el-table-column>
            <el-table-column prop="grindFee" label="磨削费用" width="auto"></el-table-column>
            <el-table-column prop="millTime" label="铣削时间" width="auto"></el-table-column>
            <el-table-column prop="millFee" label="铣削费用" width="auto"></el-table-column>
            <el-table-column prop="calibrateTime" label="校正时间" width="auto"></el-table-column>
            <el-table-column prop="calibrateFee" label="校正费用" width="auto"></el-table-column>
            <el-table-column prop="boreMillTime" label="镗铣时间" width="auto"></el-table-column>
            <el-table-column prop="boreMillFee" label="镗铣费用" width="auto"></el-table-column>
            <el-table-column prop="weldTime" label="焊接时间" width="auto"></el-table-column>
            <el-table-column prop="weldFee" label="焊接费用" width="auto"></el-table-column>
            <el-table-column prop="polishTime" label="抛光时间" width="auto"></el-table-column>
            <el-table-column prop="polishFee" label="抛光费用" width="auto"></el-table-column>
            <el-table-column prop="assembleTime" label="组装时间" width="auto"></el-table-column>
            <el-table-column prop="assembleFee" label="组装费用" width="auto"></el-table-column>
            <el-table-column prop="totalProcessFee" label="总加工费用" width="auto"></el-table-column>
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
  
      <el-dialog :title="this.dataDialogForm.id === 0 ? '新增记录' : '更新记录'" :visible.sync="dialogFormVisible" width="35%"
      @close="closeDialog">
      <el-form :model="dataDialogForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!-- <el-form-item label="ID" prop="id">
          <el-input v-model="dataDialogForm.id" disabled autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item> -->
        <el-form-item label="材料规格" prop="specification">
          <el-input v-model="dataDialogForm.specification" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="锯切时间" prop="sawTime">
          <el-input v-model="dataDialogForm.sawTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="锯切费用" prop="sawFee">
          <el-input v-model="dataDialogForm.sawFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="弯曲时间" prop="bendTime">
          <el-input v-model="dataDialogForm.bendTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="弯曲费用" prop="bendFee">
          <el-input v-model="dataDialogForm.bendFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="钻孔时间" prop="drillTime">
          <el-input v-model="dataDialogForm.drillTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="钻孔费用" prop="drillFee">
          <el-input v-model="dataDialogForm.drillFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="车削时间" prop="turnTime">
          <el-input v-model="dataDialogForm.turnTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="车削费用" prop="turnFee">
          <el-input v-model="dataDialogForm.turnFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="磨削时间" prop="grindTime">
          <el-input v-model="dataDialogForm.grindTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="磨削费用" prop="grindFee">
          <el-input v-model="dataDialogForm.grindFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="铣削时间" prop="millTime">
          <el-input v-model="dataDialogForm.millTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="铣削费用" prop="millFee">
          <el-input v-model="dataDialogForm.millFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="校正时间" prop="calibrateTime">
          <el-input v-model="dataDialogForm.calibrateTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="校正费用" prop="calibrateFee">
          <el-input v-model="dataDialogForm.calibrateFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="镗铣时间" prop="boreMillTime">
          <el-input v-model="dataDialogForm.boreMillTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="镗铣费用" prop="boreMillFee">
          <el-input v-model="dataDialogForm.boreMillFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="焊接时间" prop="weldTime">
          <el-input v-model="dataDialogForm.weldTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="焊接费用" prop="weldFee">
          <el-input v-model="dataDialogForm.weldFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="抛光时间" prop="polishTime">
          <el-input v-model="dataDialogForm.polishTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="抛光费用" prop="polishFee">
          <el-input v-model="dataDialogForm.polishFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="组装时间" prop="assembleTime">
          <el-input v-model="dataDialogForm.assembleTime" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="组装费用" prop="assembleFee">
          <el-input v-model="dataDialogForm.assembleFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="总加工费用" prop="totalProcessFee">
          <el-input v-model="dataDialogForm.totalProcessFee" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
      </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitUser('ruleForm')">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  
  </template>
  
  <script>
    export default {
      name: 'processingCost',
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
            const res = await this.$http.get('/processing-cost/list', params);
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