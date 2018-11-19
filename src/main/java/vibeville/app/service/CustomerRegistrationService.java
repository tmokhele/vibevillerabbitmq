package vibeville.app.service;

import vibeville.app.model.User;

import java.util.Set;

public interface CustomerRegistrationService {
    boolean saveUser(User user);

    Set<User> getRegistrationRequests();

    boolean deleteRequest(User userLogin);
}
