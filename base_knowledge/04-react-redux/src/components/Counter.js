import React, { Component } from "react";
import { connect } from "react-redux";

import { bindActionCreators } from "redux";

import * as counterActions from "../store/actions/Counter.action";

/**
 * counter 获取store数据
 */
function Counter(props) {
  console.log(props);
  return (
    <div>
      <button onClick={props.increment}>+1</button>
      <button
        onClick={() => {
          props.increment_n(5);
        }}
      >
        +5
      </button>
      <span> {props.count} </span>
      <button onClick={props.decrement}>-1</button>
    </div>
  );
}

//将这些行为和数据作为props传递给组件
// bindActionCreators({
//     increment(){
//         return {type:'increment'}
//     },
// },dispatch)

//设置组件需要的数据
const mapStateToProps = (state) => ({
  count: state.count,
});

//设置组件的行为
const mapDispatchToProps = (dispatch) => ({
  ...bindActionCreators(counterActions, dispatch),
});

//第一个参数：传递什么数据；回调函数参数state 认为是index中传递过来的store对象,store 是整个应用的仓库 所以需要指定当前组件需要的数据
//第二个参数
//第二个：数据传递给谁;
export default connect(mapStateToProps, mapDispatchToProps)(Counter);
