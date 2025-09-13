package com.sinaukoding.martinms.event_booking_system.mapper;

import com.sinaukoding.martinms.event_booking_system.entity.User;
import com.sinaukoding.martinms.event_booking_system.model.app.SimpleMap;
import com.sinaukoding.martinms.event_booking_system.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public SimpleMap entityToSimpleMap(User user) {
        return SimpleMap.createMap()
                .add("id", user.getId())
                .add("nama", user.getNama())
                .add("username", user.getUsername())
                .add("email", user.getEmail())
                .add("phoneNumber", user.getPhoneNumber())
                .add("role", user.getRole().getLabel())
                .add("status", user.getStatus().getLabel())
                .add("createdDate", DateUtil.formatLocalDateTimeToString(user.getCreatedDate()))
                .add("lastModifiedDate", DateUtil.formatLocalDateTimeToString(user.getModifiedDate()));
    }

}
