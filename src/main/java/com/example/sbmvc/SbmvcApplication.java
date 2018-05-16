package com.example.sbmvc;

import com.example.sbmvc.validatecode.Captcha;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
//@Order(value = 1)
public class SbmvcApplication {

	public static void main(String[] args) {
		/*SpringApplicationBuilder app = new SpringApplicationBuilder();

		app.banner(new Banner() {
			@Override
			public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
				out.println("Hello World!");
			}
		}).listeners(new ApplicationListener<ApplicationEvent>() {
			@Override
			public void onApplicationEvent(ApplicationEvent applicationEvent) {

				System.out.println("=========== Application start =========="+applicationEvent.getSource());
			}
		}).build().run(SbmvcApplication.class, args);
		*/
		//SpringApplication ap = new SpringApplication();
		/*ap.addListeners(new ApplicationListener<ApplicationEvent>() {
			@Override
			public void onApplicationEvent(ApplicationEvent applicationEvent) {
				System.out.println("=========== Application start =========="+applicationEvent.getSource());
			}
		});
		ap.setBanner(new Banner() {
			@Override
			public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
				out.println("Hello World!");
			}
		});*/
		//ap.run(SbmvcApplication.class, args);


		//LocaleContextHolder.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);

		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));

		SpringApplication.run(SbmvcApplication.class, args);
	}

	@Bean
	public Captcha createKcaptchaProducer(){
		return new Captcha("captcha");
	}

	@Bean
	public MessageSource messageSource(){
		ResourceBundleMessageSource rms = new ResourceBundleMessageSource();
		rms.setBasename("i18n/messages");
		return rms;
	}

	@Bean
	public WebMvcConfigurer getWebMvcConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void configurePathMatch(PathMatchConfigurer configurer) {

				//使用后缀，默认true , 即 当请求 /users 等价 于 /users.*
				configurer.setUseSuffixPatternMatch(false);  //设置 路径后台的 .* 为有效，不应该忽略

				//使用反斜杠，默认true , 即 当请求 /users 等价 于 /users/
				configurer.setUseTrailingSlashMatch(true);

			}

			@Override
			public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
				/*configurer.defaultContentType(MediaType.APPLICATION_JSON)
							.ignoreAcceptHeader(true)
							.mediaType("html" , MediaType.TEXT_HTML)
							.mediaType("xml" , MediaType.TEXT_XML)
							.mediaType("json" , MediaType.APPLICATION_JSON);*/
			}

			@Override
			public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

			}

			@Override
			public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

			}

			@Override
			public void addFormatters(FormatterRegistry registry) {

			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {

			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {

			}

			@Override
			public void addCorsMappings(CorsRegistry registry) {

			}

			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				//直接视图映射，不需要在controller里写方法和return view了
				//registry.addViewController("/test.html").setViewName("view/test.html");
				//registry.addViewController("/test2.html").setViewName("view/test2.html");

				registry.addViewController("/").setViewName("view/index.html");
				registry.addViewController("/index.html").setViewName("view/index.html");
				registry.addViewController("/login.html").setViewName("view/login.html");
				registry.addViewController("/auth.html").setViewName("view/auth.html");
				registry.addViewController("/public.html").setViewName("view/public.html");

				registry.addViewController("/403.html").setViewName("view/error/403.html");
				registry.addViewController("/500.html").setViewName("view/error/500.html");
				registry.addRedirectViewController("/error" , "/500.html");
			}

			@Override
			public void configureViewResolvers(ViewResolverRegistry registry) {

			}

			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

			}

			@Override
			public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

			}

			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
				/*//super.configureMessageConverters(converters);
				//1.需要定义一个convert转换消息的对象;
				FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
				//2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
				FastJsonConfig fastJsonConfig = new FastJsonConfig();
				fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
				//3处理中文乱码问题
				List<MediaType> fastMediaTypes = new ArrayList<>();
				fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
				//4.在convert中添加配置信息.
				fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
				fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
				//5.将convert添加到converters当中.
				converters.add(fastJsonHttpMessageConverter);*/
			}

			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
			}

			@Override
			public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

			}

			@Override
			public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

			}

			@Nullable
			@Override
			public Validator getValidator() {
				return null;
			}

			@Nullable
			@Override
			public MessageCodesResolver getMessageCodesResolver() {
				return new DefaultMessageCodesResolver();
			}
		};
	}
}

