package vibeville.app.controller;

import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;

@Controller
@RequestMapping("/topic-subscriber")
public class SNSEndpointController {

    @NotificationSubscriptionMapping
    public void confirmUnsubscribeMessage(
            NotificationStatus notificationStatus) {
        notificationStatus.confirmSubscription();
    }

    @NotificationMessageMapping
    public void receiveNotification(@NotificationMessage String message,
                                    @NotificationSubject String subject) {
        // handle message
    }

    @NotificationUnsubscribeConfirmationMapping
    public void confirmSubscriptionMessage(
            NotificationStatus notificationStatus) {
        notificationStatus.confirmSubscription();
    }
}
