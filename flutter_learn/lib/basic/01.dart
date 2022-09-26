void foo() {
  String str = 'hello world';
  print(str);
  Person toly = Person('toly');
  toly.say();

  //const 修饰编译器常量 必须初始化 并且不能修改
  const String t1 = "1";
  //t1 = "2";

  //final 修饰运行期不可变量
  final t2 = "2";

  // t2 = "3";

  int result = add(1, 2);
}

int add(int a, int b){
  int c = a + b;
  return c;
}

class Person {
  final String name;

  Person(this.name); //构造函数

  void say() {
    print("我是 $name ${name}"); //和kotlin字符串模版类似
  }
}
