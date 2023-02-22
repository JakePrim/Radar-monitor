import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import * as personActions from "../store/actions/Person.action";

/**
 * counter 获取store数据
 */
function Person(props) {
  console.log(props);
  return (
    <div>
      <h2>Person组件</h2>
      <button
        onClick={() => {
          props.addPerson({ id: 2, name: "syy" });
        }}
      >
        新增用户
      </button>
      <ul>
        {props.person.map((item) => {
          return <li key={item.id}>{item.name}</li>;
        })}
      </ul>
    </div>
  );
}
//设置组件需要的数据
const mapStateToProps = (state) => ({
  person: state.person,
});

//设置组件的行为
const mapDispatchToProps = (dispatch) => ({
  ...bindActionCreators(personActions, dispatch),
});

//第一个参数：传递什么数据；回调函数参数state 认为是index中传递过来的store对象,store 是整个应用的仓库 所以需要指定当前组件需要的数据
//第二个参数
//第二个：数据传递给谁;
export default connect(mapStateToProps, mapDispatchToProps)(Person);
