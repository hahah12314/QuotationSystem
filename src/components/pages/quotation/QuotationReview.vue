<template>
    <el-card class="box-card">
        <QuoteTimeline :currentStage="currentStage" />
        <div id='sysRole'>

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
                <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
                <el-table-column prop="updateTime" label="更新时间" width="150"></el-table-column>

                <el-table-column label="审核状态" width="120">
                    <template slot-scope="scope">
                        <el-tag :type="getAuditStatusType(scope.row.auditStatus)" effect="dark">
                            {{ getAuditStatusLabel(scope.row.auditStatus) }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="auditOpinion" label="审核意见" width="150">
                    <template slot-scope="scope">
                        <el-tooltip placement="top" effect="light">
                            <div slot="content">{{ scope.row.auditOpinion }}</div>
                            <span class="ellipsis" @click="showFullOpinion(scope.row.auditOpinion)">
                                {{ scope.row.auditOpinion | truncate(50) }}
                            </span>
                        </el-tooltip>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="fetchQuotationInfo(scope.row)">审核</el-button>


                    </template>
                </el-table-column>
            </el-table>


        </div>
        <!-- 弹出框显示 MaterialInfo -->
        <el-dialog :visible.sync="dialogVisible" title="原材料明细详情" width="50%">
            <MaterialInfo :row="selectedMaterialInfo" v-if="selectedMaterialInfo" />
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">关闭</el-button>
            </span>
        </el-dialog>
        <!-- 弹出框显示完整的审核意见 -->
        <el-dialog :visible.sync="fullOpinionDialogVisible" title="审核意见详情" width="50%">
            <p>{{ selectedAuditOpinion }}</p>
            <span slot="footer" class="dialog-footer">
                <el-button @click="fullOpinionDialogVisible = false">关闭</el-button>
            </span>
        </el-dialog>

        <el-dialog :title="this.dataDialogForm.quotationId === 0 ? '新增报价' : '更新报价'" :visible.sync="dialogFormVisible"
            width="35%" @close="closeDialog">
            <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini">
                <el-form-item label="报价名称" prop="quotationName">
                    <el-input v-model="dataDialogForm.quotationName" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="物料编码" prop="materialCode">
                    <el-input v-model="dataDialogForm.materialCode" autocomplete="off" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="dataDialogForm.materialName" autocomplete="off" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="单价" prop="unitPrice">
                    <el-input v-model="dataDialogForm.unitPrice" autocomplete="off" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="数量" prop="count">
                    <el-input v-model="dataDialogForm.count" autocomplete="off" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="材料规格" prop="specification">
                    <el-input v-model="dataDialogForm.specification" autocomplete="off"
                        style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="是否外采" prop="isExternalProcurement">
                    <el-switch v-model="dataDialogForm.isExternalProcurement" active-text="是"
                        inactive-text="否"></el-switch>
                </el-form-item>
                <el-form-item label="产品合计报价" prop="totalCost">
                    <el-input v-model="dataDialogForm.totalCost" autocomplete="off" style="width: 240px;"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitQuotation('ruleForm')">确 定</el-button>
            </div>
        </el-dialog>
    </el-card>
</template>

<script>
    import QuoteTimeline from '@/components/detail/QuoteTimeline'
    import MaterialInfo from '@/components/common/MaterialInfo';
    export default {
        name: 'QuotationReview',
        components: {
            MaterialInfo,
            QuoteTimeline
        },
        filters: {
            truncate(value, length) {
                if (!value) return '';
                return value.length > length ? value.slice(0, length) + '...' : value;
            }
        },


        data() {
            return {
                currentStage: 2,
                dataForm: {

                },
                dataDialogForm: {
                    materialId: 0,
                },
                dataList: [],
                pageSize: 10,
                pageIndex: 1,
                totalPage: 0,
                dataListLoading: false,
                formLabelWidth: '120px',
                dialogFormVisible: false,
                dialogSubmitForm: false,
                checks: [],
                rules: {},
                fullOpinionDialogVisible: false, // 控制完整审核意见对话框显示
                selectedAuditOpinion: '', // 存储选中的审核意见
                dialogVisible: false, // 控制弹框显示
                selectedMaterialInfo: null, // 存储后端返回的详情数据
            }
        },
        methods: {
            getAuditStatusType(auditStatus) {
                switch (auditStatus) {
                    case 0:
                        return 'danger'; // 未审核
                    case -1:
                        return 'warning'; // 已打回
                    default:
                        return 'success'; // 其他状态（如已审核）
                }
            },
            getAuditStatusLabel(auditStatus) {
                switch (auditStatus) {
                    case 0:
                        return '未审核';
                    case -1:
                        return '审核不通过';
                    default:
                        return '已审核';
                }
            },
            showFullOpinion(opinion) {
                this.selectedAuditOpinion = opinion;
                this.fullOpinionDialogVisible = true;
            },
            fetchQuotationInfo(row) {
                // 将 quotationForm 转换为 JSON 字符串
                const formData = JSON.stringify(row);
                this.$router.push({
                    path: '/auditQuotation',
                    query: { formData }
                }).catch(err => {
                    console.error('Router push error:', err);
                });
            },
            async getDataList() {
                if (this.dataListLoading) {
                    return;
                }
                this.dataListLoading = true;


                try {
                    const res = await this.$http.get('/quotations/audit');
                    this.dataList = res.data.data

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
                // 详情逻辑
                this.dialogFormVisible = true;
                this.dataDialogForm.roleId = item.roleId;
                this.dataDialogForm.roleName = item.roleName;
                this.dataDialogForm.remark = item.remark;
            },
            async handleDelete(index, item) {
                // 删除逻辑
            },
            openDialog() {
                this.dialogFormVisible = true;
                this.dataDialogForm = {
                    roleId: 0,
                    remark: '',
                    roleName: '',
                };
            },
            closeDialog() {
                this.dialogFormVisible = false;
                this.dataDialogForm = {
                    roleId: 0,
                    remark: '',
                    roleName: '',
                };
            },
            async submitRole(formName) {
                // 提交逻辑
            },
            formatPrice(value) {
                return value ? `¥ ${parseFloat(value).toFixed(2)}` : '—'; // 格式化价格
            },
            exportData() {
                // 导出数据逻辑
                const params = {
                    materialName: this.dataForm.materialName,
                    materialCode: this.dataForm.materialCode
                };
                this.$http.get('/quotations/export', { params, responseType: 'blob' })
                    .then(response => {
                        const blob = new Blob([response.data], { type: 'application/vnd.ms-excel' });
                        const link = document.createElement('a');
                        link.href = URL.createObjectURL(blob);
                        link.download = '报价单.xlsx';
                        link.click();
                    })
                    .catch(error => {
                        console.error('导出数据时出错:', error);
                        this.$message({
                            message: '导出数据时发生错误',
                            type: 'error'
                        });
                    });
            },
        },
        mounted() {
            this.getDataList();
        }
    }
</script>

<style scoped lang="less">
    .box-card {

        border-radius: 10px;
        box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
    }

    .demo-form-inline {
        margin-bottom: 20px;
    }

    .ellipsis {
        cursor: pointer;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        display: block;
        width: 100%;
    }

    .dialog-footer {
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>