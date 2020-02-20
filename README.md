# Address Book
This is a Minimum Viable Product (MVP) implementation of an address book.  Due to the lack of time and access to Product Owner, a lot of assumption were made to build a usable version.

There are many improvements that can be made to the subsequent iterations.

## System Requirement
   - Java 8 is required

## Code Structure

#### Controllers
   - Exposes the services and handles requests - i.e. REST API, SOAP API etc

#### Services
   - The business logic wrapped in as Service

#### Caches
   - Helps to reduce read latency.

#### Data Access Layer (DAL)
   - Gateway to persistence layer.

#### Helpers
   - Common tasks
   
## Developer's Guide

#### How to build
In the root of the project, issue the following command
```jshelllanguage
./gradlew shadowJar
```
A executable jar file named ```address-book.jar``` will be created in ```build/libs``` directory

#### How to execute
Copy ```address-book.jar``` to a directory/folder where you have full read/write access and issue the following command
```jshelllanguage
 java -jar address-book.jar
```
This should give you instructions on how to use this application.

#### Example Command
```jshelllanguage
 java -jar address-book.jar create -n "My Address Book"
```