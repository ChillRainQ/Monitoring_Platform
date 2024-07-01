package asia.serverchillrain.school.server;

import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.service.SystemSettingService;
import asia.serverchillrain.school.server.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * &#064;auther  2024 01 28
 * 系统初始化
 */
@Component
public class SystemInit implements ApplicationRunner {
    @Resource
    private SystemSettingService systemSettingService;
    @Resource
    private UserService userService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //从properties读到map
        systemSettingService.readSettingsToCache();
        //从map读为Setting的对象
        systemSettingService.readSettingsFormCacheToMemory();
        //尝试注册Admin
        userService.registerAdmin(Constant.adminUser);
    }
}
