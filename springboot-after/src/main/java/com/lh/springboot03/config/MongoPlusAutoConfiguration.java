package com.lh.springboot03.config;


import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnClass({MongoClient.class})
@EnableConfigurationProperties({MongoOptionProperties.class})
@ConditionalOnMissingBean(
        type = {"org.springframework.data.mongodb.MongoDatabaseFactory"}
)

/**
 * mongodb 连接高级配置
 * */
public class MongoPlusAutoConfiguration {
    public MongoPlusAutoConfiguration() {
    }
    @Bean
    @ConditionalOnMissingBean({MongoClient.class})
    public MongoClient mongo(MongoProperties properties, Environment environment, ObjectProvider<MongoClientSettingsBuilderCustomizer> builderCustomizers, MongoOptionProperties mongoOptionProperties) {

        MongoClientSettings.Builder mySetting = MongoClientSettings.builder();

        mySetting.applyToConnectionPoolSettings(builder -> {
            builder.maxSize(mongoOptionProperties.getMaxConnectionPerHost());
            builder.minSize(mongoOptionProperties.getMinConnectionPerHost());
            builder.maxWaitTime(mongoOptionProperties.getMaxWaitTime(),TimeUnit.MILLISECONDS);
            builder.maxConnectionIdleTime(mongoOptionProperties.getMaxConnectionIdleTime(),TimeUnit.MILLISECONDS);
            builder.maxConnectionLifeTime(mongoOptionProperties.getMaxConnectionLifeTime(),TimeUnit.MILLISECONDS);
        });
        mySetting.applyToClusterSettings(builder -> {
            builder.serverSelectionTimeout(mongoOptionProperties.getServerSelectionTimeout(), TimeUnit.MILLISECONDS);
            builder.localThreshold(mongoOptionProperties.getLocalThreshold(),TimeUnit.MILLISECONDS);
        });
        mySetting.applyToServerSettings(builder -> {
            builder.heartbeatFrequency(mongoOptionProperties.getHeartbeatFrequency(),TimeUnit.MILLISECONDS);
            builder.minHeartbeatFrequency(mongoOptionProperties.getHeartbeatFrequency(),TimeUnit.MILLISECONDS);

        });
        mySetting.applyToSocketSettings(builder -> {
            builder.connectTimeout(mongoOptionProperties.getConnectTimeout(),TimeUnit.MILLISECONDS);
        });
        return (new MongoClientFactory(builderCustomizers.orderedStream().collect(Collectors.toList()))).createMongoClient(mySetting.build());
    }
}



