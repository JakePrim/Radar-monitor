'use strict';
module.exports = app => {
  const mongoose = app.mongoose
  const Classification = new mongoose.Schema({
    name: { type: String, required: true },
    type: { type: String, required: true },
    parent_name: { type: String, required: true },
    parent_type: { type: String, required: true }
  })
  return mongoose.model('Classification', Classification)
}