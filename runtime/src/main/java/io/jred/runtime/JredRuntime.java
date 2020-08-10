package io.jred.runtime;

/**
 * This class provides the core runtime component of J-RED.
 * 
 * @author George.Georgopoulos
 *
 */
public final class JredRuntime {

  public JredRuntime(JredRuntimeConfigurer n) {
    
  }

  /**
   * Start the runtime.
   * 
   * @return {Promise} - resolves when the runtime is started. This does not mean
   *         the flows will be running as they are started asynchronously.
   */
  public void start() {

  }

  /**
   * Stop the runtime.
   * 
   * @return {Promise} - resolves when the runtime is stopped.
   */
  public void stop() {

  }
}
