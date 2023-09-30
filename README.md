# Java Blog API Demo

### How to run:

1. Ensure all maven packages are installed
2. Add DB_USER and DB_PASSWORD for your MYSQL instance to the enviornment variables
   - On Mac you would run (`export DB_USER=yourmysqlusername`) and (`export DB_PASSWORD=yourmysqlpassword`)
   - On Windows you would run (`set DB_USER=yourmysqlusername`) and (`set DB_PASSWORD=yourmysqlpassword`)
3. Create new MYSQL schema labeled 'javablogapi'
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
