// mock 数据
var Mock = require('mockjs')
var Random = Mock.Random;

let userModel = require('../app/model/user.js')(global);
// 随机生成一些名字和密码
const creteUserNum = 3;

async function createUser(){
  // 生成数据
  var data = (new Array(creteUserNum)).fill(1).map(() => {
    return createMockUser().user;
  })
  // 先清空所有，在插入
  await userModel.deleteMany({});
  await userModel.insertMany(data).then((d) => {
    console.log('用户插入成功');
    // 互相关注
    // 我关注了谁
    d.forEach(async (item) => {
      let filterId = d.filter(o => o._id !== item._id);// 排除自己
      // 互相关注
      let followingUsers = filterId.map(o => (o._id)); 
      item.following.push(...followingUsers);
      item.follows.push(...followingUsers);
      await item.save();
    });
  });
}

module.exports = createUser;

function createMockUser(){
  return Mock.mock({
    'user|1': [{
        'name': Random.word(),
        'sign': Random.csentence(),
        'password': Math.round(Math.random() * 1000) // 密码需要加密
    }]
  })
}