package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.cache.core.AutoExpiredMap;
import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.entity.vo.UserVo;
import asia.serverchillrain.school.server.service.UserService;
import asia.serverchillrain.school.server.utils.IPUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import asia.serverchillrain.school.server.utils.JsonUtil;

import java.io.UnsupportedEncodingException;

/**
 * &#064;auther  2024 01 27
 * Controller
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
//ip:port/user/register
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private AutoExpiredMap map;

    /**
     * 注册
     * @param email 邮箱
     * @param password MD5加密后的密码
     * @param emailCode 邮箱验证码
     * @return 响应
     * @throws MonitoringPlatformException 监控平台异常
     */
    @RequestMapping("/register")
    @CrossOrigin//跨域
    public Response<String> register(String email, String password, String emailCode) throws MonitoringPlatformException {
        return getSuccessResponse(userService.register(email, password, emailCode));
    }

    /**
     * 登录
     * @param request 本次请求
     * @param email 邮箱
     * @param password MD5加密后的密码
     * @return 响应
     * @throws MonitoringPlatformException 监控平台异常
     * @throws UnsupportedEncodingException 编码异常
     */
    @RequestMapping("/login")
    public Response<String> login(HttpServletRequest request, String email, String password) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(userService.login(request, email, password));
    }

    /**
     * 登出
     * @param request 本次请求
     * @return 响应
     * @throws MonitoringPlatformException 监控平台异常
     * @throws UnsupportedEncodingException 编码异常
     */
    @RequestMapping("/logout")
    public Response<String> logout(HttpServletRequest request) throws MonitoringPlatformException, UnsupportedEncodingException {
        return getSuccessResponse(userService.logout(request));
    }

    @RequestMapping("/getIp")
    public Response<String> getIp(HttpServletRequest request){
        return getSuccessResponse(IPUtil.getIp(request));
    }
    @RequestMapping("/getNowUser")
    public Response<UserVo> getNowUser(HttpServletRequest request){
        String ip = IPUtil.getIp(request);
        String jsonStr = (String) map.get(ip);
        UserVo nowUserVo = JsonUtil.json2Object(jsonStr, UserVo.class);
//        return getSuccessResponse((UserVo)request.getSession().getAttribute(Constant.LOG_USER));
        return getSuccessResponse(nowUserVo);
    }
    @RequestMapping("/link")
    public Response<String> link(HttpServletRequest request){
        return getSuccessResponse(userService.link(request));
    }
}
