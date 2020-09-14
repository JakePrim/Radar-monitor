'use strict'

const Service = require('egg').Service

class ActionTokenService extends Service {
  async apply(_id) {
    const {ctx} = this
    return ctx.app.jwt.sign({
      data: {
        _id: _id
      },
      // exp: Math.floor(Date.now() / 1000) + (60 * 60 * 24 * 7)
    }, ctx.app.config.jwt.secret, {
      expiresIn: '100000000000s'
    })
  }

  async saveToken(payload){
    const {ctx} = this;
    // 如果存在，需要更新
    return await ctx.model.Token.findOneAndUpdate({userId: payload.userId}, {token: payload.token}, {upsert: true});
  }

  async deleteToken(payload){
    // 如果存在，需要更新
    return await this.ctx.model.Token.remove(payload);
  }
}

module.exports = ActionTokenService