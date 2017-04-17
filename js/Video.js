'use strict';

import {
  NativeModules,
  NativeEventEmitter,
} from 'react-native';

const { RNTwilioVideo } = NativeModules;

let Video = {

  startCall({roomName, accessToken}) {
    RNTwilioVideo.startCall(accessToken,roomName)
  }
  

}
module.exports = Video;