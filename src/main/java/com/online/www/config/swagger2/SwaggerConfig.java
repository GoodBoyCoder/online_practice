package com.online.www.config.swagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.online.www.config.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    @Resource
    private SecurityProperties securityProperties;
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name(securityProperties.getTokenHeader()).description("认证token")
                .modelRef(new ModelRef("string")).parameterType("header")
                //header中的ticket参数非必填，传空也可以
                .required(false).build();
        //根据每个方法名也知道当前方法在设置什么参数
        pars.add(ticketPar.build());
        // DocumentationType.SWAGGER_2 固定的，代表swagger2
        //                .groupName("分布式任务系统")
        // 如果配置多个文档的时候，那么需要配置groupName来分组标识
        return new Docket(DocumentationType.SWAGGER_2)
                // 用于生成API信息
                .apiInfo(apiInfo())
                // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
                .select()
                // 用于指定扫描哪个包下的接口
                .apis(RequestHandlerSelectors.basePackage("com.online.www.controller"))
                // 选择所有的API,如果你想只为部分API生成文档，可以配置这里
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    /**
     * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("驾校宝典Practice项目API")
                .description("驾校宝典Practice项目SwaggerAPI管理")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
