package vibeville.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vibeville.app.model.User;
import vibeville.app.service.CustomerRegistrationService;

@RestController
@RequestMapping("/api/user")
public class CustomerRegistrationController {


    private CustomerRegistrationService customerRegistrationService;

    @Autowired
    public CustomerRegistrationController(CustomerRegistrationService customerRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
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
            return ResponseEntity.status(HttpStatus.Series.CLIENT_ERROR.value()).body(ex);
        }
        return  ResponseEntity.ok().body(user);
    }
    @GetMapping
    public ResponseEntity getRegistrationRequests()
    {
        return ResponseEntity.ok().body(customerRegistrationService.getRegistrationRequests());
    }
}
