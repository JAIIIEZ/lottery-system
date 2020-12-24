# Lottery System

The system should have a way to create Lotteries. Users can participate to any lottery that isn't finished yet. 
When you participate to a Lottery you become part of its extraction pool of potential winners. 
Once the Lottery closes a random winner is extracted from the pool and save winner number in a storage.

Requirements are:
- The service will allow anyone to register as a lottery participant.
- Lottery participants will be able to submit as many lottery ballots for any lottery that isn’t yet finished.
- Each day at midnight the lottery event will be considered closed and a random lottery winner will be selected from all participants for the day.
- All users will be able to check the winning ballot for any specific date.
- The service will have to persist the data regarding the lottery.

## 1 - Tools and Technologies Used

* [Spring Boot] - 2.0.4.RELEASE
* [JDK] - 1.8 or later
* [Spring Framework] - 5.0.8 RELEASE
* [Hibernate] - 5.2.17.Final
* [Spring Data] JPA
* [Maven] - 3.2+
* [IDE] - Eclipse or Spring Tool Suite (STS)
* [PostgreSQL] - 42.2.5
* [JSP]
* [Spring Security]
* [Spring Web]
* [Bootstrap]


## 2 - Packaging Structure
Following is the packing structure of our Lottery System




<img src="images/structure.png" alt="drawing" width="400"/>

## 3 - Database Design

The PostgreSQL database looks like:


<img src="images/postgresql.png" alt="drawing" width="800"/>



## 4 - Testing REST APIs via Postman Client


### User registration
<img src="images/postman_images/user_register.png" alt="drawing"/>

### Throws exception if user trys to register with same username
<img src="images/postman_images/user_already_exist.png" alt="drawing"/>

### Get all active lotteries
<img src="images/postman_images/active_lotteries.png" alt="drawing"/>

### Submit lottery ticket
<img src="images/postman_images/sumbit_lottery_ticket.png" alt="drawing"/>

### Throws exception if user trys to submit ticket with unregistered username
<img src="images/postman_images/submit_lottery_with_invalid_username.png" alt="drawing"/>

### Throws exception if user trys to submit ticket for passive lottery
<img src="images/postman_images/submit_passive_lottery.png" alt="drawing"/>

### End lottery by lottery id
<img src="images/postman_images/end_lottery_by_id.png" alt="drawing"/>

### Throws exception if lottery does not exist
<img src="images/postman_images/end_invalid_lottery.png" alt="drawing"/>


## 5 - Coulda/Woulda/Shoulda

- I would have implement integration tests.
- I could load data from the xml file using the dbunit library while writing unit tests.
- I would have complete front-end part.
- I could have extend queries usign DateUtils.
- I could have implement submit ticket endpoint without username parameter, I could have get active user automatically.
