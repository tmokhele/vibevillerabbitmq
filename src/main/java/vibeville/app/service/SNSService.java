package vibeville.app.service;

public interface SNSService {
    String createTopic(String topicName);

    void subscribeSNSToTopic(String topicArn, String phoneNumber);

    public void sendSMSMessageToTopic(String topicArn, String message);
}
