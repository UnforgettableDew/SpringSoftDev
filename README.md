## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Setting up the Project](#setting-up-the-project)
3. [Starting the Application](#starting-the-application)
4. [Testing Endpoints with Swagger](#testing-endpoints-with-swagger)
5. [Examples](#examples)

## Prerequisites

Before starting, ensure that you have the following prerequisites installed on your system:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community or Ultimate edition)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 17)
- [Maven](https://maven.apache.org/download.cgi) (for project dependencies and build management)

## Setting up the Project

1. Launch IntelliJ IDEA and select "Open" from the welcome screen. Alternatively, if you already have IntelliJ IDEA open, go to "File" > "Open" and navigate to the project directory.

2. Find and select the project directory that contains the this application.

3. Click "Open" to open the project in IntelliJ IDEA.

4. Once the project is opened, IntelliJ IDEA will automatically detect the project's configuration and set up the necessary dependencies.

## Starting the Application

To start the Article Management application, follow these steps:

1. Open the project in IntelliJ IDEA, if it's not already open.

2. Find the `TestTaskApplication` class in the project structure. It should be in the package hierarchy of your project (src > main > java > com.unforgettabe.testtask.TestTaskApplication.

3. Right-click on the `TestTaskApplication` class and select "Run 'TestTaskApplication'".

4. IntelliJ IDEA will start the application and display the application logs in the console. Once the application is started, you should see a message indicating that the application is running.

5. Congratulations! Test task application is now running.

## Testing Endpoints with Swagger

The Article Management application provides a Swagger UI interface for testing its endpoints. Swagger UI is a user-friendly interface that allows you to interact with the API and test its functionality.

To access the Swagger UI and test the endpoints, follow these steps:

1. Open a web browser (Google Chrome, Mozilla Firefox, etc).

2. Enter the following URL in the browser's address bar: `http://localhost:8080/swagger-ui.html`.

3. The Swagger UI page will be loaded, displaying a list of available endpoints.

4. Expand the `article-controller` section to view the available Article Controller endpoints.

5. To test an endpoint, click on the endpoint name to expand it.

6. Click on the "Try it out" button to open the endpoint testing panel.
7. Fill in the required parameters and provide the request payload in the provided input field. You can refer to the provided example of the Article payload in the [Examples](#examples) section.
8. Click the "Execute" button to send the request and view the response.

## Examples
### List of articles which belong to certain site
1. Expand GET method `/article/site/{siteId}/list`.
2. Fill in the field `siteId`.
3. Click the "Execute" button.
### Adding articles to certain site
1. Expand POST method `/article/site/{siteId}`.
2. Fill in the field `siteId`.
3. Enter list of articles you want to add. If author was entered without id, he would be automatically added to database.
4. Click the "Execute" button.
### Payload
```json
[{
  "titleEnglish": "Moby-Dick",
  "titleGerman": "Moby-Dick",
  "issn": "7654321",
  "isbn": "978-0-9876-5432-1",
  "yearOfPublication": "1851-10-17T22:00:00.000+00:00",
  "editionNumber": 1,
  "authors": [
    {
      "id": 12,
      "fullName": "Michael Johnson21"
    }
  ]
}
]
```
### Update articles on certain site
1. Expand PUT method `/article/site/{siteId}`.
2. Fill in the field `siteId`.
3. Enter list of articles you want to update (`id` is mandatory). If author was entered without id, he would be automatically added to database.
4. Click the "Execute" button.
### Payload
```json
[{
  "id":28,
  "titleEnglish": "Moby-Dick",
  "titleGerman": "Moby-Dick",
  "issn": "7654321",
  "isbn": "978-0-9876-5432-1",
  "yearOfPublication": "1851-10-17T22:00:00.000+00:00",
  "editionNumber": 1,
  "authors": [
    {
      "id": 12,
      "fullName": "Michael Johnson21"
    }
  ]
}]
```
### Delete articles on certain site
1. Expand DELETE method `/article/site/{siteId}`.
2. Fill in the field `siteId`.
3. Enter list of articles you want to delete (`id` is mandatory).
4. Click the "Execute" button.
### Payload
```json
[{
  "id":28,
  "titleEnglish": "Moby-Dick",
  "titleGerman": "Moby-Dick",
  "issn": "7654321",
  "isbn": "978-0-9876-5432-1",
  "yearOfPublication": "1851-10-17T22:00:00.000+00:00",
  "editionNumber": 1,
  "authors": [
    {
      "id": 12,
      "fullName": "Michael Johnson21"
    }
  ]
}]
```
### Find duplicates articles
1. Expand POST method `/article/duplicates`.
2. Enter article to check it on existing duplicates.
3. Click the "Execute" button.
### Payload
```json
[{
  "titleEnglish": "Moby-Dick",
  "titleGerman": "Moby-Dick",
  "issn": "7654321",
  "isbn": "978-0-9876-5432-1",
  "yearOfPublication": "1851-10-17T22:00:00.000+00:00",
  "editionNumber": 1,
  "authors": [
    {
      "id": 12,
      "fullName": "Michael Johnson21"
    }
  ]
}]
```
