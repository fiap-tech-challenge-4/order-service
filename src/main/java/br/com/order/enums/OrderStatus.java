package br.com.order.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {

    RECEIVED,
    PREPARING,
    READY,
    COMPLETED;

    public static OrderStatus toOrderStatusString(String value) {
        return Arrays.stream(OrderStatus.values())
          .filter(type -> type.name().equalsIgnoreCase(value))
          .findFirst()
          .orElse(null);
    }

}
