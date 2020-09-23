import {Controller, Get, Version} from "koa-ts-controllers";

@Controller('/test')
class TestController{
    @Get('/hello')
    async hello(){
        return 'hello test';
    }
}