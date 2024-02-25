# Written evaluation

## QUESTION 1

### Part 1

Right now, there are two tables in the database: Posts and Users. 
They are connected by a join table that describes 
the many-to-many relationship of multiple posts being associated to multiple users. 
This is achieved via foreign keys: at the time of creating the tables and 
declaring the fields in them, we can indicate which field in one table 
is a foreign key that references a field in another table.

Following this principle, introducing roles and permissions would mean 
creating two new tables in the database, Roles and Permissions. 
This is needed because a role will probably have different permissions 
to perform operations on the database: a reader will only have read permissions; 
an owner will have read and write permissions only on the posts they are owners of; 
an admin will have read and write permissions on all posts (real life cases 
will have more detailed roles and permissions, but this is just an example).
The new table Roles would probably contain a role ID field and a role name field. 
The Roles table and the User table would join into a many-to-many relationship 
over a join table Users_Roles. 
This table would contain a user ID field and a role ID field, and they would be 
referencing external fields via foreign keys: Users_Roles.userID into Users.userID, 
and Users_Roles.roleID into Roles.roleID.
The new table Permissions would probably contain a permission ID field and a 
permission name field. The Permissions table and the Roles table would join 
into a many-to-many relationship over a join table Permissions_Roles. 
This table would contain a permission ID field and a role ID field, and they would 
be referencing external fields via foreign keys: Permissions_Roles.roleID 
into Roles.roleID, and Permissions_Roles.permissionID into Permissions.permissionID.
According to how complex the business requirements are, and how big the 
list of users, roles and permissions might grow, an extra Groups table might help, 
which would assign a list of users to a group. 
If a user belongs to a certain group, the roles and permissions it has are 
automatically defined based on the group name.
Of course, it would be an overkill to have this whole Identity management 
system in place for a simple app or a case where no sensitive information 
is exposed. 
But more complex systems that require trustworthy data protection, 
might have a whole microservice (API + database) dedicated to IAM operations.

With an identity management system (as simple or complex as needed), 
we could ensure that we can fetch all roles (with associated permissions) 
related to each user with a certain ID. 
When performing operations on the database, the queries would need to be updated 
to check that the logged-in user is allowed to perform such operation, 
based on its user ID, roles and permissions (i.e. if user 123 is a reader, 
they could only perform reading operations; if user 456 was an admin, 
they could perform all operations; if user 789 was an owner of a post, 
they could add/remove users and change user roles only to posts where they are 
owners; etc.).
Foreign keys ensure that logged-in users cannot arbitrarily 
add/remove/change users, roles or permissions. 
Only an admin should be able to do that, and a regular user must not be able 
to delete an admin role from a user, for example.
Extra fields might need to be added to the Roles, Permissions and Users tables, 
for example a createdAt field that indicates when a new role was added to the 
Roles table, or when a user was assigned a certain role or permission.
This whole setup would be described as SQL statements when we create the 
database (or as the initial migration file), by creating the new tables and 
setting up the foreign keys. Join statements would be used to exploit 
the many-to-many relationships between users and roles, and roles and permissions.

### Part 2

I think being able to add/change roles and permissions associated with users implies 
using schema migrations and migration softwares. 
Any change to an existing database schema might break the database, 
because of inconsistencies between existing data and new data.
To avoid this kind of problem, schema migration softwares are used. 
They take files that describe the database schema changes in an incremental way, 
and they apply changes to the data according to each migration file, 
in order to bring the old data up to date with the most recent schema, 
without breaking things.
According to the complexity of the schemas involved, a migration-based migration 
or a state-based migration would need to be chosen.

In our case, if enums restricted the possible values roles and permissions 
can be assigned to, 
a schema migration file could add an extra fixed value to the enum, that could be 
assigned to a user from that moment, passing it to the service in the preferred way
(query parameter, part of the request body, request header, etc.). 
This would require no code changes in the GET and POST endpoints, 
because the DTO and DAO objects that describe database data would remain the same, 
the only difference is that the roleName property, for example, 
could have extra values.

## QUESTION 2

Some endpoints might have restricted access based on the roles and permissions 
associated with the logged-in user. 
If a user is a reader, they could access the GET posts endpoint, 
but not the POST endpoint (or any potential PUT and DELETE post endpoints). 
A post owner would be able to access both GET and POST endpoints; 
they could also access a potential endpoint to add a new author to a certain 
post (provided they are already owners of that post), or to change the 
role/permission of an author of a post (again, provided the post being 
modified belongs to them).
An admin could have unrestricted access to all endpoints.

The fact that every post needs to have at least one owner user implies 
that when POSTing a new post, under the hood the user ID would need to be 
associated to the owner role across different tables. When GETting the list 
of posts associated with a list of author IDs, we might need to specify 
which roles we want to fetch for: a list of posts where user ID 123 is owner 
might be different from a list of posts where user ID 123 is not owner but 
only has reading permissions.

A further consideration might be that different objects need to be returned 
as responses of the same endpoint, according to the role of the requesting user.
I can think of multiple ways of doing this:
- different DTO objects as responses, based on what role the logged-in user has
- same DTO response for every user, and null fields when accessing information that is forbidden to that user
- different endpoints to hit based on the user role

I don't think it's good practice to return different objects from the same endpoint,
it makes it harder to understand the code and the complexity can grow with
every new custom role that is created.

Nulling out forbidden fields I think it's is also bad practice, because it reveals 
information on what extra data is in the database: when data is forbidden,
its value should be inaccessible, but also the data name itself. 
So the best practice is that forbidden data simply doesn't exist for that user.

I think different endpoints are also confusing, and it might not be straightforward
to understand which one to hit, based on the user role.
I think I would keep the endpoint URI the same, but add an extra resource 
that would return a different/extended DTO response.
So `/api/user/{id}` would return the exact same User DTO response for everyone (reader
or admin), but `/api/user/{id}/permissions` would return a Permissions DTO object that
can only be accessed by an admin, for example.
I have a feeling there would be drawbacks and limitations to this approach as well,
but I seem to understand that that's what backend is all about: there's always a next 
step to make the code more secure, reliable, efficient; we also need to understand when
to stop improving existing capabilities, based on business requirements.

In general, it is not straightforward for me to predict what kind of code changes should be 
accommodated and what edge cases I may encounter. 
I think it is more natural to me to start coding, and then the 
limitations/implications of this new feature would become evident while
implementing the new code and testing it afterward. 
In general, I am a practical person, so it works well for me to learn 
and understand new things through actual coding: it is easier for me to face
whatever issue might come, rather than just thinking about it (I will probably
miss something anyway). 
Of course, in case of complex problems, I would spend time thinking, investigating,
reading and asking more experienced people. But I am sure that many aspects
of the problem would only surface when actually starting to code.
For me the best way of proceeding is always a mix of familiarizing with the task
on a theoretical level, but then moving onto the practical part. 
You won't know if you don't try.
