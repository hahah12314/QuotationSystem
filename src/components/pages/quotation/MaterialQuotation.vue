<template>

    <el-card class="box-card">
        <QuoteTimeline :currentStage="currentStage" />
        <div id='sysRole'>
            <el-form :inline="true" :model="dataForm" class="demo-form-inline" size="mini">
                <!-- <el-form-item>
                    <el-input v-model="dataForm.customer.name" placeholder="客户名称" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="getDataList()">查询</el-button>
                </el-form-item> -->

                <el-button type="success" @click="exportToPDF" size="mini">导出为 PDF</el-button>


            </el-form>

            <el-table :data="dataList" border style="width: 100%" v-loading="dataListLoading" size="mini"
                ref="multipleTable" @selection-change="handleSelectionChange">
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
                <el-table-column label="操作" width="240">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="handleEdit(scope.row)">修改</el-button>
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
            <add-quotation :outQuotationForm="selectedQuotation" :ReadOnly="true"></add-quotation>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleCloseDetailsModal">关 闭</el-button>
            </span>
        </el-dialog>
        <el-dialog title="修改报价单" :visible.sync="dialogFormVisible" width="80%" custom-class="custom-modal"
            @close="closeDialog">
            <add-quotation :outQuotationForm="selectedQuotation" :ReadOnly="false"></add-quotation>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">关 闭</el-button>
            </span>
        </el-dialog>


        <!-- <el-dialog :title="dataDialogForm.id === 0 ? '新增记录' : '编辑记录'" :visible.sync="dialogFormVisible" width="35%"
            >
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
        </el-dialog> -->
        <!-- 弹出框显示完整的审核意见 -->
        <el-dialog :visible.sync="fullOpinionDialogVisible" title="审核意见详情" width="50%">
            <p>{{ selectedAuditOpinion }}</p>
            <span slot="footer" class="dialog-footer">
                <el-button @click="fullOpinionDialogVisible = false">关闭</el-button>
            </span>
        </el-dialog>
    </el-card>

</template>

<script>
    import jsPDF from 'jspdf';
    import 'jspdf-autotable';
    require('./../../../assets/STFANGSO-normal.js')
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
        filters: {
            truncate(value, length) {
                if (!value) return '';
                return value.length > length ? value.slice(0, length) + '...' : value;
            }
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
                fullOpinionDialogVisible: false, // 控制完整审核意见对话框显示
                selectedAuditOpinion: '', // 存储选中的审核意见
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
                multipleSelection: [],
                dialogVisible: false, // 控制弹框显示
                selectedMaterialInfo: null, // 存储后端返回的详情数据
            }
        },
        computed: {

        },
        methods: {
            handleSelectionChange(val) {
                this.multipleSelection = val;
                console.log('selected', this.multipleSelection)
            },

            exportToPDF() {
                if (this.multipleSelection.length < 1) {
                    this.$message.error('请选择要导出的数据');
                }

                // 遍历选中的每一项
                this.multipleSelection.forEach((item, index) => {
                    const doc = new jsPDF(); // 实例化新的 jsPDF 对象
                    doc.setFont('STFANGSO'); // 设置中文字体

                    // 添加总标题并居中显示
                    doc.setFontSize(18);
                    doc.setTextColor(0, 0, 0); // 设置文字颜色为黑色
                    const totalPages = doc.internal.getNumberOfPages();
                    const pageHeight = doc.internal.pageSize.height;
                    const textWidth = doc.getStringUnitWidth(`报价单 - ${item.quotationId}`) * doc.getFontSize() / doc.internal.scaleFactor;
                    const x = (doc.internal.pageSize.width - textWidth) / 2;
                    doc.text(`报价单 - ${item.quotationId}`, x, 15);

                    // 定义每个部分的内容
                    const sections = [
                        {
                            title: "公司信息",
                            content: [
                                ["公司名称", item.company.name || ""],
                                ["负责人", item.company.responsiblePerson || ""],
                                ["联系方式", item.company.contactInfo || ""],
                            ],
                        },
                        {
                            title: "客户信息",
                            content: [
                                ["客户名称", item.customer.name || ""],
                                ["联系方式", item.customer.contactInfo || ""],
                                ["电子邮箱", item.customer.email || ""],
                                ["最低价格", `${item.customer.minPrice || 0.0}`],
                                ["最高价格", `${item.customer.maxPrice || 0.0}`],
                                ["客户需求", item.customer.requirements || ""],
                                ["地址", item.customer.address || ""],
                                ["付款方式", item.customer.paymentMethod || ""],
                            ],
                        },
                        {
                            title: "物料信息",
                            content: [
                                ["物料编码", item.material.quotedMaterials.materialCode || ""],
                                ["物料名称", item.material.quotedMaterials.materialName || ""],
                                ["单价", `${item.material.quotedMaterials.unitPrice || 0}`],
                                ["数量", `${item.material.quotedMaterials.quantity || 0}`],
                                ["材料规格", `${item.material.quotedMaterials.specification || ''}`],
                                ["是否外采", `${item.material.quotedMaterials.isExternalProcurement || "否"}`],
                            ],
                        },
                        {
                            title: "原材料费用(元)",
                            content: [
                                ["长/直径/外径", `${item.material.customDetails.materialCost.length || 0} `],
                                ["宽/直径/壁厚", `${item.material.customDetails.materialCost.width || 0} `],
                                ["高/长/长", `${item.material.customDetails.materialCost.height || 0} `],
                                ["毛胚重量(kg)", `${item.material.customDetails.materialCost.blankWeight || 0} `],
                                ["材料单价(元/kg)", `${item.material.customDetails.materialCost.materialUnitPrice || 0}`],
                                ["毛料费(元)", `${item.material.customDetails.materialCost.blankFee || 0}`],
                                ["废料费(元)", `${item.material.customDetails.materialCost.scrapWeight || 0} `],
                                ["废料单价(元/kg)", `${item.material.customDetails.materialCost.scrapUnitPrice || 0}`],
                                ["废料费(元)", `${item.material.customDetails.materialCost.scrapFee || 0}`],
                                ["原材料费用小计", `${item.material.customDetails.materialCost.totalMaterialCost || 0}`],

                            ],
                        },
                        {
                            title: "数割费用(元)",
                            content: [
                                ["数割(M)", `${item.material.customDetails.cuttingCost.numCut || 0}`],
                                ["下料费", `${item.material.customDetails.cuttingCost.cutFee || 0}`],
                                ["费用小计", `${item.material.customDetails.cuttingCost.totalCutCost || 0}`],
                            ],
                        },
                        {
                            title: "单件加工费用(元)",
                            content: [
                                ["锯切工时", `${item.material.customDetails.processingCost.sawTime || 0} `],
                                ["锯切费用", `${item.material.customDetails.processingCost.sawFee || 0}`],
                                ["弯曲工时", `${item.material.customDetails.processingCost.bendTime || 0} `],
                                ["弯曲费用", `${item.material.customDetails.processingCost.bendFee || 0}`],
                                ["钻孔工时", `${item.material.customDetails.processingCost.drillTime || 0} `],
                                ["钻孔费用", `${item.material.customDetails.processingCost.drillFee || 0}`],
                                ["车削工时", `${item.material.customDetails.processingCost.turnTime || 0} `],
                                ["车削费用", `${item.material.customDetails.processingCost.turnFee || 0}`],
                                ["磨削工时", `${item.material.customDetails.processingCost.grindTime || 0} `],
                                ["磨削费用", `${item.material.customDetails.processingCost.grindFee || 0}`],
                                ["铣削工时", `${item.material.customDetails.processingCost.millTime || 0} `],
                                ["铣削费用", `${item.material.customDetails.processingCost.millFee || 0}`],

                            ],
                        },
                        {
                            title: "单件加工费用(元)",
                            content: [

                                ["校直工时", `${item.material.customDetails.processingCost.calibrateTime || 0} `],
                                ["校直费用", `${item.material.customDetails.processingCost.calibrateFee || 0}`],
                                ["镗孔工时", `${item.material.customDetails.processingCost.boreMillTime || 0} `],
                                ["镗孔费用", `${item.material.customDetails.processingCost.boreMillFee || 0}`],
                                ["焊接工时", `${item.material.customDetails.processingCost.weldTime || 0} `],
                                ["焊接费用", `${item.material.customDetails.processingCost.weldFee || 0}`],
                                ["抛光工时", `${item.material.customDetails.processingCost.polishTime || 0} `],
                                ["抛光费用", `${item.material.customDetails.processingCost.polishFee || 0}`],
                                ["装配工时", `${item.material.customDetails.processingCost.assembleTime || 0} `],
                                ["装配费用", `${item.material.customDetails.processingCost.assembleFee || 0}`],
                                ["总加工费用", `${item.material.customDetails.processingCost.totalProcessCost || 0}`],
                            ],
                        },

                        {
                            title: "表面处理单件费用(元)",
                            content: [
                                ["锌重量", `${item.material.customDetails.surfaceTreatment.zincWeight || 0} `],
                                ["锌费用", `${item.material.customDetails.surfaceTreatment.zincFee || 0}`],
                                ["调质重量", `${item.material.customDetails.surfaceTreatment.temperingWeight || 0} `],
                                ["调质费用", `${item.material.customDetails.surfaceTreatment.temperingFee || 0}`],
                                ["喷砂重量", `${item.material.customDetails.surfaceTreatment.sandWeight || 0} `],
                                ["喷砂费用", `${item.material.customDetails.surfaceTreatment.sandFee || 0}`],
                                ["QPQ重量", `${item.material.customDetails.surfaceTreatment.qpqWeight || 0} `],
                                ["QPQ费用", `${item.material.customDetails.surfaceTreatment.qpqFee || 0}`],
                                ["总涂层费用", `${item.material.customDetails.surfaceTreatment.totalCoatingCost || 0}`],
                                ["镍面积(m²)", `${item.material.customDetails.surfaceTreatment.nickArea || 0} `],
                                ["镍费用", `${item.material.customDetails.surfaceTreatment.nickFee || 0}`],
                                ["铬面积(m²)", `${item.material.customDetails.surfaceTreatment.chromeArea || 0}`],
                                ["铬费用", `${item.material.customDetails.surfaceTreatment.chromeFee || 0}`],
                            ],
                        },
                        {
                            title: "喷涂单件费用(元)",
                            content: [
                                ["酸洗费用", `${item.material.customDetails.paintingCost.acidWashCost || 0}`],
                                ["酸洗重量", `${item.material.customDetails.paintingCost.acidWashWeight || 0} `],
                                ["喷塑费用", `${item.material.customDetails.paintingCost.sprayPlasticCost || 0}`],
                                ["底漆费用", `${item.material.customDetails.paintingCost.primerCost || 0}`],
                                ["底漆面积", `${item.material.customDetails.paintingCost.primerArea || 0} `],
                                ["喷塑面积", `${item.material.customDetails.paintingCost.sprayPlasticArea || 0} `],
                                ["电泳费用", `${item.material.customDetails.paintingCost.electroswimmingCost || 0}`],
                                ["电泳面积", `${item.material.customDetails.paintingCost.electroswimmingArea || 0} `],
                                ["面漆费用", `${item.material.customDetails.paintingCost.topcoatCost || 0}`],
                                ["面漆面积", `${item.material.customDetails.paintingCost.topcoatArea || 0} `],
                                ["总喷涂费用", `${item.material.customDetails.paintingCost.totalPaintingCost || 0}`],
                            ],
                        },


                        // 修改后的费用表部分
                        {
                            title: "原材料信息",
                            content: [
                                ["材料规格", `${item.material.customDetails.rawMaterials.specification || ""}`],
                                ["单位重量(公斤)", `${item.material.customDetails.rawMaterials.unitWeight || 0}`],
                                ["净重(公斤)", `${item.material.customDetails.rawMaterials.netWeight || 0}`],
                                ["裸价", `${item.material.customDetails.rawMaterials.nakedPrice || 0}`],
                                ["利润", `${item.material.customDetails.rawMaterials.profit || 0}`],
                                ["杂费", `${item.material.customDetails.rawMaterials.miscellaneousFees || 0}`],
                                ["每公斤价格", `${item.material.customDetails.rawMaterials.pricePerkg || 0}`],
                                ["未税", `${item.material.customDetails.rawMaterials.preTax || 0}`],
                                ["产品合计报价", `${item.material.customDetails.rawMaterials.totalProductQuote || 0}`]
                            ],
                        }

                    ];

                    let y = 20; // 初始 Y 位置，留出空间给总标题

                    sections.forEach(section => {
                        // 将内容转换为适合 autoTable 的格式
                        const keys = section.content.map(([key, value]) => key);
                        const values = section.content.map(([key, value]) => value);

                        // 使用 autoTable 插件渲染表格内容，包括标题
                        doc.autoTable({
                            startY: y, // 设置起始 Y 坐标
                            head: [
                                [{ content: section.title, colSpan: section.content.length, styles: { halign: 'center', fillColor: [141, 209, 107], textColor: [0, 0, 0], fontSize: 14, lineWidth: 0.1, lineColor: [0, 0, 0] } }]
                            ], // 标题行
                            body: [
                                keys,   // 第一行是键
                                values  // 第二行是值
                            ],
                            styles: { font: 'STFANGSO', fontSize: 12, cellPadding: 3, textColor: [0, 0, 0], lineWidth: 0.1, lineColor: [0, 0, 0] }, // 样式
                            theme: 'grid', // 网格样式
                            headStyles: { fillColor: [141, 209, 107], textColor: [0, 0, 0], lineWidth: 0.1, lineColor: [0, 0, 0] }, // 表头背景色和文字颜色
                            bodyStyles: { fillColor: [255, 255, 255], textColor: [0, 0, 0], lineWidth: 0.1, lineColor: [0, 0, 0] }, // 表体背景色和文字颜色
                            alternateRowStyles: { fillColor: [224, 255, 224], textColor: [0, 0, 0], lineWidth: 0.1, lineColor: [0, 0, 0] }, // 交替行背景色和文字颜色
                            tableWidth: 'auto', // 自动调整表格宽度
                            columnStyles: {
                                0: { halign: 'right' }, // 第一列右对齐
                                1: { halign: 'left' }  // 第二列左对齐
                            },
                            columnStyles: {
                                0: { halign: 'center' }, // 第一列居中对齐
                                1: { halign: 'center' }  // 第二列居中对齐
                            }
                        });

                        // 更新 Y 位置，用于分隔每个部分，不添加额外的空行
                        y = doc.lastAutoTable.finalY;
                    });

                    // 导出 PDF 文件，文件名包含索引以区分不同的文件
                    doc.save(`报价单_${item.quotationId}.pdf`);
                });
            },
            watchInfo() {
                this.dialogVisible = true;
            },
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
            showFullOpinion(opinion) {
                this.selectedAuditOpinion = opinion;
                this.fullOpinionDialogVisible = true;
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
                this.selectedQuotation = row
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