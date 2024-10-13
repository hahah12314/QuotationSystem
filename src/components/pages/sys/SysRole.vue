<template>

    <el-card class="box-card">
        <div id='sysRole'>
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model="dataForm.roleName" placeholder="角色名称" clearable></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button @click="getDataList()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="openDialog">新增</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="dataList" border style="width: 100%">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="roleId" label="ID" width="180">
                </el-table-column>
                <el-table-column prop="roleName" label="角色名称" width="180">
                </el-table-column>
                <el-table-column prop="remark" label="备注">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间">
                </el-table-column>
                <el-table-column prop="updateTime" label="更新时间">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
            </el-pagination>
        </div>

        <el-dialog :title="this.dataDialogForm.roleId===0 ? '新增角色' : '更新角色' " :visible.sync="dialogFormVisible"
            width="35%" @close="closeDialog">
            <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
                <el-form-item label="角色名称" :label-width="formLabelWidth" prop="roleName">
                    <el-input v-model="dataDialogForm.roleName" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="描述信息" :label-width="formLabelWidth" prop="remark">
                    <el-input v-model="dataDialogForm.remark" autocomplete="off" type="textarea"
                        style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="菜单权限" :label-width="formLabelWidth" prop="menuList">
                    <el-tree :data="menuAll" show-checkbox node-key="menuId" 
                        :props="defaultProps" ref="tree">
                    </el-tree>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitRole('ruleForm')">确 定</el-button>
            </div>
        </el-dialog>
    </el-card>

</template>

<script>
    export default {
      name: 'sysRole',
      data() {
        var validateName = (rule, value, callback) => {
          if (this.dataDialogForm.roleId === 0) {
            if (value === '') {
              callback(new Error('请输入角色名称'));
            } else {
              this.$http.get('/sys/sysRole/checkRoleName?roleName=' + value).then(res => {
                console.log(res.data.data);
                if (res.data.data === 'success') {
                  callback();
                } else {
                  callback(new Error('角色名称已存在'));
                }
              });
            }
          } else {
            callback();
          }
        };
  
        return {
          dataForm: {
            roleName: ''
          },
          dataDialogForm: {
            roleName: '',
            remark: '',
            roleId: 0,
            menuList: []
          },
          defaultProps: {
            children: 'children',
            label: 'name',
            id: 'menuId'
          },
          dataList: [],
          menuAll: [],
          pageSize: 5,
          pageIndex: 1,
          totalPage: 0,
          dataListLoading: false,
          formLabelWidth: '120px',
          dialogFormVisible: false,
          dialogSubmitForm: false,
          checks: [],
          rules: {
            roleName: [
              { validator: validateName, trigger: 'blur', required: true }
            ],
            remark: [
              { required: true, message: '请输入描述信息', trigger: 'blur' }
            ]
          }
        }
      },
      methods: {
        async getDataList() {
          if (this.dataListLoading) {
            return;
          }
          this.dataListLoading = true;
          const params = {
            params: {
              pageSize: this.pageSize,
              pageIndex: this.pageIndex,
              roleName: this.dataForm.roleName
            }
          };
  
          try {
            const res = await this.$http.get('/sys/sysRole/list', params);
            console.log(res);
            this.dataList = res.data.data.list;
            this.totalPage = res.data.data.totalCount;
            this.dataListLoading = false;
  
            const menuRes = await this.$http.get('/sys/sysMenu/menuAll');
            this.menuAll = menuRes.data.data.menuList;
          } catch (error) {
            console.error('获取数据列表时出错:', error);
            this.dataListLoading = false;
            this.$message({
              message: '获取数据列表时发生错误',
              type: 'error'
            });
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
          console.log('roleId' + item.roleId);
  
          try {
            const res = await this.$http.get('/sys/sysMenu/getMenuChecked?roleId=' + item.roleId);
            this.checks = res.data.data;
            this.$refs.tree.setCheckedKeys([]);
            this.$refs.tree.setCheckedKeys(this.checks);
            console.log(this.checks);
          } catch (error) {
            console.error('获取已选菜单时出错:', error);
          }
  
          this.dialogFormVisible = true;
          this.dataDialogForm.roleId = item.roleId;
          this.dataDialogForm.roleName = item.roleName;
          this.dataDialogForm.remark = item.remark;
        },
        async handleDelete(index, item) {
          this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            console.log(item);
            try {
              const res = await this.$http.get('/sys/sysRole/delete?roleId=' + item.roleId);
              console.log(res);
              await this.getDataList();
              if (res.data.data === 'success') {
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
              } else {
                this.$message({
                  type: 'warning',
                  message: '该记录不允许删除!'
                });
              }
            } catch (error) {
              console.error('删除角色时出错:', error);
              this.$message({
                message: '删除时发生错误',
                type: 'error'
              });
            }
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },
        openDialog() {
          this.dialogFormVisible = true;
          this.$refs.tree.setCheckedKeys([]);
          this.dataDialogForm = {
            roleId: 0,
            remark: '',
            roleName: '',
            menuList: []
          };
        },
        closeDialog() {
          this.dialogFormVisible = false;
          this.dataDialogForm = {
            roleId: 0,
            remark: '',
            roleName: '',
            menuList: []
          };
        },
        async submitRole(ruleForm) {
          await this.updateRole(ruleForm);
          await this.getDataList();
        },
        async updateRole(formName) {
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
  
            // 获取选中的菜单项
            this.dataDialogForm.menuList = this.$refs.tree.getCheckedKeys();
  
            // 根据roleId判断是更新还是保存
            const url = this.dataDialogForm.roleId !== 0 ? '/sys/sysRole/update' : '/sys/sysRole/save';
            const response = await this.$http.post(url, this.dataDialogForm);
  
            console.log(response);
  
            // 关闭对话框并重置表单
            this.dialogFormVisible = false;
            this.dataDialogForm = {
              roleId: 0,
              remark: '',
              roleName: '',
              menuList: []
            };
          } catch (error) {
            console.error('更新角色时出错:', error);
          } finally {
            this.dialogSubmitForm = false;
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