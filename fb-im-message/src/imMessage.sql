CREATE TABLE `tb_single_msg_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `source_id` bigint(20) NOT NULL COMMENT '消息来源用户id',
  `target_id` bigint(20) NOT NULL COMMENT '消息目标用户id',
  `msg_type` tinyint(2) NOT NULL COMMENT '消息类型 1 文本 2 语音 3 地理位置 4 视频 5 文件',
  `msg_content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `seq_id` bigint(20)  NOT NULL COMMENT '消息序列号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_targetId_seqId` (`target_id`, `seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单聊历史表';
