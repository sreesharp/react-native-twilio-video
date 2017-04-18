'use strict';

import {
  NativeModules,
  NativeEventEmitter,
} from 'react-native';

const { RNTwilioVideo } = NativeModules;

const NativeAppEventEmitter = new NativeEventEmitter(RNTwilioVideo);

const _eventHandlers = {
    onRoomConnected: new Map(),
    onRoomConnectFailure: new Map(),
    onRoomDisconnected: new Map(),
    onParticipantConnected: new Map(),
    onParticipantDisconnected: new Map(),
    onRecordingStarted: new Map(),
    onRecordingStopped: new Map(),
};

const Video = {
  /**
   * Connect to given room using given token
   */
  connect({roomName, accessToken}) {
    RNTwilioVideo.connect(accessToken, roomName);
  },

  /**
   * Disconnect from the room
   */
  disconnect(roomName) {
    RNTwilioVideo.disconnect(roomName);
  },

  /**
   * Set Log level
   */
  setLogLevel(level) {
    console.log('Not yet supported');
    // RNTwilioVideo.setLogLevel(level);
  },

  addEventListener (type, handler) {
    if (_eventHandlers[type].has(handler)) {
        return
    }
    _eventHandlers[type].set(handler, NativeAppEventEmitter.addListener(
        type, (rtn) => {
            handler(rtn)
        }
    ))
  },
  removeEventListener (type, handler) {
    if (!_eventHandlers[type].has(handler)) {
        return
    }
    _eventHandlers[type].get(handler).remove()
    _eventHandlers[type].delete(handler)
  }

}
module.exports = Video;
