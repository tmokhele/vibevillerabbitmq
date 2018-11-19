package vibeville.app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vibeville.app.model.User;
import vibeville.app.service.CustomerRegistrationService;
import vibeville.app.service.EmailService;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/user")
public class CustomerRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRegistrationController.class);


    private CustomerRegistrationService customerRegistrationService;
    private EmailService emailService;

    @Autowired
    public CustomerRegistrationController(CustomerRegistrationService customerRegistrationService,EmailService emailService) {
        this.customerRegistrationService = customerRegistrationService;
        this.emailService = emailService;
    }

    @GetMapping("/ping")
    public ResponseEntity ping()
    {
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity userRegistration(@RequestBody User user)
    {
        try {
            customerRegistrationService.saveUser(user);
        }catch (IllegalArgumentException ex)
        {
            logger.error("Exception: "+ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return  ResponseEntity.ok().body(user);
    }
    @GetMapping
    public ResponseEntity getRegistrationRequests()
    {
        return ResponseEntity.ok().body(customerRegistrationService.getRegistrationRequests());
    }

    @PostMapping("/remove")
    public ResponseEntity deleteById(@RequestBody User userLogin){
        return ResponseEntity.ok().body(customerRegistrationService.deleteRequest(userLogin));
    }

    @PostMapping("/send")
    public ResponseEntity sendNotification(@RequestBody User userLogin) throws MessagingException {
        return ResponseEntity.ok().body(emailService.sendSimpleMessage(userLogin.getEmail()));
    }
}
