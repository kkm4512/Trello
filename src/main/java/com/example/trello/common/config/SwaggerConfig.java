package com.example.trello.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trello API")
                        .version("0.1")
                        .description("Trello Project API 명세서")
                        .contact(new Contact()
                                .name("Team Github Repository")
                                .url("https://github.com/kkm4512/Trello")
                        )
                );
    }

}
