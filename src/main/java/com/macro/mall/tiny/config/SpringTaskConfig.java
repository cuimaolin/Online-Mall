package com.macro.mall.tiny.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务配置
 *
 * 业务场景说明：
 * 1. 用户对某商品进行下单操作；
 * 2. 系统需要用户购买的商品信息生成订单并锁定商品的库存
 * 3. 系统设置了60分钟用户不付款就会取消订单；
 * 4. 开启一个定时任务，每隔10分钟检查下，如果有超时还未付款的订单，就去校订单并取消锁定的商品库存
 *
 * 只需要在配置类中添加一个@EnableScheduling注解即可开启SpringTask的定时任务能力
 *
 */
@Configuration
@EnableScheduling
public class SpringTaskConfig {
}
