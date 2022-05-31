package com.lh.springboot03;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import javax.servlet.MultipartConfigElement;

@Configuration//表示配置类
@EnableTransactionManagement// 启动注解事务
//@EnableMongoPlus
@SpringBootApplication
//@MapperScan("com.lh.springboot03.mapper")//扫描指定包下的mapper（不用每个mapper都标注了）
public class Springboot03Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot03Application.class, args);
    }

    //设置文件最大传输大小
    @Bean
    public MultipartConfigElement getMultipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setFileSizeThreshold(DataSize.of(15, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(20,DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    //设置fastJson配置
    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd  HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }


}
