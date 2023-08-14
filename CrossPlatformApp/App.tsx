/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import {SafeAreaView, ScrollView, StatusBar, Text, View} from 'react-native';

function App(): JSX.Element {
  return (
    <SafeAreaView>
      <StatusBar />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <View
          style={{
            backgroundColor: 'red',
            height: 100,
          }}>
          <Text style={{color: '#fff', fontSize: 18}}>这是内部的文字</Text>
        </View>
        <Text>这是文字</Text>
      </ScrollView>
    </SafeAreaView>
  );
}

export default App;
