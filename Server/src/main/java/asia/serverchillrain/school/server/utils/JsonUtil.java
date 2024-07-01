package asia.serverchillrain.school.server.utils;


import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther 2024 01 28
 * JSON支持工具
 */

public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public static String object2Json(Object obj){
        String jsonString = JSON.toJSONString(obj);
        logger.info("尝试json化：{}", jsonString);
        return jsonString;
    }
    public static <T> T json2Object(String json, Class<T> clazz){
        return (T) JSON.parseObject(json, clazz);
    }
}
