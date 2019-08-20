# Timecheck

Timecheck is a Java API to control employees work time.

## Stack

- Java 8
- Spring Boot 2.1.7
- H2 Database

## Usage

### Create a new employee

```
POST /api/employees
```

```
{ "name": "Dev", "pis": "123456" }
```

### Clock in/out

```
POST /api/clockin/create
```

```
{ "pis": "123456", "dateTime": "2019-08-20 08:35" }
```

### Mirror

```
POST /api/clockin/mirror
```

```
{ "pis": "123456", "yearMonth": "2019-08" }
```

## Demo

> https://lifetimecheck.herokuapp.com/api/

