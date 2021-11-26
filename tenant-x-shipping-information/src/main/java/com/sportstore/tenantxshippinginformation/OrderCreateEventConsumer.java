package com.sportstore.tenantxshippinginformation;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.sportstore.tenantxshippinginformation.config.RabbitMQConfig;
import com.sportstore.tenantxshippinginformation.model.db.ShippingInformation;
import com.sportstore.tenantxshippinginformation.model.dto.OrdersDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCreateEventConsumer {

    private final ShippingInformationRepository shippingInformationRepository;

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED, containerFactory = "v1ContainerFactory" )
    public void onMessageReceivedTenantX(OrdersDTO message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        System.out.println("Message received tenantX!: " + message);
        handleOrderCreate(message);
    }

    private void handleOrderCreate(OrdersDTO order) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deliveryDate = LocalDateTime.now().plusDays(2);
        ShippingInformation shippingInformation = new ShippingInformation();
        shippingInformation.setOrderId(order.getOrderId());
        shippingInformation.setShippingTime(now);
        shippingInformation.setArrivalTime(deliveryDate);
        shippingInformationRepository.save(shippingInformation);
    }
}