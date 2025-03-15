#!/bin/sh
echo "Fixing permissions for /docker-entrypoint-initdb.d/"
chown -R mysql:mysql /docker-entrypoint-initdb.d/
chmod 755 /docker-entrypoint-initdb.d/init-db.sh
chmod 644 /docker-entrypoint-initdb.d/init.sql

echo "Waiting for MariaDB to be ready..."
until mariadb -uroot -proot -e "SELECT 1"; do
  sleep 2
done

echo "Running init.sql..."
mariadb -uroot -proot crm_db < /docker-entrypoint-initdb.d/init.sql
