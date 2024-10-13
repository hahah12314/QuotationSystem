<template>

  <el-card class="box-card">
    <div id='sysRole'>
      <el-form :inline="true" :model="dataForm" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="dataForm.materialName" placeholder="材料名称" clearable></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="formattedDataList" border style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-collapse v-model="activeNames" @change="handleChange" label-position="left" inline
              class="demo-table-expand">
              <el-collapse-item title="原材料费用" name="1">
                <el-table :data="[props.row]" style="width: 100%">
                  <el-table-column prop="raw_length" label="原材料长度" width="auto"></el-table-column>
                  <el-table-column prop="width" label="宽度" width="auto"></el-table-column>
                  <el-table-column prop="height" label="高度" width="auto"></el-table-column>
                  <el-table-column prop="blankWeight" label="毛坯重量" width="auto"></el-table-column>
                  <el-table-column prop="materialUnitPrice" label="材料单价" width="auto"></el-table-column>
                  <el-table-column prop="blankFee" label="毛坯费用" width="auto"></el-table-column>
                  <el-table-column prop="scrapWeight" label="废料重量" width="auto"></el-table-column>
                  <el-table-column prop="scrapUnitPrice" label="废料单价" width="auto"></el-table-column>
                  <el-table-column prop="scrapFee" label="废料费用" width="auto"></el-table-column>
                  <el-table-column prop="totalMaterialCost" label="总材料成本" width="auto"></el-table-column>
                </el-table>
              </el-collapse-item>
              <el-collapse-item title="数割费用" name="2">
                <el-table :data="[props.row]" style="width: 100%">
                  <el-table-column prop="numCut" label="数割(M)" width="180"></el-table-column>
                  <el-table-column prop="cutFee" label="下料费"></el-table-column>
                  <el-table-column prop="totalCutFee" label="费用小计"></el-table-column>
                </el-table>
              </el-collapse-item>

              <el-collapse-item title="单件加工费用(元)" name="4">
                <el-table :data="[props.row]" style="width: 100%">
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
                </el-table>
              </el-collapse-item>
              <el-collapse-item title="表面处理单件费用(元)" name="3">
                <el-table :data="[props.row]" style="width: 100%">
                  <el-table-column prop="zincWeight" label="镀锌重量" width="auto"></el-table-column>
                  <el-table-column prop="zincFee" label="镀锌费用" width="auto"></el-table-column>
                  <el-table-column prop="temperingWeight" label="淬火重量" width="auto"></el-table-column>
                  <el-table-column prop="temperingFee" label="淬火费用" width="auto"></el-table-column>
                  <el-table-column prop="sandWeight" label="砂光重量" width="auto"></el-table-column>
                  <el-table-column prop="sandFee" label="砂光费用" width="auto"></el-table-column>
                  <el-table-column prop="qpqWeight" label="QPQ重量" width="auto"></el-table-column>
                  <el-table-column prop="qpqFee" label="QPQ费用" width="auto"></el-table-column>
                  <el-table-column prop="totalCoatingFee" label="总涂层费用" width="auto"></el-table-column>
                </el-table>
              </el-collapse-item>
              <el-collapse-item title="喷涂单件费用" name="5">
                <el-table :data="[props.row]" style="width: 100%">
                  <el-table-column prop="sandblastTime" label="喷砂时间" width="auto"></el-table-column>
                  <el-table-column prop="sandblastFee" label="喷砂费用" width="auto"></el-table-column>
                  <el-table-column prop="paintTime" label="喷涂时间" width="auto"></el-table-column>
                  <el-table-column prop="paintFee" label="喷涂费用" width="auto"></el-table-column>
                  <el-table-column prop="plasticTime" label="塑料时间" width="auto"></el-table-column>
                  <el-table-column prop="plasticFee" label="塑料费用" width="auto"></el-table-column>
                  <el-table-column prop="totalPaintingFee" label="总喷涂费用" width="auto"></el-table-column>
                </el-table>
              </el-collapse-item>
            </el-collapse>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="detailId" label="ID">
        </el-table-column>
        </el-table-column>
        <el-table-column prop="specification" label="材料规格"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="auto"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="auto"></el-table-column>
        <el-table-column prop="nakedPrice" label="裸价" width="auto"></el-table-column>
        <el-table-column prop="profit" label="利润" width="auto"></el-table-column>
        <el-table-column prop="miscellaneousFees" label="杂费" width="auto"></el-table-column>
       

        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
      </el-pagination>
    </div>

    <el-dialog :title="this.dataDialogForm.roleId===0 ? '新增物料' : '更新物料' " :visible.sync="dialogFormVisible" width="35%"
      @close="closeDialog">
      <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
        <el-form-item label="物料编码" :label-width="formLabelWidth" prop="materialCode">
          <el-input v-model="dataDialogForm.materialCode" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="物料名称" :label-width="formLabelWidth" prop="materialName">
          <el-input v-model="dataDialogForm.materialName" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="物料单价" :label-width="formLabelWidth" prop="unitPrice">
          <el-input v-model="dataDialogForm.unitPrice" autocomplete="off" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="单件重量" :label-width="formLabelWidth" prop="weight">
          <el-input v-model="dataDialogForm.weight" autocomplete="off" style="width: 300px;"></el-input>
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
    name: 'materialDetail',
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
          materialName: ''
        },
        dataDialogForm: {

          materialId: 0,

        },
        processingData: [],

        dataList: [],
        processedMaterials: [],

        pageSize: 5,
        pageIndex: 1,
        totalPage: 0,
        dataListLoading: false,
        formLabelWidth: '120px',
        dialogFormVisible: false,
        dialogSubmitForm: false,
        checks: [],
        rules: {

        }
      }
    },
    computed: {
      formattedDataList() {
        return this.dataList.map(row => ({
          ...row,
          blankFee: this.calculateBlankFee(row),
          scrapFee: this.calculateScrapFee(row),
          totalMaterialCost: this.calculateTotalMaterialCost(row)
        }));
      }
    },
    methods: {
      processBackendData() {
        const processedMaterials = [];

        this.dataList.forEach((material, index) => {
          const materialData = {
            id: material.detailId,
            processingData: [
              { process: '锯切', time: `${material.sawTime}小时`, fee: `${material.sawFee.toFixed(2)}元` },
              { process: '弯曲', time: `${material.bendTime}小时`, fee: `${material.bendFee.toFixed(2)}元` },
              { process: '钻孔', time: `${material.drillTime}小时`, fee: `${material.drillFee.toFixed(2)}元` },
              { process: '车削', time: `${material.turnTime}小时`, fee: `${material.turnFee.toFixed(2)}元` },
              { process: '磨削', time: `${material.grindTime}小时`, fee: `${material.grindFee.toFixed(2)}元` },
              { process: '铣削', time: `${material.millTime}小时`, fee: `${material.millFee.toFixed(2)}元` },
              { process: '校正', time: `${material.calibrateTime}小时`, fee: `${material.calibrateFee.toFixed(2)}元` },
              { process: '镗铣', time: `${material.boreMillTime}小时`, fee: `${material.boreMillFee.toFixed(2)}元` },
              { process: '焊接', time: `${material.weldTime}小时`, fee: `${material.weldFee.toFixed(2)}元` },
              { process: '抛光', time: `${material.polishTime}小时`, fee: `${material.polishFee.toFixed(2)}元` },
              { process: '组装', time: `${material.assembleTime}小时`, fee: `${material.assembleFee.toFixed(2)}元` },
              { process: '总加工费用', time: '-', fee: `${material.totalProcessFee.toFixed(2)}元` }
            ]
          };

          processedMaterials.push(materialData);
        });

        this.processedMaterials = processedMaterials;
      },
      calculateBlankFee(row) {
        const blankWeight = parseFloat(row.blankWeight);
        const materialUnitPrice = parseFloat(row.materialUnitPrice);
        return (blankWeight * materialUnitPrice).toFixed(2); // 四舍五入保留两位小数
      },
      calculateScrapFee(row) {
        const scrapWeight = parseFloat(row.scrapWeight);
        const scrapUnitPrice = parseFloat(row.scrapUnitPrice);
        return (scrapWeight * scrapUnitPrice).toFixed(2); // 四舍五入保留两位小数
      },
      calculateTotalMaterialCost(row) {
        const blankFee = parseFloat(this.calculateBlankFee(row));
        const scrapFee = parseFloat(this.calculateScrapFee(row));
        return (blankFee + scrapFee).toFixed(2); // 四舍五入保留两位小数
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
            roleName: this.dataForm.materialName
          }
        };

        try {
          const res = await this.$http.get('/raw-materials/list', params);
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
  .el-collapse {
    margin-left: 20px;
  }
</style>