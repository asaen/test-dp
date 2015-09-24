# datapine

Web application, which uses Maven, JPA and Spring. It comes with an embedded H2 database and the Maven Jetty plugin configured.

To start the application use command line specified below:
```
mvn jetty:run
```

To access it type in a browser [http://localhost:8080/datapine-test-app](http://localhost:8080/datapine-test-app).

There are few roles and test users which are initialized during the start of the application:
- ROLE_ADMIN: can see list of users, every user details, all items, add or remove items
  * admin@dp.com / admin
- ROLE_USER: can see list of users, see own items and add new items
  * aa@dp.com / aa
  * bb@dp.com / bb
  * cc@dp.com / cc
- ROLE_GUEST: can see the welcome page only
  * guest@dp.com / guest

## Basic flow

When a user is registered, create domain object as it is.
When an item is added, create domain object as well as an Acl entry for item domain. 
When item object is requested via `find*` methods filter out non authorized items.
//item object must linked to user domain.(acl_sid table has  User.id as sid)

## TODOs
- [x] Implement the `UserDAO` using JPA.
- [x] Implement the `UserService` using the `UserDAO` and transactions.
- [x] Implement a RESTful `UserController` to manage users.
- [x] Implement a simple user login dialog to login a user. Use `HttpSession` to store the user credentials to save time, Spring Security can also be used.
- [x] Use Spring AOP to log all login attempts via console output or log4j.
- [x] Implement and secure via spring security `ItemDAO` and `Item`.
