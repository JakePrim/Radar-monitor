// mock 数据
var Mock = require('mockjs')
var Random = Mock.Random;

let menuModel = require('../app/model/menu.js')(global);
let classifyDatas = require('./data/classify');  // 分类数据
let classifyDatas_len = classifyDatas.length-1;
let propertyDatas = require('./data/property');  // 属性
let propertyDatas_len = propertyDatas.length-1;
// 根据用户来生成的，现获取指定个数的用户
let userModel;
try{
  userModel = global.mongoose.model('User');
}catch(e){
  userModel = require('../app/model/user.js')(global);
}
const createNum = 2;  // 生成的个数
// 每一个人创建指定数字的数据
// 分类

async function createMenu(){
  let users = await userModel.find({}).limit(10);
  if(!users || !users.length) return;
  // [[],[]]
  let datas = users.map((user) => {
    return (new Array(createNum).fill(createNum)).map(() => {
      let menu = createMockMenu().menus;
      menu.userId = user._id;
      // 随机分配给一个分类
      // let random = Math.round(Math.random()*classifyDatas_len);
      // let classify = classifyDatas[random];
      // menu.classify = classify.type;
      // menu.parent_classify = classify.parent_type;
      // 所属的属性
      // let property = propertyDatas[random];
      // menu.property = propertyDatas.reduce((obj, item) => {
      //   let random = Math.round(Math.random()*(item.list.length - 1));
      //   let option = item.list[random];
      //   obj[item.title] = option.type;
      //   return obj;
      // },{});

      menu.parent_classify = menu.classify[0];
      
      // // 让其他用户收藏
      // filterUsers.each((user) => {
      //   user.meuns.push();
      // })
      return menu;
    })
  }).reduce((item1,item2) => {
    return [
      ...item1,
      ...item2
    ]
  });
  
  await menuModel.deleteMany({});
  for(let i = 0; i < datas.length; i++){
    // 其他用户
    let filterUsers = users.filter(item => item._id !== datas[i].userId);
    // 先插入菜单
    let m = await menuModel.create(datas[i]);
    
    // 这个菜单让其他用户收藏
    for(let j = 0; j < filterUsers.length; j++){
       filterUsers[j].collections.push(m);
       await filterUsers[j].save();
    }
    // 菜单记录被收藏的用户
    m.collectionUsers.push(...filterUsers);
    await m.save();
  }
  console.log('菜谱数据插入成功');
  
}

module.exports = createMenu;

function randomNum(n){
  return Math.ceil(Math.random()*(n));
}

function createRandomId(){
  return randomNum(4) + '-' + randomNum(4);
}


function createMockMenu(userId){
  
  return Mock.mock({
    'menus|1': [
      {
        title: Random.csentence(6),  // 标题
        product_pic_url: 'https://s1.st.meishij.net/r/208/102/1025708/s1025708_156144940220181.jpg', // 成品图URL
        product_story: Random.csentence(20), // 成品图故事
        property: {
          craft: createRandomId(),  // 工艺 enum: [1,2,3,4],
          flavor: createRandomId(),  // 口味  enum: [1,2,3,4],
          hard: createRandomId(),   // 难度 enum: [1,2,3,4],
          pepole: createRandomId()  // pepole 人数: [1,2,3,4],
        },  // 属性
        raw_material: { // 料
          "main_material|3-5": [{name: Random.csentence(3), specs: Random.csentence(3)}], // 主料
          "accessories_material|3-5": [{name: Random.csentence(3), specs: Random.csentence(3)}], // 辅料
        },
        "steps|3-5": [{img_url: 'https://s1.st.meishij.net/rs/208/102/1025708/n1025708_156144944939153.jpg',describe: Random.csentence(310)}], // 步骤
        classify: createRandomId(), // 菜谱分类
        skill: Random.csentence(50),
      }]
  })
}