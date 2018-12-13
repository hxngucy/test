package com.gu.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SupportRedisUtils {
	private static Logger logger = LoggerFactory.getLogger(SupportRedisUtils.class);

    @Value("${logined.expire.second}")
    private int LOGINED_EXPIRE_SECOND;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    /**
     * 分隔符
     */
    private static String Separator = "|";

    /**
     * 运营中心缓存命名空间
     */
    public static final String SUPPORT_CACHE_NAMESPACE = "SUPPORT";
    
	@SuppressWarnings("unused")
	private static Gson GSON = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    /**
     * 根据动态key值获取加上前缀的整个key
     *
     * @param key
     * @return String
     * @Methods Name getKeyString
     * @Create In Nov 4, 2014
     */
    public static String getKeyString(String prefix, String key) throws RuntimeException {
        if (StringUtils.isBlank(prefix) || StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("prefix和key入参不能为空");
        }

        String redisKeyTemp = prefix + Separator + key;
        return redisKeyTemp;

    }


    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @return boolean
     * @Methods Name setMemberHSet
     * @Create In Nov 4, 2014 
     */
    public boolean setSupportHSet(String prefix, String key, Object value) throws RuntimeException {
        if (value == null)
            return false;

        String redisKey = getKeyString(prefix, key);

        try {
            if (value instanceof String) {
                stringRedisTemplate.opsForValue().set(redisKey, value.toString());
            } else {
                stringRedisTemplate.opsForValue().set(redisKey, JSON.toJSONString(value));
            }
            return true;
        } catch (Exception ex) {
            logger.error("setHSet error.", ex);
        }
        return false;
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @return boolean
     * @Methods Name setMemberHSet
     * @Create In Nov 4, 2014 
     */
    @SuppressWarnings({ "unused", "unchecked" })
	public boolean setSupportSetData(String prefix, String key, Object value) throws RuntimeException {
        if (value == null)
            return false;

        String redisKey = getKeyString(prefix, key);

        try {
            if (value instanceof String) {
                stringRedisTemplate.opsForValue().set(redisKey, value.toString());
            } else if(value instanceof List){
                for(String v : (List<String>)value)
                {
//                    stringRedisTemplate.opsForSet().add(redisKey, v.toCharArray());
                }
            } else {
                stringRedisTemplate.opsForValue().set(redisKey, JSON.toJSONString(value));
            }
            return true;
        } catch (Exception ex) {
            logger.error("setHSet error.", ex);
        }
        return false;
    }


    /**
     * 设置缓存，加上失效时间
     *
     * @param key
     * @param value
     * @param seconds
     * @return boolean
     * @Methods Name setMemberHSet
     * @Create In Nov 4, 2014 By lyx
     */
    public boolean setSupportHSet(String prefix, String key, Object value, int seconds) throws RuntimeException {
        boolean result = setSupportHSet(prefix, key, value);
        if (result) {
            String redisKey = getKeyString(prefix, key);
            Boolean i = stringRedisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
            return i;
        }
        return false;
    }

    /**
     * 设置缓存，加上失效时间
     *
     * @param key
     * @param value
     * @param seconds
     * @return boolean
     * @Methods Name setMemberHSet
     * @Create In Nov 4, 2014 By lyx
     */
    public boolean setSupportSetData(String prefix, String key, Object value, int seconds) throws RuntimeException {
        boolean result = setSupportSetData(prefix, key, value);
        if (result) {
            String redisKey = getKeyString(prefix, key);
            Boolean i = stringRedisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
            return i;
        }
        return false;
    }

    /**
     * 获得HashSet对象
     *
     * @param key 键值
     * @return Json String or String value
     */
    public String getSupportHSet(String prefix, String key) throws RuntimeException {
        if (key == null || key.isEmpty()){
        	return null;
        }  
        String redisKey = getKeyString(prefix, key);
        Object values = stringRedisTemplate.opsForValue().get(redisKey);
        return values == null ? null : (String) values;
    }

    /**
     * 获得HashSet对象
     *
     * @param key 键值
     * @return Json String or String value
     */
    public <T> T getSupportHSet(String prefix, String key, Class<T> clazz) throws RuntimeException {
        if (key == null || key.isEmpty()){
        	return null;
        } 
        String values = this.getSupportHSet(prefix, key);
        T t = JSON.parseObject(values, clazz);
        return t;
    }

    /**
     * redis delete utils
     *
     * @param prefix
     * @param key
     */
    public void del(String prefix, String key) {
        String redisKey = getKeyString(prefix, key);
        stringRedisTemplate.delete(redisKey);
    }

    /**
     * 设置Redis中的过期时间
     *
     * @param prefix
     * @param key
     * @param seconds
     * @return
     */
    public boolean expire(String prefix, String key, int seconds) {
        String redisKey = getKeyString(prefix, key);
        Boolean i = stringRedisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
        return i;
    }

    /**
     * 通过key获得string value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Object value = stringRedisTemplate.opsForValue().get(key);
        return value == null ? null : (String) value;
    }

    /**
     * 判断key是否已经存在Redis中
     *
     * @param redisSupportIdKey
     * @return
     */
    public boolean exists(String redisSupportIdKey) {
        return stringRedisTemplate.hasKey(redisSupportIdKey);
    }

    /**
     * 获取key对应的Redis大小
     *
     * @param virtualCardNoSequence
     * @return
     */
    public long countList(String virtualCardNoSequence) {
        return stringRedisTemplate.opsForList().size(virtualCardNoSequence);
    }

    /**
     * FIXME 这个需要单独测试
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        Object value = stringRedisTemplate.opsForList().leftPop(key);
        return value == null ? null : (String) value;
    }

    /**
     * FIXME 这个需要单独测试
     *
     * @param key
     * @param value
     */
    public void rpush(String key, String value) {
        stringRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(String key,String value){
        return this.stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }
    
    private String getAfter(int seconds) {
        String rtn = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(13, seconds);
        rtn = df.format(c.getTime());
        return rtn;
    }
    
    private String getNow() {
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(dt);
    }

    /**
     * Reids
     *
     * @param key
     * @param seconds
     * @return
     */
    public boolean lock(String key, int seconds) {

        String tsnew = getAfter(seconds);
        if (this.stringRedisTemplate.opsForValue().setIfAbsent(key, tsnew)) {
            return true;
        } else {
            String timestamp = this.stringRedisTemplate.opsForValue().get(key);
            if (timestamp != null && !timestamp.isEmpty()) {
                if (timestamp.compareTo(getNow()) <= 0) {
                    this.stringRedisTemplate.delete(key);
                    return this.stringRedisTemplate.opsForValue().setIfAbsent(key, tsnew);
                } else {
                    return false;
                }
            } else {
                return this.stringRedisTemplate.opsForValue().setIfAbsent(key, tsnew);
            }
        }
    }

    public <K, V> List<V> multiGet(Collection<K> keys, Class<V> clazz) {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> strKeys = new ArrayList<>();
        for (K key : keys) {
            if (key == null) {
                throw new NullPointerException("multiGet: keys has null value!");
            }
            strKeys.add(key.toString());
        }
        List<String> strValues = stringRedisTemplate.opsForValue().multiGet(strKeys);

        List<V> valueList = new ArrayList<>(strValues.size());
        for (String strValue : strValues) {
            V value = parseObject(strValue, clazz);
            valueList.add(value);
        }
        return valueList;
    }

    private static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }

        if (clazz == String.class) {
            return (T) json;
        }

        return JSON.parseObject(json, clazz);
    }
}
