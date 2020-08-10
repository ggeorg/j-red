package io.jred.runtime.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jton.JtonObject;

public class LocalFileSystem implements Storage {
  private static final Logger logger = LoggerFactory.getLogger(LocalFileSystem.class);
  
  //private final String 
  
  public LocalFileSystem(LocalFilesystemConfigurer conf) {
    
  }

  @Override
  public JtonObject getFlows() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void saveFlows(JtonObject config) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public JtonObject getCredentials() {
    return null;
  }

  @Override
  public void saveCredentials(JtonObject credentails) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public JtonObject getSettings() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void saveSettings(JtonObject settings) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Object getLibraryEntry(String type, String path) {
    // TODO Auto-generated method stub
    return null;
  }

}
