package com.codesetters.service.dto;

import com.codesetters.domain.OrderStatus;
import com.codesetters.domain.PaymentStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

public class OrderUpdateStatusDTO implements Serializable{

    @NotNull
    private UUID orderId;

    @NotNull
    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private UUID paymentId;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
}
