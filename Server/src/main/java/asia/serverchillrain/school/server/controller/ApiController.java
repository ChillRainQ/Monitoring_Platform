package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.service.ApiService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * &#064;auther  2024 02 01
 * Api请求Controller
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController extends BaseController {
    @Resource
    private ApiService apiService;

    /**
     * 请求远程Api
     * @param request 一次请求
     * @param type Api类型
     * @return 请求信息
     * @throws MonitoringPlatformException 监控平台异常
     */
//    127.0.0.1:8080/api/connectApi/“请求的api”/"close还是open"
    @RequestMapping("/connectApi/{type}/{model}")
    public Response<String> connectApi(HttpServletRequest request, @PathVariable String type, @PathVariable String model) throws MonitoringPlatformException {
        return getSuccessResponse(apiService.action(type, model));
    }
}
