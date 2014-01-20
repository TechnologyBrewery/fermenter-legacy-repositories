package org.tigris.atlas.mda.metadata.element;

public interface Enum {

	/**
	 * @return Returns the name.
	 */
	public String getName();
	
	/**
	 * @return Returns the value.
	 */
	public String getValue();
	
	public boolean hasValue();

}