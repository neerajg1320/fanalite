const express = require('express');
const app = express();
const cors = require('cors')

app.use(cors());

app.use(express.json())
app.use(express.urlencoded({
  extended: true
}))

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
  let log = `[${formatted_date}] ${method}:${url}`;
  console.log(log);
  next();
  let status = res.statusCode;
  log = `${status}`;
  console.log(log);
};

// app.use(demoLogger);
app.use(apiLogger);

app.use('/login', (req, res) => {
  console.log("req.body", req.body);

  if (req.body.userName === "alice") {
    res.send({
      token: 'test123'
    });
  } else {
    res.status(401).send({})
  }

});

app.listen(8080, () => {
  console.log('API is running on http://localhost:8080/login');
  console.log('Use username "alice" for a successful login');
});
