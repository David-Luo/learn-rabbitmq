package xin.luowei.demo.springboot.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.connection.CorrelationData;
@Component
@Slf4j
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    /**
     * 当消息发送到交换机（exchange）时，该方法被调用.
     * 1.如果消息没有到exchange,则 ack=false
     * 2.如果消息到达exchange,则 ack=true
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("MsgSendConfirmCallBack  , 回调correlationData:" + correlationData);
        if (ack) {
            log.info("消息发送到exchange成功");
            // TODO 删除 msgId 与 Message 的关系
        } else {
            log.info("消息发送到exchange失败");
            // TODO 消息发送到exchange失败 ， 重新发送
        }
    }
}
