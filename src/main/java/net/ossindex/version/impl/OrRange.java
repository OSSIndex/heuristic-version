/**
 *	Copyright (c) 2016 Vör Security Inc.
 *	All rights reserved.
 *	
 *	Redistribution and use in source and binary forms, with or without
 *	modification, are permitted provided that the following conditions are met:
 *	    * Redistributions of source code must retain the above copyright
 *	      notice, this list of conditions and the following disclaimer.
 *	    * Redistributions in binary form must reproduce the above copyright
 *	      notice, this list of conditions and the following disclaimer in the
 *	      documentation and/or other materials provided with the distribution.
 *	    * Neither the name of the <organization> nor the
 *	      names of its contributors may be used to endorse or promote products
 *	      derived from this software without specific prior written permission.
 *	
 *	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *	ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *	DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.ossindex.version.impl;

import net.ossindex.version.IVersion;
import net.ossindex.version.IVersionRange;

/** Two ranges anded together
 * 
 * @author Ken Duck
 *
 */
public class OrRange extends LogicalRange implements IVersionRange
{

	/**
	 * 
	 * @param operator
	 * @param version
	 */
	public OrRange(IVersionRange range1, IVersionRange range2)
	{
		this.range1 = range1;
		this.range2 = range2;
	}

	/*
	 * (non-Javadoc)
	 * @see net.ossindex.version.IVersionRange#contains(net.ossindex.version.IVersion)
	 */
	@Override
	public boolean contains(IVersion version)
	{
		return range1.contains(version) || range2.contains(version);
	}

	/*
	 * (non-Javadoc)
	 * @see net.ossindex.version.impl.IVersionRange#isAtomic()
	 */
	@Override
	public boolean isAtomic()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.ossindex.version.impl.IVersionRange#isSimple()
	 */
	@Override
	public boolean isSimple()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.ossindex.version.impl.IVersionRange#getMinimum()
	 */
	@Override
	public IVersion getMinimum()
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see net.ossindex.version.impl.IVersionRange#getMaximum()
	 */
	@Override
	public IVersion getMaximum()
	{
		throw new UnsupportedOperationException();
	}

	public String getOperator()
	{
		return "|";
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.ossindex.version.IVersionRange#intersects(net.ossindex.version.IVersionRange)
	 */
	@Override
	public boolean intersects(IVersionRange yourRange)
	{
		return range1.intersects(yourRange) || range2.intersects(yourRange);
	}
}
