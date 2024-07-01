package asia.serverchillrain.school.server.config;

import asia.serverchillrain.cache.MapCreater;
import asia.serverchillrain.cache.core.AutoExpiredMap;
import asia.serverchillrain.school.server.config.root.AppConfig;
import asia.serverchillrain.school.server.entity.Constant;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * &#064;auther  2024 01 28
 */
@Configuration
@Getter
public class ServerConfig extends AppConfig {
    @Value("${application.server.path}")
    private String serverPath;
    @Bean
    public AutoExpiredMap cache() throws IOException, ClassNotFoundException {
        return MapCreater.newMap(true, Constant.CACHE_NAME);
    }
//    @Bean
//    public InitConfig init(){
//       return new InitConfig();
//    }
}
