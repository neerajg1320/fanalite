const { authenticate } = require('@feathersjs/authentication').hooks;

const processMessage = require('../../hooks/process-message');

const populateUser = require('../../hooks/populate-user');

const forwardMessageKafka = require('../../hooks/forward-message-kafka');

module.exports = {
  before: {
    all: [ authenticate('jwt') ],
    find: [],
    get: [],
    create: [processMessage()],
    update: [],
    patch: [],
    remove: []
  },

  after: {
    all: [populateUser()],
    find: [],
    get: [],
    create: [forwardMessageKafka()],
    update: [forwardMessageKafka()],
    patch: [forwardMessageKafka()],
    remove: [forwardMessageKafka()]
  },

  error: {
    all: [],
    find: [],
    get: [],
    create: [],
    update: [],
    patch: [],
    remove: []
  }
};
