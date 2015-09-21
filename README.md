# datapine

Web application, which uses Maven, JPA and Spring. It comes with an embedded H2 database and the Maven Jetty plugin configured.

To start the application use command line specified below:
```
mvn jetty:run
```

To access it type in a browser [http://localhost:8080/datapine-test-app](http://localhost:8080/datapine-test-app).

## Basic flow

When a user is registered, create domain object as it is.
When an item is added, create domain object as well as an Acl entry for item domain. 
When item object is requested via `find*` methods filter out non authorized items.
//item object must linked to user domain.(acl_sid table has  User.id as sid)

## TODOs
- [ ] Implement the `UserDAO` using JPA.
- [ ] Implement the `UserService` using the `UserDAO` and transactions.
- [ ] Implement a RESTful `UserController` to manage users.
- [ ] Implement a simple user login dialog to login a user. Use `HttpSession` to store the user credentials to save time, Spring Security can also be used.
- [ ] Use Spring AOP to log all login attempts via console output or log4j.
- [ ] Implement and secure via spring security `ItemDAO` and `Item`.
