package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.aspect.BaseService;
import asia.serverchillrain.cache.core.AutoExpiredMap;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.enums.ApiSettingClass;
import asia.serverchillrain.school.server.entity.enums.EmailSettingClass;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.SystemSettingService;
import asia.serverchillrain.school.server.settings.RedisConfigLine;
import asia.serverchillrain.school.server.settings.api.root.ApiSetting;
import asia.serverchillrain.school.server.settings.email.root.EmailSetting;
import asia.serverchillrain.school.server.utils.JsonUtil;
import asia.serverchillrain.school.server.utils.SystemSettingUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static asia.serverchillrain.school.server.utils.SystemSettingUtil.*;

/**
 * &#064;auther  2024 01 28
 * 系统设置服务
 * 如果要能单独写，则需要读txt
 */
@Service
@PropertySource(value = "classpath:setting.properties", encoding = "UTF-8")
public class SystemSettingServiceImpl extends BaseService implements SystemSettingService {

    private static final Logger log = LoggerFactory.getLogger(SystemSettingServiceImpl.class);
    @Value("${application.api.camera.site}")
    private String cameraSite;
    @Value("${application.api.camera.open}")
    private String open;
    @Value("${application.api.rtmp.site}")
    private String rtsp;
    @Value("${application.api.model.site}")
    private String model;
    @Value("${application.api.smock.site}")
    private String smock;
//    @Value("${application.api.sleep.site}")
//    private String sleep;
    @Value("${application.api.fire.site}")
    private String fire;
    @Value("${application.api.phone.site}")
    private String phone;
    @Value("${application.system.register.message}")
    private String message;
    @Value("${application.system.register.time}")
    private String time;
    @Value("${application.system.email.email}")
    private String systemEmail;
    @Value("${application.system.email.title}")
    private String title;
    @Value("${application.api.camera.close}")
    private String close;
    @Resource
    private AutoExpiredMap cache;
    @Override
    public String readSettingsFormCacheToMemory() throws IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        readEmailConfig2Memory();
        readApis2Memory();
        return "Success!";
    }

    private void readEmailConfig2Memory() throws MonitoringPlatformException, IntrospectionException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        String emailJson = cache.getStr(KEY_EMAILS);
        EmailSetting emailsetting = new EmailSetting();
        Map<String, String> emailSettingMap = JSON.parseObject(emailJson, new TypeReference<>() {
        });
        if (emailSettingMap == null){
            log.error("缓存错误！");
            throw new MonitoringPlatformException("cache error!", ResponseCodeEnum.CODE_500);
        }
        for (Map.Entry<String, String> next : emailSettingMap.entrySet()) {
            EmailSettingClass setting = EmailSettingClass.getByName(next.getKey());
            PropertyDescriptor pd = new PropertyDescriptor(setting.getName(), EmailSetting.class);
            Method writeMethod = pd.getWriteMethod();
            Class<?> clazz = Class.forName(setting.getClassPath());
            Constructor<?> constructor = clazz.getConstructor();
            RedisConfigLine redisConfigLine = (RedisConfigLine) constructor.newInstance();
            redisConfigLine.setLine(next.getValue());
            writeMethod.invoke(emailsetting, redisConfigLine);
        }
        putSetting(KEY_EMAILS, emailsetting);
    }

    private void readApis2Memory() throws IntrospectionException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, MonitoringPlatformException, NoSuchMethodException {
        String apiJson = cache.getStr(KEY_APIS);
        ApiSetting apiSetting = new ApiSetting();
        Map<String, String> apiSettingMap = JSON.parseObject(apiJson, new TypeReference<>() {
        });
        for (Map.Entry<String, String> next : apiSettingMap.entrySet()) {
            ApiSettingClass setting = ApiSettingClass.getByName(next.getKey());
            PropertyDescriptor pd = new PropertyDescriptor(setting.getName(), ApiSetting.class);
            Method writeMethod = pd.getWriteMethod();
            Class<?> clazz = Class.forName(setting.getClassPath());
            Constructor<?> constructor = clazz.getConstructor();
            RedisConfigLine redisConfigLine = (RedisConfigLine) constructor.newInstance();
            redisConfigLine.setLine(next.getValue());
            writeMethod.invoke(apiSetting, redisConfigLine);
        }
        putSetting(KEY_APIS, apiSetting);
    }

    /**
     * 更新设置先删除对应的Cache后写入Cache，然后再修改Memory
     * @param settings 更新设置
     * @return 操作结果
     */
    @Override
    public String updateSetting(Map<String, String> settings) throws IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
//        //删除缓存
//        cache.remove(KEY_APIS);
//        cache.remove(KEY_EMAILS);
        //写入缓存
        Map<String, String> emailConfig = Map.of(
                Constant.EMAIL_TITLE, settings.get("title"),
                Constant.EMAIL_MESSAGE, settings.get("message"),
                Constant.EMAIL_TIME, settings.get("time"),
                Constant.SYSTEM_EMAIL, settings.get("email")
        );
        cache.put(KEY_EMAILS, JsonUtil.object2Json(emailConfig));
        cache.save();
        Map<String, String> apis = Map.of(
                "cameraSite", settings.get("camera_site"),
//                "cameraClose",  settings.get("camera_close"),
                "cameraOpen", settings.get("camera_open"),
                "streamSite", settings.get("stream_site"),
                "modelSite", settings.get("model_site"),
                "smock", settings.get("smock") ,
//                "sleep", settings.get("sleep"),
                "fire", settings.get("fire"),
                "phone", settings.get("phone")
        );
        cache.put(KEY_APIS, JsonUtil.object2Json(apis));
        cache.save();
        //修改Memory
        removeAllSetting();
        readApis2Memory();
        readEmailConfig2Memory();
        return "系统设置更新成功！";
    }

    @Override
    public String readSettingsToCache() {
        readCache();
        readMysqlSetting();
        return null;
    }

    @Override
    public Map readSetting() {
        return doAction(SystemSettingUtil::getSystemSettingMap, "Admin");
    }

    private void readCache() {
        //检验文件存在
        if(new File(Constant.CACHE_NAME).exists()){
            return;
        }
        readApis();
        readEmailConfig();
    }

    private void readEmailConfig() {
        Map<String, String> emailConfig;
        String emailJsons = cache.getStr(KEY_EMAILS);
        if(emailJsons != null){
            JSON.parseObject(emailJsons, new TypeReference<>() {
            });
        }else{
            //为空 写入emailConfig
            emailConfig = Map.of(
                    Constant.EMAIL_TITLE, title,
                    Constant.EMAIL_MESSAGE, message,
                    Constant.EMAIL_TIME, time,
                    Constant.SYSTEM_EMAIL, systemEmail
            );
            cache.put(KEY_EMAILS, JsonUtil.object2Json(emailConfig));
            cache.save();
        }
    }

    private void readApis() {
        Map<String, String> apis;
        String apiJsons = cache.getStr(KEY_APIS);
        if(apiJsons != null){
            JSON.parseObject(apiJsons, new TypeReference<>() {
            });
        }else{
            //为空 写入apis
            apis = Map.of(
                    "cameraSite", cameraSite,
                    "cameraClose",  close,
                    "cameraOpen", open,
                    "streamSite", rtsp,
                    "modelSite", model,
                    "smock", smock ,
//                    "sleep", sleep,
                    "fire", fire,
                    "phone", phone
            );
            cache.put(KEY_APIS, JsonUtil.object2Json(apis));
//            cache.expired(1000);
            cache.save();
        }
    }
    private void readMysqlSetting(){

    }
}
