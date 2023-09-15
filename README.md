# JPA Fetching with toString problem

## Description

Was found that if we will use [fetch = FetchType.LAZY](/src/main/java/fetching/model/User.java) with
overridden toString, which
includes the collection with LazyLoading we lose all benefits of laziness.

## More Details

In [User.java](/src/main/java/fetching/model/User.java) we have oneToMany relation on roles and
lazy-fetching. And very important thing that in this class was overridden two different toString
methods. First one makes incorrect behavior for fetch type LAZY, and doesn't work as Lazy, check the
logs:

```roomsql
select u1_0.user_id,u1_0.user_phone,u1_0.user_name from USERS u1_0 where u1_0.user_id=?
select r1_0.user_id,r1_1.role_id,r1_1.role from user_on_role r1_0 join ROLES r1_1 on r1_1.role_id=r1_0.role_id where r1_0.user_id=?
Before retrieving roles for user: User{id=1, userName='name', phone='phone', roles=[Role{id=1, role='dev'}, Role{id=2, role='admin'}, Role{id=3, role='tester'}]}
```

It means that when `EntityManager` try to find and get object, lazy loading for `roles` not working.
The second one `toString()` method works in another way, check logs:

```roomsql
select u1_0.user_id,u1_0.user_phone,u1_0.user_name from USERS u1_0 where u1_0.user_id=?
Before retrieving roles for user: User{id=2, userName='name', phone='phone'}
select r1_0.user_id,r1_1.role_id,r1_1.role from user_on_role r1_0 join ROLES r1_1 on r1_1.role_id=r1_0.role_id where r1_0.user_id=?
```

In second way we retrieve the roles only using `getRoles()`, not on `EntityManager.find()`.

## How to use code?

For testing, you can use `UserController.java`. In controller created endpoints for creation, find,
and checkLazyLoading methods. First of all create some users, and the execute checkLazyLoading
endpoint, and you will check the logs which was described.