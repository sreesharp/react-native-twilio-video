package com.sreesharp.twilio;

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

public final class Utility {
    private static HashMap<String, Room> roomMap = new HashMap<String, Room>();
    private static LocalMedia localMedia;
    private static CameraSource cameraSource = CameraSource.FRONT_CAMERA;

    public static Room getRoom(String roomName) {
        if (roomMap.containsKey(roomName))
            return (Room)roomMap.get(roomName);
        else
            return null;
    }
    public static void addRoom(String roomName, Room room) {
        roomMap.put(roomName, room);
    }
    public static void removeRoom(String roomName) {
        if(roomMap.containsKey(roomName))
            roomMap.remove(roomName);
    }

    public static LocalMedia getLocalMedia() {
        return localMedia;
    }
    public static void addLocalMedia(LocalMedia media) {
        localMedia = media;
    }

    public static void setCameraSource(CameraSource source) {
        cameraSource = source;
    }

    public static CameraSource getCameraSource() {
        return cameraSource;
    }

}