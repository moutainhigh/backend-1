CREATE TABLE `tb_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `out_trade_no` varchar(30) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '购买人uid',
  `ticket_id` bigint(20) NOT NULL COMMENT '票种id',
  `ticket_name` varchar(10) NOT NULL COMMENT '票种名称',
  `product_id` bigint(20) NOT NULL COMMENT '活动id',
  `product_name` varchar(30) NOT NULL COMMENT '活动名称',
  `quantity` int(10) NOT NULL COMMENT '数量',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `order_state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单状态 -2支付失败 -1关闭 0 待支付 1支付成功 ',
  `need_info` tinyint(2) NOT NULL COMMENT '需要购票信息 0 否 1 是',
  `pay_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '支付方式 0支付宝 1微信',
  `effect_flag` tinyint(2)  NOT NULL DEFAULT '1' COMMENT '删除状态 0 删除 1 生效',
  `activity_time` datetime NOT NULL COMMENT '活动时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


CREATE TABLE `tb_order_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `order_id` bigint(20) NOT NULL COMMENT '订单表id',
  `out_trade_no` varchar(30) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '购买人uid',
  `user_name` varchar(10) NOT NULL COMMENT '姓名',
  `user_phone` varchar(12) NOT NULL COMMENT '手机号',
  `user_card_no` varchar(20) NOT NULL COMMENT '身份证号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单用户信息表';