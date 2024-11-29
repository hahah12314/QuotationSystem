<template>

    <el-card class="box-card">
        <QuoteTimeline :currentStage="currentStage" />
        <div id='sysRole'>
            <!-- <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model="dataForm.customer.name" placeholder="客户名称" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="getDataList()">查询</el-button>
                </el-form-item>

            </el-form> -->

            <el-table :data="dataList" border style="width: 100%" v-loading="dataListLoading" size="mini">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column type="index" label="序号" width="55"></el-table-column>
                <el-table-column prop="company.name" label="公司名称" width="150"></el-table-column>
                <el-table-column prop="company.responsiblePerson" label="负责人" width="150"></el-table-column>
                <el-table-column prop="company.contactInfo" label="联系方式" width="150"></el-table-column>
                <el-table-column prop="customer.name" label="客户名称" width="150"></el-table-column>
                <el-table-column prop="customer.contactInfo" label="客户联系方式" width="150"></el-table-column>
                <el-table-column prop="customer.email" label="客户邮箱" width="150"></el-table-column>
                <el-table-column prop="customer.address" label="客户地址" width="200"></el-table-column>
                <el-table-column prop="customer.paymentMethod" label="支付方式" width="150"></el-table-column>

                <el-table-column label="状态" prop="auditStatus" width="120">

                    <template slot-scope="scope">
                        <el-button size="mini" type="success" v-if="scope.row.auditStatus === 0">未审核</el-button>
                        <el-button size="mini" type="info" v-else-if="scope.row.auditStatus === -1"
                            @click="watchInfo">审核不通过</el-button>
                        <el-button size="mini" type="danger" v-else-if="scope.row.auditStatus === 1">已审核</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="240">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                        <el-button size="mini" type="info" @click="handleInfo(scope.row)">详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
            </el-pagination>
        </div>
        <el-dialog title="报价单详情" :visible.sync="isDetailsModalVisible" width="80%" custom-class="custom-modal">
            <add-quotation :outQuotationForm="selectedQuotation" :isReadOnly="true"></add-quotation>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleCloseDetailsModal">关 闭</el-button>
            </span>
        </el-dialog>
       

        <el-dialog :title="dataDialogForm.id === 0 ? '新增记录' : '编辑记录'" :visible.sync="dialogFormVisible" width="35%"
            @close="closeDialog">
            <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini">
                <el-form-item label="公司名称" prop="company.name">
                    <el-input v-model="dataDialogForm.company.name" autocomplete="off" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="负责人" prop="company.responsiblePerson">
                    <el-input v-model="dataDialogForm.company.responsiblePerson" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="company.contactInfo">
                    <el-input v-model="dataDialogForm.company.contactInfo" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="客户名称" prop="customer.name">
                    <el-input v-model="dataDialogForm.customer.name" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="客户联系方式" prop="customer.contactInfo">
                    <el-input v-model="dataDialogForm.customer.contactInfo" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="客户邮箱" prop="customer.email">
                    <el-input v-model="dataDialogForm.customer.email" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="客户地址" prop="customer.address">
                    <el-input v-model="dataDialogForm.customer.address" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="支付方式" prop="customer.paymentMethod">
                    <el-select v-model="dataDialogForm.customer.paymentMethod" placeholder="请选择支付方式"
                        style="width: 240px;">
                        <el-option label="现金" value="现金"></el-option>
                        <el-option label="信用卡" value="信用卡"></el-option>
                        <el-option label="支付宝" value="支付宝"></el-option>
                        <el-option label="银行转账" value="银行转账"></el-option>
                        <el-option label="微信支付" value="微信支付"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="submitData()">确定</el-button>
            </div>
        </el-dialog>
        <!-- 审核不通过原因弹窗 -->
        <el-dialog :visible.sync="dialogVisible" title="审核不通过原因">
            <p>{{ auditReason }}</p>
        </el-dialog>
    </el-card>

</template>

<script>
    import AddQuotation from '@/components/pages/quotation/AddQuotation';
    import MaterialInfo from '@/components/common/MaterialInfo';
    import QuoteTimeline from '@/components/detail/QuoteTimeline'
    export default {
        name: 'materialQuotation',
        components: {
            MaterialInfo,
            AddQuotation,
            QuoteTimeline

        },
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
                dialogVisible: false,
                auditReason: '报价不合理，打回重改',
                currentStage: 7,
                dataForm: {
                    customer: {
                        name: ''
                    }
                },
                dataDialogForm: {
                    company: {
                        name: '金威',
                        responsiblePerson: '',
                        contactInfo: '',


                    },
                    customer: {
                        name: '',
                        contactInfo: '',
                        email: '',
                        minPrice: 0,
                        maxPrice: 0,
                        requirements: '',
                        address: '',
                        paymentMethod: ''
                    },
                    quotationId: "",
                    auditStatus: 0








                },
                isDetailsModalVisible: false,
                selectedQuotation: null,

                dataList: [],

                pageSize: 5,
                pageIndex: 1,
                totalPage: 0,
                dataListLoading: false,
                formLabelWidth: '120px',
                dialogFormVisible: false,
                dialogSubmitForm: false,
                checks: [],
                rules: {

                },
                dialogVisible: false, // 控制弹框显示
                selectedMaterialInfo: null, // 存储后端返回的详情数据
            }
        },
        computed: {

        },
        methods: {
            watchInfo() {
                this.dialogVisible = true;
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
                        customerName: this.dataForm.customer.name

                    }
                };

                try {
                    const res = await this.$http.get('/quotations/list', params);


                    this.dataList = res.data.data.list;
                    console.log(this.dataList);
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
                this.dataList.forEach(row => {
                    //row.auditStatus = row.auditStatus === 1 ? '审核' : '未审核';
                    row.isExternalProcurement = row.isExternalProcurement === 1 ? '是外采' : '不是外采';
                });
                console.log(this.dataList);
            },
            async fetchMaterialInfo(row) {
                if (this.dataListLoading) {
                    return;
                }
                this.dataListLoading = true;
                const params = {
                    params: {

                        specification: row.specification // 新增物料编码筛选条件
                    }
                };
                console.log(params);

                try {
                    const res = await this.$http.get('/quotations/getDetail', params);
                    this.selectedMaterialInfo = res.data.data;
                    console.log(this.selectedMaterialInfo);
                    this.dialogVisible = true;
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
            handleDelete(row) {

                this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                })
                    .then(async () => {
                        try {
                            await this.$http.delete('/quotations/delete', { data: { id: row.quotationId } });
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

            },
            handleInfo(row) {
                this.selectedQuotation = row
                this.isDetailsModalVisible = true
            },
            handleCloseDetailsModal() {
                this.isDetailsModalVisible = false;
                this.selectedQuotation = null;
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
            handleEdit(row) {
                this.dataDialogForm = { ...row };
                this.dialogFormVisible = true;
                console.log(this.dataDialogForm)
            },

            openDialog() {
                this.dialogFormVisible = true;
                this.dataDialogForm = {
                    company: {
                        name: '金威',
                        responsiblePerson: '',
                        contactInfo: '',

                    },
                    customer: {
                        name: '',
                        contactInfo: '',
                        email: '',
                        minPrice: 0,
                        maxPrice: 0,
                        requirements: '',
                        address: '',
                        paymentMethod: ''
                    },
                    quotationId: ""
                };
            },
            closeDialog() {
                this.dialogFormVisible = false;

            },
            async submitData() {
                console.log(this.dataDialogForm)
                this.dataDialogForm.auditStatus = 1
                const url = this.dataDialogForm.quotationId ? "/quotations/update" : "/quotations/save";
                try {
                    await this.$http.post(url, this.dataDialogForm);
                    this.$message.success(this.dataDialogForm.id ? "编辑成功" : "新增成功");
                    this.dialogFormVisible = false;
                    this.getDataList();
                } catch (error) {
                    console.error("提交失败", error);
                    this.$message.error("提交失败");
                }
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
    .box-card {

        border-radius: 10px;
        box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
    }

    .demo-form-inline {
        margin-bottom: 20px;
    }

    .dialog-footer {
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>