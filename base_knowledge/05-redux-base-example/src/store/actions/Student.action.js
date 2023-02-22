import {
  ADD_STUDENT,
  REMOVE_STUDENT,
  EDIT_STUDENT,
  UPDATE_STUDENT,
} from "./types/Student.type";

export const addStudent = (payload) => {
  return {
    type: ADD_STUDENT,
    payload,
  };
};

export const removeStudent = (payload) => {
  return {
    type: REMOVE_STUDENT,
    payload,
  };
};

export const editStudent = (payload) => {
  return {
    type: EDIT_STUDENT,
    payload,
  };
};

export const updateStudent = (payload) => {
  return {
    type: UPDATE_STUDENT,
    payload,
  };
};
