<template>
    <el-card class="box-card">
        <div id='workHours'>
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
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
            <el-table :data="dataList" border style="width: 100%">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="id" label="ID">
                </el-table-column>
                <el-table-column prop="processName" label="工序名称">
                </el-table-column>
                <el-table-column prop="workerName" label="工人名字">
                </el-table-column>
                <el-table-column prop="cost" label="成本">
                </el-table-column>
                <el-table-column prop="hour" label="工时">
                </el-table-column>
                <el-table-column label="操作" width="250">
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

        <el-dialog :title="this.dataDialogForm.id === 0 ? '新增工时' : '更新工时'" :visible.sync="dialogFormVisible"
            width="35%">
            <el-form :model="dataDialogForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="ID" prop="id">
                    <el-input v-model="dataDialogForm.id" autocomplete="off" style="width: 300px;" disabled></el-input>
                </el-form-item>
                <el-form-item label="工序名称" prop="processName">
                    <el-input v-model="dataDialogForm.processName" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="工人ID" prop="workerId">
                    <el-input v-model="dataDialogForm.workerId" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="成本" prop="cost">
                    <el-input v-model="dataDialogForm.cost" autocomplete="off" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="工时" prop="hour">
                    <el-input v-model="dataDialogForm.hour" autocomplete="off" style="width: 300px;"></el-input>
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
        name: 'workHours',
        data() {
            return {
                dataForm: {
                    processName: ''
                },
                dataDialogForm: {
                    id: 0,
                    processName: '',
                    workerId: '',
                    cost: ''
                },
                dataList: [],
                pageSize: 5,
                pageIndex: 1,
                totalPage: 0,
                dataListLoading: false,
                formLabelWidth: '120px',
                dialogFormVisible: false,
                dialogSubmitForm: false,
                checks: [],
                rules: {}
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
                        processName: this.dataForm.processName
                    }
                };

                try {
                    const res = await this.$http.get('/workhours/list', params);
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
            async handleEdit(index, item) {
                this.dialogFormVisible = true;
                this.dataDialogForm.id = item.id;
                this.dataDialogForm.processName = item.processName;
                this.dataDialogForm.workerId = item.workerId;
                this.dataDialogForm.cost = item.cost;
            },
            async handleDelete(index, item) {
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    try {
                        const res = await this.$http.get('/workhours/delete?id=' + item.id);
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
                    processName: '',
                    workerId: '',
                    cost: ''
                };
            },
            closeDialog() {
                this.dialogFormVisible = false;
                this.dataDialogForm = {
                    id: 0,
                    processName: '',
                    workerId: '',
                    cost: ''
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

                    const url = this.dataDialogForm.id !== 0 ? '/workhours/update' : '/workhours/save';
                    const response = await this.$http.post(url, this.dataDialogForm);

                    this.dialogFormVisible = false;
                    this.dataDialogForm = {
                        id: 0,
                        processName: '',
                        workerId: '',
                        cost: ''
                    };
                } catch (error) {
                    console.error('更新记录时出错:', error);
                } finally {
                    this.dialogSubmitForm = false;
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