package vibeville.app;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.TimeZone;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class })
public class VibeVilleRabbitClient {

    public static final String USER_REGISTRATION_REPROCESS = "reprocessing-user-registration-queue";
    public static final String USER_REGISTRATION = "user-registration-queue";
    private static final String EXCHANGE_USER_REGISTRATION = "user-registration-exchange";

    @Bean
    Queue userRegistrationQueue() {
        return QueueBuilder.durable(USER_REGISTRATION)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", USER_REGISTRATION_REPROCESS)
                .build();
    }
    @Bean
    Queue reprocessingUserRegistrationQueue() {
        return QueueBuilder.durable(USER_REGISTRATION_REPROCESS).build();
    }
    @Bean
    TopicExchange userRegistrationExchange() {
        return new TopicExchange(EXCHANGE_USER_REGISTRATION);
    }

    @Bean
    Binding bindingPublisher(Queue userRegistrationQueue, TopicExchange publisherExchange) {
        return BindingBuilder.bind(userRegistrationQueue).to(publisherExchange).with(USER_REGISTRATION);
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        return new DefaultMessageHandlerMethodFactory();
    }

    @Bean
    public VelocityEngine velocityEngine(){
        Properties properties = new Properties();
        properties.put("resource.loader", "class");
        properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityEngine(properties);
    }


    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    public static void main(String[] args) {
        SpringApplication.run(VibeVilleRabbitClient.class, args);
    }
}
