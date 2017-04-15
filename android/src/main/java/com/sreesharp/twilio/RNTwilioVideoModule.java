
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
}
