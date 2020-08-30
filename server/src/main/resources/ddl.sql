create database idc;

CREATE TABLE `identity_t` (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务标签',
  `max_id` bigint NOT NULL DEFAULT '0' COMMENT '已分配的最大ID',
  `step` bigint NOT NULL DEFAULT '10' COMMENT '步长',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
