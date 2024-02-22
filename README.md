# Getting Started

- System requirements
    - JDK 17
    - Kotlin 1.8.22
    - Gradle
- Start the dev server.
  ```
  gradle bootRun
  ```

# Verify That Everything Is Set Up Correctly

You can use cURL or a tool like [Postman](https://www.postman.com/) to test the API.

#### Example Curl Commands

You can log in as one of the seeded users with the following curl command:

```bash
curl --location --request POST 'localhost:8080/api/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "thomas",
    "password": "123456"
}'
```

Then you can use the token that comes back from the /login request to make an authenticated request to create a new blog
post

```bash
curl --location --request POST 'localhost:8080/api/posts' \
--header 'x-access-token: your-token-here' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text": "This is some text for the blog post...",
    "tags": ["travel", "hotel"]
}'
```

# Helpful Commands

- `gradle test` : This repository contains a non-comprehensive set of unit tests used to determine if your code meets
  the basic requirements of the assignment. **Please do not modify these tests.**
- This project is formatted using [google-java-format](https://github.com/google/google-java-format).

# Common Setup Errors

- If you see `gradle bootRun` get stuck with a message "80% EXECUTING", please check if there is a log message "Started
  BlogApplication in \*.\*\* seconds". Your server is running is you see this log.
