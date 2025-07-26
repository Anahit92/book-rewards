#!/bin/bash

# Test Admin Book Upload
echo "Testing Admin Book Upload..."

# First, login as admin and get token
echo "1. Logging in as admin..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }')

TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "Failed to get token. Response: $LOGIN_RESPONSE"
    exit 1
fi

echo "Token obtained: ${TOKEN:0:20}..."

# Test admin upload endpoint (without file for now)
echo "2. Testing admin upload endpoint..."
UPLOAD_RESPONSE=$(curl -s -X POST http://localhost:8080/api/admin/books/upload \
  -H "Authorization: Bearer $TOKEN" \
  -F "title=Test Book" \
  -F "author=Test Author" \
  -F "description=Test Description" \
  -F "isbn=1234567890" \
  -F "price=9.99" \
  -F "pointsCost=100" \
  -F "file=@test.pdf")

echo "Upload response: $UPLOAD_RESPONSE"

# Test admin books list
echo "3. Testing admin books list..."
BOOKS_RESPONSE=$(curl -s -X GET http://localhost:8080/api/books \
  -H "Authorization: Bearer $TOKEN")

echo "Books response: $BOOKS_RESPONSE"

echo "Admin upload test completed!" 