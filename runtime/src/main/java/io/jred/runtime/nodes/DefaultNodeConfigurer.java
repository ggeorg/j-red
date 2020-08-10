package io.jred.runtime.nodes;

import java.util.Objects;

import io.jred.runtime.nodes.flows.Flow;
import io.jton.JtonArray;
import io.jton.JtonElement;
import io.jton.JtonObject;

public class DefaultNodeConfigurer implements NodeConfigurer {

  public static void main(String[] args) {
    JtonObject o = new JtonObject();
    o.add("id", new JtonObject());

    DefaultNodeConfigurer n = new DefaultNodeConfigurer(null, o);
    System.out.println(n.getAsString("id"));
  }

  private final Flow flow;

  private final JtonObject config;

  public DefaultNodeConfigurer(Flow flow, JtonObject config) {
    this.flow = flow;
    this.config = config;
  }

  @Override
  public JtonArray getAsJtonArray(String propertyName) {
    return Objects.requireNonNull(getAsJtonArray(propertyName, false),
        "'" + propertyName + "' does not exist");
  }

  @Override
  public JtonArray getAsJtonArray(String propertyName, boolean createIfNull) {
    if (config.has(propertyName)) {
      JtonElement value = config.get(propertyName);
      if (value.isJtonArray()) {
        return value.getAsJtonArray();
      }
    }

    return createIfNull ? new JtonArray() : null;
  }

  @Override
  public JtonArray getAsJtonArray(String propertyName, JtonArray defaultValue) {
    if (config.has(propertyName)) {
      JtonElement value = config.get(propertyName);
      if (value.isJtonArray()) {
        return value.getAsJtonArray();
      }
    }

    return defaultValue;
  }

  @Override
  public JtonObject getAsJtonObject(String propertyName) {
    return Objects.requireNonNull(getAsJtonObject(propertyName, false),
        "'" + propertyName + "' does not exist");
  }

  @Override
  public JtonObject getAsJtonObject(String propertyName, boolean createIfNull) {
    if (config.has(propertyName)) {
      JtonElement value = config.get(propertyName);
      if (value.isJtonObject()) {
        return value.getAsJtonObject();
      }
    }

    return createIfNull ? new JtonObject() : null;
  }

  @Override
  public JtonObject getAsJtonObject(String propertyName, JtonObject defaultValue) {
    if (config.has(propertyName)) {
      JtonElement value = config.get(propertyName);
      if (value.isJtonObject()) {
        return value.getAsJtonObject();
      }
    }

    return defaultValue;
  }

  @Override
  public String getAsString(String propertyName) {
    return Objects.requireNonNull(getAsString(propertyName, null),
        "'" + propertyName + "' does not exist");
  }

  @Override
  public String getAsString(String propertyName, String defaulValue) {
    if (config.has(propertyName)) {
      JtonElement value = config.get(propertyName);
      if (value.isJtonPrimitive() && value.getAsJtonPrimitive().isString()) {
        return value.getAsString();
      }
    }

    return defaulValue;
  }

  @Override
  public Flow getFlow() {
    return flow;
  }
}
