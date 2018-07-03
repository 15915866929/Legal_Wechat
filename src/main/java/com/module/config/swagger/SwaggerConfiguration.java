package com.module.config.swagger;

import com.module.config.tool_config.ToolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chc
 * @create 2017-11-01 16:32
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Autowired
    ToolConfig toolConfig;
    @Bean
    public Docket createRestApi() {
        String swaggerHost = toolConfig.getSwaggerHost();
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //设置只能通过指定地址访问Swagger
                .host(swaggerHost)
                .select()
                // 自定目录位置
                .apis(RequestHandlerSelectors.basePackage(toolConfig.getSwaggerBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        // Contact contact = new Contact("chc", "url", "email");
        // 标题
        return new ApiInfoBuilder().title(toolConfig.getSwaggerTitle())
                // 描述
                .description("作用：api文档")
                // 地址
                .termsOfServiceUrl("地址http://baidu.com/")
                // 信息
                .contact("chc")
                // .contact(contact)
                // 版本号
                .version("版本1.0")
                .build();
    }

}
