const Service = require('egg').Service
class UserService extends Service {
  constructor(ctx){
    super(ctx);
  }

  async create(payload){
    const { ctx } = this;
    payload.password = await ctx.genHash(payload.password);
    return await ctx.model.User.create(payload);
  }
  async findUserInfo(payload, options){
    let  findUser = await this.ctx.model.User.findOne(payload, options);
    let menus = await this.ctx.service.menu.query({userId: payload._id});
    if(!findUser) return findUser;
    return {
      name: findUser.name,
      _id: findUser._id,
      userId: findUser._id,
      follows_len: findUser.follows.length,
      following_len: findUser.following.length,
      collections_len: findUser.collections.length,
      avatar: findUser.avatar,
      sign: findUser.sign,
      work_menus_len: menus.total,
      createdAt: findUser.createdAt,
    }
  }
  async findUser(payload, options={}){
    if('userId' in payload){  // _id 和userId 都可以搜索
      payload._id = payload.userId;
      delete payload.userId;
    }
    return await this.ctx.model.User.findOne(payload, options);
    
  }

  async login(payload){
    const { ctx } = this;
    payload.password = await ctx.genHash(payload.password);
    return ctx.model.User.findOne(payload);
  }

  //查找用户的关注者
  async findUserFollowing(payload){

    let followings = await this.ctx.model.User
                            .findOne({_id: payload.userId}, {following: 1})
                            .populate({
                              path: 'following',
                              select: 'name _id sign follows following avatar'
                            });
                            
    if(followings){
      return followings.following;
    }
    return [];
  }
  // 查找用户的粉丝
  async findUserFans(payload){
    let fans = await this.ctx.model.User
                            .findOne({_id: payload.userId}, {follows: 1})
                            .populate({
                              path: 'follows',
                              select: 'name _id sign follows following avatar'
                            });
                            
    if(fans){
      return fans.follows;
    }
    return [];
  }
  // 关注或取消关注指定用户
  /**
   *关注或取消关注
   * @param {*} payload
   * @returns {Boolean} true 添加 false，取消
   * @memberof UserService
   */
  async toggleFollow(payload){
    let following = await this.ctx.model.User
                        .findOne({_id: payload.userId})
                        .populate({
                          path: 'following',  // 关注
                        });
    let follows = await this.ctx.model.User
                .findOne({_id: payload.followUserId})
                .populate({
                  path: 'follows',  // 粉丝
                });
    let isAdd = false;
    // 关注 - 取关
    if(!!following.following.find(item => item._id.toString() === payload.followUserId)){
      // 取消关注
      following.following = following.following.filter(item => item._id.toString() !== payload.followUserId);
      // 删掉粉丝
      follows.follows = follows.follows.filter(item => item._id.toString() !== payload.userId);
      isAdd = false;
    }else {
      // 关注
      following.following.push(payload.followUserId);
      // 添加粉丝
      follows.follows.push(payload.userId);
      isAdd = true;
    }
    await following.save();
    await follows.save();
    return isAdd;
  }

  async findUserCollections(payload){
    let collections = await this.ctx.model.User
        .findOne({_id: payload.userId}, {collections: 1})
        .populate({
          path: 'collections',
          // select: 'name _id sign'
        });
        
    if(collections){
    return collections.collections;
    }
    return [];
  }

  /**
   *收藏或取消收藏
   * @param {*} payload
   * @returns {Boolean} true 收藏 false，取消
   * @memberof UserService
   */
  async toggleCollection(payload){
    let collections = await this.ctx.model.User
                        .findOne({_id: payload.userId})
                        .populate({
                          path: 'collections',  // 关注
                        });
    let collectionUsers = await this.ctx.model.Menu
                .findOne({_id: payload.menuId})
                .populate({
                  path: 'collectionUsers',  // 粉丝
                });
    let isAdd = false;
    // 关注 - 取关
    if(!!collections.collections.find(item => item._id.toString() === payload.menuId)){
      // 取消关注
      collections.collections = collections.collections.filter(item => item._id.toString() !== payload.menuId);
      // 删掉粉丝
      collectionUsers.collectionUsers = collectionUsers.collectionUsers.filter(item => item._id.toString() !== payload.userId);
      isAdd = false;
    }else {
      // 关注
      collections.collections.push(payload.menuId);
      // 添加粉丝
      collectionUsers.collectionUsers.push(payload.userId);
      isAdd = true;
    }
    await collections.save();
    await collectionUsers.save();
    return {
      isAdd,
      collection_len: collectionUsers.collectionUsers.length
    };
  }

  async changeUserInfo(payload){
    return this.ctx.model.User.findByIdAndUpdate(payload._id, {...payload})
  }
  
}

module.exports = UserService;