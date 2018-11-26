package vibeville.app.controller;

import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.SubscribeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topic-subscriber")
public class SNSEndpointController {
    private static final Logger logger = LoggerFactory.getLogger(SNSEndpointController.class);
    static AmazonSNS snsService;
    ResponseMetadata cachedResponseMetadata;
    SubscribeResult subscribe;

}
