
package com.chengqian.module.frame.config;

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
 * @author cq
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig  {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            //加了ApiOperation注解的类，才生成接口文档
//            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //包下的类，才生成接口文档
            .apis(RequestHandlerSelectors.basePackage("com.chengqian.module"))
            .paths(PathSelectors.any())
            .build()
           ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("module")
            .description("module")
            .version("1.0.0")
            .build();
    }


//    private List<ApiKey> security() {
//        ArrayList<ApiKey> apiKeys = new ArrayList<>();
//        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
//        return apiKeys;
//    }
//
//    private List<Parameter> setHeaderToken() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name("Authorization").description("验证toke").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//        return pars;
//    }

}