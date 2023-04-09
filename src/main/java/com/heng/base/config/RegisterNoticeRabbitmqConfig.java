package com.heng.base.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 声明交换机与队列
 * 绑定队列到交换机
 */
@Configuration
public class RegisterNoticeRabbitmqConfig {
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email_register";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms_register";
    // 交换机的名称
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform_register";

    /**
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic(通配符)、header交换机类型的配置
     *
     * @return the exchange
     */
    @Bean(EXCHANGE_TOPICS_INFORM)  //bead的名称和交换机名字一直
    public Exchange EXCHANGE_TOPICS_INFORM() {
        //durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS() {
        Queue queue = new Queue(QUEUE_INFORM_SMS,true);
        return queue;
    }

    //声明队列
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL() {
        Queue queue = new Queue(QUEUE_INFORM_EMAIL,true);
        return queue;
    }

    /**
     * channel.queueBind(INFORM_QUEUE_SMS,"inform_exchange_topic","inform.#.sms.#");
     * 绑定队列到交换机 .
     *
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                            @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        // # 一个或多个  *只能有1个
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                              @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
    }
  

}