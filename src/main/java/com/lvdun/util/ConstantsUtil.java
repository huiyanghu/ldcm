package com.lvdun.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/27.
 */
public class ConstantsUtil {
    public static Map constants = new HashMap();

    static {
        constants.put("CmAccount_roleFlag", "[{'key':1,'value':'管理员'},{'key':0,'value':'操作员'}]");
        //0新注册用户（未经过超级管理员审核）-1、体验用户2、正式收费用户3、正式免费用户4、停用
        constants.put("CmCustomer_status", "[{'key':0,'value':'新注册用户'},{'key':-1,'value':'体验用户'},{'key':2,'value':'正式收费用户'},{'key':3,'value':'正式免费用户'},{'key':4,'value':'停用'}]");
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
