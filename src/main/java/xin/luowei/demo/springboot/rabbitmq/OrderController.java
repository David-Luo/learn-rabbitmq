package xin.luowei.demo.springboot.rabbitmq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private Map<Integer, Order> repository = new ConcurrentHashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    @Autowired
    private OrderMessageSender orderMessageSender;

    @PostMapping("/order")
    public Order create(@RequestBody Order order) {
        order.setOrderId(idGenerator.incrementAndGet());
        order.setStatus(Order.Status.created);
        repository.put(order.getOrderId(), order);

        orderMessageSender.publishCreated(order);
        return order;
    }

    @PutMapping("/order")
    public Order deal(@RequestBody Order order) {
        order.setStatus(Order.Status.deal);
        repository.put(order.getOrderId(), order);

        orderMessageSender.publishDeal(order);
        return order;
    }

    @DeleteMapping("/order/{id}")
    public Order cancell(@PathVariable("id") Integer id) {
        Order order = repository.get(id);
        repository.remove(id);
        orderMessageSender.publishCancel(order);
        return order;
    }
    
    @DeleteMapping("/sendError")
    public Object sendError() {
        orderMessageSender.somethingWrong();
        return "sended";
    }
}