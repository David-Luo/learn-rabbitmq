package xin.luowei.demo.springboot.rabbitmq;

import lombok.Data;

/**
 * Order
 */
@Data
public class Order {

    private Integer orderId;
    private String name;
    private Status status;

    public enum Status {
        created, deal,cancell
    }

}