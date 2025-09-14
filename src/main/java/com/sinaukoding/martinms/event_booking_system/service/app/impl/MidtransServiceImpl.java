package com.sinaukoding.martinms.event_booking_system.service.app.impl;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.sinaukoding.martinms.event_booking_system.config.MidtransProperties;
import com.sinaukoding.martinms.event_booking_system.entity.Booking;
import com.sinaukoding.martinms.event_booking_system.service.app.IMidtransService;
import com.sinaukoding.martinms.event_booking_system.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://github.com/mulyosyahidin/spring-boot-vocasia-microservices/blob/main/development/cloud/payment/src/main/java/com/vocasia/payment/service/impl/MidtransPaymentServiceImpl.java
@Service
@RequiredArgsConstructor
public class MidtransServiceImpl implements IMidtransService {

    @Value("${payment.fee}")
    private Double paymentFee;

    @Value("${app.base_url}")
    private String baseUrl;

    private final MidtransProperties midtransProperties;

    private List<Map<String, Object>> getMaps(Booking booking) {
        List<Map<String, Object>> itemDetails = new ArrayList<>();

        Map<String, Object> additionalFeeItemDetail = new HashMap<>();

        additionalFeeItemDetail.put("id", "additional_fee");
        additionalFeeItemDetail.put("quantity", 1);
        additionalFeeItemDetail.put("price", paymentFee);
        additionalFeeItemDetail.put("name", "Biaya Admin");

        itemDetails.add(additionalFeeItemDetail);

        Map<String, Object> itemDetail = new HashMap<>();
        Double finalPrice = booking.getHarga();

        itemDetail.put("id", booking.getEvent().getId());
        itemDetail.put("quantity", 1);
        itemDetail.put("price", finalPrice);
        itemDetail.put("name", Util.cutString(booking.getEvent().getNama(), 20));

        itemDetails.add(itemDetail);

        return itemDetails;
    }

    @Override
    public String createTransaction(Booking booking) {
        try {
            Double totalPrice = booking.getHarga();

            String grossAmount = String.valueOf(totalPrice + paymentFee);

            MidtransSnapApi snapApi = new ConfigFactory(
                    new Config(
                            midtransProperties.getServerKey(),
                            midtransProperties.getClientKey(),
                            midtransProperties.isProduction())).getSnapApi();

            Map<String, Object> params = new HashMap<>();

            Map<String, String> transactionDetails = new HashMap<>();
            transactionDetails.put("order_id", booking.getKodeBooking());
            transactionDetails.put("gross_amount", grossAmount);
            params.put("transaction_details", transactionDetails);

            List<Map<String, Object>> itemDetails = getMaps(booking);
            params.put("item_details", itemDetails);

            Map<String, Object> customerDetail = new HashMap<>();

            customerDetail.put("first_name", booking.getUser().getNama());
            customerDetail.put("email", booking.getUser().getEmail());
            params.put("customer_details", customerDetail);

            Map<String, String> callbacks = new HashMap<>();
            callbacks.put("finish", baseUrl + "/users/bookings/data");
            params.put("callbacks", callbacks);

            return snapApi.createTransactionToken(params);
        } catch (MidtransError e) {
            throw new RuntimeException("Gagal membuat transaksi: " + e.getMessage());
        }
    }

}
