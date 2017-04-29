package net.ossindex.version.impl;

import net.ossindex.version.IVersionRange;

/** Base class for all logical ranges
 * 
 * @author Ken Duck
 *
 */
public abstract class LogicalRange
{
	/**
	 * 
	 */
	protected IVersionRange range1;
	protected IVersionRange range2;
	
	private String type;
	
	public abstract String getOperator();

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if(range1 instanceof LogicalRange) sb.append("(").append(range1).append(")");
		else sb.append(range1);
		sb.append(" ").append(getOperator()).append(" ");
		if(range2 instanceof LogicalRange) sb.append("(").append(range2).append(")");
		else sb.append(range2);
		return sb.toString();
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		if (type == null) {
			type = range1.getType();
		}
		if (type == null) {
			type = range2.getType();
		}
		return type;
	}
}
