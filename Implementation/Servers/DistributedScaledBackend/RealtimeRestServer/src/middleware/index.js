// eslint-disable-next-line no-unused-vars
module.exports = function (app) {
  // Add your custom middleware here. Remember that
  // in Express, the order matters.

  const debug = app.get("debug");

  if (debug.api) {
    app.use(apiLogger);
  }
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

  let log = `[${formatted_date}] ${req.method}:${req.url} body:${JSON.stringify(req.body)} headers: ${JSON.stringify(req.headers)}`;


  console.log(log);
  next();
  let status = res.statusCode;
  log = `${status}`;
  console.log(log);
};
