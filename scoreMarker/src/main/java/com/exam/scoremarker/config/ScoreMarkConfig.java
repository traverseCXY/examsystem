package com.exam.scoremarker.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.exam.scoremarker.ScoreMarkerMain;
import com.exam.scoremarker.entity.Constants;
import com.exam.scoremarker.entity.ExamPaper;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.context.annotation.Import;

/**
 * 配置类
 */
@Configuration
@ComponentScan("com.exam.scoremarker")
@Import(RestTemplateConfig.class)
public class ScoreMarkConfig {

    private static final Logger LOGGER = Logger.getLogger(ScoreMarkerMain.class);

    /*获取消费者*/
    @Bean
    QueueingConsumer queueingConsumer() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setPassword("guest");
        factory.setUsername("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Constants.ANSWERSHEET_DATA_QUEUE, true, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(Constants.ANSWERSHEET_DATA_QUEUE, true, consumer);
        return consumer;

    }

    @Bean
    ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }

    @Bean
    HashMap<String, ExamPaper> examPapersMap()
    {
        return new HashMap<String, ExamPaper>();
    }

    @Bean
    String answerSheetPostUri()
    {
        return "http://localhost:8080/Management/api/answersheet";
    }

    @Bean
    String examPaperGetUri()
    {
        return "http://localhost:8080/Management/api/exampaper";
    }

}
