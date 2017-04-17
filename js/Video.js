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
   * Start the video call using given token and room name
   * @Deprecated use connect method
   */
  startCall({roomName, accessToken}) {
    RNTwilioVideo.startCall(accessToken, roomName);
  },

  /**
   * Connect to given room using given token
   */
  connect({roomName, accessToken}) {
    RNTwilioVideo.startCall(accessToken, roomName);
  },

  /**
   * Disconnect from the room
   */
  disconnect(roomName) {
    console.log('Not yet supported');
    // RNTwilioVideo.disconnect(roomName);
    // TODO: internally call the room.disconnect
  },

  /**
   * Release the room
   */
  release(roomName) {
    console.log('Not yet supported');
    // RNTwilioVideo.releasae(roomName);
    // TODO: internally call Video.release
  },

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