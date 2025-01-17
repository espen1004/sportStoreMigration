package com.sportstore.productorderingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportstore.productorderingservice.model.db.Order;
import com.sportstore.productorderingservice.model.dto.OrderLineDTO;
import com.sportstore.productorderingservice.model.dto.OrdersDTO;
import com.sportstore.productorderingservice.model.dto.ShippingInfoDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrdersResource {

    private final OrderRepository orderRepository;

    @GetMapping
    public List<OrdersDTO> getOrders() {
        List<Order> orders = StreamSupport.stream((orderRepository.findAll().spliterator()), false).toList();

        return orders.stream().map(this::convertToOrderDTO).collect(Collectors.toList());
    }

    private OrdersDTO convertToOrderDTO(Order order) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setOrderId(order.getOrderId());
        ordersDTO.setCustomerId(order.getCustomerId());
        ordersDTO.setTotalCost(order.getTotalCost());
        List<OrderLineDTO> orderLineDTOS = new ArrayList<>();
        order.getOrderLines().forEach(o -> {
            OrderLineDTO orderLineDTO = new OrderLineDTO();
            orderLineDTO.setOrderLineId(o.getOrderLineId());
            orderLineDTO.setOrderId(o.getOrderId());
            orderLineDTO.setQuantity(o.getQuantity());
            orderLineDTO.setProductId(o.getProductId());
            orderLineDTO.setProductName(o.getProductName());
            orderLineDTOS.add(orderLineDTO);
        });
        ordersDTO.setLines(orderLineDTOS);
        ShippingInfoDTO shippingInfoDTO = new ShippingInfoDTO();
        shippingInfoDTO.setShippingInfoId(order.getShippingInfo().getShippingInfoId());
        shippingInfoDTO.setCity(order.getShippingInfo().getCity());
        shippingInfoDTO.setCountry(order.getShippingInfo().getCountry());
        shippingInfoDTO.setShipped(order.getShippingInfo().getShipped());
        shippingInfoDTO.setGiftWrap(order.getShippingInfo().getGiftWrap());
        shippingInfoDTO.setLine1(order.getShippingInfo().getLine1());
        shippingInfoDTO.setLine2(order.getShippingInfo().getLine2());
        shippingInfoDTO.setLine3(order.getShippingInfo().getLine3());
        shippingInfoDTO.setName(order.getShippingInfo().getName());
        shippingInfoDTO.setState(order.getShippingInfo().getState());
        shippingInfoDTO.setOrderId(order.getShippingInfo().getOrderId());
        shippingInfoDTO.setZip(order.getShippingInfo().getZip());
        ordersDTO.setShippingInfo(shippingInfoDTO);

        return ordersDTO;
    }

}
