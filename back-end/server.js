var express = require('express');
var app = express();
var https = require('https'),
    fs = require('fs');
var options = {
    key: fs.readFileSync('./privatekey.pem'),
    cert: fs.readFileSync('./certificate.pem')
}
var httpsServer = https.createServer(options,app);
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
app.use(bodyParser.json());
app.use(bodyParser.raw());
app.use(bodyParser.urlencoded({
    extended: false
}));

httpsServer.listen(6666);

var user = require("./user.js");

//使用GET方法来确定用户ID是否已存在，存在返回true，app接收到false后才提示用户注册成功
app.get('/isregister', function(req, res){
    user.findOne({ userID: req.query.userID }, function(err, user) {   //有相同用户id，注册失败
        if (err) {
            res.send(500, 'Error occurred: database error.');
        }
        res.json({
            isRegister: true
        })
    });
    res.json({
        isRegister: false
    })
})

//使用GET方法来验证登陆时账号密码的正确性，若id、密码均正确，返回true,否则返回false及失败原因
app.get('/login', function(req, res){
    user.findOne({ userID: req.query.userID }, function(err,user) {  //验证id
        if (err) {
            res.send(500, 'Error occurred: database error.');
        }
        if (user.password == req.query.password) {   //验证密码,密码正确，验证通过
            res.json({
                pass: true
            })
        }
        res.json({  //密码错误，验证错误，并提示原因
            pass:false,
            description: "密码不正确！"
        })
    })
    res.json({   //密码错误，验证错误，并提示原因
        pass:false,
        description: "账号不存在！"
    })
});

//使用POST方法来对用户数据库做增加操作
app.post('/register', function (req, res) {
    var newUser = new User({
        name: req.body.name,
        userID: req.body.userID,
        password: req.body.password
    });
    newUser.save();
    console.log("新增用户：" + newUser);
    res.send("success!");
});