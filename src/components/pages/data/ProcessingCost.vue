<template>
  <el-card class="box-card">
    <div id="processingCost">
      <!-- 查询和操作按钮 -->
      <el-form :inline="true" :model="dataForm" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="dataForm.specification" placeholder="材料规格" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog">新增</el-button>
        </el-form-item>
<!--        <el-form-item>-->
<!--          <el-button type="danger" @click="deleteBatch()">批量删除</el-button>-->
<!--        </el-form-item>-->
      </el-form>

      <!-- 数据表格 -->
      <el-table :data="dataList" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="55"></el-table-column>
       
        <el-table-column prop="specification" label="材料规格" width="130"></el-table-column>
        <el-table-column prop="sawTime" label="锯切时间"></el-table-column>
        <el-table-column prop="sawFee" label="锯切费用"></el-table-column>
        <el-table-column prop="bendTime" label="弯曲时间"></el-table-column>
        <el-table-column prop="bendFee" label="弯曲费用"></el-table-column>
        <el-table-column prop="drillTime" label="钻孔时间"></el-table-column>
        <el-table-column prop="drillFee" label="钻孔费用"></el-table-column>
        <el-table-column prop="turnTime" label="车削时间"></el-table-column>
        <el-table-column prop="turnFee" label="车削费用"></el-table-column>
        <el-table-column prop="grindTime" label="磨削时间"></el-table-column>
        <el-table-column prop="grindFee" label="磨削费用"></el-table-column>
        <el-table-column prop="millTime" label="铣削时间"></el-table-column>
        <el-table-column prop="millFee" label="铣削费用"></el-table-column>
        <el-table-column prop="calibrateTime" label="校正时间"></el-table-column>
        <el-table-column prop="calibrateFee" label="校正费用"></el-table-column>
        <el-table-column prop="boreMillTime" label="镗铣时间"></el-table-column>
        <el-table-column prop="boreMillFee" label="镗铣费用"></el-table-column>
        <el-table-column prop="weldTime" label="焊接时间"></el-table-column>
        <el-table-column prop="weldFee" label="焊接费用"></el-table-column>
        <el-table-column prop="polishTime" label="抛光时间"></el-table-column>
        <el-table-column prop="polishFee" label="抛光费用"></el-table-column>
        <el-table-column prop="assembleTime" label="组装时间"></el-table-column>
        <el-table-column prop="assembleFee" label="组装费用"></el-table-column>
        <el-table-column prop="totalProcessCost" label="总加工费用"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          style="margin-top: 20px"
      ></el-pagination>
    </div>

    <!-- 弹出框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="60%">
      <el-form :model="dataDialogForm" ref="ruleForm" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Detail ID" prop="detailId">
              <el-input v-model="dataDialogForm.detailId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="材料规格" prop="specification">
              <el-input v-model="dataDialogForm.specification"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="锯切时间" prop="sawTime">
              <el-input v-model="dataDialogForm.sawTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="锯切费用" prop="sawFee">
              <el-input v-model="dataDialogForm.sawFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="弯曲时间" prop="bendTime">
              <el-input v-model="dataDialogForm.bendTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="弯曲费用" prop="bendFee">
              <el-input v-model="dataDialogForm.bendFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="钻孔时间" prop="drillTime">
              <el-input v-model="dataDialogForm.drillTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="钻孔费用" prop="drillFee">
              <el-input v-model="dataDialogForm.drillFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车削时间" prop="turnTime">
              <el-input v-model="dataDialogForm.turnTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车削费用" prop="turnFee">
              <el-input v-model="dataDialogForm.turnFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="磨削时间" prop="grindTime">
              <el-input v-model="dataDialogForm.grindTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="磨削费用" prop="grindFee">
              <el-input v-model="dataDialogForm.grindFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="铣削时间" prop="millTime">
              <el-input v-model="dataDialogForm.millTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="铣削费用" prop="millFee">
              <el-input v-model="dataDialogForm.millFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="校正时间" prop="calibrateTime">
              <el-input v-model="dataDialogForm.calibrateTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="校正费用" prop="calibrateFee">
              <el-input v-model="dataDialogForm.calibrateFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="镗铣时间" prop="boreMillTime">
              <el-input v-model="dataDialogForm.boreMillTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="镗铣费用" prop="boreMillFee">
              <el-input v-model="dataDialogForm.boreMillFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="焊接时间" prop="weldTime">
              <el-input v-model="dataDialogForm.weldTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="焊接费用" prop="weldFee">
              <el-input v-model="dataDialogForm.weldFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="抛光时间" prop="polishTime">
              <el-input v-model="dataDialogForm.polishTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="抛光费用" prop="polishFee">
              <el-input v-model="dataDialogForm.polishFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组装时间" prop="assembleTime">
              <el-input v-model="dataDialogForm.assembleTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组装费用" prop="assembleFee">
              <el-input v-model="dataDialogForm.assembleFee"></el-input>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="总加工费用" prop="totalProcessCost">-->
<!--              <el-input v-model="dataDialogForm.totalProcessCost"></el-input>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      dataForm: {
        specification: '',
      },
      dataDialogForm: {
        id: '', // ID
        detailId: '', // Detail ID
        specification: '', // 材料规格
        sawTime: '', // 锯切时间
        sawFee: '', // 锯切费用
        bendTime: '', // 弯曲时间
        bendFee: '', // 弯曲费用
        drillTime: '', // 钻孔时间
        drillFee: '', // 钻孔费用
        turnTime: '', // 车削时间
        turnFee: '', // 车削费用
        grindTime: '', // 磨削时间
        grindFee: '', // 磨削费用
        millTime: '', // 铣削时间
        millFee: '', // 铣削费用
        calibrateTime: '', // 校正时间
        calibrateFee: '', // 校正费用
        boreMillTime: '', // 镗铣时间
        boreMillFee: '', // 镗铣费用
        weldTime: '', // 焊接时间
        weldFee: '', // 焊接费用
        polishTime: '', // 抛光时间
        polishFee: '', // 抛光费用
        assembleTime: '', // 组装时间
        assembleFee: '', // 组装费用
        totalProcessCost: '', // 总加工费用
        sawWorkhourId: '', // 锯切工时 ID
        bendWorkhourId: '', // 弯曲工时 ID
        drillWorkhourId: '', // 钻孔工时 ID
        turnWorkhourId: '', // 车削工时 ID
        grindWorkhourId: '', // 磨削工时 ID
        millWorkhourId: '', // 铣削工时 ID
        calibrateWorkhourId: '', // 校正工时 ID
        boreMillWorkhourId: '', // 镗铣工时 ID
        weldWorkhourId: '', // 焊接工时 ID
        polishWorkhourId: '', // 抛光工时 ID
        assembleWorkhourId: '', // 组装工时 ID
      },

      dataList: [],
      dialogFormVisible: false,
      pageSize: 5,
      pageIndex: 1,
      totalPage: 0,
      dialogTitle: '',
    };
  },
  methods: {
    async getDataList() {
      try {
        const response = await this.$http.get('/processing-cost/list', {
          params: {
            pageSize: this.pageSize,
            pageIndex: this.pageIndex,
            specification: this.dataForm.specification,
          },
        });
        this.dataList = response.data.data.list;
        this.totalPage = response.data.data.totalCount;
      } catch (error) {
        console.error('获取数据失败:', error);
      }
    },
    openDialog() {
      this.dialogFormVisible = true;
      this.dialogTitle = '新增记录';
      this.dataDialogForm = {
        id: '', // 主键 ID
        detailId: '', // Detail ID
        specification: '', // 材料规格
        sawTime: '', // 锯切时间
        sawFee: '', // 锯切费用
        bendTime: '', // 弯曲时间
        bendFee: '', // 弯曲费用
        drillTime: '', // 钻孔时间
        drillFee: '', // 钻孔费用
        turnTime: '', // 车削时间
        turnFee: '', // 车削费用
        grindTime: '', // 磨削时间
        grindFee: '', // 磨削费用
        millTime: '', // 铣削时间
        millFee: '', // 铣削费用
        calibrateTime: '', // 校正时间
        calibrateFee: '', // 校正费用
        boreMillTime: '', // 镗铣时间
        boreMillFee: '', // 镗铣费用
        weldTime: '', // 焊接时间
        weldFee: '', // 焊接费用
        polishTime: '', // 抛光时间
        polishFee: '', // 抛光费用
        assembleTime: '', // 组装时间
        assembleFee: '', // 组装费用
        totalProcessCost: '', // 总加工费用
        sawWorkhourId: '', // 锯切工时 ID
        bendWorkhourId: '', // 弯曲工时 ID
        drillWorkhourId: '', // 钻孔工时 ID
        turnWorkhourId: '', // 车削工时 ID
        grindWorkhourId: '', // 磨削工时 ID
        millWorkhourId: '', // 铣削工时 ID
        calibrateWorkhourId: '', // 校正工时 ID
        boreMillWorkhourId: '', // 镗铣工时 ID
        weldWorkhourId: '', // 焊接工时 ID
        polishWorkhourId: '', // 抛光工时 ID
        assembleWorkhourId: '', // 组装工时 ID
      };
    },
    handleEdit(row) {
      this.dialogFormVisible = true;
      this.dialogTitle = '编辑记录';
      this.dataDialogForm = { ...row };
    },
    async handleDelete(id) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
          .then(async () => {
            try {
              await this.$http.delete('/processing-cost/delete', { data: { id } });
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
    async submitForm() {
      try {
        if (this.dialogTitle === '新增记录') {
          await this.$http.post('/processing-cost/save', this.dataDialogForm);
        } else {
          await this.$http.post('/processing-cost/update', this.dataDialogForm);
        }
        this.$message.success('操作成功');
        this.dialogFormVisible = false;
        this.getDataList();
      } catch (error) {
        console.error('保存失败:', error);
      }
    },
    sizeChangeHandle(size) {
      this.pageSize = size;
      this.getDataList();
    },
    currentChangeHandle(page) {
      this.pageIndex = page;
      this.getDataList();
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
