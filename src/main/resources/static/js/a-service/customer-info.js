$('.ui-select').ui_select();
$("#province").select2({
    allowClear: true
});
$("#city").select2({
    allowClear: true
});
$("#region").select2({
    allowClear: true
});
function getCustomerInfo(){
    loadMask.loadStart($('.box'));
    $.ajax({
        url: "../customer/getCustomerInfo",
        type: "get",
        success: function (data) {
            if(data.isSuccess == 1){
                loadMask.loadEnd($('.box'));
                //公司信息
                var basicInfo = data.result.basicInfo;
                $('#customerName').val(basicInfo.customerName);
                $('#contactsName').val(basicInfo.contactsName);
                $('#contactsMobile').val(basicInfo.contactsMobile);
                $('#approvalTime').val(basicInfo.approvalTime);
                getProvinceData(basicInfo.province);
                getCityData(basicInfo.province,basicInfo.city);
                getRegionData(basicInfo.province, basicInfo.city,basicInfo.region);
                //运营信息
                var operateInfo = data.result.operateInfo;
                if(operateInfo.icon == ''||operateInfo.icon == null || operateInfo.icon == undefined){
                    var src = '/img/operation.jpg'
                }else{
                    var src = operateInfo.icon;
                }
                $('#operatePic').attr('src',src);
                $('#operateName').html(operateInfo.name);
                $('#operatePhone').html(operateInfo.mobile);
                $('#operateWeChat').html(operateInfo.weixin);
                $('#operateOICQ').html(operateInfo.qq);
                //常见信息
                var commonInfo = data.result.commonInfo;
                $('#shaKey').html(commonInfo.shaKey);
                $('#domain').html(commonInfo.domain);
                $('#feedback').html(commonInfo.feedback);
                //已开通服务
                var appList = data.result.appList;
                if(appList.length == 0){
                    $('.noData').show().siblings('table').hide();
                }else{
                    var tHeadHtml = '<tr>'
                                        +'<th>序号</th>'
                                        +'<th>产品ID</th>'
                                        +'<th>产品名称</th>'
                                        +'<th>审核类型</th>'
                                        +'<th>回调地址</th>'
                                        +'<th>描述信息</th>'
                                    +'</tr>';
                    $('#datatHead').html(tHeadHtml);
                    $('#datatBody').html('');
                    $.each(appList,function(i,item){
                        var id = item.id || '';
                        var appName = item.appName || '';
                        var type = item.type;
                        if(type == 1){
                            var typeStr = '文字';
                        }else if(type == 2){
                            var typeStr = '图片';
                        }
                        var feedbackUrl = item.feedbackUrl || '';
                        var description = item.description || '';
                        var tBodyHtml = '<tr>'
                                            +'<td>'+(i+1)+'</td>'
                                            +'<td>'+id+'</td>'
                                            +'<td>'+appName+'</td>'
                                            +'<td>'+typeStr+'</td>'
                                            +'<td>'+feedbackUrl+'</td>'
                                            +'<td>'+description+'</td>'
                                        +'</tr>';
                        $('#datatBody').append(tBodyHtml);
                    });
                }
            }else{
                noticeAlert('信息获取失败，请重新进入。','失败',loadMaskHide,$('.box'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('.box'));
        }
    });
}
getCustomerInfo();
function updateBasicInfo(){
    var contactsName = $('#contactsName').val();
    var contactsMobile = $('#contactsMobile').val();
    var province = $('#province').val();
    var city = $('#city').val();
    var region = $('#region').val();
    loadMask.loadStart($('#companyInfo'));
    $.ajax({
        url: "../customer/updateBasicInfo",
        type: "post",
        dataType:"json",
        data:{
            "contactsName":contactsName,
            "contactsMobile":contactsMobile,
            "province":province,
            "city":city,
            "region":region
        },
        success: function (data){
            loadMask.loadEnd($('#companyInfo'));
            if(data.isSuccess == 1){
                noticeAlert('修改成功。','成功','','');
            }else{
                noticeAlert('修改失败，请重新提交信息。','失败',loadMaskHide,$('#companyInfo'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#companyInfo'));
        }
    });
}
$('#editBtn').click(function(){
    updateBasicInfo();
});
//地区
var area_json;
function getProvinceData(provinceName) {
    $.getJSON("/js/area.json", function (data) {
        area_json = data;
        var html_str = '<option value="">请选择省</option>';
        $.each(data, function (i, item) {
            html_str += '<option value="' + item.name + '">' + item.name + '</option>';
        });
        $('#province').html(html_str);
        $('#province').select2().select2('val',provinceName);
    });
}
function getCityData(provinceName,cityName) {
    $.getJSON("/js/area.json", function (data) {
        $.each(data, function (i, item) {
            if (item.name == provinceName) {
                var html_str = '<option value="">请选择市</option>';
                $.each(item.cityList, function (j, itemj) {
                    html_str += '<option value="' + itemj.name + '">' + itemj.name + '</option>';
                });
                $('#city').html(html_str);
            }
        });
        $('#city').select2().select2('val',cityName);
    });
}
function getRegionData(provinceName,cityName,regionName) {
    $.getJSON("/js/area.json", function (data) {
        $.each(data, function (i, item) {
            if (item.name == provinceName) {
                $.each(item.cityList, function (j, itemj) {
                    if (itemj.name == cityName) {
                        var html_str = '<option value="">请选择区（县）</option>';
                        $.each(itemj.areaList, function (k, itemk) {
                            html_str += '<option value="' + itemk.name + '">' + itemk.name + '</option>';
                        });
                        $('#region').html(html_str);
                    }
                });
            }
        });
        $('#region').select2().select2('val',regionName);
    });
}
$('#province').change(function () {
    getCityData($(this).val());
    $('#region').html('<option value="">请选择区（县）</option>');
});
$('#city').change(function () {
    getRegionData($('#province').val(), $(this).val());
});