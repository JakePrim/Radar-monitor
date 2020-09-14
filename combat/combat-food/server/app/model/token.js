'use strict';
module.exports = app => {
  const mongoose = app.mongoose
  const tokenSchema = new mongoose.Schema({
    userId: { type: String, required: true },
    token: { type: String, required: true },
    createdAt: { type: Date, default: Date.now },
    updateAt: { type: Date, default: Date.now }
  })
  return mongoose.model('Token', tokenSchema)
}