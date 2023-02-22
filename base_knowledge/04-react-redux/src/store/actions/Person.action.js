import { ADD_PERSON } from "../actions/types/Person.type";

export const addPerson = (payload) => {
  return {
    type: ADD_PERSON,
    payload,
  };
};
