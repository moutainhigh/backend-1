CREATE TABLE `tb_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '发布人uid',
  `user_type` tinyint(2) NOT NULL COMMENT '发布者类型 0 商家 1 用户',
  `activity_valid` tinyint(2) NOT NULL COMMENT '有效期 0 长期 1 短期',
  `activity_title` varchar(30) NOT NULL COMMENT '活动标题',
  `member_count` int(5) COMMENT '活动人数',
  `activity_time` datetime DEFAULT NULL COMMENT '活动时间',
  `enroll_end_time` datetime DEFAULT NULL COMMENT '报名结束时间',
  `activity_address` varchar(100) DEFAULT NULL COMMENT '活动地址',
  `city_code` varchar(10) DEFAULT NULL COMMENT '城市码',
  `city_name` varchar(10) DEFAULT NULL COMMENT '城市名',
  `ad_code` varchar(15) DEFAULT NULL COMMENT '市区码',
  `ad_name` varchar(50) DEFAULT NULL COMMENT '市区名',
  `location` varchar(20) DEFAULT NULL COMMENT '定位',
  `activity_type` tinyint(2) NOT NULL COMMENT '活动类型 可配置见同名枚举',
  `need_info` tinyint(2) NOT NULL COMMENT '需要购票信息 0 否 1 是',
  `refund_flag` tinyint(2) NOT NULL COMMENT '退票标识 0 不可退 1 可退',
  `activity_state` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态 -1 停止报名 0 删除 1 草稿 2 发布 ' ,
  `front_money` decimal(10,2) DEFAULT NULL COMMENT '定金',
  `pic_url` varchar(255) DEFAULT NULL COMMENT '图片url',
  `video_url` varchar(255) DEFAULT NULL COMMENT '视频url',
  `activity_content` varchar(5000) DEFAULT NULL COMMENT '活动内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';


CREATE TABLE `tb_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `activity_id` bigint(20) NOT NULL COMMENT '活动表主键',
  `ticket_name` varchar(10) NOT NULL COMMENT '票种',
  `ticket_price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `assemble` tinyint(2) NOT NULL COMMENT '拼团 0 否 1 是',
  `assemble_price` decimal(10,2) DEFAULT NULL COMMENT '拼团价格',
  `assemble_member_count` int(5) COMMENT '活动人数',
  `ticket_state` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态 0 删除 1 发布',
  `illustration` varchar(50) DEFAULT NULL COMMENT '说明',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票种表';