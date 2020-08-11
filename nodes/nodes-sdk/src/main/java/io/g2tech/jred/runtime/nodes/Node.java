package io.g2tech.jred.runtime.nodes;

import java.util.UUID;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.g2tech.jred.runtime.nodes.flows.Flow;
import io.g2tech.jton.JtonArray;
import io.g2tech.jton.JtonElement;
import io.g2tech.jton.JtonObject;

/**
 * The Node object is the heart of a J-RED flow. It is the object that all nodes
 * extend.
 * <p>
 * 
 * 
 * @author George.Georgopoulos 
 */
public class Node {

  private static final Logger logger = LoggerFactory.getLogger(Node.class);

  public static final String ID = "id";
  public static final String TYPE = "type";
  public static final String Z = "z";
  public static final String NAME = "name";
  public static final String ALIAS = "_alias";
  public static final String MSGID = "_msgid";
  public static final String WIRES = "wires";

  private final String id;
  private final String type;
  private final String z;
  private final String name;
  private final String _alias;
  
  private final Flow _flow;
  private final boolean _asyncDelivery;

  private JtonArray wires;
  private String _wire;

  private int _wireCount;

  private Consumer<JtonElement> send = null;

  private final Consumer<JtonElement> SEND = (msg) -> {
    send(msg);
  };

  private final Consumer<JtonElement> NOOP_SEND = (msg) -> {
    // Do nothing
  };

  protected Node(NodeConfigurer n) {
    this.id = n.getAsString(ID);
    this.type = n.getAsString(TYPE);
    this.z = n.getAsString(Z);
    this.name = n.getAsString(NAME, null);
    this._alias = n.getAsString(ALIAS, null);
    this._flow = n.getFlow();
    this._asyncDelivery = this._flow != null 
        ? this._flow.isAsyncMessageDelivery() : true;
    this.updateWires(n.getAsJtonArray(WIRES, true));
  }

  /**
   * Update the wiring configuration for this node.
   * <p>
   * We try to optimize the message handling path. To do this there are three
   * cases to consider:
   * <ul>
   * <li>this node is wired to nothing. In this case we replace node.send with a
   * NO-OP function.</li>
   * <li>this node is wired to one other node. In this case we set `this._wire` as
   * reference to the node it is wired to. This means we avoid unnecessary
   * iterations over what would otherwise be a 1-element array.</li>
   * <li>this node is wired to multiple things. The normal node.send processing of
   * this.wires applies.</li>
   * </ul>
   * 
   * @param wires the new wiring configuration
   */
  private void updateWires(JtonArray wires) {
    logger.debug("updateWires:  Update the wiring configuration for this node '{}'", id);

    this.wires = wires != null ? wires : new JtonArray();
    this._wire = null;

    int wc = 0;
    for (JtonElement w : this.wires) {
      wc += w.getAsJtonArray().size();
    }
    this._wireCount = wc;
    if (wc == 0) {
      // with nothing wired to the node, no-op send
      this.send = NOOP_SEND;
    } else {
      this.send = SEND;
      if (this.wires.size() == 1 && this.wires.get(0).getAsJtonArray().size() == 1) {
        // Single wire, so we can shortcut the send when a single message is sent
        this._wire = this.wires.get(0).getAsJtonArray().getAsString();
      }
    }
  }

  /**
   * Send a message to the nodes wired.
   * 
   * @param msg A message or array of messages to send.
   */
  protected void send(JtonElement msg) {
    logger.trace("send: Send a message to the nodes wired {}", msg);

    Node node;

    if (msg == null || msg.isJtonNull()) {
      return;
    } else if (!msg.isJtonArray()) {
      if (this._wire != null) {
        // A single message and a single wire on output 0
        JtonObject _msg = msg.getAsJtonObject();
        if (!_msg.has(MSGID)) {
          _msg.addProperty(MSGID, UUID.randomUUID().toString());
        }
        // TODO metric send
        node = this._flow.getNode(this._wire);
        if (node != null) {
          node.receive(_msg);
        }
        return;
      } else {
        msg = new JtonArray().add(msg);
      }
    } else {
      msg = new JtonArray().add(msg);
    }

    int numOutputs = this.wires.size();

    // Build a list of send events so that all cloning is done before
    // any calls to node.receive
    JtonArray sendEvents = new JtonArray();

    String sendMessageId = null;

    JtonArray _msg = msg.getAsJtonArray();

    // for each output of node eg. [msgs to output 0, msgs to output 1, ...]
    for (int i = 0; i < numOutputs; i++) {
      JtonArray wires = this.wires.get(i).getAsJtonArray(); // wires leaving output i
      if (i < _msg.size()) {
        JtonArray msgs = _msg.get(i).isJtonArray() ? _msg.get(i).getAsJtonArray() : null; // msgs going to output i
        if (msgs != null) {
          if (!msgs.isJtonArray()) {
            msgs = new JtonArray().add(msgs);
          }

          int k = 0;

        }
      }
    }

    if (sendMessageId != null) {
      sendMessageId = UUID.randomUUID().toString();
    }
    // TODO metric

    for (int i = 0; i < sendEvents.size(); i++) {
      JtonObject ev = sendEvents.get(i).getAsJtonObject();
      if (!ev.has(MSGID)) {

      }
    }
  }

  /**
   * Receive a message.
   * <p>
   * This will emit the `input' event with the provided message.
   * 
   * @param msg
   */
  protected final void receive(JtonObject msg) {
    if (msg == null) {
      msg = new JtonObject();
    }
    if (!msg.has(MSGID)) {
      msg.addProperty(MSGID, UUID.randomUUID().toString());
    }
    this.emit("input", msg);
  }
  
  private void emit(String event, JtonObject msg) {
    if ("input".equals(event)) {
      
    } else {
      
    }
  }

  /**
   * Called when the node is being stopped.
   * 
   * @param removed Whether the node has been removed, or just being stopped
   * 
   */
  public void close(boolean removed) {
    logger.debug("close: Called when the node is being stopped: {}", removed);
  }

}
