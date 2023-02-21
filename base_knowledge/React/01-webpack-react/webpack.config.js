const path = require("path");

const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  mode: "development", //环境
  entry: "./src/index.js", //入口
  output: {
    //配置打包出口
    filename: "main.js",
    path: path.resolve("dist")
  },
  devServer: {
    //服务器地址
    port: 3000,
    hot: true
  },
  module: {
    //配置规则
    rules: [
      {
        test: /\.js|jsx$/,
        exclude: /node_modules/, //排除node_modules
        use: [
          //配置loader
          {
            loader: "babel-loader",
            options: {
              //preset-env 可以识别ES6+语法，preset-react可以识别react的语法
              presets: ["@babel/preset-env", "@babel/preset-react"]
            }
          }
        ]
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: "./src/index.html"
    })
  ]
};
