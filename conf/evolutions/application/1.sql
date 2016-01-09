# --- First database schema

# --- !Ups
CREATE TABLE `iq_unit_mstr` (
  `iq_unit_mstr_id` varchar(50) NOT NULL,
  `unit_name` varchar(250) DEFAULT NULL,
  `description` text,
  `nof_chairs` int(11) DEFAULT NULL,
  `nof_beds` int(11) DEFAULT NULL,
  `appt_start_time` time DEFAULT NULL,
  `nof_appt_start_with_in_hour` int(11) DEFAULT NULL,
  `nof_appt_discharge_with_in_hour` int(11) DEFAULT NULL,
  `owner` varchar(200) DEFAULT NULL,
  `created_by` varchar(200) DEFAULT NULL,
  `modified_by` varchar(200) DEFAULT NULL,
  `created_datetime` datetime DEFAULT NULL,
  `modified_datetime` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `parent_id` varchar(200) DEFAULT NULL,
  `show_on_ui` tinyint(1) DEFAULT '1',
  `iq_version` int(11) DEFAULT '0',
  `cloned_from` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`iq_unit_mstr_id`),
  UNIQUE KEY `parent_id` (`parent_id`,`iq_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


# --- !Downs
SET FOREIGN_KEY_CHECKS=0;
drop table if exists iq_unit_mstr;
SET FOREIGN_KEY_CHECKS=1;

