<template>

    <el-card class="box-card">
        <div id='sysLog'>
            <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
                <el-form-item>
                    <el-input v-model="dataForm.msg" placeholder="用户名/用户操作" clearable></el-input>
                </el-form-item>
  
                <el-form-item>
                    <el-button @click="getDataList()">查询</el-button>
                </el-form-item>
                
  
            </el-form>
            <el-table :data="dataList" border style="width: 100%" size="mini">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column type="index" label="序号" width="55"></el-table-column>
                <el-table-column prop="username" label="用户名称" width="180">
                </el-table-column>
                <el-table-column prop="operation" label="用户操作">
                </el-table-column>
                <el-table-column prop="method" label="操作方法">
                </el-table-column>
              
                <el-table-column prop="time" label="执行时间">
                </el-table-column>
            
                <el-table-column prop="createDate" label="创建时间">
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                      
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
  
  
    </el-card>
  
  </template>
  
  <script>
    export default {
      name: 'sysLog',
      data() {
        return {
          dataForm: {
            msg: ''
          },
          dataList: [],
          pageSize: 10,
          pageIndex: 1,
          totalPage: 0,
          dataListLoading: false,
          formLabelWidth: '120px'
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
              msg: this.dataForm.msg
            }
          };
  
          try {
            const res = await this.$http.get('/sys/sysLog/list', params);
            console.log(res);
            this.dataList = res.data.data.list;
            this.totalPage = res.data.data.totalCount;
            this.dataListLoading = false;
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
        async handleDelete(index, item) {
          const result = await this.$confirm('此操作将永久删除该日志, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          });
  
          if (result) {
            try {
              const res = await this.$http.get(`/sys/sysLog/delete?id=${item.id}`);
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
              console.error('删除日志时出错:', error);
              this.$message({
                type: 'error',
                message: '删除日志时发生错误'
              });
            }
          } else {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          }
        }
      },
      mounted() {
        this.getDataList();
      }
    }
  </script>
  
  <style scoped>
    .dialog-footer{
      display: flex;
      justify-content: center;
      align-items: center;
    }
  </style>