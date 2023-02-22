import React from "react";

function Flow3(props) {
  console.log(props);
  return (
    <div>
      Flow3组件
      <button
        onClick={() => {
          props.change({ name: "flow", age: 10 });
        }}
      >
        点击修改数据
      </button>
    </div>
  );
}

export default Flow3;
