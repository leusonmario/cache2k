package org.cache2k.experimentalApi;

/*
 * #%L
 * cache2k core package
 * %%
 * Copyright (C) 2000 - 2016 headissue GmbH, Munich
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.cache2k.CacheEntry;

import java.util.EventListener;
import java.util.concurrent.Executor;

/**
 * @author Jens Wilke
 */
public interface AsyncCacheLoader<K,V> {

  void get(K key, V value, CacheEntry<K, V> entry, Callback<V> callback, Executor ex);

  /**
   * Callback for async cache load.
   *
   * @author Jens Wilke
   */
  interface Callback<V> extends EventListener {

    void onLoadSuccess(V value);

    void onLoadFailure(Throwable t);

  }

}
