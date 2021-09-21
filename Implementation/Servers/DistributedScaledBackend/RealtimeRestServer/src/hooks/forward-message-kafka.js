// Use this hook to manipulate incoming or outgoing data.
// For more information on hooks see: http://docs.feathersjs.com/api/hooks.html

// eslint-disable-next-line no-unused-vars
module.exports = (options = {}) => {
  return async context => {
    const { app, data } = context;

    // console.log("[hook:forward-message-kafka]:", data);


    const kafkaProducer = app.get('kafkaProducer');

    await kafkaProducer.send({
      topic: "messages",
      messages: [
        {
          value: data.text
        }
      ]
    });

    // console.log('kafka hook: kafka message sent');

    return context;
  };
};
