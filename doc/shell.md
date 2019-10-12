# 构造异常场景测试

``` shell
├── config
│   └── generated
│       ├── rabbitmq.config
│       └── vm.2019.10.12.03.36.32.args
├── mnesia
│   ├── rabbit@rabbit3
│   │   ├── cluster_nodes.config
│   │   ├── DECISION_TAB.LOG
│   │   ├── LATEST.LOG
│   │   ├── msg_stores
│   │   │   └── vhosts
│   │   │       └── B0G5EK6O7WOYX259KRYILGU3M
│   │   │           ├── msg_store_persistent
│   │   │           │   └── 0.rdq
│   │   │           ├── msg_store_transient
│   │   │           │   └── 0.rdq
│   │   │           ├── queues
│   │   │           │   ├── 47FPKMONQJN4OAT2I7PIXJY90
│   │   │           │   │   ├── 0.idx
│   │   │           │   │   ├── 1.idx
│   │   │           │   │   ├── 2.idx
│   │   │           │   │   └── journal.jif
│   │   │           │   ├── 4RR3B2549GPXKPBXSKB9RNT2A
│   │   │           │   │   ├── 0.idx
│   │   │           │   │   └── journal.jif
│   │   │           │   ├── 8KSG139G9C1HWTSXFCQF95MCF
│   │   │           │   │   ├── 0.idx
│   │   │           │   │   ├── 1.idx
│   │   │           │   │   ├── 2.idx
│   │   │           │   │   └── journal.jif
│   │   │           │   └── DHBAG7QQ83L7CTT1H8XB2F02O
│   │   │           │       ├── 0.idx
│   │   │           │       └── journal.jif
│   │   │           └── recovery.dets

```
$ cd /data/rabbit/mnesia/rabbit@rabbit3/msg_stores/vhosts/B0G5EK6O7WOYX259KRYILGU3M/queues
$ chattr +i 8KSG139G9C1HWTSXFCQF95MCF/2.idx 


``` txt
2019-10-12 13:57:21.737 DEBUG 4293 --- [nio-8080-exec-6] o.s.web.servlet.DispatcherServlet        : DELETE "/order/5", parameters={}
2019-10-12 13:57:21.737 DEBUG 4293 --- [nio-8080-exec-6] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to public xin.luowei.demo.springboot.rabbitmq.Order xin.luowei.demo.springboot.rabbitmq.OrderController.cancell(java.lang.Integer)
2019-10-12 13:57:21.738 DEBUG 4293 --- [nio-8080-exec-6] m.m.a.RequestResponseBodyMethodProcessor : Using 'application/json', given [*/*] and supported [application/json, application/*+json, application/json, application/*+json]
2019-10-12 13:57:21.738 DEBUG 4293 --- [nio-8080-exec-6] m.m.a.RequestResponseBodyMethodProcessor : Writing [Order(orderId=5, name=bag, status=created)]
2019-10-12 13:57:21.739 DEBUG 4293 --- [nio-8080-exec-6] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
2019-10-12 13:57:21.740  INFO 4293 --- [168.56.101:5672] x.l.d.s.rabbitmq.MsgSendReturnCallback   : MsgSendReturnCallback [消息从交换机到队列失败]  message：(Body:'{"orderId":5,"name":"bag","status":"created"}' MessageProperties [headers={spring_returned_message_correlation=5, __TypeId__=xin.luowei.demo.springboot.rabbitmq.Order}, contentType=application/json, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
2019-10-12 13:57:21.740  INFO 4293 --- [168.56.101:5672] x.l.d.s.rabbitmq.MsgSendConfirmCallBack  : MsgSendConfirmCallBack  , 回调correlationData:CorrelationData [id=5]
2019-10-12 13:57:21.740  INFO 4293 --- [168.56.101:5672] x.l.d.s.rabbitmq.MsgSendConfirmCallBack  : 消息发送到exchange成功
```


2019-10-12 14:42:19.062  INFO 8134 --- [nio-8080-exec-2] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [192.168.56.101:5672]
2019-10-12 14:42:19.100  INFO 8134 --- [nio-8080-exec-2] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory#71fb8301:0/SimpleConnection@eebd888 [delegate=amqp://admin@192.168.56.101:5672/first_vhost, localPort= 42826]
2019-10-12 14:47:51.661 DEBUG 8134 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Failed to complete request: org.springframework.amqp.AmqpIOException: java.io.IOException
2019-10-12 14:47:51.670 ERROR 8134 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.amqp.AmqpIOException: java.io.IOException] with root cause

com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - failed to perform operation on queue 'queue.order.deal' in vhost 'first_vhost' due to timeout, class-id=50, method-id=10)
        at com.rabbitmq.client.impl.ChannelN.asyncShutdown(ChannelN.java:516) ~[amqp-client-5.4.3.jar:5.4.3]
        at com.rabbitmq.client.impl.ChannelN.processAsync(ChannelN.java:346) ~[amqp-client-5.4.3.jar:5.4.3]
        at com.rabbitmq.client.impl.AMQChannel.handleCompleteInboundCommand(AMQChannel.java:178) ~[amqp-client-5.4.3.jar:5.4.3]
        at com.rabbitmq.client.impl.AMQChannel.handleFrame(AMQChannel.java:111) ~[amqp-client-5.4.3.jar:5.4.3]
        at com.rabbitmq.client.impl.AMQConnection.readFrame(AMQConnection.java:670) ~[amqp-client-5.4.3.jar:5.4.3]
        at com.rabbitmq.client.impl.AMQConnection.access$300(AMQConnection.java:48) ~[amqp-client-5.4.3.jar:5.4.3]
        at com.rabbitmq.client.impl.AMQConnection$MainLoop.run(AMQConnection.java:597) ~[amqp-client-5.4.3.jar:5.4.3]
        at java.base/java.lang.Thread.run(Thread.java:834) ~[na:na]




        
文件隐藏属性
文件通常使用chmod可以修改权限，这个通常是用户权限的设置。
文件还存在一个隐藏属性 不过chattr和lsattr两个命令只在Ext2/Ext3文件系统上面生效。其他文件系统无法支持该命令。

$chattr +i filename
对文件添加 i属性，它可以让一个文件，不能被删除，改名，写入或添加数据。

$ lsattr filename
可以查看到隐藏属性列表。
如果文件不能被修改了，可以使用下面的命令去掉：

$chattr -i filename
chattr可设置的属性可以通过help查看。
