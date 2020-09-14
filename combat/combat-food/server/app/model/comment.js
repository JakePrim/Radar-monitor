'use strict';
module.exports = app => {
  const mongoose = app.mongoose
  const Comment = new mongoose.Schema({
    userId: { type: String, required: true },
    userInfo: {
      type: mongoose.Schema.Types.ObjectId, 
      ref: 'User',
    },
    menuId: { type: String, required: true },
    commentText: { type: String, required: true },
    createdAt: { type: Date, default: Date.now },
    updateAt: { type: Date, default: Date.now }
  })
  return mongoose.model('Comment', Comment)
}