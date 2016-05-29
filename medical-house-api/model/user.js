var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var User = new mongoose.Schema({
    firstName: {
        type: String,
        required: true
    },
    lastName: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    age: {
        type: Number
    },
    scores: [{
        name: String,
        scores: Array
    }]
});

module.exports = mongoose.model('User', User);