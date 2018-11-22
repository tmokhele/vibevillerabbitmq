package vibeville.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vibeville.app.model.SNSMessage;
import vibeville.app.service.CustomerRegistrationService;

@RestController
@RequestMapping("/api/email")
public class CustomerEmailController {

    private CustomerRegistrationService customerRegistrationService;

    @Autowired
    public CustomerEmailController(CustomerRegistrationService customerRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
    }

    @PostMapping
    public ResponseEntity messageConfirmation(@RequestBody SNSMessage message, @RequestHeader(value="x-amz-sns-message-type") String messageType)
    {

        if (messageType!=null && messageType.equalsIgnoreCase("Notification"))
        {
            this.customerRegistrationService.saveMessage(message);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }
}
