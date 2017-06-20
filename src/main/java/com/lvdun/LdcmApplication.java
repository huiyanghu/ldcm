package com.lvdun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.lvdun.dao")
@EntityScan(basePackages = "com.lvdun.entity")
@ServletComponentScan//扫描Servlet
@EnableTransactionManagement//开启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableScheduling//发现注解@Scheduled的任务并后台执行
public class LdcmApplication {
    protected final static Logger logger = LoggerFactory.getLogger(LdcmApplication.class);

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        System.out.println("----------EmbeddedServletContainerCustomizer containerCustomizer-----------");
        //使用Java 7内部类的一个等价实现方式
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
                container.addErrorPages(error401Page, error404Page, error500Page);
            }
        };

        //Java 8的lambda表达式来简化实现的方式
        /*return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");
            container.addErrorPages(error401Page, error404Page, error500Page);
        });*/
    }


    public static void main(String[] args) {
        SpringApplication.run(LdcmApplication.class, args);
        /*SpringApplication app = new SpringApplication(LdcmApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);*/
    }
}
