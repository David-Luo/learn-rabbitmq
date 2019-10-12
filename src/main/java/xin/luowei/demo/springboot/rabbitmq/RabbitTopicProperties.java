package xin.luowei.demo.springboot.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class RabbitTopicProperties {

    private final String EXCHANGE_TOKEN = "exchange";
    private final String QUEUE_TOKEN = "queue";
    private final String FANOUT_TOKEN = "fanout";
     //dead letter exchange
     private final String DLX_TOKEN = "dlx";
     private final String SPERATER = ".";
     private final String BIZ_TOPIC = "order";
	public static final String CHANGED = "changed";
	public static final String DEAL = "deal";
	public static final String CANCEL = "cancel";
	public static final String CREATED = "created";

     public  String join(String... param) {
        return String.join(SPERATER, param);
    }

    public  String bizExchange(){
        return join(EXCHANGE_TOKEN,BIZ_TOPIC);
    }

    public  String fanoutExchange() {
        return join(EXCHANGE_TOKEN,FANOUT_TOKEN);
    }

    public  String dlxExchange() {
        return join(EXCHANGE_TOKEN,DLX_TOKEN);
    }

    public  String bizQueue(String key){
        return join(QUEUE_TOKEN,BIZ_TOPIC,key);
    }

    public  String fanoutQueue() {
        return join(QUEUE_TOKEN, FANOUT_TOKEN);
    }

    public  String dlxQueue() {
        return join(QUEUE_TOKEN,DLX_TOKEN);
    }

    public String createRoutingKey(){
        return join(BIZ_TOPIC,CREATED);
    }
    public String dealRoutingKey(){
        return join(BIZ_TOPIC,DEAL);
    }
    public String cancellRoutingKey(){
        return join(BIZ_TOPIC,CANCEL);
    }
    public String changedRoutingKey(){
        return join(BIZ_TOPIC,"#");
    }
    
}