CREATE TABLE `tb_feed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '发布人uid',
  `clockin` tinyint(2) NOT NULL COMMENT '打卡 0 否 1 是',
  `clockin_tag` varchar(10) DEFAULT NULL COMMENT '打卡标签',
  `display_city` tinyint(2) NOT NULL COMMENT '发布到同城 0 否 1 是',
  `feed_state` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态 0 删除 1 发布',
  `feed_address` varchar(10) DEFAULT NULL COMMENT '动态地址',
  `city_code` int(10) DEFAULT NULL COMMENT '城市码',
  `ad_code` int(15) DEFAULT NULL COMMENT '市区码',
  `pic_url` varchar(255) DEFAULT NULL COMMENT '图片url',
  `video_url` varchar(255) DEFAULT NULL COMMENT '视频url',
  `feed_content` varchar(500) DEFAULT NULL COMMENT '动态内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态表';