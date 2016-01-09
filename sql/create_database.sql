CREATE DATABASE commondb CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE tenant_tenant1 CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE tenant_tenant2 CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'multitenant'@'%' IDENTIFIED by 'multitenant';
CREATE USER 'multitenant'@'localhost' IDENTIFIED by 'multitenant';

GRANT EXECUTE ON `tenant%`.* to multitenant@`%`;
FLUSH PRIVILEGES;

GRANT ALL ON `commondb`.* to multitenant@`%`;
GRANT ALL ON `tenant%`.* to multitenant@`%`;
FLUSH PRIVILEGES;