const Kafka = require('kafkajs').Kafka;

module.exports = async function (app) {
  const kafkaBroker = app.get('kafkaBroker');

  const kafkaClient = new Kafka({brokers: [kafkaBroker]});
  const kafkaConsumer = kafkaClient.consumer({groupId: "" + Date.now()});

  await kafkaConsumer.subscribe(({topic: "text", fromBeginning: false}));
  await kafkaConsumer.run({
    eachMessage: async (data) => {
      // console.log(data);
      console.log(data.message.value.toString("utf-8"));
    }
  })

  const kafkaProducer = kafkaClient.producer();
  await kafkaProducer.connect();

  await kafkaProducer.send({
    topic: "text",
    messages: [
      {value: "event from node application"}
    ]
  });

  app.set('kafkaProducer', kafkaProducer);
  console.log("'app.set(kafkaQueues)' called");
};
