#!/bin/bash

BASE_URL="http://localhost:8080"

echo "Book Rewards API - Test Script"
echo "=============================="
echo ""

# Test API info endpoint
echo "1. Testing API info endpoint..."
curl -s "$BASE_URL/api/public/info" | python3 -m json.tool 2>/dev/null || echo "API info endpoint not available"

echo ""
echo "2. Testing user registration..."
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }')

echo "Registration response:"
echo "$REGISTER_RESPONSE" | python3 -m json.tool 2>/dev/null || echo "$REGISTER_RESPONSE"

echo ""
echo "3. Testing user login..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }')

echo "Login response:"
echo "$LOGIN_RESPONSE" | python3 -m json.tool 2>/dev/null || echo "$LOGIN_RESPONSE"

# Extract token if login was successful
TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ ! -z "$TOKEN" ]; then
    echo ""
    echo "4. Testing authenticated endpoint (get current user)..."
    USER_RESPONSE=$(curl -s -X GET "$BASE_URL/api/auth/me" \
      -H "Authorization: Bearer $TOKEN")
    
    echo "User info response:"
    echo "$USER_RESPONSE" | python3 -m json.tool 2>/dev/null || echo "$USER_RESPONSE"
    
    echo ""
    echo "5. Testing item creation..."
    ITEM_RESPONSE=$(curl -s -X POST "$BASE_URL/api/items" \
      -H "Content-Type: application/json" \
      -H "Authorization: Bearer $TOKEN" \
      -d '{
        "title": "Test Book",
        "description": "A test book for the API"
      }')
    
    echo "Item creation response:"
    echo "$ITEM_RESPONSE" | python3 -m json.tool 2>/dev/null || echo "$ITEM_RESPONSE"
    
    echo ""
    echo "6. Testing get all items..."
    ITEMS_RESPONSE=$(curl -s -X GET "$BASE_URL/api/items" \
      -H "Authorization: Bearer $TOKEN")
    
    echo "Items response:"
    echo "$ITEMS_RESPONSE" | python3 -m json.tool 2>/dev/null || echo "$ITEMS_RESPONSE"
else
    echo ""
    echo "Could not extract token from login response. Skipping authenticated tests."
fi

echo ""
echo "Test completed!"
echo "If you see errors, make sure the application is running on http://localhost:8080" 