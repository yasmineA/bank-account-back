# Bank Account Back

The *Bank Account Back* handle the management of bank operations. This project is the *backend* part of it.

- History and statement account
- Deposit and withdrawal funds

We supposed that the banker will use this application.

## Getting Started

### Prerequisites
This is a *Spring Boot* application and it required :

- [Java 8](https://www.oracle.com/technetwork/java/javaee/downloads/jdk8-downloads-2133151.html)

The dependencies are handled by *maven*

### Running project
The application should be accessible at: [http://localhost:9090]()
We can build and generate jar file by using maven

### Unit and Integration tests

All the tests are located under the [tests](src/test) directory

### Api Request
the endpoint is : *http://localhost:9090/bank*

#### Provisioning a new token
we use *OAuth2* for authentification.
When no valid token available, the banker can request (using http `get`) a fresh new token to the `/oauth/token` endpoint specifying :

```bash
curl -d '{"grant_type": "password",
             "username": "admin",
             "password":"admin",
             "client_id":"SampleClientId",
             "client_secret":"client_credentials"}' 
     -X POST http://localhost:9090/oauth/token
```

In case the request is accepted, the response will contain a new token signed by the server:

```
<token>
```

Otherwise, a `403 Forbidden` response will be issued.

#### Authenticating a request

A valid non expired token must be communicated in every request using the Authorization http header:

```
Authorization: bearer <token>
```

- **Account statement**  

  ```curl GET <endPoint>/statement/{id} --header 'Authorization : barear <token>```
  return Json object
  
  ```
  {
      "accountNumber": "31113 01199 00050333182 66",
      "createdAt": "01/02/2019 10:03",
      "clientName": "MR X x",
      "balance": 280,
      "status": "ACTIVE"
  }
  
  ```

  
- **Account history**  

 ``curl GET <endPoint>/history/{id} --header 'Authorization : barear <token> ``
 
 return JSON Object
   ```
     {
         "clientName": "MR X x",
         "accountNumber": "31113 01199 00050333182 66",
         "initialBalance": 0,
         "transactions": [
             {
                 "transactionDate": "24/04/2019 23:08",
                 "operation": "DEPOSIT",
                 "amount": 300
             },
             {
                 "transactionDate": "24/04/2019 23:09",
                 "operation": "WITHDRAWAL",
                 "amount": 10
             },
             {
                 "transactionDate": "25/04/2019 00:26",
                 "operation": "WITHDRAWAL",
                 "amount": 10
             }
         ],
         "balance": 280,
         "lastUpdate": "01/02/2019 10:03"
     }
   ```

- **Account deposit**  

 ```curl POST <endPoint>/deposit/{id} "Content-Type: application/json" -d <data> --header 'Authorization : barear <token>```
 
  The body of the request contains mandatory *amount* in a json of the form:
  
   ``
   curl GET <endPoint>/deposit/{id} "Content-Type: application/json" -d <data> --header 'Authorization : barear <token>
   ``
   
    The body of the request contains mandatory *amount* in a json of the form:
  
    ``
    {
      "amount": 300
    }
    ``
  
  - **Account withdrawal**  
  
   ``` curl POST <endPoint>/withdrawal/{id} "Content-Type: application/json" -d <data> --header 'Authorization : barear <token> ``
   
    The body of the request contains mandatory *amount* in a json of the form:
  
    ```
    {
      "amount": 300,
      "operation":"WITHDRAWAL
    } 
    ```
    
For withdrawal operation can take only one of the two possibilities *WITHDRAWAL* or *WITHDRAWAL_ALL*

### Repository architecture

**[configuration](src/main/java/com/sg/bankaccountback/configuration) configuration** for application config, actually it contains spring security config

**[controller](src/main/java/com/sg/bankaccountback/controller) controller** for REST API declaration

**[exception](src/main/java/com/sg/bankaccountback/exception) exception** for error handling

**[model](src/main/java/com/sg/bankaccountback/model)** for model object

**[dto](src/main/java/com/sg/bankaccountback/dto)** for transfer object

**[dao](src/main/java/com/sg/bankaccountback/dao)** for data management

**[service](src/main/java/com/sg/bankaccountback/service) service** for additional functions needed for controller or dao

**[tests](src/main/java/com/sg/bankaccountback/test) test** for unit and integration test

### TODO
- Swagger documentation
- Delete balance field from Transaction and Account and create a model Balance to keep history
- Separate Data treatment and checking condition using service.
- adding filter date (begin,end) to get account history.