Endpoints

**1. POST /api/user/register — Register a new user**

Description: Creates a new user account.
Auth: None (public)
Consumes / Produces: application/xml

Request (RegisterRequest)
```xml
<API>
  <email>user@example.com</email>
  <name>John Doe</name>
  <password>SuperSecret123!</password>
</API>
```

Success (200 OK): Returns UserDto (new user).
Errors: 409 Conflict (email already used), 400 Bad Request (validation failed).

**2. POST /api/user/login — Login and receive token**

Description: Validates credentials and returns JWT token and user info.
Auth: None (public)
Consumes / Produces: application/xml

Request (LoginRequest)
```xml
<API>
  <email>user@example.com</email>
  <password>SuperSecret123!</password>
</API>
```

**3. GET /api/users — List users**

Description: Returns a list of users (limited by limit).
Auth: Admin only
Consumes / Produces: application/xml

Request body (ListUsersRequest) — optional limit.

**4. GET /api/user — Get a single user**

Description: Returns user details by id.
Auth: Admin only
Consumes / Produces: application/xml

Request (UserRequest)
```xml
<API>
  <id>c3b7a0d1-2f9d-4b2d-9a6f-1f2b3c4d5e6f</id>
</API>
```


**5. PUT /api/user — Update user**

Description: Updates user profile. Only logged-in user (or admin) can modify.
Auth: Required (Authorization).
Consumes / Produces: application/xml

Request (UpdateUserRequest)
```xml
<API>
  <id>c3b7a0d1-2f9d-4b2d-9a6f-1f2b3c4d5e6f</id> <!-- optional -->
  <email>new@example.com</email>
  <name>New Name</name>
  <password>NewPass123!</password>
  <roles>
    <role>USER</role>
  </roles> <!-- admin only -->
</API>
```

**6. DELETE /api/user — Delete user**

Description: Deletes a user (admin only).
Auth: Admin only
Consumes / Produces: application/xml

Request (UserRequest)
```xml
<API>
  <id>c3b7a0d1-2f9d-4b2d-9a6f-1f2b3c4d5e6f</id>
</API>
```

**7. POST /api/posts — Create a post**

Description: Creates a new post linked to logged-in user.
Auth: Required (Authorization).
Consumes / Produces: application/xml

Request (CreatePostRequest)
```xml
<API>
  <title>My first post</title>
  <content>Here is the content</content>
  <tags>
    <tag>Test1</tag>
  </tags>
  <published>true</published>
</API>
```

**8. GET /api/posts — List posts / get a single post**

Description: If ListPostsRequest contains id → returns that post. Otherwise returns a list (limited).
Auth: Optional  - allows viewing unpublished posts if owner/admin.
Consumes / Produces: application/xml

Request (ListPostsRequest)
```xml
<API>
  <id>3fa85f64-5717-4562-b3fc-2c963f66afa6</id> <!-- optional -->
  <limit>10</limit> <!-- optional -->
</API>
```


**9. GET /api/posts/byUser — Posts by user**

Description: Lists posts of a given userId.
Auth: Optional — owners/admins can see unpublished posts
Consumes / Produces: application/xml

Request (ListPostsByUserRequest)
```xml
<API>
  <userId>c3b7a0d1-2f9d-4b2d-9a6f-1f2b3c4d5e6f</userId> <!-- optional -->
  <limit>10</limit> <!-- optional -->
</API>
```


**10. PUT /api/posts — Update post**

Description: Updates a post. Owner or admin only.
Auth: Required (Authorization).
Consumes / Produces: application/xml

Request (UpdatePostRequest)
```xml
<API>
  <id>3fa85f64-5717-4562-b3fc-2c963f66afa6</id>
  <title>Updated title</title>
  <content>Updated content</content>
  <tags>
    <tag>NewTag</tag>
  </tags>
  <published>false</published>
</API>
```

**11. DELETE /api/posts — Delete post**

Description: Deletes a post. Admin can delete any; user can delete own.
Auth: Required (Authorization).
Consumes / Produces: application/xml

Request
```xml
<API>
  <id>3fa85f64-5717-4562-b3fc-2c963f66afa6</id>
</API>
```