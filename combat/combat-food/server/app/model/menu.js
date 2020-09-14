'use strict';
module.exports = app => {
  const mongoose = app.mongoose
  const MenuSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, required: false, index: true },
    name: { type: String, required: false },
    collectionUsers:[{
      type: mongoose.Schema.Types.ObjectId, 
      ref: 'User',
    }],
    title: { type: String, required: true },
    property: {
      craft: {type: String,  required: true},  // 工艺 enum: [1,2,3,4],
      flavor: {type: String, required: true},  // 口味  enum: [1,2,3,4],
      hard: {type: String, required: true},   // 难度 enum: [1,2,3,4],
      people: {type: String, required: true}  // pepole 人数: [1,2,3,4],
    },
    product_pic_url: { type: String, required: true },
    product_story: { type: String, required: true, minlength:1, maxlength:100 },
    raw_material:{ // 原材料
      main_material: [{  // 主料
        name: {type: String, required: false},
        specs: {type: String, required: false},
      }],
      accessories_material: [{  // 辅料
        name: {type: String, required: false},
        specs: {type: String, required: false},
      }]
    },
    steps: [{
      img_url: {type: String, required: false},
      describe: {type: String, required: false},
    }],
    skill:  { type: String, required: false, minlength:1, maxlength:100 },
    classify: {type: String, required: true},
    parent_classify: {type: String, required: true},
    createdAt: { type: Date, default: Date.now },
    updateAt: { type: Date, default: Date.now }
  })
  return mongoose.model('Menu', MenuSchema)
}