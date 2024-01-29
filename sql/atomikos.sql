-- order begin -----------------------------------------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `order` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use `order`;

CREATE TABLE `t_order`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `order_no`       varchar(255) DEFAULT NULL,
    `user_id`        varchar(255) DEFAULT NULL,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT '0',
    `amount`         double(14, 2
) DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- order end -----------------------------------------------------------------------------------------------------

-- stock begin -----------------------------------------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `stock` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use `stock`;

CREATE TABLE `t_stock`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `commodity_code` varchar(255) DEFAULT NULL,
    `name`           varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `commodity_code` (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_stock`
VALUES ('1', 'C201901140001', '水杯', '1000');
-- stock end -----------------------------------------------------------------------------------------------------
