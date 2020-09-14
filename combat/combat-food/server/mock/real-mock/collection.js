const users = require('../data/user.js');
const axios = require('axios');

const loginUrl = `http://localhost:7001/user/login`
const following = `http://localhost:7001/user/following`;
let count = 0;
let _id = [];
users.forEach((user, index) => {
  axios.post(loginUrl, {
    name: user.name,
    password: user.password
  }).then((d) => {
    count++;
    _id.push({
      _id: d.data.data._id,
      name: d.data.data.name,
    })
    console.log(count, users.length, )
    if(count === users.length){
      console.log('全回来了');
      f();
    }
    // users.filter(user => user.name !== d.data.data.name).forEach((item) => {
    //   axios.post(following, {followUserId}, {
    //     headers: {
    //       'authorization': d.data.data.token,
    //     },
    //   }).then(() => {
    //     console.log('插入成功')
    //   }).catch(() => {
    //     console.log('有问题', option);
    //   })
    // })
  })
});

function f(){
  users.forEach((user, index) => {
    axios.post(loginUrl, {
      name: user.name,
      password: user.password
    }).then((d) => {
      _id.filter(user => user._id !== d.data.data._id).forEach((item) => {
        axios.post(following, {followUserId: item._id}, {
          headers: {
            'authorization': `token ` + d.data.data.token,
          },
        }).then(() => {
          console.log('关注成功')
        }).catch(() => {
          console.log('有问题');
        })
      })
    })
  });
  
}