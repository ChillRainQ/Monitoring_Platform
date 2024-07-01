package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.aspect.BaseService;
import asia.serverchillrain.school.server.service.TestAOPService;
import org.springframework.stereotype.Service;

@Service
public class TestAOPServiceImpl extends BaseService implements TestAOPService {
    @Override
    public String tryDo() {
        return doAction(() -> "test", "Admin");
    }

}
