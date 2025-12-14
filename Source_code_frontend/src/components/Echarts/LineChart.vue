<template>

    <div ref="chartRef" :class="className" :style="{height:height,width:width}"/>

</template>

<script name="chart_line" setup>
import * as echarts from 'echarts'
import {merge} from "lodash";
import {formatNumbToRMB} from "@/utils/";
import {useEventListener, tryOnUnmounted, useTimeoutFn, templateRef} from "@vueuse/core";

import('echarts/theme/dark') // echarts theme


const props = defineProps({
    className: {
        type: String,
        default: 'chart'
    },
    width: {
        type: String,
        default: '100%'
    },
    height: {
        type: String,
        default: '550px'
    },
    optionData: {
        type: Object,
        default: null
    },
    initOptions: {
        type: Object,
        default: {locale: 'ZH'}
    },

    clickParams: null
});
let chart = ref({});
let chartRef = ref(null);
const chartTemp = ref(null)
const emits = defineEmits(['update:clickParams'])
const optionData_default = ref({
    title: {
        left: 'center',
        textStyle: {
            fontSize: 14,
            color: '#0543BC',
            fontWeight: "bold",
            textBorderColor: "rgba(145,204,117)",
            textBorderWidth: 0.5,
            textBorderType: "solid",
            textShadowColor: "rgba(145,204,117)",
            textShadowBlur: 1,
            textShadowOffsetX: 1,
            textShadowOffsetY: 0.5
        },
        subtextStyle: {
            fontSize: 12,
            color: '#999'
        },
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            crossStyle: {
                color: '#999'
            }
        },
        axisLabel: {
            formatter: '{value}'
        },

    },
    grid: {
        left: 10,
        right: 30,
        containLabel: true,
    },
    toolbox: {
        feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            saveAsImage: {show: true}
        }


    },
    legend: {
        itemStyle: {
            shadowBlur: 1.5
        },
        icon: "pin",
        tooltip: {
            show: true
        },
        left: 10
    },
    yAxis: [{}],
    xAxis: [
        {
            type: 'category',
            axisLabel: {
                formatter: '{value}'
            },
            nameLocation: "middle",
            nameGap: 23,

            nameTextStyle: {
                color: "#999",
                fontWeight: "bold"
            },
            axisPointer: {
                type: 'shadow',
            },
            boundaryGap: true,
            axisTick: {
                alignWithLabel: true
            },
            splitLine: {
                show: true
            },


        }
    ],
    series: []

})
const optionDataNow = computed(() => {
    //
    //
    let clonedOptionData = JSON.parse(JSON.stringify(props.optionData));
    if (clonedOptionData.series) {
        clonedOptionData.series.forEach((item, i) => {
            clonedOptionData.series[i] = merge({}, series_bar, clonedOptionData.series[i]);
        })
    }
    if (clonedOptionData.yAxis) {
        clonedOptionData.yAxis.forEach((item, i) => {
            clonedOptionData.yAxis[i] = merge({}, yAxis_bar, clonedOptionData.yAxis[i]);
        })
    }
    return merge({}, optionData_default.value, clonedOptionData);//
});

//

watch(() => optionDataNow.value, () => {
    // Only call init if the optionDataNow value has changed significantly
    if (hasSignificantChange(optionDataNow.value)) {
        init();
    }
}, {deep: true});
const lastAppliedOptions = ref(null);
function hasSignificantChange(newOptions) {
    // Implement logic to check if there's a significant change in options
    // For simplicity, here we just compare the stringified versions
    return JSON.stringify(newOptions) !== JSON.stringify(lastAppliedOptions.value);
}


const series_bar = {
    animationEasing: "linear",
    emphasis: {
        focus: 'series',
        blurScope: 'coordinateSystem'
    },
    itemStyle: {
        borderRadius: [5, 5, 0, 0]
    },
    label: {

        show: true,
        position: 'top',
        formatter: function (num) {
            return formatNumbToRMB(num.value, 0);
        }

        , fontSize: 12
    },
    markLine: {
        symbol: 'none',
        data: [
            {type: 'average', name: '平均值'}
        ]
    },
    smooth: true,

}
const yAxis_bar = {
    type: 'value',
    axisLabel: {
        formatter: '{value}'
    },

    nameTextStyle: {
        color: "#999",
        fontWeight: "bold"
    },
    axisTick: {
        show: true
    },
}

function init() {

    lastAppliedOptions.value = optionDataNow.value; // Store the last applied options



        // 初始化
        if (chart.value[chartTemp.value]) {
            chart.value[chartTemp.value].dispose();
            chart.value[chartTemp.value] = null;
        }

    chart.value[chartTemp.value] = markRaw(echarts.init(
            chartRef.value,
            null,
            props.initOptions
        )
    );
    if (chart.value[chartTemp.value]) {


        chart.value[chartTemp.value].on('click', function (params) {

            emits("update:clickParams", params)
        })
    }

    if (optionDataNow.value) {
        chart.value[chartTemp.value].setOption(optionDataNow.value);
    }
}


onMounted(() => {

    chartTemp.value = Math.random();

    nextTick(() => {

        init();

        useEventListener("resize", () => {
            if (!chart.value || !chart.value[chartTemp.value]) return;
            useTimeoutFn(() => {
                chart.value[chartTemp.value].resize();
            }, 180);
        });
    });
});

tryOnUnmounted(() => {
    //console.log(chart.value)
    if (!chart.value) return;
    //chart.value.dispose();
    chart.value = null;
});

</script>
