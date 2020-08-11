package io.g2tech.jred.runtime;

import io.g2tech.jred.runtime.storage.Storage;

public interface JredRuntimeConfigurer {

  Storage getStorage();
}
