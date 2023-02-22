import { ADD_PERSON } from "../actions/types/Person.type";

const initState = [
  {
    id: 1,
    name: "zoe",
  },
];

export default (state = initState, action) => {
  switch (action.type) {
    case ADD_PERSON:
      return [...state, action.payload];
    default:
      return state;
  }
};
