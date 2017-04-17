
package com.sreesharp.twilio;

import android.widget.Toast;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

import java.util.Map;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.twilio.video.RoomState;
import com.twilio.video.Video;
import com.twilio.video.VideoRenderer;
import com.twilio.video.TwilioException;
import com.twilio.video.AudioTrack;
import com.twilio.video.CameraCapturer;
import com.twilio.video.CameraCapturer.CameraSource;
import com.twilio.video.ConnectOptions;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalMedia;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.Media;
import com.twilio.video.Participant;
import com.twilio.video.Room;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;


public class RNTwilioVideoModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private static final String DURATION_LONG_KEY = "LONG";

    private Room room;

    public RNTwilioVideoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTwilioVideo";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    @ReactMethod
    public void startCall(String accessToken, String roomName) {
        ConnectOptions connectOptions = new ConnectOptions.Builder(accessToken)
            .roomName(roomName)
            .localMedia(Utility.getLocalMedia())
            .build();
        room = Video.connect(getReactApplicationContext(), connectOptions, roomListener());
        Utility.addRoom(roomName, room);
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

        //TODO: Use this later - refreshParticipantsVideo();
        refreshParticipantsVideo(participant);

        //TODO: Start listening for participant media events
        // participant.getMedia().setListener(mediaListener());
    }

    private void refreshParticipantsVideo( Participant participant) {
        //TODO: Get all the participants, and connect them to available remote videow views
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
                Toast.makeText(getReactApplicationContext(), "Connected to " + room.getName(), Toast.LENGTH_LONG).show();

                for (Map.Entry<String, Participant> entry : room.getParticipants().entrySet()) {
                    addParticipant(entry.getValue());
                    break;
                } 
            }

            @Override
            public void onConnectFailure(Room room, TwilioException e) {
                Toast.makeText(getReactApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDisconnected(Room room, TwilioException e) {
                Toast.makeText(getReactApplicationContext(), "Disconnected", Toast.LENGTH_LONG).show();
                /*
                videoStatusTextView.setText("Disconnected from " + room.getName());
                VideoActivity.this.room = null;
                // Only reinitialize the UI if disconnect was not called from onDestroy()
                if (!disconnectedFromOnDestroy) {
                    setAudioFocus(false);
                    intializeUI();
                    moveLocalVideoToPrimaryView();
                } */
            }

            @Override
            public void onParticipantConnected(Room room, Participant participant) {
                Toast.makeText(getReactApplicationContext(), "onParticipantConnected", Toast.LENGTH_LONG).show();
                addParticipant(participant); 
            }

            @Override
            public void onParticipantDisconnected(Room room, Participant participant) {
                // removeParticipant(participant);
            }

            @Override
            public void onRecordingStarted(Room room) {
                /*
                 * Indicates when media shared to a Room is being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                // Log.d(TAG, "onRecordingStarted");
            }

            @Override
            public void onRecordingStopped(Room room) {
                /*
                 * Indicates when media shared to a Room is no longer being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                // Log.d(TAG, "onRecordingStopped");
            }
        };
    }

}
