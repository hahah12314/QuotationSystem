<template>
    <el-card class="box-card">
        <div id='sysRole'>
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
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
            <el-table :data="dataList" border style="width: 100%">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="id" label="ID" width="80">
                </el-table-column>
                <el-table-column prop="workerName" label="工人姓名">
                </el-table-column>
                <el-table-column prop="age" label="年龄">
                </el-table-column>
                <el-table-column prop="gender" label="性别">
                </el-table-column>
                <el-table-column prop="level" label="级别">
                </el-table-column>
                <el-table-column prop="hireDate" label="入职日期">
                </el-table-column>
                <el-table-column label="操作" width="250">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
            </el-pagination>
        </div>
        <el-dialog :title="this.dataDialogForm.id === 0 ? '新增工人' : '更新工人'" :visible.sync="dialogFormVisible" width="35%">
            <el-form :model="dataDialogForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="工人姓名" prop="worker_name">
                    <el-input v-model="dataDialogForm.worker_name" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                    <el-input v-model="dataDialogForm.age" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-select v-model="dataDialogForm.gender" placeholder="请选择">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="级别" prop="level">
                    <el-input v-model="dataDialogForm.level" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="入职日期" prop="hire_date">
                    <el-date-picker v-model="dataDialogForm.hire_date" type="date" placeholder="选择日期" style="width: 300px;"></el-date-picker>
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
            pageSize: 5,
            pageIndex: 1,
            totalPage: 0,
            dataListLoading: false,
            dialogFormVisible: false,
            dialogSubmitForm: false,
            checks: []
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
                    workerName: this.dataForm.workerName
                }
            };

            try {
                const res = await this.$http.get('/workers/list', params);
                this.dataList = res.data.data.list;
                console.log(res);
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
        async handleEdit(index, item) {
            this.dialogFormVisible = true;
            this.dataDialogForm = { ...item };
        },
        async handleDelete(index, item) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                try {
                    const res = await this.$http.get('/workers/delete?id=' + item.id);
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
                    console.error('删除记录时出错:', error);
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
            this.dataDialogForm = {
                id: 0,
                worker_name: '',
                age: '',
                gender: '',
                level: '',
                hire_date: ''
            };
        },
        closeDialog() {
            this.dialogFormVisible = false;
            this.dataDialogForm = {
                id: 0,
                worker_name: '',
                age: '',
                gender: '',
                level: '',
                hire_date: ''
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

                const url = this.dataDialogForm.id !== 0 ? '/workers/update' : '/workers/save';
                const response = await this.$http.post(url, this.dataDialogForm);

                this.dialogFormVisible = false;
                this.dataDialogForm = {
                    id: 0,
                    worker_name: '',
                    age: '',
                    gender: '',
                    level: '',
                    hire_date: ''
                };
            } catch (error) {
                console.error('更新记录时出错:', error);
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