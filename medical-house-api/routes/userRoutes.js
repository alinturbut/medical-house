var UserService = require('../services/userService.js');

module.exports = function(app, responseFunction){
    app.post('/user/register',function(req, res){
        UserService.create(req.body.firstName, req.body.lastName, req.body.email, req.body.age, 
            req.body.gender, req.body.password, function(err, user){
                responseFunction(res, err, user);
            });
    });

    app.get('/user/login',function(req, res){
        UserService.login(req.query.email, req.query.password, function(err, user){
                responseFunction(res, err, user);
            });
    });

    app.post('/user/score', function(req, res){
        UserService.pushScore(req.body.email, req.body.test, req.body.score, function(err, user){
            responseFunction(res, err, user);
        });
    });

    app.get('/user/getById',function(req, res){
        UserService.getById(req.query.id, function(err, user){
                responseFunction(res, err, user);
            });
    });

    app.get('/user/getAll',function(req, res){
        UserService.findAll(function(err, user){
                responseFunction(res, err, user);
            });
    });
};
