CREATE TABLE `tb_enroll` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '报名人uid',
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `publish_user_id` bigint(20) NOT NULL COMMENT '发布人uid',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户报名信息表';


