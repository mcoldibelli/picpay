
# PicPay Challenge

This is a backend project from PicPay, a Brazilian fintech.


## Tech Stack

<span>
    <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" alt="Spring Boot" />
    <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
    <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
</span>


## Requirements

- There are two types of users, common and merchant;
- Users have full name, CPF/CNPJ (A register number for person and business), e-mail and password. CPF/CNPJ and e-mail must be uniques on the system. Thus, your implementation must allow only one register with same CPF or e-mail.
- Users can send money (to transfer) to merchants and common users;
- Merchants only receive money, aren't allowed to send money to anyone;
- Validate if user has balance before transfer;
- Before finish transfer, must reach for a external auth service.
    - Mock used with GET Http verb: https://util.devi.tools/api/v2/authorize
- The transfer process must be a transaction (i.e. can be rollback if an inconsistency happens) and money goes back to payer balance;
- When receiving money, user or merchant must get a notification (A e-mail or sms) sent from a external service, which can be unavailable or unstable.
    - Mock used to simulate the notification sent with POST Http verb: https://util.devi.tools/api/v1/notify
- This service must be RESTful;

### Transfer Endpoint
The implementation uses the following contract:

```
POST /transfer
Content-Type: application/json

{
  "value": 100.0,
  "payer": 4,
  "payee": 15
}
```
## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`DB_NAME` < Name of the Database >

`DB_USER` < Username >

`DB_PASSWORD` < Password >

`DB_PORT` < Database Port >

## Run Locally

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```

Start the docker compose to get the database started

```bash
  docker compose up -d
```

Start the Spring Boot App
```
mvn spring-boot:run
```
## API Reference

Use Swagger UI to test the API: ```localhost:8080/swagger-ui.html```
