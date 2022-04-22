# ONeul(오늘)
One Day Lifetime SNS

## Development Goals
- Delete post 24 hours old using Spring Batch
- Managing Logins Using Session
- Object Oriented Programming 
- CQRS(Command and Query Responsibility Segregation) Structure
- RESTful API

## Application Structure
### Simple CQRS
<p align="center"><img src="asset/cqrs_normal.jpeg" width=700></p>
<img src="asset/need_graphic_designer.jpeg" width=25>

## How to run
```bash
git clone {github_url}
gradle bootJar
java -jar build/libs/oneul-0.0.1-SNAPSHOT.jar 
```

## Next Level
### CQRS with separated persistance mechanisms
<p align="center"><img src="asset/cqrs_premium.png" width=300></p>