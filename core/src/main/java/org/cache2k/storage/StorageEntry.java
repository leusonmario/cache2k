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

/**
 * @author Jens Wilke; created: 2014-03-27
 */
public interface StorageEntry {

  /** Key of the stored entry */
  Object getKey();

  /** Value of the stored entry */
  Object getValueOrException();

  /** Time the entry was last fetched or created from the original source */
  long getCreatedOrUpdated();

  /**
   * Time when the value is expired and is not allowed to be returned any more.
   * The storage needs to store this value. The storage may purge entries with
   * the time exceeded. 0 if not used and no expiry is requested.
   */
  long getValueExpiryTime();

  /**
   * Expiry time of the storage entry. After reaching this time, the entry should
   * be purged by the storage. The entry expiry time will get updated by
   * the the cache when the value is not expired and the entry is still
   * accessed. Returns 0 if not used.
   */
  long getEntryExpiryTime();

}
