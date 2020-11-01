package com.milan.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Este método se utiliza para indicar que es lo que queremos exponer en nuestra documentación, en este caso
     * queremos documentar lo que está dentro de controller
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket( DocumentationType.SWAGGER_2 )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.milan.market.web.controller"))
                .build();
    }
}
