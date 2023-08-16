import {LogBox, StyleSheet, Text, View} from 'react-native';
import React, {useEffect, useRef, useState} from 'react';

export default function ViewDemo() {
  const [height, setHeight] = useState(100);
  const viewRef = useRef(null);
  useEffect(() => {
    setTimeout(() => {
      setHeight(200);
    }, 2000);
    setTimeout(() => {
      viewRef.current.setNativeProps({
        style: {backgroundColor: 'blue', width: 50},
      });
    }, 2000);
  }, []);
  return (
    <View style={styles.root}>
      <View style={styles.row1}>
        <View style={styles.subView} />
        <View style={styles.subView2} />
        <View style={styles.subView3} />
      </View>
      <View style={[styles.row1, styles.row2]}>
        <View style={styles.subView4} />
        <View style={styles.subView5} />
        <View style={styles.subView6} />
      </View>
      <View style={[styles.row1, styles.row2]}>
        <View style={styles.subView7} />
        <View style={styles.subView8} />
        <View style={styles.subView9} />
      </View>
      <View
        ref={viewRef}
        style={[styles.sub, {height}]}
        onLayout={e => {
          console.log(e.nativeEvent);
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  sub: {
    width: 100,
    backgroundColor: 'red',
    position: 'absolute',
    top: 330,
  },
  root: {
    width: '100%',
    height: '100%',
    backgroundColor: '#c0c0c0',
    flexDirection: 'column',
    alignItems: 'flex-end',
    justifyContent: 'center',
  },
  row1: {
    flexDirection: 'row',
    width: '100%',
  },
  row2: {
    marginTop: 10,
  },
  subView: {
    width: 100,
    height: 100,
    backgroundColor: 'red',
    flexGrow: 1,
  },

  subView2: {
    width: 100,
    height: 100,
    backgroundColor: 'blue',
    flexGrow: 2,
  },

  subView3: {
    width: 100,
    height: 100,
    backgroundColor: 'yellow',
    flexGrow: 3,
  },
  subView4: {
    width: 100,
    height: 100,
    backgroundColor: 'red',
    flex: 1,
  },

  subView5: {
    width: 100,
    height: 100,
    backgroundColor: 'blue',
    flex: 2,
  },

  subView6: {
    width: 10,
    height: 100,
    backgroundColor: 'green',
    flex: 3,
  },
  subView7: {
    width: '15%',
    height: 100,
    backgroundColor: 'red',
  },

  subView8: {
    width: '25%',
    height: 100,
    backgroundColor: 'blue',
    borderWidth: 10,
    borderColor: 'white',
  },

  subView9: {
    width: '40%',
    height: 100,
    backgroundColor: 'green',
  },
});
