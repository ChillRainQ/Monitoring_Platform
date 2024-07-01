package asia.serverchillrain.school.server.utils;

import asia.serverchillrain.school.server.SystemInit;
import asia.serverchillrain.school.server.settings.RedisConfigLine;
import asia.serverchillrain.school.server.settings.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * &#064;auther  2024 02 02
 * 系统设置工具
 */

public class SystemSettingUtil {
    private static Logger logger = LoggerFactory.getLogger(SystemInit.class);
    public static final String KEY_APIS = "APIS";
    public static final String KEY_EMAILS = "EMAILS";
    private static final Map<String, Setting> SYSTEM_CACHE = new ConcurrentHashMap<>();
    public static Setting getSystemSetting(String key){
        return SYSTEM_CACHE.get(key);
    }

    public static void putSetting(String keyEmails, Setting setting) {
        SYSTEM_CACHE.put(keyEmails, setting);
    }

    public static void removeAllSetting(){
        for (String key : SYSTEM_CACHE.keySet()) {
            SYSTEM_CACHE.remove(key);
        }
        if (SYSTEM_CACHE.isEmpty()){
            return;
        }else {
            logger.error("system setting update failed");
        }
        return;
    }
    public static Map getSystemSettingMap() {
        return SYSTEM_CACHE;
    }
}
