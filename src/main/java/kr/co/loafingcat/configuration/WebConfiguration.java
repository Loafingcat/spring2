package kr.co.loafingcat.configuration;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import kr.co.loafingcat.configuration.servlet.handler.BaseHandlerInterceptor;
import kr.co.loafingcat.framework.data.web.MySQLPageRequestHandleMethodArgumentResolver;
import kr.co.loafingcat.mvc.domain.BaseCodeLabelEnum;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	
	@Autowired
	private GlobalConfig config;
	
	private static final String WINDOWS_FILE = "file:///";
	private static final String LINUX_FILE = "file:";

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		// spring에서 제공하는 메세지 소스.
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		// setBasename을 통해서 클래스 패스에 메세지 폴더에 메세지를 읽어들임.
		source.setBasename("classpath:/messages/message");
		// 인코딩은 UTF-8로
		source.setDefaultEncoding("UTF-8");
		// CacheSeconds는 뭐야 찾아보자
		source.setCacheSeconds(60);
		// DefaultLocale은 한국어로
		source.setDefaultLocale(Locale.KOREAN);
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

	@Bean
	public BaseHandlerInterceptor baseHandlerInterceptor() {
		return new BaseHandlerInterceptor();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(BaseCodeLabelEnum.class, new BaseCodeLabelEnumJsonSerializer());
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}

	@Bean
	public MappingJackson2JsonView mappingJackson2JsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setContentType(MediaType.APPLICATION_JSON_VALUE);
		jsonView.setObjectMapper(objectMapper());
		return jsonView;
	}
	
	@Bean
	public GlobalConfig config() {
		return new GlobalConfig();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(baseHandlerInterceptor());
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// 페이지 리졸버 등록
		resolvers.add(new MySQLPageRequestHandleMethodArgumentResolver());
		
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 업로드 파일 static resource 접근 경로
		String resourcePattern = config.getUploadResourcePath() + "**";
		//로컬(윈도우 환경)
		if (config.isLocal()) {
			registry.addResourceHandler(resourcePattern)
			.addResourceLocations("file:///" + config.getUploadFilePath());
		} else {
			// 리눅스 또는 유닉스 환경
			registry.addResourceHandler(resourcePattern)
			.addResourceLocations("file:" + config.getUploadFilePath());
		}
	}
}