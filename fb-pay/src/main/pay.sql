//FIXME
CREATE TABLE `tb_pay_trace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `out_trade_no` varchar(30) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '购买人uid',
  `ticket_id` bigint(20) NOT NULL COMMENT '票种id',
  `product_id` bigint(20) NOT NULL COMMENT '产品id',
  `quantity` int(10) NOT NULL COMMENT '数量',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `order_state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单状态 -1关闭 0 待支付 1支付成功 2支付失败',
  `pay_channel` tinyint(2) NOT NULL DEFAULT '0' COMMENT '支付方式 0支付宝 1微信',
  `effect_flag` tinyint(2)  NOT NULL DEFAULT '1' COMMENT '删除状态 0 删除 1 生效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付流水表';
