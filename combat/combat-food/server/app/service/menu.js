
const Service = require('egg').Service
const pageSize = 5;
class MenuService extends Service {
  constructor(ctx){
    super(ctx);
  }

  async publish(payload){
    const { ctx } = this;

    if(!payload.parent_classify) {
      payload.parent_classify = payload.classify[0];
    }
    // 错误的设置
    const menu = await this.ctx.model.Menu.create(payload);
    return menu;
  }

  async query(payload, otherData={page:0}){

    const { ctx, service } = this;
    const field = {userId:1, title: 1, classify: 1, property: 1, product_pic_url: 1,name:1};
    const page = +otherData.page;
    const skip = (page-1) * pageSize;
    // const total = await this.ctx.model.Menu.count();
    let query = ctx.helper.filterDef(payload);
    let queryList = this.ctx.model.Menu.find(query, field);
    const total = await queryList.count();
    let list = [];
    if(page == 0){
      list = await queryList.find().sort({'_id':-1});
    }else {
      list = await queryList.find().skip(skip).limit(pageSize).sort({_id: -1});
    }
    
    // 查找评论
    for(let i = 0; i < list.length; i++){
      const commnets = await service.menu.getComment({menu_id: list[i]._id});
      list[i]._doc.comments_len = commnets.length;
      list[i]._doc.menuId = list[i]._id;
    }
    
    return {
      list,
      total: total,
      current_page: page,
      page_size: pageSize
    }
  }
  async changeMenuInfo(updateAttr, updateValue){
    const { ctx } = this;
    return await this.ctx.model.Menu.update({userId: updateAttr.userId},updateValue, { multi: true });
  }
  async menuInfo(payload){
    const { ctx } = this;
    return await this.ctx.model.Menu.findOne(payload);
  }
  
  // 获取所有的分类
  async classify(){
    const classifies = await this.ctx.model.Classification.find().select('-_id -__v');
    // 格式化一下
    let obj = [];
    classifies.forEach((item) => {
      let option = obj.find(o => o.parent_type === item.parent_type);
      if(option) {
        option.list.push(item);
      }else {
        let o = {
          parent_type: item.parent_type,
          parent_name: item.parent_name,
        }
        o.list = [item];
        obj.push(o);
      }
    });

    return obj;
  }
  // 获取所有属性的分类
  async property(){
    const Property = await this.ctx.model.Property.find().select('-_id -__v');
    // 格式化一下
    let obj = [];
    Property.forEach((item) => {
      let option = obj.find(o => o.parent_type === item.parent_type);
      if(option) {
        option.list.push(item);
      }else {
        let o = {
          parent_type: item.parent_type,
          parent_name: item.parent_name,
          title: item.title,
        }
        o.list = [item];
        obj.push(o);
      }
    });

    return obj;
  }

  async comment(payload){
    await this.ctx.model.Comment.create(payload);
    return await this.ctx.model.Comment.findOne(payload).populate({path: 'userInfo', select: 'name _id avatar'});
  }
  async getComment(payload){
    return await this.ctx.model.Comment.find(payload).sort({'_id':-1}).populate({path: 'userInfo', select: 'name _id avatar'});
  }
}

module.exports = MenuService;