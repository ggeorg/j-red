package io.g2tech.jred.runtime.storage;

import io.g2tech.jton.JtonObject;

public interface Storage {

  JtonObject getFlows();
  
  void saveFlows(JtonObject config);
  
  JtonObject getCredentials();
  
  void saveCredentials(JtonObject credentails);
  
  JtonObject getSettings();
  
  void saveSettings(JtonObject settings);
  
  // sessions
  
  /* Library Functions */
  
  Object getLibraryEntry(String type, String path);
  
  // saveLibraryEntry: function(type, path, meta, body)
}
