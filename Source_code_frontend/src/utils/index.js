/**
 *
 * VUE的过滤过程功能,
 * 直接在EL的表格中使用,使用方法参考formatNumbToRMB
 *
 */


/*数字转千分位 货币 金额 显示
* num,   金额
* precision,  小数位数
 * separator  分隔器样式
* */
export function formatNumbToRMB(num, precision, separator) {
    var parts;
    // 判断是否为数字
    if (!isNaN(parseFloat(num)) && isFinite(num)) {
        // 把类似 .5, 5. 之类的数据转化成0.5, 5, 为数据精度处理做准, 至于为什么
        // 不在判断中直接写 if (!isNaN(num = parseFloat(num)) && isFinite(num))
        // 是因为parseFloat有一个奇怪的精度问题, 比如 parseFloat(12312312.1234567119)
        // 的值变成了 12312312.123456713
        num = Number(num);
        // 处理小数点位数
        if (!precision) precision = 2//默认保留两位,因为线上支付有可能充值是两位
        num = (typeof precision !== 'undefined' ? num.toFixed(precision) : num).toString();
        // 分离数字的小数部分和整数部分
        parts = num.split('.');
        // 整数部分加[separator]分隔, 借用一个著名的正则表达式
        parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','));

        return parts.join('.');
    }
    return '';
}

function pluralize(time, label) {
    if (time === 1) {
        return time + label
    }
    return time + label + 's'
}

export function timeAgo(time) {
    const between = Date.now() / 1000 - Number(time)
    if (between < 3600) {
        return pluralize(~~(between / 60), ' minute')
    } else if (between < 86400) {
        return pluralize(~~(between / 3600), ' hour')
    } else {
        return pluralize(~~(between / 86400), ' day')
    }
}

/*格式化时间*/
export function parseTime(time, cFormat) {
    //if (!(time > 0)||typeof time === 'undefined') {
        if (typeof time === 'undefined' || time === null) {

       // console.log(time > 0)
        return null
    }
    if (arguments.length === 0) {
        return null
    }
    const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
    let date
    if (typeof time === 'object') {
        date = time
    } else if (typeof time === 'number') {
        //时间戳转日期格式
        if (('' + time).length === 10) time = parseInt(time) * 1000
        date = new Date(parseInt(time));
    } else {
       // console.log(('' + time))
        //console.log(('' + time).length)
        //文本转日期格式
        if (('' + time).length === 10 || ('' + time).length === 19) {
            date = new Date(time)
        }
    }

   // console.log(date)
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    }
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key]
        if (key === 'a') return ['一', '二', '三', '四', '五', '六', '日'][value - 1]
        if (result.length > 0 && value < 10) {
            value = '0' + value
        }
        return value || 0
    })
    return time_str
}

export function formatTime(time, option) {
    time = +time * 1000
    const d = new Date(time)
    const now = Date.now()

    const diff = (now - d) / 1000

    if (diff < 30) {
        return 'just now'
    } else if (diff < 3600) { // less 1 hour
        return Math.ceil(diff / 60) + 'Minutes ago'
    } else if (diff < 3600 * 24) {
        return Math.ceil(diff / 3600) + 'Hours ago'
    } else if (diff < 3600 * 24 * 2) {
        return '1 day ago'
    } else {
        return Math.ceil(diff / (3600 * 24)) + 'day'
    }
}


export function getCheckboxItem(filterName, jsonData) {
    let data = ''
    jsonData.map(v => {
        if (data == "") {
            data = v[filterName]
        } else {
            data = data + "`" + v[filterName]
        }
    })
    return data
};


//获取URL中的参数
export function getQueryObject(url) {
    url = url == null ? window.location.href : url
    const search = url.substring(url.lastIndexOf('?') + 1)
    const obj = {}
    const reg = /([^?&=]+)=([^?&=]*)/g
    search.replace(reg, (rs, $1, $2) => {
        const name = decodeURIComponent($1)
        let val = decodeURIComponent($2)
        val = String(val)
        obj[name] = val
        return rs
    })
    return obj
}

/**
 *get getByteLen
 * @param {Sting} val input value
 * @returns {number} output value
 */
export function getByteLen(val) {
    let len = 0
    for (let i = 0; i < val.length; i++) {
        if (val[i].match(/[^\x00-\xff]/ig) != null) {
            len += 1
        } else {
            len += 0.5
        }
    }
    return Math.floor(len)
}

export function cleanArray(actual) {
    const newArray = []
    for (let i = 0; i < actual.length; i++) {
        if (actual[i]) {
            newArray.push(actual[i])
        }
    }
    return newArray
}

export function param(json) {
    if (!json) return ''
    return cleanArray(Object.keys(json).map(key => {
        if (json[key] === undefined) return ''
        return encodeURIComponent(key) + '=' +
            encodeURIComponent(json[key])
    })).join('&')
}

export function param2Obj(url) {
    const search = url.split('?')[1]
    if (!search) {
        return {}
    }
    return JSON.parse('{"' + decodeURIComponent(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"') + '"}')
}


/* 科学数字 格式化*/
export function nFormatter(num, digits) {
    const si = [
        {value: 1E18, symbol: 'E'},
        {value: 1E15, symbol: 'P'},
        {value: 1E12, symbol: 'T'},
        {value: 1E9, symbol: 'G'},
        {value: 1E6, symbol: 'M'},
        {value: 1E3, symbol: 'k'}
    ]
    for (let i = 0; i < si.length; i++) {
        if (num >= si[i].value) {
            return (num / si[i].value + 0.1).toFixed(digits).replace(/\.0+$|(\.[0-9]*[1-9])0+$/, '$1') + si[i].symbol
        }
    }
    return num.toString()
}


export function html2Text(val) {
    const div = document.createElement('div')
    div.innerHTML = val
    return div.textContent || div.innerText
}


export function toThousandslsFilter(num) {
    return (+num || 0).toString().replace(/^-?\d+/g, m => m.replace(/(?=(?!\b)(\d{3})+$)/g, ','))
}


export function objectMerge(target, source) {
    /* Merges two  objects,
       giving the last one precedence */

    if (typeof target !== 'object') {
        target = {}
    }
    if (Array.isArray(source)) {
        return source.slice()
    }
    for (const property in source) {
        if (source.hasOwnProperty(property)) {
            const sourceProperty = source[property]
            if (typeof sourceProperty === 'object') {
                target[property] = objectMerge(target[property], sourceProperty)
                continue
            }
            target[property] = sourceProperty
        }
    }
    return target
}

export function scrollTo(element, to, duration) {
    if (duration <= 0) return
    const difference = to - element.scrollTop
    const perTick = difference / duration * 10
    setTimeout(() => {
        //console.log(new Date())
        element.scrollTop = element.scrollTop + perTick
        if (element.scrollTop === to) return
        scrollTo(element, to, duration - 10)
    }, 10)
}

export function toggleClass(element, className) {
    if (!element || !className) {
        return
    }
    let classString = element.className
    const nameIndex = classString.indexOf(className)
    if (nameIndex === -1) {
        classString += '' + className
    } else {
        classString = classString.substr(0, nameIndex) + classString.substr(nameIndex + className.length)
    }
    element.className = classString
}



export const pickerOptions = {
    shortcuts: [{
        text: 'Last week',
        onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
        }
    }, {
        text: 'Last month',
        onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
        }
    }, {
        text: 'In the past three months',
        onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
        }
    }]
}

export function getTime(type) {
    if (type === 'start') {
        return new Date().getTime() - 3600 * 1000 * 24 * 90
    } else {
        return new Date(new Date().toDateString())
    }
}

export function debounce(func, wait, immediate) {
    let timeout, args, context, timestamp, result

    const later = function () {
        // 据上一次触发时间间隔
        const last = +new Date() - timestamp

        // 上次被包装函数被调用时间间隔last小于设定时间间隔wait
        if (last < wait && last > 0) {
            timeout = setTimeout(later, wait - last)
        } else {
            timeout = null
            // 如果设定为immediate===true，因为开始边界已经调用过了此处无需调用
            if (!immediate) {
                result = func.apply(context, args)
                if (!timeout) context = args = null
            }
        }
    }

    return function (...args) {
        context = this
        timestamp = +new Date()
        const callNow = immediate && !timeout
        // 如果延时不存在，重新设定延时
        if (!timeout) timeout = setTimeout(later, wait)
        if (callNow) {
            result = func.apply(context, args)
            context = args = null
        }

        return result
    }
}

export function deepClone(source) {
    if (!source && typeof source !== 'object') {
        throw new Error('error arguments', 'shallowClone')
    }
    const targetObj = source.constructor === Array ? [] : {}
    for (const keys in source) {
        if (source.hasOwnProperty(keys)) {
            if (source[keys] && typeof source[keys] === 'object') {
                targetObj[keys] = source[keys].constructor === Array ? [] : {}
                targetObj[keys] = deepClone(source[keys])
            } else {
                targetObj[keys] = source[keys]
            }
        }
    }
    return targetObj
}


//加法
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
export function accAdd(arg1, arg2) {
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2))
    /*let return_dd = 0
    return_dd = (arg1 * m + arg2 * m) / m
    console.log(arg1)
    console.log(arg2)
    console.log(m)
    console.log(return_dd)
    if (!return_dd) return_dd = 0*/

    let temp_num = (arg1 * m + arg2 * m) / m
    //toFixed这个是四舍六入五取偶
    return Number(temp_num.toFixed(2).toString())//保留一位后输出,计算的值,只保留一位
}

/*
//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg){
    return accAdd(arg,this);
}

*/


//减法
//说明：javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。
//调用：accSub(arg1,arg2)
//返回值：arg1减上arg2的精确结果
export function accSub(arg1, arg2) {
    /*console.log("---".arg2)
    let aa=-arg2
    console.log("---".aa)*/
    return accAdd(arg1, -arg2);
}

/*//给Number类型增加一个sub方法，调用起来更加方便。
Number.prototype.sub = function (arg){
    return accSub(this,arg);
}*/


//乘法
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
export function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length
    } catch (e) {
    }
    let temp_num = Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    return Number(temp_num.toFixed(1).toString())//保留一位后输出,计算的值,只保留一位,便于用毛支付,不用分
}

/*//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg){
    return accMul(arg, this);
}*/


//除法
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//小数位数 默认1
//返回值：arg1除以arg2的精确结果
//
//TODO ?? with在这个ES6严格模式下不能使用
export function accDiv(arg1, arg2, precision) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length
    } catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length
    } catch (e) {
    }
    //with (Math) {
    r1 = Number(arg1.toString().replace(".", ""))
    r2 = Number(arg2.toString().replace(".", ""))
    let temp_num = (r1 / r2) * Math.pow(10, t2 - t1);


    let precision_t = 3

    //console.log(precision)
    if (precision != undefined) precision_t = precision
    return Number(temp_num.toFixed(precision_t).toString())//保留一位后输出
    // }
}

/*//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg){
    return accDiv(this, arg);
} */


/*

*将数据分组
* array   待分组数据
* groupByName       分组的值
*
* 返回格式    array{
*           groupInfo:{
*              x1:string,
*              x2:string,
*              x3:string,
*           },
*           childInfo:{
*           0:array,
*           1:array,
*           2:array,
*           }
* }
*
* 引用方式
*
*                         const sorted = groupBy(array, function (item) {
                            return  {
                            这里是要分组的数据名称
                                'x1':item.x1,
                                'x2':item.x2,
                                'x3':item.x3,
                            };
                        });

*
*
*                          console.log(  sorted[0]);	//a b c d);	//a b c d
                         console.log(  sorted[0].groupInfo.group_ch);	//a b c d);	//a b c d

*
*
*
* */
export function groupBy(array, groupByName) {
    //debugger;//设置断点
    const groups = {};
    array.forEach(function (o) {

        // 分组值序列化,引用时要JSON.parse反序列成数组,将o发给调用的地方,返回O里面对应的值
        const groupByName_value = JSON.stringify(groupByName(o));


        groups[groupByName_value] = groups[groupByName_value] || [];
        groups[groupByName_value].push(o);//添加值
    });
    // console.log(groups)
    return Object.keys(groups).map(function (groupByName_value) {
        let groupInfo = (JSON.parse(groupByName_value))//反序列成数组,组信息
        const dd = {
            groupInfo,
            'childInfo': groups[groupByName_value],//子内容信息
        }
        return dd;
    });
}

//输入的时间是'XXXX/3/11 12:00'这种格式
export const compareTime = (gettime) => {
    var today = new Date()  //获取当前时间
    gettime = gettime.replace(/-/g, '/');
    //转化成时间戳作比较
    var endTime = new Date(gettime) //自己的时间
    if (today.getTime() > endTime.getTime()) {
        //当前时间大于我的时间
        return "超时"
    } else {
        //当前时间小于我的时间
        return ""
    }
}
