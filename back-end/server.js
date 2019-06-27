
const https = require('https');
const fs_1 = require('fs');
const options = {
    pfx: fs_1.readFileSync('../SSL/diaosudev.cn.pfx'),
    passphrase: '873340a0lc6w5'
  };


var express = require('express');
var app = express();
var httpsServer = https.createServer(options,app);
var bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.raw());
app.use(bodyParser.urlencoded({
    extended: false
}));

var user = require("./user.js");
var id_exist = false;
var password_verify = false;
var user_list = [];
user.find(function (err, users) {
    users.forEach(function (item, index, arr) {
        user_list.push({
            name: item.name,
            userID: item.userID
        });
    })
})


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
    user.findOne({ userID: req.query.userID }, function(err,user_find) {  //验证id
        id_exist = true; //账号存在
        console.log("id");
        if (err) {
            res.send(500, 'Error occurred: database error.');
        }
        if (user_find.password == req.query.password) {   //验证密码,密码正确，验证通过
            console.log(req.query.userID + "登陆");
            password_verify = true;  //密码正确
            console.log("password");
        }
        if(id_exist && password_verify){
            console.log(1);
            res.json({
                pass: true,
                user_list: user_list
            });
        }
        else if(id_exist){
            console.log(2);
            res.json({  //密码错误，验证错误，并提示原因
                pass:false,
                description: "密码不正确！"
            })
        }
        else{
            console.log(3);
            res.json({   //密码错误，验证错误，并提示原因
                pass:false,
                description: "账号不存在！"
            })
        }
    })
});

//使用POST方法来对用户数据库做增加操作
app.post('/register', function (req, res) {
    var newUser = new user({
        name: req.body.name,
        userID: req.body.userID,
        password: req.body.password
    });
    newUser.save();
    user_list.push({
        name: newUser.name,
        userID: newUser.userID
    })
    console.log("新增用户：" + newUser);
    res.send("success!");
});

//使用POST方法获取拨打对象IP地址

httpsServer.listen(6666);