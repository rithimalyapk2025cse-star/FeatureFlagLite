# FeatureFlagLite

A simple Spring Boot + MySQL Feature Flag and Configuration Management Service for a Java & DBMS college assessment.

## Objective

Allow applications to enable or disable features independently for DEV, TEST and PROD without changing application code.

## Main Features

- Feature flag CRUD
- DEV / TEST / PROD environments
- Environment-specific ON/OFF state
- Change history
- Deterministic percentage rollout
- REST APIs
- Validation and global exception handling
- MySQL persistence
- Simple Thymeleaf dashboard

## Database

Create the database if needed:

```sql
CREATE DATABASE feature_flag_lite;
```

Then edit `src/main/resources/application.properties` and set the MySQL password.

## Run

From the project folder:

```bash
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080/
```

## Important API Examples

Create feature:

POST /api/flags

```json
{
  "name": "Dark Mode",
  "description": "New dark theme",
  "defaultState": false,
  "rolloutPercentage": 50
}
```

Set PROD state:

POST /api/flags/1/state?environment=PROD

```json
{
  "enabled": true,
  "changedBy": "Admin"
}
```

Evaluate user:

GET /api/flags/1/evaluate/PROD?userId=user123

Get change history:

GET /api/flags/1/changes
