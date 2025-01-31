package com.hotel_management.configuration;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    private final String accountSid;
    private final String authToken;

    public TwilioConfig(@Value("${twilio.account.sid}") String accountSid,
                        @Value("${twilio.auth.token}") String authToken) {
        this.accountSid = accountSid;
        this.authToken = authToken;
    }

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
}
