const { Kafka } = require("kafkajs");

const kafkaBroker = "ubuntu-18:9092";

subscribe().then(() => console.log("Done"), err => console.log(err));

produce().then(() => console.log("Done"), err => console.log(err));


async function subscribe() {
    const kafka = new Kafka({brokers: [kafkaBroker]});

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

async function produce() {
    const kafka = new Kafka({brokers: [kafkaBroker]});

    const producer = kafka.producer();
    await producer.connect();

    await new Promise(resolve => setTimeout(resolve, 1000));

    await producer.send({
        topic: "text",
        messages: [
            {value: "event from node application"}
        ]
    });
}