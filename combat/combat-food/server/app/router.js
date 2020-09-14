'use strict';

/**
 * @param {Egg.Application} app - egg application
 */
module.exports = app => {
  const { router, controller } = app;
  
  router.get('/', controller.home.index);
  router.post('/upload', controller.home.upload);
  router.get('/banner', controller.home.banner);

  const token = app.middleware.token();

  router.post('/user/create', controller.user.create);
  router.post('/user/login', controller.user.login);
  router.post('/user/login_out', token, controller.user.login_out);
  router.post('/user/info', token, controller.user.info);
  router.post('/user/edit', token, controller.user.edit);

  // 用户操作行为
  // 我关注的
  router.post('/user/following', controller.userAction.following);
  router.get('/user/following', controller.userAction.following);

  // 我的粉丝
  router.get('/user/fans', controller.userAction.fans);
  // 我收藏的
  router.post('/user/collection', token, controller.userAction.collection);
  router.get('/user/collection', token, controller.userAction.collection);

  // 菜谱相关
  router.post('/menu/publish', token, controller.menu.publish);
  router.get('/menu/query', controller.menu.query);
  router.get('/menu/classify', controller.menu.classify);
  router.get('/menu/property', controller.menu.property);
  router.get('/menu/menuInfo', controller.menu.menuInfo);
  router.post('/menu/comment', token, controller.menu.comment);
  router.get('/menu/comment', controller.menu.comment);
  
};
