//vue-cli 配置 合并到内置的webpack的配置中
module.exports={
    publicPath:'/',
    devServer:{
        proxy:{//配置开发代理 解决跨域的问题
            'api/':{
                target:'http://127.0.0.1:7001',
                changeOrigin:true,
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    }
}