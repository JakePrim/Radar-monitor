import React from "react";

/**
 * JSX 可以看作是JS语言的扩展，既不是字符串也不是HTML，它具备了JS所有的功能，同时还可以被转为
 * HTML在界面上进行展示(react react-dom)
 * JSX的特点：
 *
 * - 动态显示数据
 * - 支持调用方法，可以调用JS内置的方法
 * - 支持表达式(不能写if else,支持三元表达式)
 * - 支持模版字符串
 * - 支持注释，注释不会被渲染
 * - 不能直接打印对象，需要对对象进行处理
 * - JSX 本身就是一个表达式
 * - JSX添加属性：字符串属性，直接双引号包裹、动态属性
 * - JSX添加子元素，JSX只能有一个父元素
 * - JSX 单标签必须正确关闭
 */

//- JSX 本身就是一个表达式
const jsxN = <div>jsx</div>;

const name = "JSX dynamic data";

const obj = {
  name: "react",
  age: 100,
};

function sayHi() {
  return "hello";
}

const flag = false;

const age = 100;

function JsxBase() {
  return (
    <>
      <p>{name}</p>
      <p>{sayHi()}</p>
      <p>{console.log("1111")}</p>
      <p>{Math.random()}</p>
      <p>{1 + 2 + 3}</p>
      <p>{flag ? "登录" : "未登录"}</p>
      <p>{`hello,${name}`}</p>
      <p>{/**这是注释的内容 */}</p>
      <p>{JSON.stringify(obj)}</p>
      <p title="自定义标题">添加属性</p>
      <p title={age}>添加动态属性</p>
    </>
  );
}

export default JsxBase;
