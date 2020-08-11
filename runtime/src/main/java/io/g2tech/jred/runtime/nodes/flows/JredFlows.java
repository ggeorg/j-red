package io.g2tech.jred.runtime.nodes.flows;

import io.g2tech.jred.runtime.JredRuntime;
import io.g2tech.jton.JtonObject;

public class JredFlows {

  private final JredRuntime runtime;

  private JtonObject activeConfig;

  public JredFlows(JredRuntime runtime) {
    this.runtime = runtime;
  }

  public JredRuntime getRuntime() {
    return runtime;
  }

  private void loadFlows() {

  }

  /**
   * Gets the current flow configuration.
   * 
   * @return the active flow configuration
   */
  public JtonObject getFlows() {
    return activeConfig;
  }

  /**
   * Sets the current active configuration.
   * 
   * @param _config      the configuration to enable
   * @param _credentials
   * @param type         the type of deployment to do: full (default), nodes,
   *                     flows, load
   * @param forceStart
   * 
   * @return a promise for the saving/starting of the new flow
   */
  public void setFlows(JtonObject _config, JtonObject _credentials, String type, boolean forceStart) {

  }
}
