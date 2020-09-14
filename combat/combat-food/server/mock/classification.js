
let meunClassificationModel = require('../app/model/classification.js')(global);
let datas = require('./data/classify');

module.exports = async function(){
  for(let i = 0; i < datas.length; i++){
    // 保证不重复添加
    let ification = await meunClassificationModel.findOne({type: datas[i].type});
    if(ification) continue;
    await meunClassificationModel.create(datas[i]).then((e,d) => {
    })
  }
  
};