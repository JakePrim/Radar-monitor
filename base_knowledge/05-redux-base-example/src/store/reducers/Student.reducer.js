import {
  ADD_STUDENT,
  REMOVE_STUDENT,
  EDIT_STUDENT,
  UPDATE_STUDENT,
} from "../actions/types/Student.type";

const initState = {
  studentList: [],
  showStudent: null,
};

function StudentRedux(state = initState, action) {
  console.log(action);
  switch (action.type) {
    case ADD_STUDENT:
      return {
        studentList: [...state.studentList, action.payload],
        showStudent: null,
      };
    case REMOVE_STUDENT:
      const studentList = JSON.parse(JSON.stringify([...state.studentList]));
      const index = studentList.findIndex(
        (student) => student.number === action.payload
      );
      studentList.splice(index, 1);
      return {
        studentList,
      };
    case EDIT_STUDENT:
      const list = JSON.parse(JSON.stringify([...state.studentList]));
      //查找回显的数据
      const showIndex = list.findIndex(
        (student) => student.number === action.payload
      );
      if (showIndex !== -1) {
        const showStudent = list[showIndex];
        return {
          studentList: state.studentList,
          showStudent,
        };
      }
      return state;
    case UPDATE_STUDENT:
      const student = action.payload;
      const updateList = JSON.parse(JSON.stringify([...state.studentList]));
      const updateIndex = updateList.findIndex(
        (item) => item.number === student.number
      );
      if (updateIndex !== -1) {
        //更新数据
        updateList[updateIndex] = student;
        return {
          studentList: updateList,
          showStudent: null,
        };
      }
      return state;
    default:
      return state;
  }
}

export default StudentRedux;
