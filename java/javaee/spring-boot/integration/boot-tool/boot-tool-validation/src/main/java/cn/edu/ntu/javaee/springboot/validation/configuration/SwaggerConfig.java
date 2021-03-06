package cn.edu.ntu.javaee.springboot.validation.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zack <br>
 * @create 2020-04-27 11:45 <br>
 */
@EnableKnife4j
@Configuration
@EnableSwagger2
@ConditionalOnProperty(
        prefix = "swagger2",
        value = {"enable"},
        havingValue = "true")
public class SwaggerConfig implements WebMvcConfigurer {

    private final List<ResponseMessage> responseMessageList = new ArrayList<>();

    public SwaggerConfig() {
        responseMessageList.add(
                new ResponseMessageBuilder().code(404).message("Not Found").build());
        responseMessageList.add(
                new ResponseMessageBuilder()
                        .code(400)
                        .message("Internal Error")
                        .responseModel(new ModelRef("ErrorResponse"))
                        .build());
        responseMessageList.add(
                new ResponseMessageBuilder().code(401).message("Unauthorized").build());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // release swagger
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // release relevant js
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestUserApi() {

        return getDocket()
                .groupName("jsr-validation-v1.0")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
        // .apis(RequestHandlerSelectors.basePackage("cn.edu.ntu.boot.swagger2.controller"));
    }

    private Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                // Extensibility mechanism to add a servlet path mapping,
                // if there is one, to the apis base path.
                // .pathMapping("/")
                .globalOperationParameters(parameter())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api Document")
                .description("Api Document Description")
                .termsOfServiceUrl("https://github.com/Alice52/project/")
                .version("1.0")
                .build();
    }

    private List<Parameter> parameter() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(
                new ParameterBuilder()
                        .name("token")
                        .description("Api Token")
                        .modelRef(new ModelRef("String"))
                        .parameterType("header")
                        .required(false)
                        .build());

        return parameters;
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope =
                new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }
}
