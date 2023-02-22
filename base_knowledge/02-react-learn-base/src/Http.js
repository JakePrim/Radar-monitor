import React, { Component } from "react";
import axios from "axios";

/**
 * 网络请求 数据状态处理
 *
 * 请求转发：
 * 1. package.json 配置"proxy": "http://localhost:3005"
 * 2. 客户端和服务器同源，由服务端进行请求转发 拿到结果传给客户端,服务端不存在跨域的问题
 * 3. http-proxy-middleware 工具包处理请求转发 灵活配置服务器地址，通过setupProxy.js配置
 *
 * Mock数据：
 * 1. public/api/data.json 配置json数据 通过访问xxx/api/data.json 就可以获取到模拟的数据
 * 2. 通过第三方的工具进行mock数据 例如：apipost
 */
class Http extends Component {
  constructor() {
    super();
    this.state = {
      msg: "",
    };
  }

  render() {
    return <div>ajax 网络请求 {this.state.msg}</div>;
  }

  async componentDidMount() {
    // 配置proxy的处理
    const data = await axios.get("/api/welcome").then((res) => res.data);
    this.setState(data);
  }
}

export default Http;
