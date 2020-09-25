import {Body, Controller, Get, Header, Params, Post, Query, Version} from "koa-ts-controllers";
import {IsNumberString} from 'class-validator';
import {Boom} from '@hapi/Boom';

//定义验证类
class GetUsersQuery {
    @IsNumberString({
        no_symbols: false
    }, {
        message: 'page必须是数字'
    })
    page: number;
}

@Controller('/test')
class TestController {
    @Get('/hello')
    async hello(a: any) {
        console.log(a.b)
        return 'hello test';
    }

    //Params的方式
    @Get('/user/:id(\\d+)')//只有数字才能走到当前访问地址
    public async index(@Params("id") id: number) {
        return '当前params的id:' + id;
    }

    //Query的方式
    @Get('/user2')
    async getUsers2(@Query() q: { id: number }) {
        return '当前params的id:' + q.id;
    }

    //Post方式 需要安装：koaBodyParser 来处理post的body 添加到koa的中间件上即可 主要要在koa-route之前
    @Post('/user3')
    async postUser(@Body() body: {
        name: string,
        password: string
    }, @Header() h: any) {
        return '当前的用户数据:' + JSON.stringify(body) + JSON.stringify(h);
    }

    //通过class-validator 验证
    @Get('/allusers')
    async getUsers(
        @Query() q: GetUsersQuery
    ) {
        console.log(q);
        if (true){
            //出现了逻辑性错误
        }
        return '传过来的query' + JSON.stringify(q);
    }
}