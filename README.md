# Multiple Profiles Example
This project shows how to set up a Spring Boot app to have a local development database configuration that is overridden when deployed to Pivotal Application Service.

## To use
1. Call `create-db.ps1` to create a credhub service that points to the database you want to use.  This command will create a credhub based service if that is available, otherwise it will fall back to creating a less secure User Provided Service.
2. Call `gradlew assemble` to create the JAR to push up to PAS.
3. Call `cf push` to push the application to PAS and bind it to the user provided service you created.

You can then navigate to `https://multiprofile.<your-pas-apps-domain>` to test out the app.

## Clean up
Call the `cleanup.ps1` script to clean up the items created by this example project.