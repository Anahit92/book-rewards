#!/bin/bash

echo "Resetting Book Rewards Database..."
echo "=================================="

# Database connection details
DB_HOST="localhost"
DB_PORT="5432"
DB_NAME="bookrewards"
DB_USER="postgres"
DB_PASSWORD="admin"

echo "Dropping database if it exists..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "DROP DATABASE IF EXISTS $DB_NAME;"

echo "Creating new database..."
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "CREATE DATABASE $DB_NAME;"

echo "Database reset complete!"
echo "Now start your Spring Boot application to create tables with UUID columns." 