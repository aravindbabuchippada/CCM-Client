package com.ccm.client.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerFeedbackConsumerService {
    private final List<String> feedbackStore = new ArrayList<>();

    @KafkaListener(topics = "customer-feedback-topic", groupId = "ccm-client-group")
    public void consumeFeedback(String feedbackMsg) {
        feedbackStore.add(feedbackMsg);
    }

    public List<String> getAllFeedback() {
        return feedbackStore;
    }
}
