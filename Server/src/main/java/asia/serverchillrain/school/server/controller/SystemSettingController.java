package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.SystemSettingService;
import asia.serverchillrain.school.server.utils.SystemSettingUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * &#064;author  2024 01 28
 * 系统设置Controller
 */
@RestController
@RequestMapping("/system")
@CrossOrigin
public class SystemSettingController extends BaseController {
    @Resource
    private SystemSettingService systemSettingService;

    /**
     * 从内存库中刷新缓存
     * @return 结果
     * @throws UnsupportedEncodingException 异常
     * @throws IntrospectionException 异常
     * @throws MonitoringPlatformException 异常
     * @throws ClassNotFoundException 异常
     * @throws InvocationTargetException 异常
     * @throws IllegalAccessException 异常
     * @throws InstantiationException 异常
     */
    @RequestMapping("/refreshFromRedis")
    public Response<String> refresh() throws UnsupportedEncodingException, IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        return getSuccessResponse(systemSettingService.readSettingsFormCacheToMemory());
    }

    /**
     * 读取系统设置
     * @return 系统设置
     */
    @RequestMapping("/readSetting")
    public Response<Map> readSetting(){
        return getSuccessResponse(systemSettingService.readSetting());
    }

    /**
     * 更新系统设置
     * @param settings 新设置
     * @return
     * @throws IntrospectionException
     * @throws MonitoringPlatformException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    @RequestMapping("/updateSetting")
    public Response<String> updateSetting(@RequestParam Map<String, String> settings) throws IntrospectionException, MonitoringPlatformException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return getSuccessResponse(systemSettingService.updateSetting(settings));
    }
}
