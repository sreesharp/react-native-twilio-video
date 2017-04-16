
package com.sreesharp.twilio;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNTwilioVideoModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNTwilioVideoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNTwilioVideo";
  }

  @ReactMethod
  public void startCall(String accessToken, String roomName) {
    AlertDialog alertDialog = new AlertDialog.Builder(reactContext).create();
    alertDialog.setTitle("Alert");
    alertDialog.setMessage("Alert message to be shown");
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    alertDialog.show();
  }

}
