# Event Booking System API Service

## Endpoint

| # | HTTP Method | Endpoint         | Authentication | Role  |
|---|-------------|------------------|----------------|-------|
| 1 | GET         | `/`              | ❌              | -     |
| 2 | POST        | `/auth/login`    | ❌              | -     |
| 3 | POST        | `/auth/register` | ❌              | -     |
| 4 | GET         | `/auth/me`       | ✅              | -     |
| 5 | DELETE      | `/auth/logout`   | ✅              | -     |
| 6 | POST        | `/files/upload`  | ✅              | Admin |
| 7 | GET         | `/files/view`    | ✅              | -     |

### User
User terdiri dari dua role, yaitu `Admin` dan `User`, dimana `Admin` berperan untuk menambahkan data sedangkan `User` sebagai pembeli.

#### Default Admin
User `Admin` dibuat saat aplikasi _start up_ dengan data:
- username: `admin`
- password: `password`

### User
User dapat mendaftar melalui _endpoint_ `/auth/register`.