# Event Booking System API Service

## Endpoint
| #  | HTTP Method | Endpoint               | Authentication | Role  |
|----|-------------|------------------------|----------------|-------|
| 1  | GET         | `/`                    | ❌              | -     |
| 2  | POST        | `/auth/login`          | ❌              | -     |
| 3  | POST        | `/auth/register`       | ❌              | -     |
| 4  | GET         | `/auth/me`             | ✅              | -     |
| 5  | DELETE      | `/auth/logout`         | ✅              | -     |
| 6  | POST        | `/files/upload`        | ✅              | Admin |
| 7  | GET         | `/files/view`          | ✅              | -     |
| 8  | GET         | `/admin/events`        | ✅              | Admin |
| 9  | GET         | `/admin/events/{id}`   | ✅              | Admin |
| 10 | POST        | `/admin/events`        | ✅              | Admin |
| 11 | PUT         | `/admin/events/{id}`   | ✅              | Admin |
| 12 | DELETE      | `/admin/events/{id}`   | ✅              | Admin |
| 13 | GET         | `/admin/users`         | ✅              | Admin |
| 14 | GET         | `/admin/users/{id}`    | ✅              | Admin |
| 15 | PUT         | `/admin/users/{id}`    | ✅              | Admin |
| 16 | DELETE      | `/admin/users/{id}`    | ✅              | Admin |
| 17 | GET         | `/users/events`        | ❌              | -     |
| 18 | GET         | `/users/events/{id}`   | ❌              | -     |
| 19 | GET         | `/users/bookings`      | ✅              | User  |
| 20 | GET         | `/users/bookings/{id}` | ✅              | User  |
| 21 | POST        | `/users/bookings`      | ✅              | User  |
| 22 | GET         | `/users/bookings/data` | ❌              | -     |
| 23 | POST        | `/midtrans/callback`   | ❌              | -     |

### User
User terdiri dari dua role, yaitu `Admin` dan `User`, dimana `Admin` berperan untuk menambahkan data sedangkan `User` sebagai pembeli.

#### Default Admin
User `Admin` dibuat saat aplikasi _start up_ dengan data:
- username: `admin`
- password: `password`

### User
User dapat mendaftar melalui _endpoint_ `/auth/register`.