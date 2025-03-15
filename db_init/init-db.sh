#!/bin/sh

echo "Fixing permissions for /docker-entrypoint-initdb.d/"
chown -R mysql:mysql /docker-entrypoint-initdb.d/
chmod 755 /docker-entrypoint-initdb.d/init-db.sh
chmod 644 /docker-entrypoint-initdb.d/init.sql

echo "Waiting for MariaDB to be ready..."
while ! mariadb -uroot -proot -h database -e "SELECT 1" &>/dev/null; do
  echo "MariaDB is not ready yet, retrying in 2s..."
  sleep 2
done

echo "MariaDB is up! Running init.sql..."
mariadb -uroot -proot -h database crm_db < /docker-entrypoint-initdb.d/init.sql
echo "Database initialized successfully!"
