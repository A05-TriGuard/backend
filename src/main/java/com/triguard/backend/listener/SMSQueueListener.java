package com.triguard.backend.listener;

import com.triguard.backend.utils.ConstUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 用于处理短信发送的消息队列监听器
 */
@Component
@RabbitListener(queues = ConstUtils.MQ_SMS)
public class SMSQueueListener {
    // TODO: 添加短信发送逻辑
}
