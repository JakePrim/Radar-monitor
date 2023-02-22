import React, { Component } from "react";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import * as studentAction from "../store/actions/Student.action";

class StudentList extends Component {
  removeHandler = (number, ev) => {
    ev.preventDefault();
    this.props.removeStudent(number);
  };

  updateHandler = (number, ev) => {
    ev.preventDefault();
    this.props.editStudent(number);
  };

  render() {
    const { studentList } = this.props;
    return (
      <div className="col-md-6 col-md-offset-1">
        <table className="table table-striped table-hover">
          <thead>
            <tr>
              <th>学号</th>
              <th>姓名</th>
              <th>性别</th>
              <th>年龄</th>
              <th>入学时间</th>
              <th>爱好</th>
              <th>所属学院</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            {studentList.map((item) => {
              return (
                <tr key={item.number}>
                  <td>{item.number}</td>
                  <td>{item.name}</td>
                  <td>{item.sex}</td>
                  <td>{item.age}</td>
                  <td>{item.date}</td>
                  <td>
                    {item.hobbies.map((hobby, index) => (
                      <span key={index}>{hobby} </span>
                    ))}
                  </td>
                  <td>{item.college}</td>
                  <td>
                    <a
                      href="#"
                      onClick={this.removeHandler.bind(this, item.number)}
                    >
                      删除
                    </a>
                    <a
                      href="#"
                      onClick={this.updateHandler.bind(this, item.number)}
                    >
                      修改
                    </a>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
        {studentList.length === 0 && <p className="text-center">无学生信息</p>}
        <p>总共有 {studentList.length} 个学生</p>
        <p>
          学生的平均年龄是
          {studentList.length > 0
            ? studentList
                .map((item) => Number.parseInt(item.age))
                .reduce((previous, current) => previous + current, 0) /
              studentList.length
            : 0}
        </p>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  studentList: state.studentList,
});

const mapDispatchToProps = (dispatch) => ({
  ...bindActionCreators(studentAction, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(StudentList);
