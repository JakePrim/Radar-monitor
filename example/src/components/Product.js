import {View, Text, StyleSheet} from 'react-native';
import React from 'react';

export default function Product({product = {name: '苹果', price: '1元'}}) {
  return (
    <View style={productStyle.parent}>
      <Text style={{flex: 1}}>{product.name}</Text>
      <Text style={{width: 50}}>{product.price}</Text>
    </View>
  );
}

const productStyle = StyleSheet.create({
  parent: {
    flexDirection: 'row',
    marginTop: 5,
  },
});
