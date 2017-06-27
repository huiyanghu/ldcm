$('.ui-select').ui_select();
function setLiToph(){
    var w = $('.pic-list li').width();
    $('.pic-list li .top').css({'height':w,'line-height':w+'px'});
}
window.onresize = function(){
    setLiToph();
}
function getAppList(){
    $.ajax({
        url:"../dataRecord/getAppList",
        type: "get",
        success: function (data){
            if(data.isSuccess == 1){
                var codeAppList = data.result.codeAppList;
                var optionHtml = '<option value="">全部</option>';
                $.each(codeAppList,function(i,item){
                    var appName = item.appName;
                    var id = item.id;
                    optionHtml += '<option value="'+id+'">'+appName+'</option>';
                });
                var appSelect = '<select name="appName" id="appName" class="form-control ui-select" style="width: 100%;">'
                    +optionHtml
                '</select>';
                $('#appNameDiv').html(appSelect);
                $('#appName').ui_select();
            }
        }
    });
}
getAppList();
var currentPaging=1;
var currentPageSize=12;
$('#dataBtn').click(function(){
    getDataList(1);
});
getDataList(1);

function getDataList(page){
    var appName = $('#appName').val();
    var status = $('#status').val();
    var reasonCode = $('#reasonCode').val();
    var reviewType = $('#reviewType').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    var conditionName = $('#conditionName').val();
    var conditionValue = $('#conditionValue').val();
    loadMask.loadStart($('#dataTable'));
    $.ajax({
        url: "../dataRecord/list",
        type: "get",
        dataType:"json",
        data:{
            "dataType":2,
            "page":page,
            "pageSize":currentPageSize,
            "appName":appName,
            "status":status,
            "reasonCode":reasonCode,
            "reviewType":reviewType,
            "startTime":startTime,
            "endTime":endTime,
            "conditionName":conditionName,
            "conditionValue":conditionValue
        },
        success: function (data) {
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#dataTable'));
                var dataRecordPage = data.result.dataRecordPage;
                var paginationJson = {
                    currentPage: dataRecordPage.currentPage,
                    pageSize: dataRecordPage.pageSize,
                    pageCount: dataRecordPage.pageCount,
                    total: dataRecordPage.total
                };
                creat_pagination(paginationJson,'pic');
                var dataList = dataRecordPage.content;
                if(dataList.length == 0){
                    $('.noData').show().siblings('.pic-list').hide();
                }else{
                    $('.pic-list').html('');
                    $.each(dataList,function(i,item){
                        var id = item.id || '';
                        var content = item.data_content || '';
                        var pubDate = item.publish_date.substring(0,item.publish_date.length-5) || '';
                        var status = item.status;
                        if(status == 1){
                            var reviewHtml = '<i value="1" title="通过" class="fa fa-check-circle active"></i>'
                                +'<i value="0" title="不通过" class="fa fa-times-circle"></i>'
                                +'<i value="2"title="不确定" class="fa fa-question-circle"></i>';
                        }else if(status == 0){
                            var reviewHtml = '<i value="1" title="通过" class="fa fa-check-circle"></i>'
                                +'<i value="0" title="不通过" class="fa fa-times-circle active"></i>'
                                +'<i value="2"title="不确定" class="fa fa-question-circle"></i>';
                        }else if(status == 2){
                            var reviewHtml = '<i value="1" title="通过" class="fa fa-check-circle"></i>'
                                +'<i value="0" title="不通过" class="fa fa-times-circle"></i>'
                                +'<i value="2"title="不确定" class="fa fa-question-circle active"></i>';
                        }
                        var reason = item.reason_code;
                        var userIp = item.user_ip || '';
                        var liHtml = '<li>'
                                        +'<div class="top">'
                                            +'<div class="pic-over">'
                                                +'<img src="'+content+'" alt="'+content+'" />'
                                            +'</div>'
                                            +'<div class="bottom">'
                                                +'<div class="bg"></div>'
                                                +'<div class="field">'
                                                    +'<div class="pull-left left-half">数据ID：</div>'
                                                    +'<div class="pull-left">'+id+'</div>'
                                                +'</div>'
                                                +'<div class="field">'
                                                    +'<div class="pull-left left-half">发布时间：</div>'
                                                    +'<div class="pull-left">'+pubDate+'</div>'
                                                +'</div>'
                                                +'<div class="field">'
                                                    +'<div class="pull-left left-half">审核结果：</div>'
                                                    +'<div class="pull-left review-td" id="td'+id+'">'
                                                        +reviewHtml
                                                    +'</div>'
                                                +'</div>'
                                                +'<div class="field">'
                                                    +'<div class="pull-left left-half">原因分析：</div>'
                                                    +'<div class="pull-left">'
                                                        +'<select name="" id="select'+id+'"class="form-control ui-select" style="width: 90%;">'
                                                            +'<option value="0">内容正常</option>'
                                                            +'<option value="7">广告</option>'
                                                            +'<option value="2">色情</option>'
                                                            +'<option value="1">政治</option>'
                                                            +'<option value="3">违法</option>'
                                                            +'<option value="4">违规</option>'
                                                            +'<option value="6">异常行为</option>'
                                                            +'<option value="9">自定义识别</option>'
                                                        +'</select>'
                                                    +'</div>'
                                                +'</div>'
                                            +'</div>'
                                        +'</div>'
                                    +'</li>';
                        $('.pic-list').append(liHtml);
                        if(status == 1){
                            $('#select'+id).ui_select().disable().val(0);
                        }else{
                            $('#select'+id).ui_select().val(reason);
                        }
                        $('#td'+id+' .fa').click(function(){
                            var value = $(this).attr('value');
                            $(this).addClass('active').siblings().removeClass('active');
                            if(value == '1'){
                                $(this).parents('.bottom').find('.ui-select').ui_select().disable().val(0);
                            }else{
                                $(this).parents('.bottom').find('.ui-select').ui_select().enable();
                            }
                            saveChange(id,value);
                        });
                    });
                    setLiToph();
                }
            }else{
                noticeAlert('数据获取失败，请重新搜索。','失败',loadMaskHide,$('#dataTable'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#dataTable'));
        }
    });
}
var reasonCodeJson = [];
function saveChange(id,value){
    var dataJson = {};
    dataJson.id=id;
    dataJson.status=value;
    reasonCodeJson.push(dataJson);
}
function setReasonCodeBatch(){
    $.each(reasonCodeJson,function(i,item){
        var id = item.id;
        item.reason = $('#select'+id).val();
    });
    loadMask.loadStart($('#dataTable'));
    reasonCodeJson = JSON.stringify(reasonCodeJson);
    $.ajax({
        url: "../dataRecord/setReasonCodeBatch",
        type: "post",
        dataType:"json",
        data:{
            "reasonCodeJson":reasonCodeJson
        },
        success: function (data){
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#dataTable'));
                noticeAlert('修改成功。','成功',loadMaskHide,$('#dataTable'));
            }else{
                noticeAlert('修改失败，请重新修改。','失败',loadMaskHide,$('#dataTable'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#dataTable'));
        }
    })
}
$('#submitAudit').click(function(){
    setReasonCodeBatch();
});