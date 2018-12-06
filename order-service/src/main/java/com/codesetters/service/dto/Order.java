package com.codesetters.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{

    @NotNull
    private OrdersDTO orderInfo;

    private List<OrderItemDTO> itemsInfo;


    public OrdersDTO getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrdersDTO orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<OrderItemDTO> getItemsInfo() {
        return itemsInfo;
    }

    public void setItemsInfo(List<OrderItemDTO> itemsInfo) {
        this.itemsInfo = itemsInfo;
    }


    @Override
    public String toString() {
        return "Order{" +
            ", orderInfo='" + getOrderInfo() + "'" +
            ", ItemsInfo='" + getItemsInfo() + "'" +
            "}";
    }
}
