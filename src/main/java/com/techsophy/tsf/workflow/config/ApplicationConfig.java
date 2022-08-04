package com.techsophy.tsf.workflow.config;

import com.techsophy.idgenerator.IdGeneratorImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.*;

@Configuration
public class ApplicationConfig
{
    @Value(GATEWAY_URL)
    String gatewayUrl;

    @Bean
    public IdGeneratorImpl idGeneratorImpl()
    {
        return new IdGeneratorImpl();
    }

    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title(WORKFLOW_MODELER).version(VERSION_1).description(WORKFLOW_MODELER_API_VERSION_V1))
                .servers( List.of(new Server().url(gatewayUrl)));
    }
}
