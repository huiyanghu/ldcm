$('.ui-select').ui_select();
$('.time-group a').click(function(){
    $(this).addClass('active').siblings().removeClass('active');
    var flag = Number($(this).attr('value'));
    refreshByDate(flag);
});
function refreshByDate(flag){
    var c;
}
refreshByDate(0);
