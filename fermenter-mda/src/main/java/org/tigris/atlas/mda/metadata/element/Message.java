package org.tigris.atlas.mda.metadata.element;

import java.util.Collection;

public interface Message {

	public static final String DEFAULT_LOCALE = "DEFAULT";
	
	String getKey();
	
	Collection getLocales();
	
	MessageText getSummary(String locale);
	
	MessageText getDetail(String locale);
	
}
