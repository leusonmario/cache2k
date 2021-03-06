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
 * If object implements {@link ValueWithNextRefreshTime} then the next refresh time
 * is fetched from the object. If not, the default linger time is used by the cache.
 *
 * @author Jens Wilke; created: 2013-05-02
 * @deprecated Use {@link org.cache2k.ValueWithExpiryTime#AUTO_EXPIRY}
 */
public final class AutoEntryRefreshController<T> implements RefreshController<T> {

  public static final AutoEntryRefreshController INSTANCE = new AutoEntryRefreshController();

  @Override
  public long calculateNextRefreshTime(
    T _oldObject,
    T _newObject,
    long _timeOfLastRefresh, long now) {
    if (_newObject instanceof ValueWithNextRefreshTime) {
      return ((ValueWithNextRefreshTime) _newObject).getNextRefreshTime();
    }
    return Long.MAX_VALUE;
  }

}
