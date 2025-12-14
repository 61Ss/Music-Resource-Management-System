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
        default: '650px'
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
        text: ' ',
        subtext: ' Click the text below to jump to the search.',
        x: 'center',

        left: 'center',

        subtextStyle: {
            fontSize: 12,
            color: '#999'
        },
    },

    tooltip: {},
    series: [

        {

            type: 'graph',

            layout: 'force',

            animation: true,

            roam: true,
            label: {
                position: 'right',
                show: true,
                formatter: '{b}'
            },

            labelLayout: {
                hideOverlap: true
            },
            lineStyle: {
                color: 'source',
                curveness: 0.3
            },
            draggable: true,
            focusNodeAdjacency: true,

            force: {
                repulsion: 10,

                 edgeLength: 200,


                 gravity:0.0003
            },

        }]
})
const optionDataNow = computed(() => {

    if (props.optionData.series) {

        props.optionData.series.forEach((item, i) => {
            //console.log(series_bar)
            // console.log(this.optionData.series[i])
            props.optionData.series[i] = merge({}, series_bar, props.optionData.series[i]);
        })
    }
    return merge({}, optionData_default.value, props.optionData);
});


watch(() => props.optionData, () => {
    //console.log('监听到改变')
    if (Object.prototype.toString.call(props.optionData) === "[object Object]") {
        init();
        chart.value[chartTemp.value].setOption(props.optionData);
    }
}, {deep: true})


const series_bar = {}

function init() {


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
            //console.log(params)

            emits("update:clickParams", params)
        })
    }

    if (optionDataNow.value) {
        chart.value[chartTemp.value].setOption(optionDataNow.value);
    }
}


onMounted(() => {
    //页面加载后
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
