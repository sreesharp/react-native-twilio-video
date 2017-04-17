package com.sreesharp.twilio;

import com.twilio.video.CameraCapturer.CameraSource;
import com.twilio.video.LocalMedia;
import com.twilio.video.Participant;
import com.twilio.video.Room;
import com.twilio.video.VideoView;

import java.util.HashMap;

public final class Utility {
    private static HashMap<String, Room> roomMap = new HashMap<String, Room>();
    private static HashMap<String, Participant> participantMap = new HashMap<String, Participant>();
    private static LocalMedia localMedia;
    private static CameraSource cameraSource = CameraSource.FRONT_CAMERA;
    // TODO: Currently supporting only one remote participant
    // private static HashMap<String, VideoView> remoteViewMap = new HashMap<String, VideoView>();
    private static VideoView remoteView;

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

    public static Participant getParticipant(String participantId) {
        if (participantMap.containsKey(participantId))
            return (Participant)participantMap.get(participantId);
        else
            return null;
    }
    
    public static void addParticipant(String participantId, Participant participant) {
        participantMap.put(participantId, participant);
    }
    
    public static void removeParticipant(String participantId) {
        if(participantMap.containsKey(participantId))
            participantMap.remove(participantId);
    }

    //TODO: Currently supporting only one remote participant, hence not using the remoteViewMap
    public static void setRemoteVideoView(VideoView view) {
        remoteView = view;
    }

    public static VideoView getRemoteVideoView() {
        return remoteView;
    }

}