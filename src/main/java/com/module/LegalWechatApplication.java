package com.module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@SpringBootApplication
@ComponentScan("com")
@MapperScan("com.module.mapper")
@EnableScheduling
public class LegalWechatApplication {

//	@Resource
//	@Getter
//	private ServiceFactory serviceFactory;
//
//	@Resource
//	@Getter
//    DaoFactory daoFactory;

//	public static void main(String[] args) {
//		//SpringApplication.run(LegalWechatApplication.class, args);
//		new SpringApplicationBuilder(LegalWechatApplication.class).web(true).run(args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder LegalWechatApplication) {
//		return LegalWechatApplication.sources(com.module.LegalWechatApplication.class);
//	}

//	static {
//		DBManager.init();
//	}


//	@PostConstruct  //@PostConstruct优先级低于@Configuration
//	public void init() {
//		serviceFactory.getCreateIndexService().createIndexMongoDb();
//
//	}

//	@PreDestroy
//	public void destory() {
//
//	}

	public static void main(String[] args) {
		SpringApplication.run(LegalWechatApplication.class, args);
	}


}
