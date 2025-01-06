Dynamic Pricing API

Overview
The Dynamic Pricing API is a RESTful service designed to support an e-commerce platform with advanced features such as real-time price adjustments, predictive search, and inventory management. Built with Spring Boot and connected to a MySQL database, this API provides robust endpoints for managing products, categories, users, and carts while enabling dynamic pricing based on business rules.

This API is part of a broader ecosystem, including a Vue.js front-end e-commerce website that integrates seamlessly with this backend.


Features
- Product Management: Create, update, delete, and fetch products.
- Dynamic Pricing: Real-time price adjustments based on inventory levels or demand criteria.
- Inventory Management: Manage stock levels and update product availability.
- Predictive Search: Suggest products based on partial matches in names or descriptions.
- User Authentication: Manage users with secure login credentials.
- Cart Management: Add, update, and remove items from a shopping cart.


Roadmap
Phase 1: Setup and Foundation
- ✅ Initial project setup with Spring Boot.
- ✅ Basic /hello endpoint for testing.
  
Phase 2: Product Management
- ✅ Product Entity with CRUD endpoints.
- ✅ Connection to MySQL database hosted on school servers.
  
Phase 3: Dynamic Pricing
- 🔄 Implement dynamic pricing logic (in progress).
- 🔄 Define and apply pricing rules for products.
  
Phase 4: Inventory and Demand
- 🔄 Add endpoints for managing product stock.
- 🔄 Integrate pricing rules with inventory levels.
- ❓ (Optional) Track user behaviour for demand-based pricing.

Phase 5: Finalization
- 🔄 Add input validation and error handling.
- 🔄 Document the API using Swagger.
- ❓ Deploy the API on a cloud provider (TBD).


Project Architecture
Entities
- Product: Represents an item for sale.
  - id, name, description, price, stock
    
- Cart: Represents a user's shopping cart.
  - id, userId, totalPrice, items
    
- CartItem: Represents an item in a cart.
  - id, productId, quantity, price
    
- User: Represents a customer or admin.
  - id, email, password, role
    
- Category: Represents product categorization.
  - id, name
    
- Relationships
- Product ↔ Category: Many-to-One
- Cart ↔ CartItem: One-to-Many
- User ↔ Cart: One-to-One

  
Tech Stack
- Backend: Spring Boot (Java)
- Database: MySQL (hosted on school servers)
- Frontend: Vue.js (external integration)


Current Progress
Completed:
- Repository interfaces for entities.
- Connected to MySQL database.
- Learned and implemented exception handling.
- Built the service layer for CRUD operations.

Next Steps:
- create ErrorResponse class for structured error responses.
- Implement centralized exception handling using @ControllerAdvice.
- Add repository methods for filtering, sorting, and predictive search.
- Build service layer for advanced business logic.
- Integrate authentication and authorization.
