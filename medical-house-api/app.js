var express = require('express');
var connect = require('connect');
var mongoose = require('mongoose');
var port = process.env.OPENSHIFT_NODEJS_PORT || 8089;
var ipaddress = process.env.OPENSHIFT_NODEJS_IP || "0.0.0.0";
var mongoDbOpenshift = process.env.OPENSHIFT_MONGODB_DB_URL;
var routes = require('./routes/routes.js');

var app = express();

// Configuration
app.use(function (err, req, res, next) {
    if (err.name === 'UnauthorizedError') {
        res.header('Access-Control-Allow-Origin', req.headers.origin);
        res.header('Access-Control-Allow-Methods', 'POST, GET, PUT, DELETE, OPTIONS');
        res.header('Access-Control-Allow-Credentials', false);
        res.header('Access-Control-Max-Age', '86400');
        res.header('Access-Control-Allow-Headers', 'Access-Control-Allow-Origin, X-Requested-With, X-HTTP-Method-Override,  Content-Type, Accept, Authorization');
        res.status(401).send(401)
        console.log("401 error")
    }
});

app.use(connect.logger('dev'));
app.use(connect.json());
app.use(connect.urlencoded());

app.all('/*', function (req, res, next) {
    req.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'POST, GET, PUT, DELETE, OPTIONS');
    res.header('Access-Control-Allow-Credentials', false);
    res.header('Access-Control-Max-Age', '86400');
    res.header('Access-Control-Allow-Headers', 'Access-Control-Allow-Origin, X-Requested-With, X-HTTP-Method-Override,  Content-Type, Accept, Authorization');
    next();
});

app.options('*', function (req, res) {
    res.send(200);
});

//routes
require('./routes/routes.js')(app);

//connection with the database
if (mongoDbOpenshift) {
    mongoose.connect(mongoDbOpenshift);
    console.log('Found mongo database on openshift!');
} else if (process.argv[2] != null) {
    mongoose.connect(process.argv[2]);
    console.log('Found mongo database location correctly!');
} else {
    console.log('Attempting to access mongo on default location!');
    mongoose.connect('mongodb://localhost:8010/medical-house');
}

app.listen(port, ipaddress, function () {
    console.log("App is listening on port:" + port + " address: " + ipaddress);
});
module.exports = app;
