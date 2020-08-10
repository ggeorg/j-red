package io.jred.runtime;

import io.jred.runtime.storage.Storage;

public interface JredRuntimeConfigurer {

  Storage getStorage();
}
