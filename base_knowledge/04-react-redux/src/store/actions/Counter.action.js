import {
  INCREMENT,
  INCREMENT_N,
  DECREMENT,
} from "../actions/types/Counter.type";

export const increment = () => {
  return {
    type: INCREMENT,
  };
};

export const decrement = () => {
  return {
    type: DECREMENT,
  };
};

export const increment_n = (payload) => {
  return {
    type: INCREMENT_N,
    payload,
  };
};
