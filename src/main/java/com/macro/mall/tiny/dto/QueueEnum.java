package com.macro.mall.tiny.dto;

import lombok.Getter;

/**
 * 消息队列枚举配置
 * 用于延迟消息队列以及处理取消订单消息队列的常量定义，包括交换机名词、队列名词、路由键名称
 *
 * mall.order.direct(取消订单消息队列所绑定的交换机)：绑定的队列为mall.order.cancel
 * 一旦有消息以mall.order.cancel为路由发过来，会发送到此队列
 *
 * mall.order.ttl(订单延迟消息队列所绑定的交换机)：绑定的队列为mall.order.cancel.ttl
 * 一旦消息以mall.order.cancel.ttl为路由键发过来，会转发到此队列，并在此队列保存一段时间
 * 等到超时了会自动将消息发送到mall.order.cancel(取消订单消息消费队列)
 *
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}