'use strict';

import { PropTypes } from 'react';
import { requireNativeComponent, View } from 'react-native';

var iface = {
  name: 'VideoView',
  propTypes: {
    isLocal: PropTypes.bool,  
    ...View.propTypes // include the default view properties
  },
};

module.exports = requireNativeComponent('TwilioVideoView', iface);
