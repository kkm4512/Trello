package com.example.trello.domain.slack;

import com.example.trello.common.annotation.CardChangeSlack;
import com.example.trello.common.annotation.CommentAddSlack;
import com.example.trello.common.annotation.MemberAddSlack;
import com.example.trello.common.annotation.Slack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackService {
    private final RestTemplate restTemplate;

    @Value("${slack.bot-token}")
    private String token;
    public String getIdByEmail(String email){
        try {
            // URL Setting
            String url = "https://slack.com/api/users.lookupByEmail" + "?email=" + email;

            // Header
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            // restTemplate
            HttpEntity<String> reqEntity = new HttpEntity<>(headers);
            ResponseEntity<String> resEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    reqEntity,
                    String.class
            );

            JSONObject jsonObject = new JSONObject(resEntity.getBody());
            JSONObject user = jsonObject.getJSONObject("user");
            return user.getString("id");

        } catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    };

    public void sendMessage(String channel, String text){
        try {
            // URL Setting
            String url = "https://slack.com/api/chat.postMessage" + "?channel=" + channel + "&text=" + text;

            // Header
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            // restTemplate
            HttpEntity<String> reqEntity = new HttpEntity<>(headers);
            restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    reqEntity,
                    String.class
            );

        } catch (Exception e){
            log.error(e.getMessage());
        }
    };
    @Slack
    public void SlackTest(){

    }

    @MemberAddSlack
    public void memberAddSlackTest(String name){

    }

    @CardChangeSlack
    public void cardChangeSlackTest(){

    }

    @CommentAddSlack
    public void commentAddSlackTest() {
    }
}
