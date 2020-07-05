CREATE TABLE `tb_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `info_id` bigint(20) NOT NULL COMMENT '帖子主键',
  `info_type` tinyint(10) NOT NULL COMMENT '帖子类型 1 活动 2动态',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `like_state` tinyint(2) NOT NULL COMMENT '状态 0 取消 1 点赞',
  `avatar` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `nickname` varchar(10) DEFAULT NULL COMMENT '用户昵称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_info` (`info_id`, `info_type`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

CREATE TABLE `tb_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `info_id` bigint(20) NOT NULL COMMENT '帖子主键',
  `info_type` tinyint(10) NOT NULL COMMENT '帖子类型 1 活动 2动态',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `avatar` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `nickname` varchar(10) DEFAULT NULL COMMENT '用户昵称',
  `to_user_id` decimal(10,2) NOT NULL COMMENT '被评论用户id',
  `to_avatar` varchar(100) DEFAULT NULL COMMENT '被评论用户头像',
  `to_nickname` varchar(10) DEFAULT NULL COMMENT '被评论用户昵称',
  `content` varchar(100) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';
