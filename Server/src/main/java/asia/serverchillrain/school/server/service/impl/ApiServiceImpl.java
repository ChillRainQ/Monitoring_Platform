package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.aspect.BaseService;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.enums.ApiSettingClass;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.entity.interfaces.ApiFunction;
import asia.serverchillrain.school.server.service.ApiService;
import asia.serverchillrain.school.server.settings.api.root.ApiSetting;
import asia.serverchillrain.school.server.utils.HttpUtil;
import asia.serverchillrain.school.server.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static asia.serverchillrain.school.server.utils.SystemSettingUtil.KEY_APIS;
import static asia.serverchillrain.school.server.utils.SystemSettingUtil.getSystemSetting;
/**
 * @auther 2024 02 01
 * API服务
 */
@Service
public class ApiServiceImpl extends BaseService implements ApiService {
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    /**
     * Api请求
     * @param type Api的type
     * @return 响应内容
     * @throws MonitoringPlatformException 监控平台异常
     */
    @Override
    public String action(String type, String model) throws MonitoringPlatformException {
        return doAction(() -> {
            //从SystemSetting获取设置
            ApiSetting apiSetting = (ApiSetting) getSystemSetting(KEY_APIS);
            //构建url地址
            String url = null;
            if(model != null && model.equals("close")){
                if(type.equals("camera")){
                    url = apiSetting.getApiByType(ApiSettingClass.CAMERA_SITE.getName())
                            + "/" + model + "/" + type;
                }else{
                    url = apiSetting.getApiByType(ApiSettingClass.MODEL_SITE.getName())
                            + "/" + model + "/" + type;
                }
            }else{
                //开启
                //从设置中读取Api地址
                String api = apiSetting.getApiByType(type);
                //不能直接请求camera_site，因为这只是一个网络地址
                if (api == null || api.equals(ApiSettingClass.CAMERA_SITE.getName())) {
                    try {
                        throw new MonitoringPlatformException("请求了错误的API！", ResponseCodeEnum.CODE_404);
                    } catch (MonitoringPlatformException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (type.equals(ApiSettingClass.CAMERA_OPEN.getName())) {
                    url = apiSetting.getApiByType(ApiSettingClass.CAMERA_SITE.getName())
                            + api + apiSetting.getApiByType(ApiSettingClass.STREAM_SITE.getName());
                } else{
                    url = apiSetting.getApiByType(ApiSettingClass.MODEL_SITE.getName())
                            + api + apiSetting.getApiByType(ApiSettingClass.STREAM_SITE.getName());
                }
            }
            //
            //执行HTTP
            String finalUrl = url;
            return doFunction(() -> {
                String json = HttpUtil.HttpGet(finalUrl);
                logger.info("请求的url为---> {}", finalUrl);
                Response<String> response = JsonUtil.json2Object(json, Response.class);
                return response.getInfo();
            });
        }, "NeedLogin");
    }

    /**
     * 函数接口
     * @param func 函数
     * @return 执行是否成功
     */
    private String doFunction(ApiFunction<String> func){
        return func.action();
    }
}
