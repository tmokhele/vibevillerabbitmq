package vibeville.app.service;

import vibeville.app.model.User;

import java.util.List;

public interface CustomerRegistrationService {
    boolean saveUser(User user);

    List<User> getRegistrationRequests();
}
