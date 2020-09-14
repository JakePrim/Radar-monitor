'use strict';

const Controller = require('egg').Controller;



class UserActionController extends Controller {
  /**
   * get 获取关注者 {userId}
   * post 关注或取消关注指定的用户 {followUserId:,}
   * 
   */
  async following() {
    const { ctx,service } = this;
    if(ctx.request.method === 'GET'){
      const payload = ctx.request.query || {};
      const payloadClone = ctx.helper.cloneDeepWith(payload);
      //现在自己
      let follows = await service.user.findUserFollowing(payloadClone);
      let followsData = follows.map((item) => {
        return {
          name: item.name,
          _id: item._id,
          userId: item._id,
          sign: item.sign,
          avatar: item.avatar,
          following_len: item.following.length,
          follows_len: item.follows.length,
        }
      })
      // 找到
      ctx.body = {
        code:0,
        data:{
          userId: payload.userId,
          list: followsData
        },
        mes: '返回我的关注'
      }
      return;
    }
    const body = ctx.request.body || {};
    // 用户自己
    let authorization = ctx.request.header.authorization.split(' ')[1];
    let decode = ctx.app.jwt.decode(authorization);
    body.userId = decode.data._id
    let isAdd = await service.user.toggleFollow(body);
    // 获取添加关注用户的信息
    const findFollowUser = await service.user.findUserInfo({_id: body.followUserId});
    ctx.body = {
      code: 0,
      data:{
        ...findFollowUser,
        isFollowing: isAdd
      },
      mes: isAdd ? '已关注' : '已取消关注'
    }
  }
  /**
   * get 获取粉丝 {userId}
   * 
   */
  async fans() {
    const { ctx,service } = this;
    const payload = ctx.request.query || {};
      const payloadClone = ctx.helper.cloneDeepWith(payload);
      //现在自己
      let follows = await service.user.findUserFans(payloadClone);
      let followsData = follows.map((item) => {
        return {
          name: item.name,
          _id: item._id,
          userId: item._id,
          sign: item.sign,
          avatar: item.avatar,
          following_len: item.following.length,
          follows_len: item.follows.length,
        }
      })
      // 找到
      ctx.body = {
        code:0,
        data:{
          userId: payload.userId,
          list: followsData
        },
        mes: '返回我的粉丝'
      }
  }
  /**
   * 收藏的菜单
   */
  async collection(){
    const { ctx,service } = this;
    if(ctx.request.method === 'GET'){
      const payload = ctx.request.query || {};
      const validateResult = await ctx.validate('user.collection', payload);
      if(!validateResult) return;
      const payloadClone = ctx.helper.cloneDeepWith(payload);
      //现在自己
      let collections = await service.user.findUserCollections(payloadClone);
      // 找到
      ctx.body = {
        code: 0,
        data:{
          userId: payload.userId,
          list: collections
        },
        mes: '成功返回收藏'
      }
      return;
    }

    const body = ctx.request.body || {};
    // 用户自己
    let authorization = ctx.request.header.authorization.split(' ')[1];
    let decode = ctx.app.jwt.decode(authorization);
    body.userId = decode.data._id;
    let toggleInfo = await service.user.toggleCollection(body);
    ctx.body = {
      code: 0,
      data:{
        isCollection:toggleInfo.isAdd,
        collection_len: toggleInfo.collection_len
      },
      mes: toggleInfo.isAdd ? '已收藏' : '已取消收藏'
    }
  }
}

module.exports = UserActionController;
