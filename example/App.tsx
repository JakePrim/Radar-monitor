import React from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  useColorScheme,
} from 'react-native';

import {Colors} from 'react-native/Libraries/NewAppScreen';
import ProductPage from './src/pages/ProductPage';
import FunctionView from './src/components/FunctionView';

function App(): JSX.Element {
  //获取当前的显示模式：夜间模式 和 白天模式
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={Colors.white}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <ProductPage />
        <FunctionView name="lisi" age="12" />
      </ScrollView>
    </SafeAreaView>
  );
}

export default App;
