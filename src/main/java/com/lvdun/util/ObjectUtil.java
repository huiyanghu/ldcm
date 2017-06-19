package com.lvdun.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 对象类高级操作
 */
public class ObjectUtil {


    /**
     * 两个相同类型对象覆盖(oldObj覆盖newObj,isNullReplace当oldObj字段值为空是否覆盖，true覆盖，false不覆盖)
     *
     * @author jonas
     * @date 2014-11-28 上午10:28:54
     * @param newObj
     * @param oldObj
     * @param isNullReplace
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void objectReplace(Object newObj, Object oldObj, boolean isNullReplace) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (newObj != null && oldObj != null) {
            Class _class1 = newObj.getClass();
            Class _class2 = oldObj.getClass();
            Field[] fields = _class1.getDeclaredFields();
            for (Field field : fields) {
                Method getMethod = getMethod(_class2, field, "get");
                if (getMethod != null) {
                    Object value = getMethod.invoke(oldObj, null);
                    if (!isNullReplace && value == null) {
                        continue;
                    }
                    Method setMethod = getMethod(_class1, field, "set");
                    setMethod.invoke(newObj, value);
                }
            }
        }
    }


    /**
     * 两个相同类型对象覆盖(oldObj覆盖newObj,isNullReplaceList里面值为空也需要覆盖的字段)
     *
     * @author jonas
     * @date 2014-11-28 上午10:28:54
     * @param newObj
     * @param oldObj
     * @param isNullReplaceList
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void objectReplace(Object newObj, Object oldObj, List<String> isNullReplaceList)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (newObj != null && oldObj != null) {
            if (isNullReplaceList == null) {
                isNullReplaceList = new ArrayList<String>();
            }
            Class _class1 = newObj.getClass();
            Class _class2 = oldObj.getClass();
            if (_class1.getName().equals(_class2.getName())) {
                Field[] fields = _class1.getDeclaredFields();
                for (Field field : fields) {
                    Method getMethod = getMethod(_class1, field, "get");
                    if (getMethod != null) {
                        Object value = getMethod.invoke(oldObj, null);
                        if (!isNullReplaceList.contains(field.getName()) && value == null) {
                            continue;
                        }
                        Method setMethod = getMethod(_class1, field, "set");
                        setMethod.invoke(newObj, value);
                    }
                }
            }
        }
    }


    /**
     * 将List<T>里面对象转换成List<Map>
     *
     * @author jonas
     * @date 2014-11-28 上午10:30:17
     * @param list
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ParseException
     * @throws InvocationTargetException
     */
    public static List<Map<String, Object>> listToMap(List list) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        List<Map<String, Object>> listMap = null;
        for (Object object : list) {
            if (listMap == null) {
                listMap = new ArrayList<Map<String, Object>>();
            }
            Map<String, Object> map = objectToMap(object, null);
            if (map != null && !map.isEmpty()) {
                listMap.add(map);
            }
        }
        return listMap;
    }


    public static Map<String, Object> objectToMap(Object object) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException {
        Class _class = object.getClass();
        Field[] fields = _class.getDeclaredFields();
        Map<String, Object> map = null;
        for (Field field : fields) {
            if (map == null) {
                map = new HashMap<String, Object>();
            }
            Method method = getMethod(_class, field, "get");
            if (method != null) {
                setFieldValue(object, field, map, method, true);
            }
        }
        return map;
    }


    public static void listMapValueToString(List<Map> listMap) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        if (listMap == null) {
            return;
        }
        for (Map map : listMap) {
            mapValueToString(map);
        }
    }


    public static void listMapValueToStrings(List<Map<String, Object>> listMap) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        if (listMap == null) {
            return;
        }
        for (Map map : listMap) {
            mapValueToString(map);
        }
    }


    public static Map<String, Object> objectToMap(Object object, List<String> ignoreFieldList) {
        Class _class = object.getClass();
        Field[] fields = _class.getDeclaredFields();
        Map<String, Object> map = null;
        for (Field field : fields) {
            if (map == null) {
                map = new HashMap<String, Object>();
            }
            String fieldName = field.getName();
            if (ignoreFieldList != null && ignoreFieldList.contains(fieldName)) {
                continue;
            }
            Method method = getMethod(_class, fieldName, "get");
            if (method != null) {
                try {
                    setFieldValue(object, field, map, method, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }


    /**
     * 将List<T>里面对象转换成List<Map>
     *
     * @author jonas
     * @date 2014-11-28 上午10:30:17
     * @param list
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ParseException
     * @throws InvocationTargetException
     */
    public static List<Map<String, Object>> listToMap(List list, List<String> ignoreFieldList)
            throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        List<Map<String, Object>> listMap = null;
        for (Object object : list) {
            if (listMap == null) {
                listMap = new ArrayList<Map<String, Object>>();
            }
            Map<String, Object> map = objectToMap(object, ignoreFieldList);
            if (map != null && !map.isEmpty()) {
                listMap.add(map);
            }
        }
        return listMap;
    }


    public static void mapValueToString(Map map) {
        if (map != null) {
            for (Object key : map.keySet()) {
                Object value = map.get(key);
                if (value != null) {
                    map.put(key, value.toString());
                }
            }
        }
    }


    public static List<Map> listToMapByMethod(List list) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        List<Map> listMap = null;
        for (Object object : list) {
            if (listMap == null) {
                listMap = new ArrayList<Map>();
            }
            Map map = objectToMapByMethod(object, null);
            if (map != null && !map.isEmpty()) {
                listMap.add(map);
            }
        }
        return listMap;
    }


    public static List<Map<String, Object>> listToMapByMethod(List list, List<String> ignoreFieldList)
            throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        List<Map<String, Object>> listMap = null;
        for (Object object : list) {
            if (listMap == null) {
                listMap = new ArrayList<Map<String, Object>>();
            }
            Map<String, Object> map = objectToMapByMethod(object, ignoreFieldList);
            if (map != null && !map.isEmpty()) {
                listMap.add(map);
            }
        }
        return listMap;
    }


    public static Map<String, Object> objectToMapByMethod(Object object, List<String> ignoreFieldList)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException, SecurityException {
        Class _class = object.getClass();
        Method[] methods = _class.getMethods();
        Map map = null;
        for (Method method : methods) {
            if (map == null) {
                map = new HashMap();
            }
            String methodName = method.getName();
            if (ignoreFieldList != null && ignoreFieldList.contains(methodName)) {
                continue;
            }
            String prefix = methodName.substring(0, 3);
            String suffix = methodName.substring(3, methodName.length());
            if ("get".equals(prefix)) {
                setFieldValue(object, suffix, map, method, true);
            } else if ("set".equals(prefix)) {
                Method _method = null;
                try {
                    _method = _class.getMethod("get" + suffix, null);
                } catch (NoSuchMethodException e) {
                }
                if (_method != null) {
                    setFieldValue(object, suffix, map, _method, true);
                }
            } else {
                setFieldValue(object, methodName, map, method, true);
            }
        }
        return map;
    }


    /**
     * 将对象里面的String字段值消除空格
     *
     * @author jonas
     * @date 2014-11-28 上午10:32:54
     * @param object
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    public static Object objectFieldTrim(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
        Class _class = object.getClass();
        Field[] fields = _class.getDeclaredFields();
        for (Field field : fields) {
            Method method = getMethod(_class, field, "get");
            Object value = method.invoke(object, null);
            if (value != null) {
                if (field.getType().getName().equals("java.lang.String")) {
                    setFieldValue(object, field, value.toString().trim());
                } else {
                    setFieldValue(object, field, value.toString());
                }
            }
        }
        return object;
    }


    /**
     * 获取对象get或者set方法
     *
     * @author jonas
     * @date 2014-11-28 上午10:34:56
     * @param _class
     * @param field
     * @param prefix
     * @return
     */
    public static Method getMethod(Class _class, Field field, String prefix) {
        Method[] methods = _class.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            String fieldName = field.getName();
            String _fieldName = prefix + fieldName;
            if (methodName.toUpperCase().equals(_fieldName.toUpperCase())) {
                return method;
            }
        }
        return null;
    }


    /**
     * 获取对象get或者set方法
     *
     * @author jonas
     * @date 2014-11-28 上午10:34:56
     * @param _class
     * @param fieldName
     * @param prefix
     * @return
     */
    public static Method getMethod(Class _class, String fieldName, String prefix) {
        Method[] methods = _class.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            String _fieldName = prefix + fieldName;
            if (methodName.toUpperCase().equals(_fieldName.toUpperCase())) {
                return method;
            }
        }
        return null;
    }


    private static void setFieldValue(Object obj, Field field, String value) throws IllegalArgumentException, IllegalAccessException, ParseException {
        field.setAccessible(true);
        String fieldType = field.getType().getName();
        if (fieldType.equals("java.lang.String")) {
            field.set(obj, value);
        } else if (fieldType.equals("java.lang.Integer") || fieldType.equals("int")) {
            field.set(obj, Integer.valueOf(value));
        } else if (fieldType.equals("java.lang.Long") || fieldType.equals("long")) {
            field.set(obj, Long.valueOf(value));
        } else if (fieldType.equals("java.lang.Float") || fieldType.equals("float")) {
            field.set(obj, Float.valueOf(value));
        } else if (fieldType.equals("java.lang.Double") || fieldType.equals("double")) {
            field.set(obj, Double.valueOf(value));
        } else if (fieldType.equals("java.util.Date")) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            field.set(obj, df.parse(value));
        } else if (fieldType.equals("java.math.BigDecimal")) {
            field.set(obj, new BigDecimal(value));
        } else if (fieldType.equals("java.lang.Boolean") || fieldType.equals("boolean")) {
            field.set(obj, Boolean.valueOf(value));
        } else if (fieldType.equals("java.lang.Byte") || fieldType.equals("byte")) {
            field.set(obj, Byte.valueOf(value));
        } else if (fieldType.equals("java.lang.Short") || fieldType.equals("short")) {
            field.set(obj, Short.valueOf(value));
        }
    }


    private static void setFieldValue(Object obj, Field field, Map map, Method method, boolean isLegalMethod)
            throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        field.setAccessible(true);
        String fieldName = field.getName();
        setFieldValue(obj, fieldName, map, method, isLegalMethod);
    }


    private static void setFieldValue(Object obj, String fieldName, Map map, Method method, boolean isLegalMethod)
            throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException {
        if (map != null && map.containsKey(fieldName.toUpperCase())) {
            fieldName = obj.getClass().getSimpleName() + fieldName;
        }
        Class[] _c = method.getParameterTypes();
        Class returnCls = method.getReturnType();
        if (isLegalMethod) {
            if ((_c == null || _c.length == 0) && isBasicType(returnCls.getName())) {
                map.put(fieldName.toUpperCase(), method.invoke(obj, null));
            }
        } else {
            map.put(fieldName.toUpperCase(), method.invoke(obj, null));
        }
    }


    public static boolean isBasicType(String typeName) {
        if (typeName.equals("java.lang.String") || typeName.equals("java.lang.Integer") || typeName.equals("int") || typeName.equals("java.lang.Long") || typeName.equals("long")
                || typeName.equals("java.lang.Float") || typeName.equals("float") || typeName.equals("java.lang.Double") || typeName.equals("double")
                || typeName.equals("java.util.Date") || typeName.equals("java.math.BigDecimal") || typeName.equals("java.lang.Boolean") || typeName.equals("boolean")
                || typeName.equals("java.lang.Byte") || typeName.equals("byte") || typeName.equals("java.lang.Short") || typeName.equals("short")) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isCollectionType(Object obj) {
        if (obj instanceof Map || obj instanceof Collection) {
            return true;
        } else {
            return false;
        }
    }


    public static void loopFieldNewInstance(Object obj) throws Exception {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Class fieldType = field.getType();
            if (!ObjectUtil.isBasicType(fieldType.getName()) && !fieldType.isInterface() && !ObjectUtil.isCollectionType(fieldType.newInstance())) {// 判断该字段是否基本类型
                Object _obj = fieldType.newInstance();
                Method method = ObjectUtil.getMethod(cls, field, "set");
                if (method != null) {
                    method.invoke(obj, _obj);
                    loopFieldNewInstance(_obj);
                }
            }
        }


    }


    public static void getPropertyStringByValue(Object obj, String prefix, List<String> propertyNames, List<Object> values) throws Exception {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Method method = getMethod(cls, field, "get");
            Object value = null;
            if (method != null) {
                value = method.invoke(obj, null);
            }
            if (value != null) {
                String fieldType = field.getType().getName();
                String fieldName = (prefix == null ? "" : prefix + ".") + field.getName();
                if (isBasicType(fieldType)) {// 判断该字段是否基本类型
                    propertyNames.add(fieldName);
                    values.add(value);
                } else {
                    boolean isColl = isCollectionType(field.getType().newInstance());
                    if (!isColl) {// 不是集合类型
                        getPropertyStringByValue(value, fieldName, propertyNames, values);
                    }
                }
            }
        }


    }


    public static Object toObjectByRequest(Class cls, HttpServletRequest request) {
        try {
            Object object = cls.newInstance();
            Map map = request.getParameterMap();
            for (Iterator localIterator = map.keySet().iterator(); localIterator.hasNext();) {
                Object key = localIterator.next();
                String _key = key.toString();
                String value = request.getParameter(_key);
                if ((value != null) && (value.trim() != "")) {
                    loopSetFieldValue(_key, object, value);
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void loopSetFieldValue(String key, Object obj, String value) throws Exception {
        if (key.indexOf(".") == -1) {
            Field field = getFieldObj(obj, key);
            if (field != null) {
                setFieldValue(obj, field, value.toString());
            }
        } else {
            String prefix = key.substring(0, key.indexOf("."));
            String suffix = key.substring(key.indexOf(".") + 1, key.length());
            Field field = getFieldObj(obj, prefix);
            if (field != null) {
                Method getMethod = getMethod(obj.getClass(), field.getName(), "get");
                if (getMethod != null) {
                    Object object = getMethod.invoke(obj, null);
                    if (object == null) {
                        object = field.getType().newInstance();
                        Method setMethod = getMethod(obj.getClass(), field.getName(), "set");
                        setMethod.invoke(obj, new Object[] { object });
                    }
                    if (suffix.indexOf(".") != -1) {
                        loopSetFieldValue(suffix, object, value);
                    } else {
                        Field _field = getFieldObj(object, suffix);
                        if (_field != null)
                            setFieldValue(object, _field, value);
                    }
                }
            }
        }
    }


    private static Field getFieldObj(Object object, String key) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            if (field.getName().equals(key)) {
                return field;
            }
        }
        return null;
    }

}

