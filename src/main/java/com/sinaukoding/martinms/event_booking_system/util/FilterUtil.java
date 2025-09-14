package com.sinaukoding.martinms.event_booking_system.util;

import com.sinaukoding.martinms.event_booking_system.builder.CustomBuilder;
import com.sinaukoding.martinms.event_booking_system.builder.CustomSpecification;
import com.sinaukoding.martinms.event_booking_system.builder.SearchCriteria;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class FilterUtil {

    public static void builderConditionNotBlankEqual(String field, String value, CustomBuilder<?> builder) {
        if (StringUtils.isNotBlank(value)) {
            builder.with(SearchCriteria.of(field, CustomSpecification.OPERATION_EQUAL, value));
        }
    }

    public static void builderConditionNotNullEqual(String field, Object value, CustomBuilder<?> builder) {
        if (value != null) {
            builder.with(SearchCriteria.of(field, CustomSpecification.OPERATION_EQUAL, value));
        }
    }

    public static void builderConditionNotBlankLike(String field, String value, CustomBuilder<?> builder) {
        if (StringUtils.isNotBlank(value)) {
            builder.with(SearchCriteria.of(field, CustomSpecification.OPERATION_LIKE, value));
        }
    }

    public static void builderConditionNotBlankEqualJoin(String field, String value, CustomBuilder<?> builder) {
        if (StringUtils.isNotBlank(value)) {
            builder.with(SearchCriteria.of(field, CustomSpecification.OPERATION_JOIN_EQUAL, value));
        }
    }

    public static void builderConditionNotBlankInJoin(String field, Set<String> listValue, CustomBuilder<?> builder) {
        if (listValue != null && !listValue.isEmpty()) {
            builder.with(SearchCriteria.of(field, CustomSpecification.OPERATION_JOIN_IN, listValue));
        }
    }

}
