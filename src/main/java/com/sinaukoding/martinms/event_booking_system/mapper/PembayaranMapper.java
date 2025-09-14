package com.sinaukoding.martinms.event_booking_system.mapper;

import com.sinaukoding.martinms.event_booking_system.config.MidtransProperties;
import com.sinaukoding.martinms.event_booking_system.entity.Pembayaran;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.util.DateUtil;
import com.sinaukoding.martinms.event_booking_system.util.MidtransUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PembayaranMapper {

    private final MidtransProperties midtransProperties;

    public SimpleMap entityToSimpleMap(Pembayaran pembayaran) {
        SimpleMap simpleMap = new SimpleMap();

        simpleMap.put("id", pembayaran.getId());
        simpleMap.put("status", pembayaran.getStatus());
        simpleMap.put("token", pembayaran.getToken());
        simpleMap.put("harga", pembayaran.getHarga());
        simpleMap.put("fee", pembayaran.getFee());
        simpleMap.put("total", pembayaran.getTotal());
        simpleMap.put("expired_at", DateUtil.formatLocalDateTimeToString(pembayaran.getExpiredAt()));
        simpleMap.put("paid_at", DateUtil.formatLocalDateTimeToString(pembayaran.getPaidAt()));
        simpleMap.put("payment_url", MidtransUtil.generateMidtransPaymentUrl(pembayaran.getToken(), midtransProperties.isProduction()));

        return simpleMap;
    }

}
