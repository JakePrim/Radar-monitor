// 模拟想后端发送请求，来注册用户
let axios = require('axios');
let users = require('../data/user.js')
let createUrl = `http://localhost:7001/user/create`;

users.forEach((user) => {
  axios.post(createUrl, {
    name:user.name,
    password: user.password
    }).then((d => {
      console.log(d.data)
    })).catch(e => {
      console.log(e);
    });
})

