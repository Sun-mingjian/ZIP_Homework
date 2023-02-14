# User API Dev Guide

## Building and Running

1. `Java 11`, `Mysql`, `git`, `gradle`, `docker`, `IntellijIDEA`, `Postman`, `MYSQL Workbench` installed locally.
2. Under the root folder, run ``./gradlew clean build`` command to build the project.
3. If you are using intellij, you can run this application by the clicking running button on the top right panel of the
   IDE.
4. If you are using terminal(linux), you can run this application by typing ``./gradlew  bootRun``
5. Also, you can containerize and run this application by using docker compose, which would build mysql and
   application images and run as containers, please try to run this following commands:
    ```
    docker-compose build  
    docker-compose up
    ```

## Testing

1. For functional unit testing, we use junit5 and mockito, please check `UserServiceTest.java` file.
2. For functional integration testing, we use springboot test powered by spring framework, please
   check `UserControllerTest.java` file.
3. For api testing, please try to use postman as the api client, and hit url like :
    1. ``GET http://localhost:8080/api/user/1``
    2. ``GET http://localhost:8080/api/users``
    3. ``POST http://localhost:8080/api/user``
       ```
        {
          "username":"michael",
          "monthly_salary": 5000.00,
          "monthly_expense": 2222.00,
          "email":"michael@gmail.com"
        }
       ```
    4. ``POST http://localhost:8080/api/user/1/account``
       ```
       {
         "account_type":"ZIP PAY"
       }
       ```
    5. ``GET http://localhost:8080/api/accounts``
4. For database debug, please try to use any database client tools like mysql `MYSQL Workbench`, connect it with
   host url `jdbc:mysql://localhost:3306/zip`.

## Deploying

1. Deployments are managed via Jenkins. `Master` is automatically deployed to staging `staging.api.zip.user.com`,
   while `Release` is auto-deployed to production `api.zip.user.com`.
2. Any changes around database will be handled by liquibase script, please create liquibase script
   under `resource\db\{version}`folder(Under Construction), they would be auto deployed through the app deployment
   process.

## Additional Information
Please reach out to Michael(`michael.sun.career@gmail.com`) for any issues.
