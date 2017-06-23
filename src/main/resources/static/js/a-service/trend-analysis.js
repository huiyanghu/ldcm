$('.ui-select').ui_select();
$('.time-group a').click(function(){
	$(this).addClass('active').siblings().removeClass('active');
	var flag = Number($(this).attr('value'));
	refreshByDate(flag);
});
function refreshByDate(flag){
	getTotalResult(flag);
	getChapterGeneral(flag);
	getChapterByType(flag);
	getChapterByApp(flag);
}
refreshByDate(0);
//数据总览
function getTotalResult(flag){
	$.ajax({
		url:"../statArrive/getTotalResult",
		type:"get",
		data:{flag:flag},
		dataType:"json",
		success:function(data){
			if(data.isSuccess == 1){
				if(data.result.count_total == '' || data.result.count_total == null || data.result.count_total == undefined){
					$('#count_total').html('暂时还没有数据').addClass('small-text');
				}else{
					$('#count_total').html(data.result.count_total).removeClass('small-text');
				}
				if(data.result.count_pass == '' || data.result.count_pass == null || data.result.count_pass == undefined){
					$('#count_pass').html('暂时还没有数据').addClass('small-text');
				}else{
					$('#count_pass').html(data.result.count_pass).removeClass('small-text');
				}
				if(data.result.count_delete == '' || data.result.count_delete == null || data.result.count_delete == undefined){
					$('#count_delete').html('暂时还没有数据').addClass('small-text');
				}else{
					$('#count_delete').html(data.result.count_delete).removeClass('small-text');
				}
				if(data.result.count_review == '' || data.result.count_review == null || data.result.count_review == undefined){
					$('#count_review').html('暂时还没有数据').addClass('small-text');
				}else{
					$('#count_review').html(data.result.count_review).removeClass('small-text');
				}
			}else{
				noticeAlert('数据获取失败，请重新选择日期以便刷新。','失败','','');
			}
		},
		error:function(error){
			noticeAlert('网络出错，请重新连接网络。','错误！','','');
		}
	});
}
//总图
function getChapterGeneral(flag){
	if(flag == 0 || flag == -1){
		var dataType = [['hour', [1]]];
	}else{
		var dataType = [['day', [1]]];
	}
	loadMask.loadStart($('#general'));
	$.ajax({
		url:"../statArrive/getChapterGeneral",
		type:"get",
		data:{flag:flag},
		dataType:"json",
		success:function(data){
			if(data.isSuccess == 1){
				loadMask.loadEnd($('#general'));
				if($.isEmptyObject(data.result)){
					console.log('no data');
				}else{
					var total = data.result.total;
					var pass = data.result.pass;
					var diagramJson = [
						{
							name:'内容接入量',
							data:total,
							dataGrouping:{
								dateTimeLabelFormats:{
									units: dataType
								}
							}
						},{
							name:'已审核量',
							data:pass,
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
							name:'内容接入量',
							data:total,
							dataGrouping:{
								dateTimeLabelFormats:{
									units: dataType
								}
							}
						},{
							type: 'column',
							name:'已审核量',
							data:pass,
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
		url:"../statArrive/getChapterByType",
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
					var total = data.result.total;
					var pass = data.result.pass;
					var histogramJson1 = [];
					var histogramJson2 = [];
					$.each(type,function(i,item){
						var groupJson = {};
						groupJson.type='column';
						groupJson.name = item;
						groupJson.data = total[i];
						groupJson.dataGrouping = dataGroupingJson;
						histogramJson1.push(groupJson);
					});
					$.each(type,function(i,item){
						var groupJson = {};
						groupJson.type='column';
						groupJson.name = item;
						groupJson.data = pass[i];
						groupJson.dataGrouping = dataGroupingJson;
						histogramJson2.push(groupJson);
					});
					chart($('#chart3'),histogramJson1);
					chart($('#chart4'),histogramJson2);
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
		url:"../statArrive/getChapterByApp",
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
					var total = data.result.total;
					var pass = data.result.pass;
					var histogramJson1 = [];
					var histogramJson2 = [];
					$.each(app,function(i,item){
						var groupJson = {};
						groupJson.type='column';
						groupJson.name = item;
						groupJson.data = total[i];
						groupJson.dataGrouping = dataGroupingJson;
						histogramJson1.push(groupJson);
					});
					$.each(app,function(i,item){
						var groupJson = {};
						groupJson.type='column';
						groupJson.name = item;
						groupJson.data = pass[i];
						groupJson.dataGrouping = dataGroupingJson;
						histogramJson2.push(groupJson);
					});
					chart($('#chart5'),histogramJson1);
					chart($('#chart6'),histogramJson2);
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

