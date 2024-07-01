package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * &#064;@auther 2024 03 24
 */
@RestController
@RequestMapping("/stream")
@CrossOrigin
public class StreamController extends BaseController {

    @RequestMapping("/site/{model}")
    public String getStreamSite(@PathVariable("model") String model) {
//        从流媒体服务器设置截取ip，再根据协议和对应的端口和地址生成最终的地址
        return model;
    }
}
