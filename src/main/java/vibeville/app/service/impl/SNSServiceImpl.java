package vibeville.app.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vibeville.app.service.SNSService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SNSServiceImpl implements SNSService {

    private static final Logger logger = LoggerFactory.getLogger(SNSServiceImpl.class);

    @Value("${KEY_ID}")
    private String awsAccessKeyId;
    @Value("${ACCESS_KEY}")
    private String awsAccessKeySecret;
    @Value("${REGION}")
    private String awsRegion;

    private AmazonSNS snsClient;

    @PostConstruct
    public void setConnection() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessKeySecret);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        snsClient = AmazonSNSClient.builder()
                .withRegion(awsRegion)
                .withCredentials(awsCredentialsProvider)
                .build();
    }

    @Override
    public String createTopic(String topicName) {
        CreateTopicRequest topicRequest = new CreateTopicRequest(topicName);
        CreateTopicResult topicResult = snsClient.createTopic(topicRequest);

        logger.info("Create topic request: " + snsClient.getCachedResponseMetadata(topicRequest));
        logger.info("Create topic result: " + topicResult);
        return topicResult.getTopicArn();
    }

    @Override
    public void subscribeSNSToTopic(String topicArn, String phoneNumber) {
        String protocol = "sms";
        SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, protocol, phoneNumber);
        SubscribeResult subscribeResult = snsClient.subscribe(subscribeRequest);

        logger.info("Subscribe request: " + snsClient.getCachedResponseMetadata(subscribeRequest));
        logger.info("Subscribe result: " + subscribeResult);

    }

    @Override
    public void sendSMSMessageToTopic(String topicArn, String message) {
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("mySenderID") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.50") //Sets the max price to 0.50 USD.
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Promotional") //Sets the type to promotional.
                .withDataType("String"));

        PublishResult publishResult = snsClient.publish(new PublishRequest()
                .withTopicArn(topicArn)
                .withMessage(message)
                .withMessageAttributes(smsAttributes));
        logger.info("Public Result: " + publishResult);

    }
}
