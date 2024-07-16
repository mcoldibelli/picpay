package com.picpay.challenge_backend.services;

import com.picpay.challenge_backend.domain.user.User;
import com.picpay.challenge_backend.exceptions.NotificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.notification.url}")
    private String notificationUrl;

    public void sendNotification(User user, String message) {
        String email = user.getEmail();

        try {
            ResponseEntity<Map> notificationResponse = this.restTemplate.postForEntity(notificationUrl, email, Map.class);
            Map body = notificationResponse.getBody();

            if (body == null) {
                System.out.println(message);
            }
        } catch(HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("An error occurred. Requesting service again");
            this.sendNotification(user, message);
        } catch(Exception e) {
            throw new NotificationException("An unexpected error occurred");
        }
    }
}
