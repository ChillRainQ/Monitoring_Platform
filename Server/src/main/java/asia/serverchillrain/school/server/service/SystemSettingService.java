package asia.serverchillrain.school.server.service;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 系统配置服务
 */
public interface SystemSettingService {
    String readSettingsFormCacheToMemory() throws UnsupportedEncodingException, IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException;

    String updateSetting(Map<String, String> settings) throws IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    String readSettingsToCache() throws UnsupportedEncodingException;

    Map readSetting();
}
