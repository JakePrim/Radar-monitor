import React, { Component, Fragment } from "react";

/**
 * 创建组件的方式：
 * - 函数组件
 * - 类组件: 必须继承Component；必须实现render方法；组件名称首字母必须大写；
 * <Fragment> 作为组件的跟元素 <> </>,防止嵌套过多的div
 */
//类组件
class T1 extends Component {
  render() {
    return (
      <>
        <p>T1 组件的内容</p>
      </>
    );
  }
}

export default T1;
