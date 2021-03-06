package org.cache2k.jcache.provider;

/*
 * #%L
 * cache2k JCache JSR107 implementation
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

import javax.cache.Cache;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.management.CacheMXBean;

/**
 * @author Jens Wilke
 */
public class CacheJmxConfiguration implements CacheMXBean {

  Cache cache;

  public CacheJmxConfiguration(Cache cache) {
    this.cache = cache;
  }

  @Override
  public String getKeyType() {
    return configuration().getKeyType().getName();
  }

  @Override
  public String getValueType() {
    return configuration().getValueType().getName();
  }

  @Override
  public boolean isManagementEnabled() {
    return completeConfiguration().isManagementEnabled();
  }

  @Override
  public boolean isReadThrough() {
    return completeConfiguration().isReadThrough();
  }

  @Override
  public boolean isStatisticsEnabled() {
    return completeConfiguration().isStatisticsEnabled();
  }

  @Override
  public boolean isStoreByValue() {
    return configuration().isStoreByValue();
  }

  @Override
  public boolean isWriteThrough() {
    return completeConfiguration().isWriteThrough();
  }

  private Configuration configuration() {
    return cache.getConfiguration(Configuration.class);
  }

  CompleteConfiguration completeConfiguration() {
    return (CompleteConfiguration) cache.getConfiguration(CompleteConfiguration.class);
  }

}
