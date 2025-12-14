
export  const   tableHeight = getAutoTableHeight()

/*列表页用的功能*/
function getAutoTableHeight() {
//动态调节表格高度
    let tableHeight_temp = 400//默认高度
    /*let aaaaaa=''
      aaaaaa=document*/
    const windows_h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;// 页面显示屏幕高


    const main_header = 100;//  layout的顶部栏高度
    const footer_h = 47//  layout的底部


    tableHeight_temp = windows_h - footer_h - main_header;

    //console.log('表格' + tableHeight_temp)
    return null
}

