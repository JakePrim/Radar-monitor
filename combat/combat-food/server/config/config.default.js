/* eslint valid-jsdoc: "off" */

'use strict';
const path = require('path');
/**
 * @param {Egg.EggAppInfo} appInfo app info
 */
module.exports = appInfo => {
  /**
   * built-in config
   * @type {Egg.EggAppConfig}
   **/
  
  const config = exports = {};
  config.static = {
    prefix: '/static', 
    dir: path.join(appInfo.baseDir, 'app/public/'),
    dynamic: true, // 如果当前访问的静态资源没有缓存，则缓存静态文件，和`preload`配合使用；
    preload: false,
    maxAge: 31536000, // in prod env, 0 in other envs
    buffer: true, // in prod env, false in other envs
  };
  // use for cookie sign key, should change to your own and keep security
  config.keys = appInfo.name + '_1560783914976_4751';

  // add your middleware config here
  config.middleware = [ 'errorHandler' ];

  // add your user config here
  const userConfig = {
    // myAppName: 'egg',
  };

  config.security = {
    csrf: {
      enable: false,
    },
    domainWhiteList: [ 'http://127.0.0.1:7001', 'http://172.16.224.234:8080' ],
  }

  config.mongoose = {
    url: 'mongodb://127.0.0.1:27017/meishijie',
    options: {
      server: { poolSize: 20 },
      reconnectTries: 10,
      reconnectInterval: 500,
    },
  }

  exports.bcrypt = {
    saltRounds: 10 // default 10
  }

  config.jwt = {
    secret: 'meishijie',
    enable: true, // default is false
    match: '/meishijie', // optional
  }

  config.validatePlus = {
    resolveError(ctx, errors) {
      if (errors.length) {
        ctx.type = 'json';
        ctx.status = 400;
        ctx.body = {
          code: 400,
          error: errors,
          message: '参数错误',
        };
      }
    }
  };
  
  
  config.multipart = {
    mode: 'stream',
    tmpdir: path.join(process.cwd(), './app/public'),
    fileSize: '50mb',
    whitelist: [
      '.png',
      '.jpg',
      '.gif',
      '.mp4',
      '.zip'
    ],
  };

  return {
    ...config,
    ...userConfig,
  };
};
