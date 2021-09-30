const Kafka = require('kafkajs').Kafka;

module.exports = async function (app) {
  const kafkaConfig = app.get('kafka');
  const debug = app.get('debug');

  if (!kafkaConfig.active) {
    return;
  }

  const kafkaClient = new Kafka(kafkaConfig);
  const kafkaConsumer = kafkaClient.consumer({groupId: kafkaConfig.consumer.groupName});

  if (kafkaConsumer) {
    await kafkaConsumer.subscribe(({topic: kafkaConfig.consumer.topic, fromBeginning: false}));
    await kafkaConsumer.run({
      eachMessage: async (data) => {

        if (debug.kafka) {
          console.log("src/kafka.js", data.message.value.toString("utf-8"));
        }

        // Currently our user is hardcoded.
        // We should get the userId passed by Kafka message

        try {
          const user = await app.service('users').get(kafkaConfig.user_id, {});

          if (user) {
            delete user.password;
            const messageService = app.service('messages');
            const params = {user, text: data.message.value.toString("utf-8")};

            messageService.emit('kafkaTransaction', params);
          }
        } catch (e) {
          console.log(e.message);
        }
      }
    });

    const kafkaProducer = kafkaClient.producer();
    await kafkaProducer.connect();

    if (kafkaConfig.producer.sendActivationMessage) {
      await kafkaProducer.send({
        topic: kafkaConfig.producer.topic,
        messages: [
          {
            value: "The node kafka producer is active"
          }
        ]
      });
    }

    app.set('kafkaProducer', kafkaProducer);
    app.set('kafkaConsumer', kafkaConsumer);

    if (debug.kafka) {
      console.log("Configured kafkaConsumer and kafkaProducer in app");
    }
  }
};
