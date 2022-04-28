# ONeul(오늘)
One Day Lifetime SNS

## Development Goals
- Delete post 24 hours old using Spring Batch
- Managing Logins Using Session
- Object Oriented Programming 
- [CQRS(Command and Query Responsibility Segregation) Structure](https://zzzinho.tistory.com/123)
- RESTful API
- [Unit Test](https://mangkyu.tistory.com/143)
  
## Application Structure
### Simple CQRS
<p align="center"><img src="asset/cqrs_normal.jpeg" width=700></p>
<img src="asset/need_graphic_designer.jpeg" width=25>

### Directory Structure
[Domain Directory Structure](https://github.com/cheese10yun/spring-guide/blob/master/docs/directory-guide.md)
```
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── onuel
    │   │               ├── OneulApplication.java
    │   │               ├── domain
    │   │               │   ├── user
    │   │               │   │   ├── controller
    │   │               │   │   ├── service
    │   │               │   │   ├── repository
    │   │               │   │   ├── domain
    │   │               │   │   ├── dto
    │   │               │   │   └── exception
    │   │               │   ├── post
    │   │               │   │   ├── controller
    │   │               │   │   ├── service
    │   │               │   │   │   ├── command
    │   │               │   │   │   └── query                            
    │   │               │   │   ├── repository
    │   │               │   │   ├── domain
    │   │               │   │   ├── dto
    │   │               │   │   └── exception
    │   │               ├── global
    │   │                   ├── common
    │   │                   │   ├── request
    │   │                   │   └── response
    │   │                   ├── config
    │   │                   │   ├── RedisConfig.java
    │   │                   │   └── security
    │   │                   │       └── WebSecurityConfig.java 
    │   │                   ├── error
    │   │                   │   ├── ErrorResponse.java
    │   │                   │   ├── GlobalExceptionHandler.java
    │   │                   │   └── exception
    │   │                   └── util
    │   │                       ├── LoginCheckInterceptor.java
    │   └── resources
    │       └── application.yml

```
## How to run
```bash
git clone https://github.com/zzzinho/Oneul.git
gradle bootJar
docker compose up --build -d
```

## Next Level
### CQRS with separated persistance mechanisms
<p align="center"><img src="asset/cqrs_premium.png" width=300></p>