import React from 'react';
import ProductTable from '../components/ProductTable';
import {Image, StyleSheet, Text, View} from 'react-native';

const PRODUCTS = [
  {category: '水果', price: '￥1', name: 'PingGuo'},
  {category: '水果', price: '￥1', name: 'HuoLongGuo'},
  {category: '水果', price: '￥2', name: 'BaiXiangGuo'},
  {category: '蔬菜', price: '￥2', name: 'BoCai'},
  {category: '蔬菜', price: '￥4', name: 'NanGua'},
  {category: '蔬菜', price: '￥1', name: 'WanDou'},
];

export default function ProductPage() {
  const onLayout = e => {};

  return (
    <View>
      <ProductTable products={PRODUCTS} />
    </View>
  );
}

//样式表
const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    justifyContent: 'center',
    height: 60,
    borderWidth: 1,
  },
  texts: {
    fontSize: 18,
    includeFontPadding: false,
    textAlignVertical: 'center',
  },
});
