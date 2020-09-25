import configs from './configs';
import Koa, {Context} from 'koa';//注意安装类型声明
import {bootstrapControllers} from 'koa-ts-controllers';//
import KoaRouter from 'koa-router';// @types/koa-router
import path from 'path';
import koaBodyParser from 'koa-bodyparser';

//ts-node-dev typescript 支持ts的热重载:配置scripts "dev": "ts-node-dev ./src/app.ts"

//koa-ts-controllers ts
//bootstrapControllers 注册路由
(async () => {
    const app = new Koa();
    const router = new KoaRouter();
    await bootstrapControllers(app, {
        router,
        basePath: '/api',
        versions: [1],
        controllers: [
            path.resolve(__dirname, 'controllers/**/*')//将装饰器注册到路由中
        ],
        errorHandler: async (err: any, ctx: Context) => {//统一错误处理
            let status = 500;
            let body: any = {
                statusCode: status,
                error: "Internal Server error",
                message: "An internal server error occurred"
            }
            //判断当前err是否存在 可能是代码的业务逻辑错误
            if (err.output) {
                status = err.output.statusCode;
                body = {...err.output.payload}
                if (err.data) {
                    body.errorDetails = err.data
                }
            }
            ctx.status = status;
            ctx.body = body;
        }
    });
    app.use(koaBodyParser());
    app.use(router.routes());
    app.listen(configs.server.port, configs.server.host, () => {
        console.log(`服务启动成功:http://${configs.server.host}:${configs.server.port}`)
    });
})();


