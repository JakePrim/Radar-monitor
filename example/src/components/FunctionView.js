import {
  View,
  Text,
  ScrollView,
  StyleSheet,
  useWindowDimensions,
} from 'react-native';
import React, {useState, useEffect, useRef} from 'react';

export default function FunctionView(props) {
  const {name, age} = props;
  //hook [变量名,更新函数] = useState(默认值)
  const [address, setAddress] = useState('将书生');
  const [hobby, setHobby] = useState('足球');

  //useRef：获取View的引用
  const scrollViewRef = useRef(null);

  //useWindowDimensions:获取屏幕的信息
  const {width, height} = useWindowDimensions();

  console.log(`width:${width};height:${height}`);

  //完成渲染后的处理：useEffect 监听值的变化，一旦变化就会进行通知
  useEffect(() => {
    console.log('useEffect .....');
    setTimeout(() => {
      setAddress('河北省石家庄市');
      scrollViewRef.current.scrollToEnd({animated: true});
    }, 2000);
  }, []); //[] 如果是空 就会相当于 渲染完成的回调
  console.log('return ....');
  //可以监听多个值的变化
  useEffect(() => {
    console.log(`update address:${address}`);
  }, [address, name, age]);

  return (
    <View style={{width: '100%', height: 200, backgroundColor: 'blue'}}>
      <Text style={{color: 'white', fontSize: 20}}>姓名：{name}</Text>
      <Text style={{color: 'white'}}>年龄:{age}</Text>
      <Text style={{color: 'white'}}>{address}</Text>
      <Test title="标题" content={'内容'} />
      <ScrollView ref={scrollViewRef}>
        <Text style={{fontSize: 24, marginVertical: 12, color: 'red'}}>
          AAA
        </Text>
        <Text style={{fontSize: 24, marginVertical: 12, color: 'red'}}>
          BBB
        </Text>
        <Text style={{fontSize: 24, marginVertical: 12, color: 'red'}}>
          CCC
        </Text>
        <Text style={{fontSize: 24, marginVertical: 12, color: 'red'}}>
          DDD
        </Text>
        <Text style={{fontSize: 24, marginVertical: 12, color: 'red'}}>
          EEE
        </Text>
        <Text style={{fontSize: 24, marginVertical: 12, color: 'red'}}>
          FFF
        </Text>
      </ScrollView>
    </View>
  );
}

const Test = ({title, content}) => {
  return (
    <View>
      <Text style={{color: 'white'}}>{title}</Text>
      <Text style={{color: 'white'}}>{content}</Text>
    </View>
  );
};

const style = StyleSheet.create({
  t: {color: 'white'},
});
