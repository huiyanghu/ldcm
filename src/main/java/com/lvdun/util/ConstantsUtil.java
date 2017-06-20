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
        constants.put("CmAccount_RoleFlag", "[{'key':1,'value':'管理员'},{'key':'0','value':'操作员'}]");
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


    /*public static void main(String[] args) {
        List<Map> list = ConstantsUtil.getConstants("grabListRuleListStatus");
        System.out.println(list);
        String value = ConstantsUtil.getConstants("grabListRuleListStatus", "1");
        System.out.println(value);
    }*/

}
