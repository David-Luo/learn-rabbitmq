package xin.luowei.demo.springboot.rabbitmq;

import static java.util.Map.of;

import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Autowired
    RabbitTopicProperties rabbitTopicProperties;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Qualifier("bizExchange")
    TopicExchange bizExchange() {
        Map<String, Object> immutableMap = of("alternate-exchange", rabbitTopicProperties.fanoutExchange());
        return new TopicExchange(rabbitTopicProperties.bizExchange(), true, false, immutableMap);
    }

    @Bean
    @Qualifier("fanoutExchange")
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(rabbitTopicProperties.fanoutExchange());
    }

    @Bean
    @Qualifier("dlxExchange")
    TopicExchange dlxExchange() {
        return new TopicExchange(rabbitTopicProperties.dlxExchange());
    }

    @Bean
    Queue oderCreatedQueue() {
        Map<String, Object> immutableMap = of("x-dead-letter-exchange", rabbitTopicProperties.dlxExchange());
        return new Queue(rabbitTopicProperties.bizQueue(RabbitTopicProperties.CREATED), true, false, false,
                immutableMap);
    }

    @Bean
    Queue oderDealQueue() {
        Map<String, Object> immutableMap = of("x-dead-letter-exchange", rabbitTopicProperties.dlxExchange());
        return new Queue(rabbitTopicProperties.bizQueue(RabbitTopicProperties.DEAL), true, false, false, immutableMap);
    }

    @Bean
    Queue oderChangedQueue() {
        Map<String, Object> immutableMap = of("x-dead-letter-exchange", rabbitTopicProperties.dlxExchange());
        return new Queue(rabbitTopicProperties.bizQueue(RabbitTopicProperties.CHANGED), true, false, false,
                immutableMap);
    }

    @Bean
    Queue dlxQueue() {
        return new Queue(rabbitTopicProperties.dlxQueue(), true, false, false);
    }

    @Bean
    Queue fanoutQueue() {
        return new Queue(rabbitTopicProperties.fanoutQueue(), true, false, false);
    }

    @Bean
    Binding bindingDlx(Queue dlxQueue, TopicExchange dlxExchange) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("#");
    }

    @Bean
    Binding bindingFanout(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingCreated(Queue oderCreatedQueue, TopicExchange bizExchange) {
        return BindingBuilder.bind(oderCreatedQueue).to(bizExchange).with(rabbitTopicProperties.createRoutingKey());
    }

    @Bean
    Binding bindingDeal(Queue oderDealQueue, TopicExchange bizExchange) {
        return BindingBuilder.bind(oderDealQueue).to(bizExchange).with(rabbitTopicProperties.dealRoutingKey());
    }

    @Bean
    Binding bindingChange(Queue oderChangedQueue, TopicExchange bizExchange) {
        return BindingBuilder.bind(oderChangedQueue).to(bizExchange).with(rabbitTopicProperties.changedRoutingKey());
    }

    @RabbitListener(queues = "queue.order.deal")
    public void receiveChanged(Order message) {
        System.out.println("收到消息：" + message);
       
    }

    @RabbitListener(queues = "queue.order.created")
    public void receiveCreated(Order message) {
        System.out.println("收到消息：" + message);
        int i = 1 / 0;
    }

}