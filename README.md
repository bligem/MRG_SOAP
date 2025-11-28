# MGR_SOAP

Endpoints

**1. POST /api/user/register — Register a new user**

Description: Creates a new user account.
Auth: None (public)
Consumes / Produces: application/xml

Request (RegisterRequest)
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:api="http://soap.api.com/">
    <soap:Body>
        <api:register>
            <RegisterRequest>
                <email>user@example.com</email>
                <name>John Doe</name>
                <password>SuperSecret123!</password>
            </RegisterRequest>
        </api:register>
    </soap:Body>
</soap:Envelope>

```

Success (200 OK): Returns UserDto (new user).
Errors: 409 Conflict (email already used), 400 Bad Request (validation failed).

**2. POST /api/user/login — Login and receive token**

Description: Validates credentials and returns JWT token and user info.
Auth: None (public)
Consumes / Produces: application/xml

Request (LoginRequest)
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:api="http://soap.api.com/">
    <soapenv:Header/>
    <soapenv:Body>
        <api:login>
            <LoginRequest>
                <email>user@example.com</email>
                <password>SuperSecret123!</password>
            </LoginRequest>
        </api:login>
    </soapenv:Body>
</soapenv:Envelope>

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
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:api="http://soap.api.com/">
    <soap:Header>
        <api:Authorization>Bearer TOKEN</api:Authorization>
    </soap:Header>
    <soap:Body>
        <api:listUser>
            <ListUser>
                <id>UUID</id>
            </ListUser>
        </api:listUser>
    </soap:Body>
</soap:Envelope>

```


**5. PUT /api/user — Update user**

Description: Updates user profile. Only logged-in user (or admin) can modify.
Auth: Required
Consumes / Produces: application/xml

Request (UpdateUserRequest)
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:api="http://soap.api.com/">
    <soap:Header>
        <api:Authorization>Bearer TOKEN</api:Authorization>
    </soap:Header>
    <soap:Body>
        <api:updateUser>
            <UpdateUser>
                <id>UUID</id>
                <email>user@example.com</email>
                <name>user</name>
                <password>SuperSecret123!</password>
                <roles>
                    <role>ROLE</role>
                </roles>
            </UpdateUser>
        </api:updateUser>
    </soap:Body>
</soap:Envelope>
```

**6. DELETE /api/user — Delete user**

Description: Deletes a user (admin only).
Auth: Admin only
Consumes / Produces: application/xml

Request (UserRequest)
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:api="http://soap.api.com/">
    <soap:Header>
        <api:Authorization>Bearer TOKEN</api:Authorization>
    </soap:Header>
    <soap:Body>
        <api:deleteUser>
            <DeleteUser>
                <id>79c872ff-1f9a-4d01-88bc-9046f34b0314</id>
            </DeleteUser>
        </api:deleteUser>
    </soap:Body>
</soap:Envelope>

```

**7. POST /api/posts — Create a post**

Description: Creates a new post linked to logged-in user.
Auth: Required
Consumes / Produces: application/xml

Request (CreatePostRequest)
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:api="http://soap.api.com/">
    <soap:Header>
        <api:Authorization>Bearer TOKEN</api:Authorization>
    </soap:Header>
    <soap:Body>
        <api:createPost>
            <CreatePost>
                <title>title</title>
                <content>content</content>
                <tags>
                    <tag>tag</tag>
                </tags>
                <published>true / false</published>
            </CreatePost>
        </api:createPost>
    </soap:Body>
</soap:Envelope>
```

**8. GET /api/posts — List posts / get a single post**

Description: If ListPostsRequest contains id → returns that post. Otherwise returns a list (limited).
Auth: Optional  - allows viewing unpublished posts if owner/admin.
Consumes / Produces: application/xml

Request (ListPostsRequest)
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:api="http://soap.api.com/">
    <soapenv:Header>
        <api:Authorization>Bearer TOKEN</api:Authorization>
    </soapenv:Header>
    <soapenv:Body>
        <api:listPosts>
            <ListPosts/>
        </api:listPosts>
    </soapenv:Body>
</soapenv:Envelope>

```


**9. GET /api/posts/byUser — Posts by user**

Description: Lists posts of a given userId.
Auth: Optional — owners/admins can see unpublished posts
Consumes / Produces: application/xml

Request (ListPostsByUserRequest)
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:api="http://soap.api.com/">
    <soapenv:Header>
        <api:Authorization>Bearer TOKEN</api:Authorization>
    </soapenv:Header>
    <soapenv:Body>
        <api:listPostsByUser>
            <ListPostsByUserRequest/>
        </api:listPostsByUser>
    </soapenv:Body>
</soapenv:Envelope>
```


**10. PUT /api/posts — Update post**

Description: Updates a post. Owner or admin only.
Auth: Required
Consumes / Produces: application/xml

Request (UpdatePostRequest)
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:api="http://soap.api.com/">
    <soapenv:Header>
        <api:Authorization>Bearer TOKEN</api:Authorization>
    </soapenv:Header>
    <soapenv:Body>
        <api:updatePost>
            <UpdatePost>
                <id>UUID</id>
                <title>title</title>
                <content>content</content>
                <tags>
                    <tag>tag</tag>
                </tags>
                <published>true / false</published>
            </UpdatePost>
        </api:updatePost>
    </soapenv:Body>
</soapenv:Envelope>

```

**11. DELETE /api/posts — Delete post**

Description: Deletes a post. Admin can delete any; user can delete own.
Auth: Required
Consumes / Produces: application/xml

Request
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:api="http://soap.api.com/">
    <soap:Body>
        <api:deletePost>
            <DeletePost>
                <id>3fa85f64-5717-4562-b3fc-2c963f66afa6</id>
            </DeletePost>
        </api:deletePost>
    </soap:Body>
</soap:Envelope>

```

**How to run**

Set up fields in docker-compose:
```jsunicoderegexp
      POSTGRES_DB: api_db
      POSTGRES_USER: api_user
      POSTGRES_PASSWORD: api_pass
```

Set up fields in application.properties inside of resources:

```jsunicoderegexp
      app.jwt.secret: YOUR_SECRET
      server.port: PORT
```

Adjust application.yml

Build project:
```shell
mvn clean package
```

