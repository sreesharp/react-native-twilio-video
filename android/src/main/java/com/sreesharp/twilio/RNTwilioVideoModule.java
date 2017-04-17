
package com.sreesharp.twilio;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.twilio.video.CameraCapturer.CameraSource;
import com.twilio.video.ConnectOptions;
import com.twilio.video.Participant;
import com.twilio.video.Room;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;

import java.util.HashMap;
import java.util.Map;


public class RNTwilioVideoModule extends ReactContextBaseJavaModule {

    private WritableMap params;

    public RNTwilioVideoModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNTwilioVideo";
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        return constants;
    }

    @ReactMethod
    public void connect(String accessToken, String roomName) {
        ConnectOptions connectOptions = new ConnectOptions.Builder(accessToken)
            .roomName(roomName)
            .localMedia(Utility.getLocalMedia())
            .build();
        Room room = Video.connect(getReactApplicationContext(), connectOptions, roomListener());
        if(room != null)
            Utility.addRoom(roomName, room);
    }

    @ReactMethod
    public void disconnect(String roomName) {
        Room room = Utility.getRoom(roomName);
        if(room != null)
            room.disconnect();
    }


    @ReactMethod
    public void switchCamera() {
        if (Utility.getCameraSource() == CameraSource.FRONT_CAMERA)
            Utility.setCameraSource(CameraSource.BACK_CAMERA);
        else
            Utility.setCameraSource(CameraSource.FRONT_CAMERA);
        
        //TODO: Notify all the local VideoView components
    }

    private void addParticipant(Participant participant) {
        
        //Add the participants to list
        Utility.addParticipant(participant.getIdentity(), participant);
        refreshParticipantsVideo(participant); //TODO: Use this later - refreshParticipantsVideo();

        //TODO: Start listening for participant media events
        // participant.getMedia().setListener(mediaListener());
    }

    private void refreshParticipantsVideo( Participant participant) {
        //TODO: Get all the participants, and connect them to available remote video views
        // Currently just using the most recent participant
        // Participant participant = Utility.getParticipant(id);

        if (participant.getMedia().getVideoTracks().size() > 0) {
            VideoTrack videoTrack = participant.getMedia().getVideoTracks().get(0);
            VideoView remoteView = Utility.getRemoteVideoView();
            remoteView.setMirror(false);
            videoTrack.addRenderer(remoteView);
        }
    }

      /*
     * Room events listener
     */
    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {
                params = Arguments.createMap();
                params.putString("roomName", room.getName());
                sendEvent("onRoomConnected", params);

                for (Map.Entry<String, Participant> entry : room.getParticipants().entrySet()) {
                    addParticipant(entry.getValue());
                    break;
                } 
            }

            @Override
            public void onConnectFailure(Room room, TwilioException e) {
                params = Arguments.createMap();
                params.putString("err",  e.getMessage());
                sendEvent("onRoomConnectFailure", params);
            }

            @Override
            public void onDisconnected(Room room, TwilioException e) {
                params = Arguments.createMap();
                params.putString("err",  e.getMessage());
                sendEvent("onRoomDisconnected", params);

                //TODO: Notify other VideoViews
            }

            @Override
            public void onParticipantConnected(Room room, Participant participant) {
                params = Arguments.createMap();
                params.putString("id",  participant.getIdentity());
                sendEvent("onParticipantConnected", params);
                addParticipant(participant); 
            }

            @Override
            public void onParticipantDisconnected(Room room, Participant participant) {
                params = Arguments.createMap();
                params.putString("id",  participant.getIdentity());
                sendEvent("onParticipantDisconnected", params);

                //TODO: removeParticipant(participant);
            }

            @Override
            public void onRecordingStarted(Room room) {
                params = Arguments.createMap();
                params.putString("roomName",  room.getName());
                sendEvent("onRecordingStarted", params);
            }

            @Override
            public void onRecordingStopped(Room room) {
                params = Arguments.createMap();
                params.putString("roomName",  room.getName());
                sendEvent("onRecordingStopped", params);
            }
        };
    }

}
