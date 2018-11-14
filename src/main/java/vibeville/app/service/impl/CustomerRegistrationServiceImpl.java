package vibeville.app.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vibeville.app.config.RabbitMQConfig;
import vibeville.app.model.User;
import vibeville.app.service.CustomerRegistrationService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final RabbitTemplate rabbitTemplate;
    private Set<User> users = new LinkedHashSet<>();

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
    public Set<User> getRegistrationRequests() {
        return users;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTRATION)
    public void getQueueItem(User user)
    {
        if (!users.contains(user)) {
            users.add(user);
        }else throw new IllegalArgumentException(String.format("Request for email: %s already exists, please try a new email or wait for your request to be processed",user.getEmail()));
    }
}
