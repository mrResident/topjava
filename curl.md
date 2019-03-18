### curl commands for testing rest service


#### create Meals
`curl -s -X POST -d '{"dateTime":"2019-03-17T08:00","description":"New breakfast","calories":250}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals`

#### update Meals
`curl -s -X PUT -d '{"dateTime":"2015-05-31T18:30", "description":"Updated dinner", "calories":500}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100007`

#### delete Meals
`curl -s -X DELETE http://localhost:8080/topjava/rest/meals/100003`

#### get All Meals
`curl -s http://localhost:8080/topjava/rest/meals`

#### get Meals by id
`curl -s http://localhost:8080/topjava/rest/meals/100007`

#### get Meals not found
`curl -s -v http://localhost:8080/topjava/rest/meals/100000`

#### filter Meals
`curl -s "http://localhost:8080/topjava/rest/meals/datebetween?startDate=2015-05-30&startTime=08:00:00&endDate=2015-05-31&endTime=10:00:00"`

#### get All Users
`curl -s http://localhost:8080/topjava/rest/admin/users`

#### get Users by id
`curl -s http://localhost:8080/topjava/rest/admin/users/100000`