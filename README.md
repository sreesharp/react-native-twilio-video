
# React Native Twilio Video

React Native wrapper for Twilio Video SDK (Android and iOS)

## Current status
This component is under active development now. APIs might break in future updates. Please don't use it any production ready app yet.

## Installation
You need to install the SDK with `yarn` and configure native Android/iOS project in react native project.

### Create React Native project
First create a React Native project:

`react-native init YourApp`

### Install JavaScript packages
Install and link the react-native-twilio-video package:

`yarn add https://github.com/sreesharp/react-native-twilio-video`

`react-native link`

## Usage
Below snippet is copied from the quickstart sample. `VideoView` is responsible for rendering both local and remote video stream based on the `isLocal` attribute. `Video` is responsible for all the non-ui related methods and event callbacks.
```java
const TwilioSDK = require('react-native-twilio-video');
const {
    Video,
    VideoView,
} = TwilioSDK;

// add listeners
Video.addEventListener('onRoomConnected', roomConnectedHandler);
Video.addEventListener('onParticipantConnected', participantConnectedHandler);

....
_onConnect() {
    //Get the access token from server
    const ACCESS_TOKEN = "YOUR_TWILIO_ACCESS_TOKEN";
    Video.startCall({roomName: this.state.roomName, accessToken: ACCESS_TOKEN});
}
...
  render() {
    return (
      <View style={styles.container}>
        <VideoView isLocal={false} style={styles.fullView}/>
        <View style={styles.bottomView}>
          <VideoView isLocal={true} style={styles.thumbnailView}/>
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

```
## Quickstart sample in action
![Video Walkthrough](quickstart.gif)