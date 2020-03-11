# PocketClone API [![Build Status](https://travis-ci.com/samjones1001/pocket-clone-backcend.svg?branch=master)](https://travis-ci.com/samjones1001/pocket-clone-backend) [![codecov](https://codecov.io/gh/samjones1001/pocket-clone-backend/branch/master/graph/badge.svg)](https://codecov.io/gh/samjones1001/pocket-clone-backend)

A Kotlin/Spring api for storing and retrieving web links for later reading. Intended for use with [PocketClone](https://github.com/samjones1001/pocket-clone-frontend)

### Live Version 
A live version of this api is hosted at `https://protected-harbor-70609.herokuapp.com`. It responds to requests sent to the enpoints listed below in the [api section](#api)

## Setup and Running

This project uses [Gradle](https://gradle.org/) to manage dependencies.

- Clone this repository and navigate to its root directory in the command line
- If not already present on your system, install gradle using `brew install gradle` for macOS, or `sdk install gradle 6.0` for other Unix-based operating systems.
- Install dependencies and build the project using `./gradlew build`

The application can be run in two modes, standard and test. Using either mode will start the api on port 8080.

#### Standard Mode

In standard mode, the api will expect to interact with a locally running [PosgreSQL](https://www.postgresql.org/) database. Running in standard mode will therefor require some initial setup:

- Ensure that PostgreSQL is [installed on your system](https://www.postgresql.org/docs/9.0/tutorial-install.html)
- [Setup a PostgreSQL database](https://www.postgresql.org/docs/9.0/tutorial-createdb.html) with a name of of your choice.
- Set the following environment variables to allow for connection to your database: 
    - `DB_HOST`
    - `DB_NAME`
    - `DB_USERNAME`
    - `DB_PASSWORD`
    
Once this setup is complete, you will be able to run the api locally by navigating to the root directory of the project and using `./gradlew bootRun`.

#### Test Mode

In test mode, the api will interact with an in memory h2 database, and therefore requires no further preliminary setup from the user.

To run in test mode, navigate to the root directory of the project and use `SPRING_PROFILES_ACTIVE=test ./gradlew bootRun`.

## Tests

Prior to running the tests please ensure that you have installed all required dependencies as per the instructions in the [Setup and Running section](#setup-and-running).
Tests can be run from the command line using `./gradlew test` or started using your ide. 

All test suites are configured to make use of an in memory h2 database, so no additional database setup will be required.

## API 

The API exposes the following endpoints:

- `GET /api/articles`
  Returns a list of all articles currently saved to the database
  
- `POST /api/articles`
  Creates a new article and saves it to the database. Expects a JSON request body with the format { "url" : String }
  
- `DELETE /api/articles/{id}`
  Deletes an article from the database with an id matching that passed in the url
  
- `PUT api/articles/{id}`
  Updates the `isRead` property of an article in the database with an id matching that passed in the url. Expects a JSON request body with the format { "isRead" : boolean }
  
- `GET /api/recommendations`
  Retrieves a list of the ten most recent stories from the [Guardian API](https://open-platform.theguardian.com/documentation/)
