'use strict';
const path = require('path');
const fs = require('fs');
const host = 'http://127.0.0.1:7001'

const Controller = require('egg').Controller;
class HomeController extends Controller {
  async index() {
    const { ctx } = this;
    ctx.body = 'hi, egg';
  }
  async banner() {
    const { ctx,service } = this;
    const menus = await service.menu.query({},{page:1});
    ctx.body = {
      code: 0,
      data:{
        list: menus.list.slice(0,5)
      },
      mes: 'banner返回成功'
    }
  }
  async upload () {
    const { ctx } = this;
    const {type} = ctx.request.query;

    // 如果没有传type或者传入的type不对，则提示
    if(!type || !ctx.upload[type]){
      return ctx.body = {
        code: 1,
        data:{},
        mes: '请上传正确的type类型'
      }
    }

    const fileOptions = ctx.upload[type];
    const stream = await ctx.getFileStream();
    const info = await ctx.helper.writeStreamToDisk(stream, fileOptions);

    if(info.error){
      return ctx.body = {code: 1,data:{},mes: info.mes}
    }
    ctx.body = {
      code: 0,
      data:{
        url: host + info.accessPath
      },
      mes: '上传图片成功'
    }
  }
}

module.exports = HomeController;
