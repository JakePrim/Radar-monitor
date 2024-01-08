import {Text, StyleSheet} from 'react-native';
import React from 'react';

export default function Category({category}) {
  return <Text style={categoryStyle.categoryTxt}>{category}</Text>;
}

const categoryStyle = StyleSheet.create({
  categoryTxt: {
    marginTop: 20,
    flexDirection: 'row',
    width: 100,
    fontWeight: 'bold',
  },
});
