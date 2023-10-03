# Java Blog API Demo

### How to run (inital instructions):

1. Ensure all maven packages are installed
2. Create new MYSQL schema labeled 'javablogapi'
3. Add these environment variables to the run command
   1. **MYSQL_DB_URI** (MYSQL database connection string)
   2. **DB_USER** (MYSQL database user)
   3. **DB_PASSWORD** (MYSQL database password)
   4. **API_URL** (The base url for api routes [should be set to <ins>http://localhost:8080</ins> locally])
4. Run the application with this command: `./mvnw spring-boot:run`

### Routes:

- **/api/posts** (GET)
  - Get all posts from the database
- **/api/posts/create** (POST)
  - Creates new post (**must include title & content in JSON body**)
- **/api/posts/update/[postId]** (PUT)
  - Updates existing post with the matching id (**must include title & content as params**)
- **/api/posts/delete/[postId]** (DELETE)
  - Deletes existing post with matching id
