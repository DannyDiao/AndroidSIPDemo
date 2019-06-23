var mongoose = require('./connect.js');
var Schema = mongoose.Schema;

var userSchema = Schema({
    name:String,     //用户姓名
    userID:String,    //用户ID
    password:String   //用户密码
});

module.exports = mongoose.model('user',userSchema);