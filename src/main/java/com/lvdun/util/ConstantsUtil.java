package com.lvdun.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/27.
 */
public class ConstantsUtil {
    public static String SERVER_IP="localhost";
    public static String SERVER_PORT="80";


    public static Map constants = new HashMap();
    static {
        constants.put("CmAccount_roleFlag", "[{'key':1,'value':'管理员'},{'key':0,'value':'操作员'}]");
        constants.put("CmCustomer_status", "[{'key':0,'value':'新注册用户'},{'key':-1,'value':'体验用户'},{'key':1,'value':'正式收费用户'},{'key':2,'value':'正式免费用户'},{'key':3,'value':'暂停客户'}]");

        //审核结果
        constants.put("DataRecord_status", "[{'key':0,'value':'不通过'},{'key':1,'value':'通过'},{'key':2,'value':'不确定'}]");
        //不通过原因
        constants.put("DataRecord_reasonCode", "[{'key':0,'value':'正常内容'},{'key':7,'value':'广告'},{'key':2,'value':'色情'},{'key':1,'value':'政治'},{'key':3,'value':'违法'},{'key':4,'value':'违规'},{'key':6,'value':'异常行为'},{'key':9,'value':'自定义识别'}]");
        //审核方式
        constants.put("DataRecord_reviewType", "[{'key':-1,'value':'智能审核'},{'key':0,'value':'人工审核'}]");
        //查询字段
        constants.put("DataRecord_condation", "[{'key':'data_content','value':'指定内容'},{'key':'user_ip','value':'指定ip'},{'key':'device_id','value':'指定设备号'}]");

    }



    public static List<Map> getConstants(String constantsKey) {
        List<Map> list = (List<Map>) JSON.parse("" + constants.get(constantsKey));
        return list;
    }

    public static String getConstants(String constantsKey, String key) {
        List<Map> list = getConstants(constantsKey);
        if (StringUtil.isNotEmpty(key)) {
            for (Map m : list) {
                if (m.get("key") != null && key.equals("" + m.get("key"))) {
                    return "" + m.get("value");
                }
            }
        }
        return "";
    }




}
