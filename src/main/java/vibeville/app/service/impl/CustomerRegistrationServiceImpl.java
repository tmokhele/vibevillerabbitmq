package vibeville.app.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vibeville.app.config.RabbitMQConfig;
import vibeville.app.model.User;
import vibeville.app.service.CustomerRegistrationService;

import java.util.Iterator;
import java.util.LinkedHashSet;
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
    public boolean saveUser(User user) throws IllegalArgumentException {
            if (!users.contains(user)) {
                rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTRATION, user);
            }else {
                throw new IllegalArgumentException(String.format("Request for email: %s already exists, please try a new email or wait for your request to be processed",user.getEmail()));
            }
        return true;
    }

    @Override
    public Set<User> getRegistrationRequests() {
        return users;
    }

    @Override
    public boolean deleteRequest(User userLogin) {
        for (Iterator<User> it = users.iterator(); it.hasNext();) {
            User element = it.next();
            if (element.getEmail().equalsIgnoreCase(userLogin.getEmail())) {
                it.remove();
            }
        }
       return true;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTRATION)
    public void getQueueItem(User user)
    {
            users.add(user);

    }
}
