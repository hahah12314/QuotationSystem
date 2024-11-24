<template>
    <el-card class="box-card">
        <div id="workHours">
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
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top: 30px;"></el-pagination>
        </div>

        <el-dialog :title="dataDialogForm.id === 0 ? '新增工时' : '编辑工时'" :visible.sync="dialogFormVisible" width="35%">
            <el-form :model="dataDialogForm" ref="ruleForm" label-width="100px">
                <el-form-item label="工序名称" prop="processName">
                    <el-select v-model="dataDialogForm.processName" placeholder="请选择">
                        <el-option label="锯" value="锯"></el-option>
                        <el-option label="弯" value="弯"></el-option>
                        <el-option label="钻" value="车"></el-option>
                        <el-option label="车" value="车"></el-option>
                        <el-option label="外磨" value="外磨"></el-option>
                        <el-option label="铣" value="铣"></el-option>
                        <el-option label="校平" value="校平"></el-option>
                        <el-option label="镗铣" value="镗铣"></el-option>
                        <el-option label="焊" value="焊"></el-option>
                        <el-option label="打磨" value="打磨"></el-option>
                        <el-option label="装" value="装"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="工人姓名" prop="workerId">
                    <el-input v-model="dataDialogForm.workerName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="成本" prop="cost">
                    <el-input v-model="dataDialogForm.cost" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="工时" prop="hour">
                    <el-input v-model="dataDialogForm.hour" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="submitForm">确定</el-button>
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
            };
        },
        methods: {
            // 查询数据
            async getDataList() {
                const params = {
                    pageSize: this.pageSize,
                    pageIndex: this.pageIndex,
                    processName: this.dataForm.processName || "", // 空值传递空字符串
                };

                console.log("查询参数：", params);

                try {
                    const response = await this.$http.get("/workhours/list", { params });
                    const data = response.data.data;
                    this.dataList = data.list || [];
                    this.totalPage = data.totalCount || 0;
                } catch (error) {
                    console.error("获取数据失败：", error);
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
            async handleEdit(row) {
                this.dataDialogForm = { ...row };
                this.dialogFormVisible = true;
            },
            async handleDelete(row) {
                this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                })
                    .then(async () => {
                        try {
                            await this.$http.delete('/workhours/delete', { data: { id: row.id } });
                            this.$message.success('删除成功');
                            this.getDataList(); // 重新获取数据列表
                        } catch (error) {
                            console.error('删除失败:', error);
                            this.$message.error('删除失败');
                        }
                    })
                    .catch(() => {
                        this.$message.info('已取消删除');
                    });
            }
            ,
            async submitForm() {
                const url = this.dataDialogForm.id ? "/workhours/update" : "/workhours/save";
                try {
                    await this.$http.post(url, this.dataDialogForm);
                    this.$message.success(this.dataDialogForm.id ? "更新成功" : "新增成功");
                    this.dialogFormVisible = false;
                    await this.getDataList();
                } catch (error) {
                    console.error("保存失败", error);
                    this.$message.error("保存失败");
                }
            },
        },
        mounted() {
            this.getDataList();
        },
    };
</script>

<style scoped>
    .dialog-footer{
      display: flex;
      justify-content: center;
      align-items: center;
    }
  </style>