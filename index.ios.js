/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
  NativeModules,
  Alert
} from 'react-native';

const { TwitterSignin } = NativeModules;

export default class TwitterLogin extends Component {
  onLoginClick() {
  TwitterSignin.logIn((error, loginData) => {
    if (error) {
      // Login failed
      Alert.alert('Error');
      return;
    }
    // Successfully logged in
    Alert.alert(`Welcome ${loginData.userName}, access token: ${loginData.authToken}, secret access token: ${loginData.authTokenSecret}`);
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.ios.js
        </Text>
        <Text style={styles.instructions}>
          Press Cmd+R to reload,{'\n'}
          Cmd+D or shake for dev menu
        </Text>

        <TouchableHighlight onPress={this.onLoginClick.bind(this)}>
          <View style={styles.button}>
            <Text style={styles.buttonText}>Connect with Twitter</Text>
          </View>
        </TouchableHighlight>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('TwitterLogin', () => TwitterLogin);
