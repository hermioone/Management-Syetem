create table `product_info` (
	`product_id` VARCHAR(32) not null,
	`product_name` VARCHAR(64) not null,
	`product_price` DECIMAL(8, 2) not null,
	`product_stock` int not null,
	`product_description` VARCHAR(64),
	`product_icon` VARCHAR(512),
	`category_type` int not null,
	`create_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	`update_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
	PRIMARY key (`product_id`)
);


create table `product_category`(
	`category_id` int not null auto_increment,
	`category_name` varchar(64) not null,
	`category_type` int not null,
	`create_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	`update_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
	PRIMARY key (`category_id`),
	unique key `uqe_category_type` (`category_type`)
);


create table `order_master` (
	`order_id` varchar(32) not null,
	`buyer_name` varchar(32) not null,
	`buyer_phone` varchar(32) not null,
	`buyer_address` varchar(128) not null,
	`buyer_openid` varchar(64) not null,
	`order_amount` DECIMAL(8, 2) not null,
	`order_status` TINYINT(3) not null DEFAULT 0,
	`pay_status` TINYINT(3) not null DEFAULT 0,
	`create_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	`update_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
	PRIMARY key (`order_id`),
	key `idx_buyer_openid` (`buyer_openid`)
);


create table `order_detail` (
	`detail_id` VARCHAR(32) not null,
	`order_id` VARCHAR(32) not null,
	`product_id` VARCHAR(32) not null,
	`product_name` VARCHAR(64) not null,
	`product_price` DECIMAL(8, 2) not null,
	`product_quantity` int not null,
	`product_icon` VARCHAR(512),
	`create_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	`update_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP,
	PRIMARY key (`detail_id`),
	key `idx_order_id` (`order_id`)
);
