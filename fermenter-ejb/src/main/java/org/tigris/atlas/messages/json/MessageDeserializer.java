package org.tigris.atlas.messages.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.tigris.atlas.messages.Message;
import org.tigris.atlas.messages.MessageUtils;
import org.tigris.atlas.messages.Severity;

/**
 * De-serializes the given JSON content, which is expected to be generated by {@link MessageSerializer}, into it's
 * corresponding {@link Message} object representation.
 */
public class MessageDeserializer extends JsonDeserializer<Message> {

	@Override
	public Message deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String key = null;
		Severity severity = null;
		List<String> properties = new ArrayList<String>();
		List<Object> inserts = new ArrayList<Object>();

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = jp.getCurrentName();
			if ("key".equals(fieldName)) {
				key = jp.nextTextValue();
			} else if ("severity".equals(fieldName)) {
				String severityStr = jp.nextTextValue();
				if (StringUtils.isNotEmpty(severityStr)) {
					severity = Severity.valueOf(severityStr);
				}
			} else if ("properties".equals(fieldName)) {
				jp.nextToken();
				while (jp.nextToken() != JsonToken.END_ARRAY) {
					properties.add(jp.getText());
				}
			} else if ("inserts".equals(fieldName)) {
				jp.nextToken();
				while (jp.nextToken() != JsonToken.END_ARRAY) {
					inserts.add(jp.getText());
				}
			}
		}
		return MessageUtils.createMessage(key, severity, properties.toArray(new String[properties.size()]),
				inserts.toArray(new Object[inserts.size()]));
	}

	protected void handleRequiredFieldMissing(String fieldName) throws IOException {
		throw new IOException("Required field [" + fieldName + "] from serialized Message JSON missing");
	}

}
