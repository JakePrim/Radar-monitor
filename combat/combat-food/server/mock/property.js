
let propertyModel = require('../app/model/property.js')(global);
let datas = require('./data/property');

module.exports = async function(){
    // 先清空所有，在插入
    await propertyModel.deleteMany({});
  for(let i = 0; i < datas.length; i++){
    // 保证不重复添加
    let ification = await propertyModel.findOne({type: datas[i].type});
    if(ification) continue;
    await propertyModel.create(datas[i]).then((e,d) => {
      console.log('分类插入成功')
    })
  }
  
};