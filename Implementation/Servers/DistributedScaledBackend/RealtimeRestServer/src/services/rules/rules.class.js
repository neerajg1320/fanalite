const { Service } = require('feathers-mongodb');

exports.Rules = class Rules extends Service {
  constructor(options, app) {
    super(options);
    
    app.get('mongoClient').then(db => {
      this.Model = db.collection('rules');
    });
  }
};
