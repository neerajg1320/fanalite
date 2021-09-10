const express = require('express');
const app = express();
const cors = require('cors')

app.use(cors());

let demoLogger = (req, res, next) => {
  console.log("Hello from logger");
  next();
};

// Ref: https://codesource.io/creating-a-logging-middleware-in-expressjs/

let apiLogger = (req, res, next) => {
  let current_datetime = new Date();
  let formatted_date =
    current_datetime.getFullYear() +
    "-" +
    (current_datetime.getMonth() + 1) +
    "-" +
    current_datetime.getDate() +
    " " +
    current_datetime.getHours() +
    ":" +
    current_datetime.getMinutes() +
    ":" +
    current_datetime.getSeconds();
  let method = req.method;
  let url = req.url;
  let status = res.statusCode;
  let log = `[${formatted_date}] ${method}:${url} ${status}`;
  console.log(log);
  next();
};

// app.use(demoLogger);
app.use(apiLogger);

app.use('/login', (req, res) => {
    console.log("req.body", req.body);

    res.send({
      token: 'test123'
    });
});

app.use('/badlogin', (req, res) => {
  res.send({}, 401);
});


app.listen(8080, () => console.log('API is running on http://localhost:8080/login'));
