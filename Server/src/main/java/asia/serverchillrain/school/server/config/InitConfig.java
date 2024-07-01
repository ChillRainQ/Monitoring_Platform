package asia.serverchillrain.school.server.config;

import asia.serverchillrain.aspect.FunctionMapManager;
import asia.serverchillrain.cache.core.AutoExpiredMap;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.entity.vo.UserVo;
import asia.serverchillrain.school.server.utils.IPUtil;
import asia.serverchillrain.school.server.utils.JsonUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class InitConfig {
    @Resource
    private AutoExpiredMap map;

    public InitConfig() {
        FunctionMapManager.addFunction("Admin", ()->{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            HttpSession session = request.getSession();
            String ip = IPUtil.getIp(request);
            String jsonStr = (String)map.get(ip);
            UserVo userInfo = JsonUtil.json2Object(jsonStr, UserVo.class);
//            UserVo userInfo = (UserVo) session.getAttribute(Constant.LOG_USER);
            if(userInfo == null){
                return false;
            }

            if(userInfo.getStatus() == 5){
                System.out.println("pass");
                return true;
            }
            return false;
        });
        FunctionMapManager.addFunction("", () -> true);
        FunctionMapManager.addFunction("NeedLogin", () -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
//            UserVo userInfo = (UserVo) session.getAttribute(Constant.LOG_USER);
            String ip = IPUtil.getIp(request);
            String jsonStr = (String)map.get(ip);
            UserVo userInfo = JsonUtil.json2Object(jsonStr, UserVo.class);
            if(userInfo != null){
                return true;
            }
            return false;
        });
    }
}
