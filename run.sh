#!/bin/bash

echo "Book Rewards API - Starting Application"
echo "======================================"

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

echo "Java version:"
java -version

echo ""
echo "Starting Spring Boot application..."
echo "The API will be available at: http://localhost:8080"
echo "API Documentation: http://localhost:8080/api/public/info"
echo "H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""

# Try to run the application
# If you have Maven installed, uncomment the next line:
# mvn spring-boot:run

# For now, we'll just show instructions
echo "To run the application:"
echo "1. Open the project in IntelliJ IDEA"
echo "2. Right-click on BookRewardsApplication.java"
echo "3. Select 'Run BookRewardsApplication'"
echo ""
echo "Or install Maven and run: mvn spring-boot:run" 