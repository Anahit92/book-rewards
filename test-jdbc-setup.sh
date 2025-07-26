#!/bin/bash

echo "Testing Spring Data JDBC Setup..."

# Start the application
echo "Starting application..."
java -jar target/book-rewards-0.0.1-SNAPSHOT.jar &
APP_PID=$!

# Wait for application to start
echo "Waiting for application to start..."
sleep 15

# Test if application is running
if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Application is running on port 8080"
else
    echo "❌ Application failed to start"
    kill $APP_PID 2>/dev/null
    exit 1
fi

# Test database connection
echo "Testing database connection..."
if curl -s http://localhost:8080/h2-console > /dev/null 2>&1; then
    echo "✅ H2 Console is accessible"
else
    echo "❌ H2 Console is not accessible"
fi

# Test user registration
echo "Testing user registration..."
REGISTER_RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}')

echo "Registration response: $REGISTER_RESPONSE"

# Test login
echo "Testing login..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}')

echo "Login response: $LOGIN_RESPONSE"

# Stop the application
echo "Stopping application..."
kill $APP_PID 2>/dev/null

echo "Test completed!" 