<template>
    <div class="customer-analysis">
        <div class="tabs">
            <div v-for="tab in tabs" :key="tab.name" :class="['tab', { active: activeTab === tab.name }]"
                @click="activeTab = tab.name">
                {{ tab.label }}
            </div>
        </div>
        <div class="content">
            <component :is="activeComponent" />
        </div>
    </div>
</template>
<script>
    import CustomerGrowth from "@/components/customer/CustomerGrowth.vue";
    import OrderStats from "@/components/customer/OrderStats.vue";
    import ProfitContribution from "@/components/customer/ProfitContribution.vue";
    import MaterialPreference from "@/components/customer/MaterialPreference.vue";

    export default {
        name: "CustomerAnalysis",
        components: {
            CustomerGrowth,
            OrderStats,
            ProfitContribution,
            MaterialPreference,
        },
        data() {
            return {
                activeTab: "growth",
                tabs: [
                    { label: "客户增长趋势", name: "growth" },
                    { label: "客户订单情况", name: "order-stats" },

                    { label: "客户物料偏好", name: "material-preference" },
                ],

            };
        },
        computed: {
            activeComponent() {
                switch (this.activeTab) {
                    case "growth":
                        return "CustomerGrowth";
                    case "order-stats":
                        return "OrderStats";

                    case "material-preference":
                        return "MaterialPreference";
                    default:
                        return "CustomerGrowth";
                }
            },
        },
    };
</script>
<style scoped>
    .customer-analysis {
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px;
        width: 100%;
    }

    .tabs {
        display: flex;
        justify-content: center;

       
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
    }

    .tab {
        font-size: 16px;
        font-weight: 500;
        color: #333;
        padding: 16px 24px;
        cursor: pointer;
        transition: background-color 0.3s, color 0.3s;
        border-radius: 8px 8px 0 0;
    }

    .tab:hover {
        background-color: #e4f0ff;
    }

    .tab.active {
        color: #409EFF;
        background-color: #e4f0ff;
    }

    .content {
        width: 95%;

        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
   
    }
</style>