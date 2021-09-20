const Kafka = require('kafkajs').Kafka;

module.exports = async function (app) {
  const kafkaConfig = app.get('kafka');

  const kafkaClient = new Kafka(kafkaConfig);
  const kafkaConsumer = kafkaClient.consumer({groupId: kafkaConfig.consumer.groupName});

  await kafkaConsumer.subscribe(({topic: "transactions", fromBeginning: false}));
  await kafkaConsumer.run({
    eachMessage: async (data) => {
      console.log("src/kafka.js", data.message.value.toString("utf-8"));
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
