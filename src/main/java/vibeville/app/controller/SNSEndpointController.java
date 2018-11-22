package vibeville.app.controller;

import com.rabbitmq.tools.json.JSONReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vibeville.app.model.SNSMessage;

@Controller
@RequestMapping("/topic-subscriber")
public class SNSEndpointController {
    private static final Logger logger = LoggerFactory.getLogger(SNSEndpointController.class);
    @NotificationSubscriptionMapping
    public void confirmUnsubscribeMessage( SNSMessage status) {
        logger.error("testing"+ status);

    }

    @NotificationMessageMapping
    public void receiveNotification(@NotificationMessage String message,
                                    @NotificationSubject String subject) {
        // handle message
    }

//    @NotificationUnsubscribeConfirmationMapping
//    public void confirmSubscriptionMessage(@NotificationMessage
//            NotificationStatus notificationStatus) {
//
//        notificationStatus.confirmSubscription();
//    }
}
