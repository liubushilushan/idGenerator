CREATE TABLE `verify_correct` (
  `id` bigint NOT NULL DEFAULT '0' COMMENT '已分配的最大ID',
  `biz_tag` varchar(128) CHARACTER SET utf8mb4  NOT NULL COMMENT '业务标签',
  `tid` varchar(128) CHARACTER SET utf8mb4  NOT NULL COMMENT '线程id',
  PRIMARY KEY (`id`,`biz_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;