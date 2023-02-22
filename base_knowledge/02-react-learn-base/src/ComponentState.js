import React, { Component } from "react";

/**
 * 组件状态： 状态就是数据，因此组件状态指的就是某一个组件自己的数据
 * 数据驱动DOM，当我们修改某一个数据的时候，界面上的DOM中数据展示也会自动更新
 * setState 是异步函数：async/await 异步执行、callback回调函数异步结果
 * setState 在使用的时候除了可以传入对象之外 还能够传入函数,调用多次会执行多次
 */
class ComponentState extends Component {
  constructor(props) {
    super(props);
    //在类组件中 默认存在一个state属性，用于保存当前状态的组件数据
    //可以通过setState来修改数据的值
    this.state = {
      name: "react",
      age: 18,
      count: 0,
    };
  }

  // update() {} this存在问题 需要使用箭头函数的形势
  update = () => {
    this.setState({ name: "update", age: 20 }, () => {
      ///由于setState是异步的，可以在回调中获取更新后的值
      console.log(this.state.name);
    });
  };

  in = () => {
    //传入函数(推荐)：调用多次，每次都会执行， 就会执行多次，最终count+6
    this.setState((state) => ({
      count: state.count + 1,
    }));
    this.setState((state) => ({
      count: state.count + 5,
    }));

    //传入对象：会进行合并，只会执行最后一次 最终count是+5
    this.setState({
      count: this.state.count + 1,
    });
    this.setState({
      count: this.state.count + 5,
    });
  };

  render() {
    return (
      <>
        组件状态
        {this.state.name}
        {this.state.age}
        <button onClick={this.update}>更新</button>
        <div>
          <p>{this.state.count}</p>
          <button onClick={this.in}>+1</button>
        </div>
      </>
    );
  }
}

export default ComponentState;
