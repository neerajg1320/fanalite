const mongoose = require('mongoose');
mongoose.connect('mongodb+srv://neeraj:Feathers@cluster0.95lt1.mongodb.net/myFirstDatabase?retryWrites=true&w=majority');

const Cat = mongoose.model('Cat', { name: String });

const kitty = new Cat({ name: 'Zildjian' });
kitty.save().then(() => console.log('meow'));
