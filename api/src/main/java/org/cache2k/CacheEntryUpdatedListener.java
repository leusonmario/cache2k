package org.cache2k;

/*
 * #%L
 * cache2k API only package
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

/**
 * Fires in case a cache entry is mutated. This happens on {@link Cache#put} and its variants
 * if a previous mapping exists, or if a value is reloaded or refreshed after expiry.
 *
 * @author Jens Wilke
 */
public interface CacheEntryUpdatedListener<K, V> extends CacheEntryListener<K,V> {

  void onEntryUpdated(Cache<K,V> cache, CacheEntry<K, V> previousEntry, CacheEntry<K, V> currentEntry);

}
