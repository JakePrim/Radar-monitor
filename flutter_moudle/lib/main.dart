import 'package:flutter/material.dart';
import 'package:flutter_moudle/page/favorite_page.dart';
import 'package:flutter_moudle/page/recommend_page.dart';

//至少要有一个主入口
void main() => runApp(MyApp(FavoritePage()));

//注册一个入口
@pragma("vm:entry-point")
void recommend() => runApp(MyApp(RecommendPage()));

class MyApp extends StatelessWidget {
  final Widget page;

  const MyApp(this.page, {Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FlutterModule',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Scaffold(
        //表示是一个页面
        body: page,
      ),
    );
  }
}
