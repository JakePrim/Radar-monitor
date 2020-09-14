const mongoose = require('mongoose');

mongoose.connect('mongodb://127.0.0.1:27017/meishijie',{useNewUrlParser: true, useCreateIndex: true});
var db = mongoose.connection;

module.exports = function(){
  return new Promise((resolve, reject) => {
    db.on('error', console.error.bind(console, 'connection error:'));
    db.once('open', function (callback) {
        global.db = db;
        global.mongoose = mongoose;
        resolve('数据库成功连接');
    });
  })
}

