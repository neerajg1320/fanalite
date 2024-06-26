const feathers = require('@feathersjs/feathers');
const express = require('@feathersjs/express');
const socketio = require('@feathersjs/socketio');


class MessageService {
    constructor() {
        this.messages = [];
    }

    async find() {
        return this.messages;
    }

    async create(data) {
        const message = {
            id: this.messages.length,
            text: data.text
        };

        this.messages.push(message);

        return message;
    }
}



// Add the express related part
const app = express(feathers());

app.use(express.json());
app.use(express.urlencoded({extended: true}));
app.use(express.static(__dirname));
app.configure(express.rest());
app.configure(socketio());

app.use('/messages', new MessageService());
app.use(express.errorHandler());

// Add any new real-time connection to the `everybody` channel
app.on('connection', connection =>
    app.channel('everybody').join(connection)
)

// Publish all events to the `everybody` channel
app.publish(data => app.channel('everybody'));

app.listen(3030).on('listening', () =>
    console.log('Feathers server listening on localhost:3030')
)

app.service('messages').create({
    text: 'Hello world from the server'
});
