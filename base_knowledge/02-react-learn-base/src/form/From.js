import React, { Component } from "react";

/**
 * 受控表单：表单元素的值全部由react进行管理，此时表单元素的值在state中管理
 *  1. 将state中的状态与表单的value值进行绑定
 *  2. onChange --> ev.target.value 更新状态值 [ev.target.name]:ev.target.value 进行状态匹配
 *  3. readOnly 就不需要实现onChange
 *  4. defaultValue 只是设置状态值 表单的值不交给react处理
 *
 * 非受控表单: 不受react管理，表单元素的数据由DOM元素本身进行管理，表单元素的值也是存放在DOM元素里，获取的时候需要操作DOM元素
 */
class From extends Component {
  state = {
    name: "react",
    age: 40,
    test: "test",
    subject: "java",
    sex: "男",
  };

  hobbies = [
    {
      id: 1,
      title: "vue",
      isCheck: false,
    },
    {
      id: 2,
      title: "react",
      isCheck: true,
    },
    {
      id: 3,
      title: "Angular",
      isCheck: false,
    },
  ];

  handlerHobbies = (index, ev) => {
    this.hobbies[index].isCheck = ev.target.checked;
  };

  /**
   * input 的name属性 必须和state的一致
   */
  handler = (ev) => {
    console.log(ev.target.name);
    const prop = ev.target.name;
    this.setState({
      [prop]: ev.target.value,
    });
  };

  submit = (ev) => {
    ev.preventDefault();
    console.log("执行了提交");
    let result = this.hobbies
      .filter((hobby) => hobby.isCheck)
      .map((item) => item.id);
    result = {
      ...this.state,
      result,
    };
    console.log(result);
    // 获取ref 非受控表单的值
    console.log(this.refs.username.value);
  };

  render() {
    return (
      <form onSubmit={this.submit}>
        <div>
          <h3>表单的使用</h3>
          <input name="name" value={this.state.name} onChange={this.handler} />
          <input name="age" value={this.state.age} onChange={this.handler} />
          {/** readOnly 和 onChange 必须实现一个否则会警告 或者将value设置为defaultValue */}
          <input type="text" value={this.state.test} readOnly />
          <input type="text" defaultValue={this.state.test} />
          <p>下拉框</p>
          <p>
            <select
              name="subject"
              value={this.state.subject}
              onChange={this.handler}
            >
              <option value="react">react</option>
              <option value="java">java</option>
              <option value="node">node</option>
            </select>
            <span> state:{this.state.subject}</span>
          </p>
          <p>单选框</p>
          <p>
            <input
              type="radio"
              name="sex"
              value="男"
              defaultChecked={this.state.sex === "男"}
              onChange={this.handler}
            />
            男
            <input
              type="radio"
              name="sex"
              value="女"
              onChange={this.handler}
              defaultChecked={this.state.sex === "女"}
            />
            女
          </p>
          <span>state:{this.state.sex}</span>
          <p>复选框</p>
          <p>
            {this.hobbies.map((item, index) => {
              return (
                <label key={item.id}>
                  <input
                    type="checkbox"
                    defaultChecked={item.isCheck}
                    onChange={this.handlerHobbies.bind(this, index)}
                  />
                  {item.title}
                </label>
              );
            })}
            <button
              type="button"
              onClick={() => {
                console.log(this.hobbies);
              }}
            >
              点击查看状态
            </button>
          </p>
        </div>
        <p>非受控表单-ref不再推荐使用</p>
        <p>
          <input type="text" ref="username" />
        </p>
        <input type="submit" />
      </form>
    );
  }
}

export default From;
