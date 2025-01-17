package com.sportstore.shoppingcartservice;

import static com.sportstore.shoppingcartservice.config.RabbitMQConfig.CART_CHECKOUT_EXCHANGE;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sportstore.shoppingcartservice.eventbus.EventBusService;
import com.sportstore.shoppingcartservice.model.db.CartLine;
import com.sportstore.shoppingcartservice.model.dto.CartDTO;
import com.sportstore.shoppingcartservice.model.dto.CartLineDTO;
import com.sportstore.shoppingcartservice.model.event.CartCheckoutEvent;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("cart-api")
public class CartResource {

    private final CartLineRepository cartLineRepository;
    private final EventBusService eventBusService;
    @Resource
    private UserInfo userInfo;


    @GetMapping("cart")
    public List<CartLine> getCart() {
        //Not needed
        return StreamSupport.stream((cartLineRepository.findAll().spliterator()), false).collect(Collectors.toList());
    }

    @PostMapping("cart")
    public List<CartLine> addCartLine() {
        //Not needed
        throw new UnsupportedOperationException();
    }

    @GetMapping("cartline")
    public CartLine getCartLine(@RequestBody CartLineRequest request) {
        //Not needed
        Optional<CartLine> optionalCartLine = cartLineRepository.findById(request.getCartId());
        if (optionalCartLine.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart line does not exist");
        }

        return optionalCartLine.get();
    }

    @PutMapping("cartline")
    public CartLine updateCartLine() {
        //Not needed
        throw new UnsupportedOperationException("");
    }

    @PostMapping("checkout")
    public void checkout(@RequestBody CartDTO cart) {
        cart.setTotalCost(calculateCosts(cart.getLines()));
        CartCheckoutEvent cartCheckoutEvent = new CartCheckoutEvent();
        cartCheckoutEvent.setCartDTO(cart);
        cartCheckoutEvent.setName(CartCheckoutEvent.class.getSimpleName());
        cartCheckoutEvent.setTenant(userInfo.getTenant());
        eventBusService.publishEvent(CART_CHECKOUT_EXCHANGE, cartCheckoutEvent);
    }

    private double calculateCosts(List<CartLineDTO> cartLineDTOS) {
        if (cartLineDTOS == null) {
            return 0;
        }
        return cartLineDTOS.stream().mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity()).sum();
    }

}

@Data
class CartLineRequest {

    private long cartId;

}
