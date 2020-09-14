'use strict';
module.exports = app => {
  const mongoose = app.mongoose
  const FollowSchema = new mongoose.Schema({
    userId: {type: mongoose.Schema.Types.ObjectId,required: true},
    follows: [{ // 谁关注了我
      _id: false,
      userId: {type: mongoose.Schema.Types.ObjectId, required: true},
      followData: { type: Date, default:Date.now}
    }],
    following: [{ // 我关注了谁
      _id: false,
      userId: {type: mongoose.Schema.Types.ObjectId, required: true},
      followData: { type: Date, default:Date.now}
    }], 
    createdAt: { type: Date, default: Date.now },
    updateAt: { type: Date, default: Date.now }
  })
  return mongoose.model('Follow', FollowSchema)
}