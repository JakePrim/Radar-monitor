import configs from './configs';
import Koa from 'koa';//注意安装类型声明
import {bootstrapControllers} from 'koa-ts-controllers';
import KoaRouter from 'koa-router';
import path from 'path';

//koa-ts-controllers ts
//bootstrapControllers 注册路由
(async () => {
    const app = new Koa();
    const router = new KoaRouter();
    await bootstrapControllers(app,{
        router,
        basePath:'/api',
        versions:[1],
        controllers:[
            path.resolve(__dirname,'controllers/**/*')
        ]
    });
    app.use(router.routes());
    app.listen(configs.server.port, configs.server.host, () => {
        console.log(`服务启动成功:http://${configs.server.host}:${configs.server.port}`)
    });
})();


