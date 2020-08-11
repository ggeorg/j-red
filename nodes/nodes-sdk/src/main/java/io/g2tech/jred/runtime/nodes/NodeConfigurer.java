package io.g2tech.jred.runtime.nodes;

import io.g2tech.jred.runtime.nodes.flows.Flow;
import io.g2tech.jton.JtonArray;
import io.g2tech.jton.JtonObject;

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
