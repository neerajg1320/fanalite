const { Kafka } = require("kafkajs");

run().then(() => console.log("Done"), err => console.log(err));

async function run() {
    const kafka = new Kafka({brokers: ["ubuntu-18:9092"]});

    const consumer = kafka.consumer({groupId: "" + Date.now()});
    await consumer.connect();

    await consumer.subscribe(({topic: "text", fromBeginning: true}));
    await consumer.run({
        eachMessage: async (data) => {
            // console.log(data);
            console.log(data.message.value.toString("utf-8"));
        }
    })
}