package vibeville.app.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vibeville.app.config.RabbitMQConfig;
import vibeville.app.model.User;
import vibeville.app.service.CustomerRegistrationService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final RabbitTemplate rabbitTemplate;
    private List<User> users = new ArrayList<>();

    @Autowired
    public CustomerRegistrationServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public boolean saveUser(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTRATION,user);
        return true;
    }

    @Override
    public List<User> getRegistrationRequests() {
        return users;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTRATION)
    public void getQueueItem(User user)
    {
        users.add(user);
    }
}
