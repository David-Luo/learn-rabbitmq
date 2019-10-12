package xin.luowei.demo.springboot.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitTopicProperties properties;
    @Autowired
    private RabbitTemplate.ConfirmCallback confirmCallback;
    @Autowired
    private RabbitTemplate.ReturnCallback returnCallback;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        // rabbitTemplate.setReturnCallback(returnCallback);
    }

    public void publishCreated(Order order) {
        rabbitTemplate.convertAndSend(properties.bizExchange(), properties.createRoutingKey(), order,
                new CorrelationData("" + order.getOrderId()));
    }

    public void publishDeal(Order order) {
        rabbitTemplate.convertAndSend(properties.bizExchange(), properties.dealRoutingKey(), order,
                new CorrelationData("" + order.getOrderId()));
    }

    public void publishCancel(Order order) {
        rabbitTemplate.convertAndSend(properties.bizExchange(), properties.dealRoutingKey(), order,
                new CorrelationData("" + order.getOrderId()));
    }

    public void somethingWrong() {
        rabbitTemplate.convertAndSend(properties.bizExchange(), "properties.dealRoutingKey()", "xxx");
    }
}