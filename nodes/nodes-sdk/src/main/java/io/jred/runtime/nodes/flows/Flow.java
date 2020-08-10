package io.jred.runtime.nodes.flows;

import io.jred.runtime.nodes.Node;

/**
 * This interface represents a flow within the runtime. It is responsible for
 * creating, starting and stopping all nodes within the flow.
 * 
 * @author George.Georgopoulos
 *
 */
public interface Flow {

  /**
   * Get a node instance from this flow. If the node is not known to this flow,
   * pass the request up to the parent.
   * 
   * @param id
   * @return
   */
  default Node getNode(String id) {
    return getNode(id, false);
  }

  /**
   * Get a node instance from this flow. If the node is not known to this flow,
   * pass the request up to the parent.
   * 
   * @param id
   * @param cancelBubble if {@code true}, prevents the flow from passing the
   *                     request to the parent. This stops infinite loops when the
   *                     parent asked this floe for the node to begin with.
   * @return
   */
  Node getNode(String id, boolean cancelBubble);

  boolean isAsyncMessageDelivery();
  
}
