// Initializes the `rules` service on path `/rules`
const { Rules } = require('./rules.class');
const hooks = require('./rules.hooks');

module.exports = function (app) {
  const options = {
    paginate: app.get('paginate')
  };

  // Initialize our service with any options it requires
  app.use('/rules', new Rules(options, app));

  // Get our initialized service so that we can register hooks
  const service = app.service('rules');

  service.hooks(hooks);
};
