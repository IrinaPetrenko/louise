# louise

Louise ss a Pet project to train programing language quizes (currently only *java* is supported).

You can add any new question to the quiz, select random quiz and test your answer on it.

*To start the application localy:*
- add your cht-gpt credentials to env_vars (https://help.openai.com/en/articles/4936850-where-do-i-find-my-secret-api-key, format: ```Bearer <generated-api-key>```);
- run ```docker compose build```;
- run ```docker compose up```;

*To run tests:*
- ```mvn test```;

**Request examples from Postman:**

**Create new quiz**
```
POST http://127.0.0.1:8080/java/quiz/new
{
    "question" : "What is Java?"
}
```

**Get all exisintg quizes**
```
GET http://127.0.0.1:8080/java/quiz/all
```

**Delete quiz by id**
```
DELETE http://127.0.0.1:8080/java/quiz/{id}
```

**Get random Quiz**
```
GET http://127.0.0.1:8080/java/quiz/random
```

**Check answer**
```
POST http://127.0.0.1:8080/java/quiz/answer
{
    "questionId": 1,
    "userAnswer": "your answer"
}
```


