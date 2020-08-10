package io.jred.runtime.nodes;

import io.jred.runtime.nodes.flows.Flow;
import io.jton.JtonArray;
import io.jton.JtonObject;

public interface NodeConfigurer {

	JtonArray getAsJtonArray(String propertyName);

	JtonArray getAsJtonArray(String propertyName, boolean createIfNull);

	JtonArray getAsJtonArray(String propertyName, JtonArray defaultValue);

	JtonObject getAsJtonObject(String propertyName);

	JtonObject getAsJtonObject(String propertyName, boolean createIfNull);

  JtonObject getAsJtonObject(String propertyName, JtonObject defaultValue);
  
  String getAsString(String propertyName);
  
  String getAsString(String propertyName, String defaulValue);
  
  Flow getFlow();

}
