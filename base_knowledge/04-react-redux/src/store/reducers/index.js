//合并reducer
import { combineReducers } from "redux";

import counterReducer from "./Counter.reducer";
import personReducer from "./Persion.reducer";

//合并到结果：{counter:{count},person:[{...}]}
export default combineReducers({
  counter: counterReducer,
  person: personReducer,
});
