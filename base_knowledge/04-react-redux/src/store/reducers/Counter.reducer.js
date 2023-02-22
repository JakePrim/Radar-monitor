import {
  INCREMENT,
  INCREMENT_N,
  DECREMENT,
} from "../actions/types/Counter.type";

const initState = {
  count: 0,
};

function counterReducer(state = initState, action) {
  switch (action.type) {
    case INCREMENT:
      return {
        count: state.count + 1,
      };

    case INCREMENT_N:
      return {
        count: state.count + action.payload,
      };
    case DECREMENT:
      return {
        count: state.count - 1,
      };
    default:
      return state;
  }
}

export default counterReducer;
