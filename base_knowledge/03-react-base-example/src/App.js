import "./App.css";
import StudentTitle from "./components/StudentTitle";
import AddStudents from "./components/AddStudent";
import StudentList from "./components/StudentList";

import React, { Component, PureComponent } from "react";

class App extends PureComponent {
  state = {
    studentList: [],
    showStudent: null,
  };

  addList = (student, callback) => {
    const studentList = [...this.state.studentList];
    const index = studentList.findIndex(
      (item) => item.number === student.number
    );
    if (index === -1) {
      //异步操作，当执行完这个操作 在通知子组件进行数据重置
      this.setState(
        {
          studentList: [...this.state.studentList, student],
          showStudent: null,
        },
        callback
      );
    } else {
      //更新数据
      studentList[index] = student;
      this.setState(
        {
          studentList,
          showStudent: null,
        },
        callback
      );
    }
  };

  removeStudent = (number) => {
    ///由于存在数组 有深拷贝的问题
    const studentList = JSON.parse(JSON.stringify([...this.state.studentList]));
    const index = studentList.findIndex((student) => student.number === number);
    studentList.splice(index, 1);
    this.setState({ studentList });
  };

  getStudent = (number) => {
    const studentList = JSON.parse(JSON.stringify([...this.state.studentList]));
    //查找回显的数据
    const index = studentList.findIndex((student) => student.number === number);
    if (index !== -1) {
      const showStudent = studentList[index];
      this.setState({
        showStudent,
      });
    }
  };

  /**
   * 组件生命周期：组件被创建然后插入DOM当中
   * 1. constructor 组件初始化配置
   * 2. render 解析JSX 渲染DOM
   * 3. componentDidMount 当组件挂载之后执行: 发送网络请求 添加定时器 添加事件监听 获取DOM元素
   * @returns
   */
  constructor(props) {
    super(props);
    console.log("constructor");
  }

  componentDidMount = () => {
    console.log("componentDidMount 挂载完毕");
    window.addEventListener("click", this.foo);
  };

  /**
   *  组件更新：
   * 1. 当数据更新之后，组件就需要被重新渲染
   * 2. 外部传入的props，自身state状态
   *
   * 更新方法：
   * shouldComponentUpdate(nextProps,nextState) 组件是否更新 默认返回true，返回false组件不会更新，可以通过PureComponent 来代替该方法
   * render()
   * componentDidUpdate() 组件更新完成之后执行
   */

  // shouldComponentUpdate(nextProps, nextState) {
  //   console.log("shouldComponentUpdate");
  //   return true;
  // }

  foo = () => {
    console.log("绑定事件");
  };

  componentDidUpdate() {
    console.log("componentDidUpdate");
  }

  /**
   * 组件卸载阶段：将组件从DOM上删除
   *
   * 1. componentWillUnMount 解除事件监听 等销毁操作
   */
  componentWillUnmount() {
    console.log("componentWillUnmount");
    window.removeEventListener("click", this.foo);
  }

  render() {
    console.log("render");
    return (
      <div className="container">
        <StudentTitle />
        <AddStudents
          addList={this.addList}
          showStudent={this.state.showStudent}
        />
        <StudentList
          studentList={this.state.studentList}
          removeStudent={this.removeStudent}
          getStudent={this.getStudent}
        />
      </div>
    );
  }
}

export default App;
