import React from "react";

/**
 * 循环数据渲染
 * - jsx 中可以直接将数组中的数据解构,所以可以解构数组中的item是jsx的结构
 * - 可以将数组如果是对象形势，则可以通过map组装jsx返回即可
 */

const arr = [1, 2, 3];
const arr2 = [<p>11</p>, <p>22</p>, <p>33</p>];

const arr3 = [
  {
    id: 1,
    name: "jake",
    age: 12,
    salary: 100
  },
  {
    id: 2,
    name: "prim",
    age: 13,
    salary: 10
  },
  {
    id: 3,
    name: "jon",
    age: 12,
    salary: 90
  }
];

function App3() {
  return (
    <div>
      循环数据渲染
      <p>{arr}</p>
      <p>{arr2}</p>
      <ul>
        {arr3.map((item) => {
          return (
            <li key={item.id}>
              <span>{item.name}</span>|<span>{item.age}</span>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

export default App3;
