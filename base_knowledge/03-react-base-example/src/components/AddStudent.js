import React, { Component } from "react";

class AddStudents extends Component {
  constructor(props) {
    super(props);
    this.stateHandler = this.stateHandler.bind(this);
  }

  state = {
    number: "",
    name: "",
    age: "",
    sex: "女",
    college: "大前端",
    date: "",
    hobbies: [
      {
        id: 1,
        name: "足球",
        isChecked: false,
      },
      {
        id: 2,
        name: "篮球",
        isChecked: false,
      },
      {
        id: 3,
        name: "羽毛球",
        isChecked: false,
      },
    ],
  };

  origin = JSON.parse(JSON.stringify({ ...this.state }));

  stateHandler = (ev) => {
    const prop = ev.target.name;
    const value = ev.target.value;
    this.setState({
      [prop]: value,
    });
  };

  /**
   * 处理复选框 数据改变逻辑 同步到状态中去
   * @param {*} index
   * @param {*} ev
   */
  hobbyHandler = (index, ev) => {
    const hobbies = [...this.state.hobbies];
    hobbies[index].isChecked = ev.target.checked;
    this.setState({ hobbies });
  };

  submit = (ev) => {
    //阻止默认行为
    ev.preventDefault();
    //1. 处理hobbies提交数据
    const hobbies = [...this.state.hobbies]
      .filter((hobby) => hobby.isChecked)
      .map((hobby) => hobby.name);
    //2. 整体提交的数据
    const formValue = { ...this.state, hobbies };
    //3. 通知父子组数据添加
    this.props.addList(formValue, () => {
      //父组件处理完毕后 还原数据状态
      console.log(this.origin);
      ///注意将hobbies重置 hobbies是数组 有深拷贝的问题
      const data = {
        ...this.origin,
        hobbies: [
          {
            id: 1,
            name: "足球",
            isChecked: false,
          },
          {
            id: 2,
            name: "篮球",
            isChecked: false,
          },
          {
            id: 3,
            name: "羽毛球",
            isChecked: false,
          },
        ],
      };
      this.setState(data);
    });
  };

  render() {
    if (
      this.props.showStudent != null &&
      this.props.showStudent.number !== this.state.number
    ) {
      //注意要处理hobby
      const student = { ...this.props.showStudent };
      //拿到原始的hobbies
      const hobbies = [...this.origin.hobbies];

      //回显选中的状态
      for (const hobby in student.hobbies) {
        //回显的hobbies 处理原始的hobbies
        hobbies.map((item) => {
          if (hobby === item.name) {
            item.isChecked = true;
          }
          return item;
        });
      }
      console.log(hobbies);
      student.hobbies = hobbies;
      this.setState({ ...student });
    }
    return (
      <div className="col-md-5">
        <form onSubmit={this.submit.bind(this)}>
          <div className="form-group">
            <label>学号</label>
            <input
              type="number"
              name="number"
              className="form-control"
              placeholder="请输入学号"
              readOnly={this.props.showStudent != null}
              value={this.state.number}
              onChange={this.stateHandler}
            />
          </div>
          <div className="form-group">
            <label>姓名</label>
            <input
              name="name"
              type="text"
              value={this.state.name}
              onChange={this.stateHandler}
              className="form-control"
              placeholder="请输入姓名"
            />
          </div>
          <div className="form-group">
            <label>性别&nbsp;&nbsp;</label>
            <label className="checkbox-inline">
              <input
                type="radio"
                value="男"
                name="sex"
                checked={this.state.sex === "男"}
                onChange={this.stateHandler}
              />
              男
            </label>
            <label className="checkbox-inline">
              <input
                type="radio"
                value="女"
                name="sex"
                checked={this.state.sex === "女"}
                onChange={this.stateHandler}
              />
              女
            </label>
          </div>
          <div className="form-group">
            <label>年龄</label>
            <input
              type="text"
              name="age"
              value={this.state.age}
              onChange={this.stateHandler}
              className="form-control"
              placeholder="请输入年龄"
            />
          </div>
          <div className="form-group">
            <label>入学时间</label>
            <input className="form-control" type="date" />
          </div>
          <div className="form-group">
            <label>爱好</label>
            {this.state.hobbies.map((hobby, index) => {
              return (
                <div className="checkbox" key={hobby.id}>
                  <label>
                    <input
                      type="checkbox"
                      checked={hobby.isChecked}
                      value={hobby.name}
                      onChange={this.hobbyHandler.bind(this, index)}
                    />
                    {hobby.name}
                  </label>
                </div>
              );
            })}
          </div>
          <div className="form-group">
            <label>所属学院</label>
            <select
              className="form-control"
              name="college"
              value={this.state.college}
              onChange={this.stateHandler}
            >
              <option value="大前端">大前端</option>
              <option value="Java">Java</option>
              <option value="python">python</option>
            </select>
          </div>
          <button type="submit" className="btn btn-default">
            {this.props.showStudent != null ? "修改" : "添加"}
          </button>
        </form>
      </div>
    );
  }
}

export default AddStudents;
