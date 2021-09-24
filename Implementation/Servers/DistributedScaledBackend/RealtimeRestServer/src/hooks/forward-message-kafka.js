// Use this hook to manipulate incoming or outgoing data.
// For more information on hooks see: http://docs.feathersjs.com/api/hooks.html

// eslint-disable-next-line no-unused-vars
module.exports = (options = {}) => {
  return async context => {
    const { app, data } = context;

    const kafkaConfig = app.get('kafka');
    const debug = app.get('debug');

    if (!kafkaConfig.active) {
      return;
    }

    const kafkaProducer = app.get('kafkaProducer');

    if (kafkaProducer) {
      await kafkaProducer.send({
        topic: kafkaConfig.producer.topic,
        messages: [
          {
            value: data.text
          }
        ]
      });

      if(debug.kafka) {
        console.log('kafka hook: kafka message sent');
      }
    }

    return context;
  };
};
