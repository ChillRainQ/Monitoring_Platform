package asia.serverchillrain.school.server.settings.api.root;


import asia.serverchillrain.school.server.settings.Setting;
import asia.serverchillrain.school.server.settings.api.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * &#064;auther  2024 02 02
 * Api设置
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSetting extends Setting {
    private SleepModel sleep;
    private SmockModel smock;
    private PhoneModel phone;
    private FireModel fire;
    private StreamSite streamSite;
    private ModelSite modelSite;
    private CameraHardWare cameraSite;
    private CameraOpenSite cameraOpen;
    private CameraCloseSite cameraClose;

    @Override
    public String getApiByType(String type){
        if("sleep".equals(type)) return sleep.getLine();
        if("smock".equals(type)) return smock.getLine();
        if("phone".equals(type)) return phone.getLine();
        if("fire".equals(type)) return fire.getLine();
        if("cameraSite".equals(type)) return cameraSite.getLine();
        if("cameraOpen".equals(type)) return cameraOpen.getLine();
        if("cameraClose".equals(type)) return cameraClose.getLine();
        if("streamSite".equals(type)) return streamSite.getLine();
        if("modelSite".equals(type)) return modelSite.getLine();
        return null;
    }
}

