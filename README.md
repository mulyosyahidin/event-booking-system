# Event Booking System API Service

## Endpoint
| #  | HTTP Method | Endpoint                      | Authentication | Role  |
|----|-------------|-------------------------------|----------------|-------|
| 1  | GET         | `/`                           | ❌              | -     |
| 2  | POST        | `/auth/login`                 | ❌              | -     |
| 3  | POST        | `/auth/register`              | ❌              | -     |
| 4  | GET         | `/auth/me`                    | ✅              | -     |
| 5  | DELETE      | `/auth/logout`                | ✅              | -     |
| 6  | POST        | `/files/upload`               | ✅              | Admin |
| 7  | GET         | `/files/view`                 | ✅              | -     |
| 8  | GET         | `/admin/events`               | ✅              | Admin |
| 9  | GET         | `/admin/events/{id}`          | ✅              | Admin |
| 10 | POST        | `/admin/events`               | ✅              | Admin |
| 11 | PUT         | `/admin/events/{id}`          | ✅              | Admin |
| 12 | DELETE      | `/admin/events/{id}`          | ✅              | Admin |
| 14 | GET         | `/admin/events/{id}/bookings` | ✅              | Admin |
| 14 | GET         | `/admin/users`                | ✅              | Admin |
| 15 | GET         | `/admin/users/{id}`           | ✅              | Admin |
| 16 | PUT         | `/admin/users/{id}`           | ✅              | Admin |
| 17 | DELETE      | `/admin/users/{id}`           | ✅              | Admin |
| 18 | GET         | `/users/events`               | ❌              | -     |
| 19 | GET         | `/users/events/{id}`          | ❌              | -     |
| 20 | GET         | `/users/bookings`             | ✅              | User  |
| 21 | GET         | `/users/bookings/{id}`        | ✅              | User  |
| 22 | POST        | `/users/bookings`             | ✅              | User  |
| 23 | GET         | `/users/bookings/data`        | ❌              | -     |
| 24 | POST        | `/midtrans/callback`          | ❌              | -     |
| 25 | GET         | `/admin/bookings`             | ✅              | Admin |
| 26 | GET         | `/admin/bookings/{id}`        | ✅              | Admin |
| 27 | GET         | `/admin/overview`             | ✅              | Admin |

### User
User terdiri dari dua role, yaitu `Admin` dan `User`, dimana `Admin` berperan untuk menambahkan data sedangkan `User` sebagai pembeli.

#### Default Admin
User `Admin` dibuat saat aplikasi _start up_ dengan data:
- username: `admin`
- password: `password`

### User
User dapat mendaftar melalui _endpoint_ `/auth/register`.

## Profile
### Development
- base url: `http://localhost:8080`

### Staging
- base url: https://event-booking.codewith.cyou
- swagger url: https://event-booking.codewith.cyou/swagger-ui/index.html

## Postman
- [Postman - Collection](./postman/Collection.json)
- [Environment - Dev](./postman/Environment%20-%20Dev.json)
- [Environment - Staging](./postman/Environment%20-%20Staging.json)

## Flowchart
- [Login](./flowchart/Login.jpg)
- [Admin - Tambah Event](./flowchart/Admin%20-%20Tambah%20Event.jpg)
- [User - Booking](./flowchart/User%20-%20Booking.jpg)
