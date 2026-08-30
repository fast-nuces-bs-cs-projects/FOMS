# Foodie API Documentation - JavaFX Dashboard

Base URL: `http://localhost:8081`

**Authentication Note:** All endpoints (except login) require a JWT Bearer token in the header.
`Authorization: Bearer <your_token_here>`

---

## 1. Authentication

### 1.1 Login (Admin)
* **Endpoint:** `POST /auth/login`
* **Request Body:**
  ```json
  {
    "email": "admin@foodie.com",
    "password": "admin123"
  }
  ```
* **Success Response:**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR..."
  }
  ```

### 1.2 Change Password
* **Endpoint:** `POST /auth/change-password`
* **Description:** Changes the password for the currently logged-in admin. Requires a valid JWT token.
* **Request Body:**
  ```json
  {
    "current_password": "admin123",
    "new_password": "newSecurePassword!"
  }
  ```
* **Success Response (200 OK):** `Password changed successfully`
* **Error Response (400 Bad Request):** `Incorrect current password`

---

## 2. Dashboard Analytics

### 2.1 Get Dashboard Data
* **Endpoint:** `GET /dashboard`
* **Description:** Returns aggregate data for charts and summaries.
* **Success Response:**
  ```json
  {
    "summary": {
      "totalOrders": 1245,
      "pendingOrders": 12,
      "revenue": 45600.50,
      "completedOrders": 1200
    },
    "weeklyOrders": [
      { "day": "Mon", "count": 45 }
    ],
    "recentOrders": [
      {
        "id": "ORD-1",
        "customer": "John Doe",
        "amount": 1250.00,
        "status": "PENDING"
      }
    ]
  }
  ```

---

## 3. Menu Management

### 3.1 Get All Menu Items
* **Endpoint:** `GET /api/menu`

### 3.2 Add Menu Item
* **Endpoint:** `POST /api/menu`
* **Request Body:**
  ```json
  {
    "name": "Spicy Chicken Burger",
    "description": "Spicy and crispy",
    "category": "Main Course",
    "price": 950.00,
    "status": "Available",
    "imageUrl": "/images/spicy_burger.png"
  }
  ```

### 3.3 Update Menu Item
* **Endpoint:** `PUT /api/menu/{id}`
* **Request Body:** (Same structure as Add Menu Item)

### 3.4 Delete Menu Item
* **Endpoint:** `DELETE /api/menu/{id}`

---

## 4. Orders Management

### 4.1 Get All Orders (History Table)
* **Endpoint:** `GET /orders`
* **Success Response:**
  ```json
  [
    {
      "id": "ORD-1",
      "customer": "John Doe",
      "date": "2023-10-25T14:30:00",
      "items": 3,
      "total": 3450.00,
      "status": "COMPLETED"
    }
  ]
  ```

### 4.2 Get Incoming Orders (Live Feed)
* **Endpoint:** `GET /orders/incoming`
* **Success Response:**
  ```json
  [
    {
      "id": "ORD-5",
      "customer": "Kamal Silva",
      "items": "2x Classic Burger, 1x Coca Cola",
      "time": "10:30:00",
      "amount": 1900.00
    }
  ]
  ```

### 4.3 Update Order Status (Accept/Reject)
* **Endpoint:** `PUT /orders/{id}/status`
* **Request Body:**
  ```json
  {
    "status": "ACCEPTED"
  }
  ```

---

## 5. Customer Management

### 5.1 Get All Customers
* **Endpoint:** `GET /api/customers`

### 5.2 Add Customer Manually
* **Endpoint:** `POST /api/customers`
* **Request Body:**
  ```json
  {
    "name": "Jane Doe",
    "email": "jane@example.com",
    "password": "password123",
    "phone": "0771234567"
  }
  ```

### 5.3 Update Customer Status (Activate/Deactivate)
* **Endpoint:** `PUT /api/customers/{id}/status`
* **Request Body:**
  ```json
  {
    "status": "Inactive"
  }
  ```
