'use strict';
module.exports = app => {
  const mongoose = app.mongoose
  const UserSchema = new mongoose.Schema({
    name: { type: String, required: true },
    password: { type: String, required: true },
    sign: { type: String,default: '' },
    avatar: { type: String, default: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'},
    userage: {type: Number},
    follows: [{ 
      type: mongoose.Schema.Types.ObjectId, 
      ref: 'User',
    }],
    following: [{ 
      type: mongoose.Schema.Types.ObjectId, 
      ref: 'User',
    }],
    collections:[{
      type: mongoose.Schema.Types.ObjectId, 
      ref: 'Menu',
    }],
    logindate : { type: Date, default:Date.now},   
    createdAt: { type: Date, default: Date.now },
    updateAt: { type: Date, default: Date.now }
  })
  return mongoose.model('User', UserSchema)
}