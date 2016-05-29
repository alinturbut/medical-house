var User = require('../model/user.js');

exports.create = function(firstName, lastName, email, age, gender, password, callback) {
	var newUser = new User({
		firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        gender: gender,
        age: age
	});

	newUser.save(function(err){
		if(err) {
			callback(err, 500);
		} else {
			callback(null, {'user': newUser});
		}
	});
}

exports.login = function(email, password, callback) {
	User.findOne({email: email}, function(err, data){
		if(err || data === null) {
			callback(err, 'not found');
		} else {
			if(password === data.password) {
				callback(null, {'user': data});
			} else {
				callback(400, 'incorrect password')
			}
		}
	})
}

exports.getById = function(id, callback) {
	User.findOne({_id: id}, function(err, data){
		if(err) {
			callback(err, 404);
		} else {
			callback(null, {'user': data});
		}
	})
}

exports.findAll = function(callback) {
	User.find({}, function(err,users){
		callback(null, {'users': users});
	});
}

exports.pushScore = function(email, test, score, callback) {
	var scoreMap = {name: test, scores: [score]};
	User.findOne({email: email}, function(err, data){
		if(err || data === null) {
			callback(err, 'not found');
		} else {
			if(data.scores === undefined) {
				data.scores = {};
			}
			var found = false;
			for(var entry in data.scores) {
				console.log(data.scores[entry]);
				if(data.scores[entry].name === test) {
					found = true;
					data.scores[entry].scores.push(score);
				}
			}
			if(found === false) {
				data.scores.push(scoreMap);
			}
			data.save(function(err) {
				if(err){
					callback(err, 500);
				} else {
					callback(null, {'user': data});
				}
			});
		};
	});
}