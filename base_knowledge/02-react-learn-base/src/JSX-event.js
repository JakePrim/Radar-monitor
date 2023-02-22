import React from "react";

/**
 * 1. 事件绑定操作:onClick = {事件监听的名称}
 * 2. 事件监听传参：a. 利用箭头函数内部调用事件监听的时候传递实参。b.利用bind方法返回一个新的函数在事件发生时调用，此时也可以传递参数
 * 3. 获取事件对象：a. 默认情况下不需要接收参数，第一个参数默认是ev
 */

const handler = () => {
  console.log("事件监听执行了");
};

const handler2 = (a) => {
  console.log(a);
};

const handler3 = (ev) => {
  console.log(ev);
};

const handler4 = (a, b, ev) => {
  console.log(a, b);
  console.log(ev);
};

function App2() {
  return (
    <div>
      事件操作
      <button onClick={handler}>点击触发事件</button>
      <button onClick={() => handler2(123)}>事件监听传参-箭头函数方式</button>
      <button onClick={handler2.bind(null, 12)}>事件监听传参-bind方式</button>
      <button onClick={handler3}>获取事件对象-默认第一个参数是事件对象</button>
      <button onClick={(ev) => handler3(ev)}>
        获取事件对象-箭头函数方式需要传递ev
      </button>
      <button onClick={handler3.bind(null)}>获取事件对象-bind方式默认ev</button>
      <button onClick={handler4.bind(null, 1, 2)}>
        获取事件对象-bind方式有参数,默认最后一个参数是ev
      </button>
    </div>
  );
}

export default App2;
