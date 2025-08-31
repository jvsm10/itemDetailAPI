# How to Run the Project - Item Detail API

## 🚀 Quick Start Guide

### Prerequisites
- **Java 11** or higher installed
- **Maven 3.6** or higher (or use the included Maven Wrapper)
- **Port 8080** available on the system

### Prerequisites Verification

```bash
# Check Java version
java -version
# Should show: openjdk version "11.x.x" or higher

# Check Maven (optional, since we use Maven Wrapper)
mvn -version
```

## 📦 Step-by-Step Execution

### 1. Environment Setup

```bash
# Navigate to project directory
cd item-detail-api
```

### 2. Compilation

```bash
# Clean and compile the project
mvn clean compile

# Expected output:
# [INFO] BUILD SUCCESS
# [INFO] Total time: ~5-10 seconds
```

### 3. Running Tests

```bash
# Execute all tests
mvn test

# Expected output:
# [INFO] Tests run: 29, Failures: 0, Errors: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

### 4. Running the Application

```bash
# Start the application
mvn spring-boot:run

# Expected output:
# Started ItemDetailApiApplication in X.XXX seconds
# Tomcat started on port(s): 8080 (http)
```

### 5. Execution Verification

In another terminal, run:

```bash
# Test if the API is working
curl http://localhost:8080/api/items/health

# Expected response:
# API is running!
```

## 🧪 API Testing

### Available Endpoints

#### 1. Health Check
```bash
curl http://localhost:8080/api/items/health
```

#### 2. List All Items
```bash
curl http://localhost:8080/api/items
```

#### 3. Get Details of a Specific Item
```bash
# Existing item
curl http://localhost:8080/api/items/MLB123456789

# Non-existent item (returns 404)
curl http://localhost:8080/api/items/NONEXISTENT
```

### Response Examples

#### Item Details (Success - 200)
```json
{
  "item": {
    "id": "MLB123456789",
    "name": "Samsung Galaxy S24 Ultra 256GB Smartphone",
    "description": "Samsung Galaxy S24 Ultra smartphone with 256GB...",
    "price": 4999.99,
    "currency": "BRL",
    "category": "Cell Phones & Smartphones",
    "sellerId": "SELLER001",
    "availableQuantity": 15,
    "imageUrl": "https://..."
  },
  "seller": {
    "id": "SELLER001",
    "name": "TechStore Premium",
    "rating": 4.8,
    "address": "São Paulo, SP"
  },
  "reviews": [
    {
      "id": "REV001",
      "itemId": "MLB123456789",
      "userId": "USER001",
      "rating": 5,
      "comment": "Excellent smartphone!",
      "date": "2024-08-15"
    }
  ],
  "averageRating": 4.5,
  "totalReviews": 2
}
```