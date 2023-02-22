import React, { Component } from "react";
import PropTypes from "prop-types";

/**
 * 在类组件中存在一个props属性 ，外部所传递进来的数据都可以通过props访问
 */
class Header extends Component {
  //设置参数的默认值
  static defaultProps = {
    name: "11",
    age: 100
  };

  static propTypes = {
    name: PropTypes.string.isRequired,
    age: PropTypes.number
  };

  render() {
    const { name, age } = this.props;
    return (
      <div>
        Header 组件内容
        <p>{name}</p>
        <p>{age}</p>
        {this.props.children}
      </div>
    );
  }
}

export default Header;
