<template>

    <el-card class="box-card">
        <div id='sysRole'>
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model="dataForm.name" placeholder="菜单名称" clearable></el-input>
                </el-form-item>
  
                <el-form-item>
                    <el-button @click="getDataList()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="openDialog">新增</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="dataList" border style="width: 100%" row-key="name"
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="menuId" label="菜单ID" width="180">
                </el-table-column>
                <el-table-column prop="name" label="菜单名称" width="180">
                </el-table-column>
  
  
                <el-table-column prop="path" label="路径">
                </el-table-column>
                <el-table-column prop="icon" label="菜单图标" width="180">
                </el-table-column>
                <el-table-column prop="parentId" label="父菜单ID">
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
  
        <el-dialog :title="this.dataDialogForm.menuId===0 ? '新增菜单' : '更新菜单' " :visible.sync="dialogFormVisible"
            width="35%" @close="closeDialog">
            <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
                <el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name">
                    <el-input v-model="dataDialogForm.name" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="路径" :label-width="formLabelWidth" prop="path">
                    <el-input v-model="dataDialogForm.path" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="菜单图标" :label-width="formLabelWidth" prop="icon">
                    <el-input v-model="dataDialogForm.icon" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="父菜单" :label-width="formLabelWidth" prop="parentId">
                    <el-select v-model="dataDialogForm.parentId" placeholder="本身为父菜单">
                        <el-option v-for="item in dataList" :key="item.name" :label="item.name" :value="item.menuId">
                        </el-option>
                    </el-select>
                </el-form-item>
  
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitMenu('ruleForm')">确 定</el-button>
            </div>
        </el-dialog>
    </el-card>
  
  </template>
  
  <script>
    export default {
      name: 'sysMenu',
      data() {
        return {
          dataForm: {
            name: ''
          },
          dataDialogForm: {
            name: '',
            menuId: 0,
            parentId: 0,
            path: '',
            icon: ''
          },
          dataList: [],
          pageSize: 5,
          pageIndex: 1,
          totalPage: 0,
          dataListLoading: false,
          formLabelWidth: '120px',
          dialogFormVisible: false,
          dialogSubmitForm: false,
          options: [],
          rules: {
            name: [
              {
                validator: async (rule, value, callback) => {
                  if(this.dataDialogForm.menuId===0){
                    if (value === '') {
                      callback(new Error('请输入菜单名称'));
                    } else {
                      try {
                        const res = await this.$http.get(`/sys/sysMenu/checkMenuName?name=${value}`);
                        if (res.data.data === 'success') {
                          callback();
                        } else {
                          callback(new Error('菜单名称已存在'));
                        }
                      } catch (error) {
                        console.error('验证菜单名称时出错:', error);
                        callback(new Error('验证菜单名称时发生错误'));
                      }
                    }
                  }else{
                    callback()
                  }
                  
                },
                trigger: 'blur',
                required: true
              }
            ]
          }
        };
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
              name: this.dataForm.name
            }
          };
  
          try {
            const res = await this.$http.get('/sys/sysMenu/list', params);
            this.dataList = res.data.data.list;
            this.options = res.data.data.list;
            this.totalPage = res.data.data.totalCount;
            this.dataListLoading = false;
            console.log(res);
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
        handleEdit(index, item) {
          this.dataDialogForm.menuId = item.menuId;
          this.dataDialogForm.name = item.name;
          this.dataDialogForm.icon = item.icon;
          this.dataDialogForm.path = item.path;
          this.dataDialogForm.parentId = item.parentId;
          this.dialogFormVisible = true;
          
        },
        async handleDelete(index, item) {
          const result = await this.$confirm('此操作将永久删除该菜单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          });
  
          if (result) {
            try {
              const res = await this.$http.get(`/sys/sysMenu/delete?menuId=${item.menuId}`);
              this.getDataList();
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
              console.error('删除菜单时出错:', error);
              this.$message({
                type: 'error',
                message: '删除菜单时发生错误'
              });
            }
          } else {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          }
        },
        openDialog() {
          this.dialogFormVisible = true;
          this.dataDialogForm = {
            name: '',
            menuId: 0,
            parentId: 0,
            path: '',
            icon: ''
          };
        },
        closeDialog() {
          this.dialogFormVisible = false;
          this.dataDialogForm = {
            name: '',
            menuId: 0,
            parentId: 0,
            path: '',
            icon: ''
          };
        },
        submitMenu(ruleForm) {
          this.updateMenu(ruleForm);
        },
        async updateMenu(formName) {
          try {
            await this.$refs[formName].validate(async (valid) => {
              if (!valid) {
                console.log('error submit!!');
                return false;
              }
              if (this.dialogSubmitForm) {
                return;
              }
              this.dialogSubmitForm = true;
              
              const url = this.dataDialogForm.menuId !== 0 ? '/sys/sysMenu/update' : '/sys/sysMenu/save';
              const response = await this.$http.post(url, this.dataDialogForm);
    
              console.log(response);
              this.dialogFormVisible = false;
              this.getDataList();
              this.dataDialogForm = {
                name: '',
                menuId: 0,
                parentId: 0,
                path: '',
                icon: ''
              };
              this.dialogSubmitForm = false;
            });
          } catch (error) {
            console.error('提交表单时出错:', error);
          }
        }
      },
      mounted() {
        this.getDataList();
      }
    }
  </script>
  
  <style scoped>
  
  </style>