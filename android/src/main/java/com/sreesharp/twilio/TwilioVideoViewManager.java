
package com.sreesharp.twilio;

import android.util.Log;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.twilio.video.CameraCapturer;
import com.twilio.video.LocalMedia;
import com.twilio.video.LocalVideoTrack;
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