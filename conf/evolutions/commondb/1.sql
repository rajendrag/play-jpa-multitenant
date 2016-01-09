# --- First database schema

# --- !Ups
CREATE TABLE `com_tenant` (
  `tenant_id` varchar(50) NOT NULL,
  `tenant_name` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `com_layout_page` (
  `page_id` varchar(50) NOT NULL,
  `page_title` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `com_tenant` values ('tenant_1', 'tenant1');
insert into `com_tenant` values ('tenant_2', 'tenant2');

# --- !Downs
delete from `com_tenant`;
SET FOREIGN_KEY_CHECKS=0;
drop table if exists com_layout_page;
drop table if exists com_tenant;
SET FOREIGN_KEY_CHECKS=1;

