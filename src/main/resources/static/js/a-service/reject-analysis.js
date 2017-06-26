$('.ui-select').ui_select();
$('.time-group a').click(function(){
    $(this).addClass('active').siblings().removeClass('active');
    var flag = Number($(this).attr('value'));
    refreshByDate(flag);
});
function refreshByDate(flag){
    getChapterGeneral(flag);
    getChapterByType(flag);
    getChapterByApp(flag);
}
refreshByDate(0);
//总图
function getChapterGeneral(flag){
    if(flag == 0 || flag == -1){
        var dataType = [['hour', [1]]];
    }else{
        var dataType = [['day', [1]]];
    }
    loadMask.loadStart($('#general'));
    $.ajax({
        url:"../statArriveDelete/getChapterGeneral",
        type:"get",
        data:{flag:flag},
        dataType:"json",
        success:function(data){
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#general'));
                if($.isEmptyObject(data.result)){
                    console.log('no data');
                }else{
                    var zz = data.result.zz;
                    var wg = data.result.wg;
                    var sq = data.result.sq;
                    var wf = data.result.wf;
                    var diagramJson = [
                        {
                            name:'色情',
                            data:sq,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        },{
                            name:'政治',
                            data:zz,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        },{
                            name:'违法',
                            data:wf,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        },{
                            name:'违规',
                            data:wg,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        }
                    ];
                    var histogramJson = [
                        {
                            type: 'column',
                            name:'色情',
                            data:sq,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        },{
                            type: 'column',
                            name:'政治',
                            data:zz,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        },{
                            type: 'column',
                            name:'违法',
                            data:wf,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        },{
                            type: 'column',
                            name:'违规',
                            data:wg,
                            dataGrouping:{
                                dateTimeLabelFormats:{
                                    units: dataType
                                }
                            }
                        }
                    ];
                    chart($('#chart1'),diagramJson);
                    chart($('#chart2'),histogramJson);
                }
            }else{
                noticeAlert('数据获取失败，请重新选择日期以便刷新。','失败',loadMaskHide,$('#general'));
            }
        },
        error:function(error){
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#general'));
        }
    });
}
//类型
function getChapterByType(flag){
    if(flag == 0 || flag == -1){
        var dataGroupingJson = {
            units:[['hour', [1]]]
        }
    }else{
        var dataGroupingJson = {
            units:[['day', [1]]]
        }
    }
    loadMask.loadStart($('#mold'));
    $.ajax({
        url:"../statArriveDelete/getChapterByType",
        type:"get",
        data:{flag:flag},
        dataType:"json",
        success:function(data){
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#mold'));
                if($.isEmptyObject(data.result)){
                    console.log('no data');
                }else{
                    var type = data.result.type;
                    var zz = data.result.zz;
                    var wg = data.result.wg;
                    var sq = data.result.sq;
                    var wf = data.result.wf;
                    var histogramJson1 = [];
                    var histogramJson2 = [];
                    var histogramJson3 = [];
                    var histogramJson4 = [];
                    $.each(type,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = sq[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson1.push(groupJson);
                    });
                    $.each(type,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = zz[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson2.push(groupJson);
                    });
                    $.each(type,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = wf[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson3.push(groupJson);
                    });
                    $.each(type,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = wg[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson4.push(groupJson);
                    });
                    chart($('#chart3'),histogramJson1);
                    chart($('#chart4'),histogramJson2);
                    chart($('#chart5'),histogramJson3);
                    chart($('#chart6'),histogramJson4);
                }
            }else{
                noticeAlert('数据获取失败，请重新选择日期以便刷新。','失败',loadMaskHide,$('#mold'));
            }
        },
        error:function(error){
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#mold'));
        }
    });
}
//产品
function getChapterByApp(flag){
    if(flag == 0 || flag == -1){
        var dataGroupingJson = {
            units:[['hour', [1]]]
        }
    }else{
        var dataGroupingJson = {
            units:[['day', [1]]]
        }
    }
    loadMask.loadStart($('#product'));
    $.ajax({
        url:"../statArriveDelete/getChapterByApp",
        type:"get",
        data:{flag:flag},
        dataType:"json",
        success:function(data){
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#product'));
                if($.isEmptyObject(data.result)){
                    console.log('no data');
                }else{
                    var app = data.result.app;
                    var zz = data.result.zz;
                    var wg = data.result.wg;
                    var sq = data.result.sq;
                    var wf = data.result.wf;
                    var histogramJson1 = [];
                    var histogramJson2 = [];
                    var histogramJson3 = [];
                    var histogramJson4 = [];
                    $.each(app,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = sq[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson1.push(groupJson);
                    });
                    $.each(app,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = zz[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson2.push(groupJson);
                    });
                    $.each(app,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = wf[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson3.push(groupJson);
                    });
                    $.each(app,function(i,item){
                        var groupJson = {};
                        groupJson.type='column';
                        groupJson.name = item;
                        groupJson.data = wg[i];
                        groupJson.dataGrouping = dataGroupingJson;
                        histogramJson4.push(groupJson);
                    });
                    chart($('#chart7'),histogramJson1);
                    chart($('#chart8'),histogramJson2);
                    chart($('#chart9'),histogramJson3);
                    chart($('#chart10'),histogramJson4);
                }
            }else{
                noticeAlert('数据获取失败，请重新选择日期以便刷新。','失败',loadMaskHide,$('#product'));
            }
        },
        error:function(error){
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#product'));
        }
    });
}