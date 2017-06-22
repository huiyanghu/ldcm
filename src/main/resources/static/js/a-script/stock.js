//颜色值
var colorArr = ['#45c8dc','#988fd8','#24c5a4','#ffc941','#988fd8','#717af6','#ff9242','#84d9e7'];
//曲线图&柱状图
function chart(obj,dataJson){
    obj.highcharts('StockChart', {
        chart: {
            alignTicks: false
        },
        colors:colorArr,
        title: {
            text: '',
            style: {
                display:'none'
            }
        },
        navigator :{
            enabled: false
        },
        yAxis: {
            opposite: false
        },
        legend: {
            enabled: true,
            align: 'left',
            verticalAlign: 'top',
            symbolHeight: 12,
            symbolWidth: 12,
            symbolRadius: 2,
            itemStyle:{
                "fontWeight":"400"
            },
            x: 0,
            y: 0
        },
        rangeSelector : {
            height:0,
            allButtonsEnabled: true,
            buttonTheme:{
                style:{
                    display:'none'
                }
            },
            labelStyle: {
                display:'none',
                width:0
            },
            selected: 0,
            inputEnabled:false
        },
        series: dataJson
    });
}
