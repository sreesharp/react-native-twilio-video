import React, { Component } from 'react';
const TwilioSDK = require('react-native-twilio-video');

import {
  AppRegistry,
  StyleSheet,
  Text,
  Button,
  View,
  Dimensions,
  TouchableHighlight,
  Image,
  Modal,
  TextInput,
} from 'react-native';

const {
  Video,
  VideoView,
} = TwilioSDK;

var { height, width} = Dimensions.get('window');

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  fullView: {
    height: height * 5/8, 
    width: width - 20,
    margin: 10,
  },
  thumbnailView: {
    height: height * 1/4, 
    width: width / 2,
    marginLeft: 10,
    marginRight: 10,
  },
  bottomView: {
    flex: 1,
    flexDirection: 'row',
  },
  controlsView: {
    flex: 1,
  },
  controlButton: {
    height: 50,
    width: 50,
    padding: 10,
    margin: 10,
  },
  modalBackground: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#88888888',
  },
  modalDialog: {
    width: 250,
    height: 150,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'white',
    borderRadius: 10,
  },
});

export default class quickstart extends Component {

  constructor(props) {
    super(props);
    this.state = {
      frontCamera: true,
      videoOn: true,
      audioOn: true,
      callOff: true,
      modalVisible: false,
      text: 'Room Name',
    };
  }

  _setModalVisible(visible) {
    this.setState({modalVisible: visible});
  }

  _onConnect() {
    this.setState({modalVisible: false});
    //TODO: Get the access token from server
    const ACCESS_TOKEN = "ACCESS_TOKEN";
    Video.startCall({roomName: "Sreesharp", accessToken: ACCESS_TOKEN});
  }
  
  _onCallButtonPress = () => {
    this.setState({modalVisible: true});
  }

  _onCameraButtonPress = () => {

  }

  _onAudioButtonPress = () => {

  }

  _onVideoButtonPress = () => {

  }

  renderCallButton() {
    return (
      <TouchableHighlight onPress={this._onCallButtonPress}>
        <Image
          style={styles.controlButton}
          source={this.state.callOff ? require('./images/start_call.png'): require('./images/end_call.png')}
        />
      </TouchableHighlight>
    );
  }

  renderVideoButton() {
    return (
      <TouchableHighlight onPress={this._onVideoButtonPress}>
        <Image
          style={styles.controlButton}
          source={this.state.videoOn ? require('./images/video_on.png') : require('./images/video_off.png')}
        />
      </TouchableHighlight>
    );
  }

  renderAudioButton() {
    return (
      <TouchableHighlight onPress={this._onAudioButtonPress}>
        <Image
          style={styles.controlButton}
          source={this.state.audioOn ? require('./images/audio_on.png') : require('./images/audio_off.png')}
        />
      </TouchableHighlight>
    );
  }

  renderCameraButton() {
    return (
      <TouchableHighlight onPress={this._onCameraButtonPress}>
        <Image
          style={styles.controlButton}
          source={require('./images/switch_camera.png')}
        />
      </TouchableHighlight>
    );
  }

  render() {
    return (
      <View style={styles.container}>
        <Modal
          animationType={"fade"}
          transparent={true}
          visible={this.state.modalVisible}
          onRequestClose={() => {alert("Trying to connect")}}
          >
         <View style={styles.modalBackground}>
          <View style={styles.modalDialog}>
            <TextInput
              style={{height: 40, width: 200}}
              onChangeText={(text) => this.setState({text})}
              value={this.state.text}
            />
            <Button
              onPress={() => {this._onConnect()}}
              title="Connect"
              color="#841584"
            />
          </View>
         </View>
        </Modal>
        <VideoView isLocal={true} style={styles.fullView}/>
        <View style={styles.bottomView}>
          <VideoView isLocal={false} style={styles.thumbnailView}/>
          <View style={styles.controlsView}>
            <View style={styles.bottomView}>
              {this.renderCallButton()}
              {this.renderCameraButton()}
            </View>
            <View style={styles.bottomView}>
              {this.renderVideoButton()}
              {this.renderAudioButton()}
            </View>
          </View>
        </View>
      </View>
    );
  }
}
