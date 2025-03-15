CREATE USER 'crm_admin'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON crm_db.* TO 'crm_admin'@'%';
FLUSH PRIVILEGES;
