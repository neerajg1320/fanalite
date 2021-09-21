const Kafka = require('kafkajs').Kafka;

module.exports = async function (app) {
  const kafkaConfig = app.get('kafka');

  const kafkaClient = new Kafka(kafkaConfig);
  const kafkaConsumer = kafkaClient.consumer({groupId: kafkaConfig.consumer.groupName});

  await kafkaConsumer.subscribe(({topic: "transactions", fromBeginning: false}));
  await kafkaConsumer.run({
    eachMessage: async (data) => {
      console.log("src/kafka.js", data.message.value.toString("utf-8"));

      const messageService = app.service('messages');
      const params = {user: {_id: "4BTU9gmA9AwM0720"}, message: data.message.value.toString("utf-8")};

      messageService.emit('kafkaTransaction', params);
      // console.log('Custom event emitted');

      // Right now this is not working so we will have to find another way to push transcations to front-endnd
      // messageService.create({data, params});
    }
  });

  const kafkaProducer = kafkaClient.producer();
  await kafkaProducer.connect();

  if (kafkaConfig.producer.sendActivationMessage) {
    await kafkaProducer.send({
      topic: "text",
      messages: [
        {
          value: "The node kafka producer is active"
        }
      ]
    });
  }

  app.set('kafkaProducer', kafkaProducer);
  app.set('kafkaConsumer', kafkaConsumer);

  console.log("Configured kafkaConsumer and kafkaProducer in app");
};
