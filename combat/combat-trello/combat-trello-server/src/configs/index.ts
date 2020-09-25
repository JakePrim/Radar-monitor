/**
 * 配置信息
 */
const configs = {
    development: {//开发环境配置
        server: {
            host: 'localhost',
            port: 8080
        }
    },
    test:{//测试环境配置
        server: {
            host: 'localhost',
            port: 8080
        }
    },
    production:{//生产环境配置
        server: {
            host: 'localhost',
            port: 8080
        }
    }
};
//将process.env.NODE_ENV 进行约束
//type configKeys = 'development' | 'test' | 'production';
//可以自动推导类型 typeof 获取类型 keyof 获取key值
type configKeys = keyof typeof configs;

//根据系统设置的环境变量获取返回哪个环境的配置  注意安装：npm i -D @types/node node的类型声明
//process读取当前运行环境相关的信息
const NODE_EVN = process.env.NODE_ENV as configKeys || 'development';

export default configs[NODE_EVN];