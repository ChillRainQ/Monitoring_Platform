package asia.serverchillrain.school.server.entity.enums;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import lombok.Getter;

/**
 * Api设置绑定
 */
@Getter
public enum ApiSettingClass {
    CAMERA_SITE("cameraSite", "asia.serverchillrain.school.server.settings.api.CameraHardWare"),
    PHONE("phone", "asia.serverchillrain.school.server.settings.api.PhoneModel"),
    SLEEP("sleep", "asia.serverchillrain.school.server.settings.api.SleepModel"),
    FIRE("fire", "asia.serverchillrain.school.server.settings.api.FireModel"),
    SMOCK("smock", "asia.serverchillrain.school.server.settings.api.SmockModel"),
    CAMERA_CLOSE("cameraClose", "asia.serverchillrain.school.server.settings.api.CameraCloseSite"),
    CAMERA_OPEN("cameraOpen", "asia.serverchillrain.school.server.settings.api.CameraOpenSite"),
    MODEL_SITE("modelSite", "asia.serverchillrain.school.server.settings.api.ModelSite"),
    STREAM_SITE("streamSite", "asia.serverchillrain.school.server.settings.api.StreamSite")
    ;
    private final String name;
    private final String classPath;
    public static ApiSettingClass getByName(String name) throws MonitoringPlatformException {
        for(ApiSettingClass api : ApiSettingClass.values()){
            if(api.getName().equals(name)){
                return api;
            }
        }
        throw new MonitoringPlatformException("错误的API名称！", ResponseCodeEnum.CODE_700);
    }
    ApiSettingClass(String name, String classPath) {
        this.name = name;
        this.classPath = classPath;
    }

}
