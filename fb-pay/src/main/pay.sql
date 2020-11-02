CREATE TABLE `tb_pay_trace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_id` varchar(50) NOT NULL COMMENT 'appid',
  `trade_no` varchar(30) NOT NULL COMMENT '支付宝交易凭证号',
  `out_trade_no` varchar(30) NOT NULL COMMENT '原支付请求的商户订单号',
  `buyer_id` varchar(30) NOT NULL COMMENT '家支付宝账号对应的支付宝唯一用户号',
  `buyer_logon_id` varchar(30) NOT NULL COMMENT '买家支付宝账号',
  `seller_id` varchar(20) NOT NULL COMMENT '卖家支付宝用户号',
  `seller_email` varchar(20) NOT NULL COMMENT '卖家支付宝账号',
  `trade_status` varchar(5) NOT NULL COMMENT '交易目前所处的状态',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `receipt_amount` decimal(10,2) DEFAULT NULL COMMENT '商家在交易中实际收到的款项',
  `buyer_pay_amount` decimal(10,2) DEFAULT NULL COMMENT '用户在交易中支付的金额',
  `refund_fee` decimal(10,2) DEFAULT NULL COMMENT '用户在交易中支付的金额, 单位为元',
  `subject` varchar(30) NOT NULL COMMENT '商品的标题/交易标题/订单标题/订单关键字等',
  `body` varchar(30) NOT NULL COMMENT '该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来',
  `fund_bill_list` varchar(30) NOT NULL COMMENT '支付成功的各个渠道金额信息',
  `passback_params` varchar(20) NOT NULL COMMENT '扩展字段，公共回传参数，如果请求时传递了该参数',
  `gmt_create` datetime DEFAULT NULL COMMENT '交易创建的时间',
  `gmt_payment` datetime DEFAULT NULL COMMENT '交易的买家付款时间',
  `gmt_refund` datetime DEFAULT NULL COMMENT '交易的退款时间',
  `gmt_close` datetime DEFAULT NULL COMMENT '交易结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付流水表';
