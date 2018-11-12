package vibeville.app.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vibeville.app.config.RabbitMQConfig;
import vibeville.app.model.User;
import vibeville.app.util.ObjectWriterUtil;

@Service
public class CustomerRegistrationQueueService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRegistrationQueueService.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CustomerRegistrationQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void placeUserOnQueue(User user)
    {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTRATION, ObjectWriterUtil.getJsonObject(user));
        } catch (JsonProcessingException e) {
            logger.error(String.format("CustomerRegistrationQueueService Error converting object to json %s",e.getMessage()));
        }
    }
}
