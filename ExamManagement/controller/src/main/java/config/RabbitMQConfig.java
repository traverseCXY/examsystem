package config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //    注入连接工厂，spring的配置
    @Bean
    public ConnectionFactory connectionFactory() {
                 CachingConnectionFactory connection = new CachingConnectionFactory();
                 connection.setAddresses("5672");
                 connection.setUsername("guest");
                 connection.setPassword("guest");
                 connection.setVirtualHost("/");
                 return connection;
    }

 // 配置RabbitAdmin来管理rabbit
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //用RabbitAdmin一定要配置这个，spring加载的是后就会加载这个类================特别重要
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean(name="direct.queue01")
     public Queue answersheetToScoreMaker() {
        return new Queue("examplus.answersheetToScoreMaker");
    }

    //    跟spring整合注入改模板
    @Bean
    public RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }
}
