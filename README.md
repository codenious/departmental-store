# Departmental Store (Mnytra-inspired)

This project is a Spring Boot + Spring Cloud sample that mimics a departmental store with **Product** and **Order** services, secured by OAuth2 JWT roles.

## Tech Stack
- Spring Boot 3
- Spring Cloud OpenFeign
- PostgreSQL
- Spring Security (OAuth2 Resource Server)
- SpringDoc OpenAPI

## Services & Endpoints
### Catalog
- `GET /api/catalog/landing` — Landing page with featured collections.

### Product Service
- `GET /api/products` — List products.
- `GET /api/products/{id}` — Product details.
- `POST /api/products` — Create product (ADMIN).
- `PUT /api/products/{id}` — Update product (ADMIN).
- `DELETE /api/products/{id}` — Delete product (ADMIN).

### Order Service
- `POST /api/orders` — Create order.
- `GET /api/orders` — List orders.
- `GET /api/orders/{orderNumber}` — Order detail.

## Security (OAuth2 JWT)
All `/api/**` endpoints require a JWT with `roles` claim.

Example claim payload:
```json
{
  "sub": "user@store.com",
  "roles": ["USER"],
  "iss": "departmental-store-auth"
}
```

For admin operations, include `"roles": ["ADMIN"]`.

The JWT secret and issuer are configured under `store.security.jwt` in `application.yml`.

## PostgreSQL
Set environment variables or use defaults:
```
STORE_DB_URL=jdbc:postgresql://localhost:5432/departmental_store
STORE_DB_USERNAME=store_user
STORE_DB_PASSWORD=store_password
```

## API Docs
Swagger UI: `/swagger-ui`
