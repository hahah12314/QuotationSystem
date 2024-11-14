<template>
    <div>
        
        <el-collapse v-model="activeNames" @change="handleChange" label-position="left" inline
            class="demo-table-expand">
            <el-collapse-item title="原材料信息" name="1">
                <el-table :data="[row]" style="width: 100%">
                    <el-table-column prop="specification" label="材料规格"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                    <el-table-column prop="updateTime" label="更新时间"></el-table-column>
                    <el-table-column prop="nakedPrice" label="裸价"></el-table-column>
                    <el-table-column prop="profit" label="利润"></el-table-column>
                    <el-table-column prop="miscellaneousFees" label="杂费"></el-table-column>
                </el-table>
            </el-collapse-item>

            <el-collapse-item title="原材料费用" name="2">
                <el-table :data="[row]" style="width: 100%">
                    <el-table-column prop="length" label="原材料长度"></el-table-column>
                    <el-table-column prop="width" label="宽度"></el-table-column>
                    <el-table-column prop="height" label="高度"></el-table-column>
                    <el-table-column prop="blankWeight" label="毛坯重量"></el-table-column>
                    <el-table-column prop="materialUnitPrice" label="材料单价"></el-table-column>
                    <el-table-column prop="blankFee" label="毛坯费用"></el-table-column>
                    <el-table-column prop="scrapWeight" label="废料重量"></el-table-column>
                    <el-table-column prop="scrapUnitPrice" label="废料单价"></el-table-column>
                    <el-table-column prop="scrapFee" label="废料费用"></el-table-column>
                    <el-table-column prop="totalMaterialCost" label="总材料成本"></el-table-column>
                </el-table>
            </el-collapse-item>

            <el-collapse-item title="数割费用" name="3">
                <el-table :data="[row]" style="width: 100%">
                    <el-table-column prop="numCut" label="数割(M)" width="180"></el-table-column>
                    <el-table-column prop="cutFee" label="下料费"></el-table-column>
                    <el-table-column prop="totalCutFee" label="费用小计"></el-table-column>
                </el-table>
            </el-collapse-item>

            <el-collapse-item title="单件加工费用(元)" name="4">
                <el-table :data="[row]" style="width: 100%">
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

            <el-collapse-item title="表面处理单件费用(元)" name="5">
                <el-table :data="[row]" style="width: 100%">
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

            <el-collapse-item title="喷涂单件费用" name="6">
                <el-table :data="[row]" style="width: 100%">
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
    </div>
</template>

<script>
    export default {
        props: {
            row: {
                type: Object,
                required: true,
            },
        },
        data() {
            return {
                activeNames: [],
            };
        },
        methods: {
            handleChange(value) {
                this.activeNames = value;
            },
        },
    };
</script>

<style scoped>
    .demo-table-expand {
        margin: 10px 0;
    }
</style>