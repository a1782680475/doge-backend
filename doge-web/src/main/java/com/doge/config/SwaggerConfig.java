package com.doge.config;

import cn.hutool.core.collection.CollUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.Example;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置类
 *
 * @author shixinyu
 * @date 2021-06-09 08:36
 */
@Configuration
@EnableOpenApi
@Profile({"dev", "test"})
public class SwaggerConfig implements WebMvcConfigurer, ApiListingScannerPlugin {

    /**
     * 配置docket以配置Swagger具体参数
     *
     * @param
     * @return springfox.documentation.spring.web.plugins.Docket
     */
    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .securitySchemes(Collections.singletonList(HttpAuthenticationScheme.JWT_BEARER_BUILDER
                        .name("Authorization")
                        .build()))
                .securityContexts(Collections.singletonList(SecurityContext.builder()
                        .securityReferences(Collections.singletonList(SecurityReference.builder()
                                .scopes(new AuthorizationScope[0])
                                .reference("Authorization")
                                .build()))
                        .operationSelector(o -> o.requestMappingPattern().matches("^(?!/auth/login).*$"))
                        .operationSelector(o -> o.requestMappingPattern().matches("^(?!/auth/register).*$"))
                        .build()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doge.controller"))
                .build();
    }

    /**
     * 配置文档信息
     *
     * @param
     * @return springfox.documentation.service.ApiInfo
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("山东星科智能科技股份有限公司", "https://www.xktech.com", "xktechrs@163.com");
        return new ApiInfoBuilder().title("多吉后台管理系统接口文档")
                .description("多吉后台管理系统接口文档")
                .contact(contact)
                .version("1.0")
                .build();
    }

    /**
     * 目前登录是Filter拦截处理，需要手动添加文档
     *
     * @param context DocumentationContext
     * @return List<ApiDescription>
     */
    @Override
    public List<ApiDescription> apply(DocumentationContext context) {
        Operation usernamePasswordOperation = new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.POST)
                .summary("用户名/密码登录")
                .notes("用户名/密码登录")
                .tags(CollUtil.newHashSet("用户登录注册等相关业务"))
                // 接收参数格式
                .consumes(CollUtil.newHashSet(MediaType.APPLICATION_JSON_VALUE))
                // 返回参数格式
                .produces(CollUtil.newHashSet(MediaType.APPLICATION_JSON_VALUE))
                .requestParameters(Arrays.asList(
                        new RequestParameterBuilder()
                                .description("用户名")
                                .name("username")
                                .required(true)
                                .in(ParameterType.QUERY)
                                .parameterIndex(0)
                                .build(),
                        new RequestParameterBuilder()
                                .description("密码")
                                .name("password")
                                .required(true)
                                .in(ParameterType.QUERY)
                                .parameterIndex(1)
                                .build(),
                        new RequestParameterBuilder()
                                .description("验证码")
                                .name("imgCaptcha")
                                .required(true)
                                .in(ParameterType.QUERY)
                                .parameterIndex(2)
                                .build()
                ))
                .responses(CollUtil.newArrayList(new Response("200", "OK", true, CollUtil.newArrayList(), CollUtil.newArrayList(), CollUtil.newArrayList(new Example("1", null, null, "{\n" +
                        "\t\"code\": 200,\n" +
                        "\t\"remark\": \"登录成功\",\n" +
                        "\t\"result\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTYyMzgxNzQ1OCwidXNlcmlkIjoxLCJjcmVhdGVkIjoxNjIzODEwMjU4NDQ3LCJ1c2VybmFtZSI6InVzZXIxIn0.783CcRu_YyKhFVyXipxzFad14sni19TvSJanSaR6ap5yXQnv4734sB8WZ8V--ovaQzCaV7YvocNC4HFpJey6sg\"\n" +
                        "}", null, MediaType.APPLICATION_JSON_VALUE)), CollUtil.newArrayList())))
                .build();
        ApiDescription loginApiDescription = new ApiDescription("login", "/auth/login", "登录", "用户登录接口",
                Arrays.asList(usernamePasswordOperation), false);
        return Arrays.asList(loginApiDescription);
    }


    /**
     * 是否使用此插件
     *
     * @param documentationType swagger文档类型
     * @return true 启用
     */
    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.OAS_30.equals(documentationType);
    }
}
