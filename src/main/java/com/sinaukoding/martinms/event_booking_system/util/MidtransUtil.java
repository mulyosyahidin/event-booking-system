package com.sinaukoding.martinms.event_booking_system.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MidtransUtil {

    public static String generateMidtransPaymentUrl(String token, boolean isProduction) {
        String baseUrl = isProduction
                ? "https://app.midtrans.com"
                : "https://app.sandbox.midtrans.com";

        return baseUrl + "/snap/v4/redirection/" + token + "#/payment-list";
    }

    public static String generateMidtransHash(String orderId, String statusCode, String grossAmount, String serverKey) {
        try {
            String input = orderId + statusCode + grossAmount + serverKey;

            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash using SHA-512", e);
        }
    }
}
