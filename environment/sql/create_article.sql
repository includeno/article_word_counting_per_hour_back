DROP TABLE IF EXISTS `article_versions`;
DROP TABLE IF EXISTS `articles`;

CREATE TABLE `articles`
(
    `id`    int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title` varchar(255) NOT NULL COMMENT '标题',
    `valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `article_versions`
(
    `id`    int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `article_id` int(10) NOT NULL COMMENT '文章id',
    `content` text NOT NULL COMMENT '内容',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `version` int(10) NOT NULL COMMENT '版本',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `article_id` (`article_id`),
    CONSTRAINT `article_versions_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;