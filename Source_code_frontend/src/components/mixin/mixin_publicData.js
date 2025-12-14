export const pickerRangOptions = {
    /*disabledDate(time) {
        //今日以后的日期不可选
        return time.getTime()  > Date.now();
    },*/
    shortcuts: [{
        text: 'Last week',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            return [start, end]
        }
    }, {
        text: 'Last month',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            return [start, end]
        }
    }, {
        text: 'In the past three months',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            return [start, end]
        }
    }, {
        text: 'Last year',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
            return [start, end]
        }
    }]
}
//日期范围选择时的快捷项
export const pickerRangNoAfterOptions = {
    disabledDate(time) {
        //今日以后的日期不可选

        let day = new Date().toLocaleDateString()//取日期格式 2019/03/09
        let day_int = new Date(day + ' 23:59:59').getTime()//当天24点以前
        return time.getTime() > day_int;
    },
    shortcuts: [{
        text: 'Last week',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            return [start, end]
        }
    }, {
        text: 'Last month',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            return [start, end]
        }
    }, {
        text: 'In the past three months',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            return [start, end]
        }
    }, {
        text: 'Last year',
        value: () => {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
            return [start, end]
        }
    }]
}
//今日以后的日期不可选
export const pickerDayNoAfterOptions = {
    disabledDate(time) {
        //今日以后的日期不可选

        let day = new Date().toLocaleDateString()//取日期格式 2019/03/09
        let day_int = new Date(day + ' 23:59:59').getTime()//当天24点以前
        return time.getTime() > day_int;
    },
    shortcuts: [{
        text: 'Today',
        value: () => {
            return new Date()
        }
    }, {
        text: 'Yesterday',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            //console.log(date)
            return date
        }
    }, {
        text: 'a week ago',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            return date
        }
    }]
}
//今日以后的日期可选
export const pickerDayUseAfterOptions = {
    /*disabledDate(time) {
        //今日以后的日期不可选
        return time.getTime() > Date.now();
    },*/
    shortcuts: [{
        text: 'Today',
        value: () => {
            return new Date()
        }
    }, {
        text: 'Yesterday',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            return date
        }
    }, {
        text: 'a week ago',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            return date
        }
    }]
}
//今日以前的日期不可选
export const pickerDayNobefoOptions = {
    disabledDate(time) {
                //今日以前的日期不可选
        return time.getTime() <Date.now()-86400*1000;
    },
    shortcuts: [{
        text: 'Today',
        onClick(picker) {
            picker.$emit('pick', new Date());
        }
    }, {
        text: 'Yesterday',
        onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
        }
    }, {
        text: 'a week ago',
        onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
        }
    }]
}


