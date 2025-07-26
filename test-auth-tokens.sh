#!/bin/bash

# Test script for auth token functionality
BASE_URL="http://localhost:8080/api"

echo "Testing Auth Token Functionality"
echo "================================"

# Test 1: Register a new user
echo "1. Registering a new user..."
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }')

echo "Register response: $REGISTER_RESPONSE"
TOKEN=$(echo $REGISTER_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ -n "$TOKEN" ]; then
    echo "✅ Registration successful, token received"
else
    echo "❌ Registration failed"
    exit 1
fi

# Test 2: Login with the same user
echo ""
echo "2. Logging in with the same user..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }')

echo "Login response: $LOGIN_RESPONSE"
LOGIN_TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ -n "$LOGIN_TOKEN" ]; then
    echo "✅ Login successful, new token received"
else
    echo "❌ Login failed"
    exit 1
fi

# Test 3: Access protected endpoint
echo ""
echo "3. Testing protected endpoint..."
ME_RESPONSE=$(curl -s -X GET "$BASE_URL/auth/me" \
  -H "Authorization: Bearer $TOKEN")

echo "Me response: $ME_RESPONSE"

if echo "$ME_RESPONSE" | grep -q "testuser"; then
    echo "✅ Protected endpoint accessible with token"
else
    echo "❌ Protected endpoint access failed"
fi

# Test 4: Logout
echo ""
echo "4. Testing logout..."
LOGOUT_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/logout" \
  -H "Authorization: Bearer $TOKEN")

echo "Logout response: $LOGOUT_RESPONSE"

if echo "$LOGOUT_RESPONSE" | grep -q "Successfully logged out"; then
    echo "✅ Logout successful"
else
    echo "❌ Logout failed"
fi

# Test 5: Try to access protected endpoint after logout
echo ""
echo "5. Testing access after logout..."
ME_AFTER_LOGOUT=$(curl -s -X GET "$BASE_URL/auth/me" \
  -H "Authorization: Bearer $TOKEN")

echo "Me after logout response: $ME_AFTER_LOGOUT"

if echo "$ME_AFTER_LOGOUT" | grep -q "Invalid token"; then
    echo "✅ Token properly revoked after logout"
else
    echo "❌ Token still valid after logout"
fi

echo ""
echo "Auth token testing completed!" 