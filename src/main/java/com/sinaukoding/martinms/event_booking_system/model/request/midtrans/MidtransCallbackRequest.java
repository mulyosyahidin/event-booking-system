package com.sinaukoding.martinms.event_booking_system.model.request.midtrans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MidtransCallbackRequest {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("gross_amount")
    private String grossAmount;

    @JsonProperty("signature_key")
    private String signatureKey;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("fraud_status")
    private String fraudStatus;

    @JsonProperty("transaction_time")
    private String transactionTime;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("settlement_time")
    private String settlementTime;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("expiry_time")
    private String expiryTime;

}
