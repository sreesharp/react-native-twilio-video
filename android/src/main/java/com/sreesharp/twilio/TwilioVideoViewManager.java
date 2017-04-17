
package com.sreesharp.twilio;

import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

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



public class TwilioVideoViewManager extends SimpleViewManager<VideoView> {

  public static final String REACT_CLASS = "TwilioVideoView";
  private static final String TAG = "TwilioVideoViewManager";
  private ThemedReactContext context;
  
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public VideoView createViewInstance(ThemedReactContext context) {
    this.context = context;
    return new VideoView(context);
  }

  @ReactProp(name = "isLocal")
  public void setIsLocal(VideoView videoView, Boolean isLocal) {
    if(isLocal) { //Local video
      try {
        LocalMedia localMedia = LocalMedia.create(context);
        CameraCapturer cameraCapturer = new CameraCapturer(context, Utility.getCameraSource());
        LocalVideoTrack localVideoTrack = localMedia.addVideoTrack(true, cameraCapturer);
        videoView.setMirror(true);
        localVideoTrack.addRenderer(videoView);
        Utility.addLocalMedia(localMedia);
      }
      catch(Exception ex) {
        Log.d(TAG, ex.getMessage());
      }
    } else { //Remote video
      Utility.setRemoteVideoView(videoView);
    }
  }
}