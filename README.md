# Event Booking System API Service

## Endpoint
| #  | HTTP Method | Endpoint             | Authentication | Role  |
|----|-------------|----------------------|----------------|-------|
| 1  | GET         | `/`                  | ❌              | -     |
| 2  | POST        | `/auth/login`        | ❌              | -     |
| 3  | POST        | `/auth/register`     | ❌              | -     |
| 4  | GET         | `/auth/me`           | ✅              | -     |
| 5  | DELETE      | `/auth/logout`       | ✅              | -     |
| 6  | POST        | `/files/upload`      | ✅              | Admin |
| 7  | GET         | `/files/view`        | ✅              | -     |
| 8  | GET         | `/admin/events`      | ✅              | Admin |
| 9  | GET         | `/admin/events/{id}` | ✅              | Admin |
| 10 | POST        | `/admin/events`      | ✅              | Admin |
| 11 | PUT         | `/admin/events/{id}` | ✅              | Admin |
| 12 | DELETE      | `/admin/events/{id}` | ✅              | Admin |

### User
User terdiri dari dua role, yaitu `Admin` dan `User`, dimana `Admin` berperan untuk menambahkan data sedangkan `User` sebagai pembeli.

#### Default Admin
User `Admin` dibuat saat aplikasi _start up_ dengan data:
- username: `admin`
- password: `password`

### User
User dapat mendaftar melalui _endpoint_ `/auth/register`.