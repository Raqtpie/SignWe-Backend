package com.turingteam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SignWe接口文档")
                        .description("基本的一些接口说明")
                        .version("1.0")
                        .contact(new Contact().name("Raqtpie").url("https://github.com/Raqtpie/SignWe-Backend"))
                        .termsOfService("https://turingteam.coffee")
                        .license(new License().name("Apache 2.0 许可").url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}
