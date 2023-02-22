import React, { Component } from "react";
import Flow1 from "./Flow1";

/**
 * 1. 单向数据流动定义：单向数据流的设计原则，要求将不同组件之间需要共享的数据都定义在上层
 * 2. 数据修改：需要在上层组件定义数据修改的函数
 * 3. 单向数据流动，自顶向下，从父组件传到自组件；共享的数据定义在上层组件中；子组件通过调用父组件传递过来的函数更改数据
 * 当数据发生更改之后，会重新渲染组件树
 */
class Flow extends Component {
  state = {
    name: "react",
    age: 20,
  };

  // 定义状态的更新方法 只负责定义 在想要修改数据的地方进行调用
  handler = ({ name, age }) => {
    this.setState({
      name,
      age,
    });
  };

  render() {
    return (
      <div>
        Flow单向数据流动演示
        <Flow1 {...this.state} change={this.handler} />
      </div>
    );
  }
}

export default Flow;
