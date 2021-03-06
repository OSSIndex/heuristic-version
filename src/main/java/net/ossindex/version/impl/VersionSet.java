/**
 * Copyright (c) 2016 Vör Security Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.ossindex.version.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import net.ossindex.version.IVersion;
import net.ossindex.version.IVersionRange;

/** An version set is a range where all the contained values are
 * explicit ranges. 
 *
 * @author Ken Duck
 *
 */
public class VersionSet
    extends AbstractCommonRange
    implements Iterable<IVersion>
{
  private String type;

  private boolean hasErrors = false;

  /**
   * Used for both atomic and simple versions
   */
  private SortedSet<IVersion> set = new TreeSet<IVersion>();

  public VersionSet()
  {
  }

  public VersionSet(IVersion version)
  {
    set.add(version);
  }

  /*
   * (non-Javadoc)
   * @see net.ossindex.version.IVersionRange#contains(net.ossindex.version.IVersion)
   */
  @Override
  public boolean contains(IVersion version)
  {
    return set.contains(version);
  }

  /*
   * (non-Javadoc)
   * @see net.ossindex.version.impl.IVersionRange#isAtomic()
   */
  @Override
  public boolean isDiscrete()
  {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see net.ossindex.version.impl.IVersionRange#isSimple()
   */
  @Override
  public boolean isSimple() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see net.ossindex.version.impl.IVersionRange#getMinimum()
   */
  @Override
  public IVersion getMinimum()
  {
    if (set.isEmpty()) {
      return new VersionImpl(0,0,0);
    }
    return set.first();
  }

  /*
   * (non-Javadoc)
   * @see net.ossindex.version.impl.IVersionRange#getMaximum()
   */
  @Override
  public IVersion getMaximum()
  {
    if (set.isEmpty()) {
      return new VersionImpl(0,0,0);
    }
    return set.last();
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    for (Iterator<IVersion> it = set.iterator(); it.hasNext(); ) {
      IVersion version = it.next();
      sb.append(version);
      if (it.hasNext()) {
        sb.append(",");
      }
    }
    return sb.toString();
  }

  @Override
  public String toMavenString()
  {
    StringBuffer sb = new StringBuffer();
    for (Iterator<IVersion> it = set.iterator(); it.hasNext(); ) {
      IVersion version = it.next();
      sb.append("[" + version + "]");
      if (it.hasNext()) {
        sb.append(",");
      }
    }
    return sb.toString();
  }

  /** Add another version to the set
   *
   * @param version
   */
  public void add(IVersion version)
  {
    set.add(version);
  }

  /*
   * (non-Javadoc)
   * @see net.ossindex.version.IVersionRange#intersects(net.ossindex.version.IVersionRange)
   */
  @Override
  public boolean intersects(IVersionRange yourRange)
  {
    // The set has explicit versions, so just check these against the supplied range
    for (IVersion version : set) {
      if (yourRange.contains(version)) {
        return true;
      }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<IVersion> iterator()
  {
    return set.iterator();
  }

  /** Get the max range, the min range, and then return a range based on those
   * values. *UNLESS* there are only a limited number of ranges here, in which case
   * return them all.
   *
   * @see net.ossindex.version.IVersionRange#getSimplifiedRange()
   */
  @Override
  public IVersionRange getSimplifiedRange() {
    if (set.size() < 5) {
      return this;
    }
    IVersion max = null;
    IVersion min = null;

    for (IVersion v : set) {
      if (max == null) {
        max = v;
      }
      else {
        if (v.compareTo(max) > 0) {
          max = v;
        }
      }
      if (min == null) {
        min = v;
      }
      else {
        if (v.compareTo(min) < 0) {
          min = v;
        }
      }
    }

    return new BoundedVersionRange(min, max);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setHasErrors(boolean b) {
    hasErrors = b;
  }

  public boolean hasErrors() {
    return hasErrors;
  }

  public Collection<IVersion> getVersions() {
    return set;
  }
}
