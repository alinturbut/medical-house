/**
 * Created by Turbut Alin on 25.11.2014.
 */

var userRoutes = require('./userRoutes.js');
var path = require('path');

module.exports = function(app){
    var responseFunction = function(res,err,response){
        if(err){
            res.status(err).send(response).end();
            console.log(err);
        }else{
            res.json(response);
            console.log(response);
        }
    }

    userRoutes(app,responseFunction);
};
