<template>

    <div ref="chartRef" :class="className" :style="{height:height,width:width}"/>

</template>

<script name="chart_pie" setup>
import * as echarts from 'echarts'
import {merge} from "lodash";
import {formatNumbToRMB} from "@/utils/";
import {tryOnUnmounted, useEventListener, useTimeoutFn} from "@vueuse/core";

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
        default: '450px'
    },
    optionData: {
        //数据
        type: Object,
        default: null
    },
    initOptions: {
        //参数配置
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
        trigger: 'item',

        formatter: function (num) {

            return num.name + ':' + formatNumbToRMB(num.value, 0) + '(' + num.percent + '%)'
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
        bottom: '0',
        left: 'center',
        type: 'scroll',
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

    },

    series: [{
        type: 'pie',
        top: -10,
        radius: '60%',
        center: ['50%', '60%'],
        height: '95%',
        label: {
            formatter: function (num) {

                return '{name|' + num.name + '}\n' +
                    '{data|' + formatNumbToRMB(num.value, 0) + '(' + num.percent + '%)}'
            },
            alignTo: 'edge',
            minMargin: 5,
            edgeDistance: 10,
            lineHeight: 15,
            rich: {
                name: {
                    fontSize: 12,
                    fontWeight: 'bold',
                },
                data: {
                    fontSize: 10,
                    color: '#999'
                }
            },

        },
        labelLine: {
            length: 15,
            length2: 0,
            maxSurfaceAngle: 80
        },
        labelLayout: function (params) {
            const isLeft = params.labelRect.x < params.labelRect.width / 2;

            const points = params.labelLinePoints;
            // Update the end point.
            points[2][0] = isLeft
                ? params.labelRect.x
                : params.labelRect.x + params.labelRect.width;
            return {
                labelLinePoints: points
            };
        },
        itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
        }


    }]
})

const optionDataNow = computed(() => {

    let clonedOptionData = JSON.parse(JSON.stringify(props.optionData));
    return merge({}, optionData_default.value, clonedOptionData);// 合并配置对象
});


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
    // console.log(chart.value)
    if (!chart.value) return;
    //chart.value.dispose();
    chart.value = null;
});

</script>
