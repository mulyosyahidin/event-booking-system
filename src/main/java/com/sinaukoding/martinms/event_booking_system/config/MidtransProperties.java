package com.sinaukoding.martinms.event_booking_system.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.midtrans")
@Getter
@Setter
public class MidtransProperties {

    private String merchantId;
    private String clientKey;
    private String serverKey;
    private boolean production;

}
