package org.cache2k.storage;

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

import org.cache2k.StorageConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple storage implementation, just uses a hashmap. Usable for testing and as a
 * tiny storage implementation example.
 *
 * @author Jens Wilke; created: 2014-06-21
 */
public class ByReferenceHeapStorageImpl implements CacheStorage {

  StorageConfiguration<Void> config;
  CacheStorageContext context;
  HashMap<Object, HeapEntry> entries;

  public void open(CacheStorageContext ctx, StorageConfiguration<Void> cfg) {
    context = ctx;
    config = cfg;
    final int entryCapacity = cfg.getEntryCapacity();
    if (entryCapacity == Integer.MAX_VALUE) {
      entries = new HashMap<Object, HeapEntry>();
    } else {
      entries = new LinkedHashMap<Object, HeapEntry>(100, .75F, true) {

        @Override
        protected boolean removeEldestEntry(Map.Entry<Object, HeapEntry> _eldest) {
          if (getEntryCount() > entryCapacity) {
            context.notifyExpired(_eldest.getValue());
            return true;
          }
          return false;
        }
      };
    }

  }

  @Override
  public synchronized StorageEntry get(Object key) throws Exception {
    HeapEntry e = entries.get(key);
    return e;
  }

  @Override
  public void put(StorageEntry e) throws Exception {
    HeapEntry he = new HeapEntry();
    he.key = e.getKey();
    he.value = e.getValueOrException();
    he.updated = e.getCreatedOrUpdated();
    he.entryExpiry = e.getEntryExpiryTime();
    he.valueExpiry = e.getValueExpiryTime();
    synchronized (this) {
      entries.put(e.getKey(), he);
    }
  }

  @Override
  public synchronized boolean remove(Object key) throws Exception {
    return entries.remove(key) != null;
  }

  @Override
  public synchronized boolean contains(Object key) throws Exception {
    return entries.containsKey(key);
  }

  @Override
  public synchronized void clear() throws Exception {
    entries.clear();
  }

  @Override
  public void close() throws Exception {
    entries = null;
  }

  @Override
  public void visit(VisitContext ctx, EntryFilter f, EntryVisitor v) throws Exception {
    List<StorageEntry> l = new ArrayList<StorageEntry>();
    synchronized (this) {
      for (StorageEntry e : entries.values()) {
        if (f.shouldInclude(e.getKey())) {
          l.add(e);
        }
      }
    }
    for (StorageEntry e : l) {
      if (f.shouldInclude(e.getKey())) {
        v.visit(e);
      }
    }
  }

  @Override
  public synchronized int getEntryCount() {
    return entries.size();
  }

  static class HeapEntry implements StorageEntry {
    Object key;
    Object value;
    long updated;
    long valueExpiry;
    long entryExpiry;

    @Override
    public Object getKey() {
      return key;
    }

    @Override
    public Object getValueOrException() {
      return value;
    }

    @Override
    public long getCreatedOrUpdated() {
      return updated;
    }

    @Override
    public long getValueExpiryTime() {
      return valueExpiry;
    }

    @Override
    public long getEntryExpiryTime() {
      return entryExpiry;
    }

    @Override
    public String toString() {
      return "HeapEntry{" +
          "key=" + key +
          ", value=" + value +
          ", updated=" + updated +
          ", valueExpiry=" + valueExpiry +
          ", entryExpiry=" + entryExpiry +
          '}';
    }
  }

  public static class Provider
    extends CacheStorageProviderWithVoidConfig implements ByReferenceHeapStorage {

    @Override
    public ByReferenceHeapStorageImpl create(CacheStorageContext ctx, StorageConfiguration<Void> cfg) {
      ByReferenceHeapStorageImpl st = new ByReferenceHeapStorageImpl();
      st.open(ctx, cfg);
      return st;
    }

  }

}
