package gov.ny.its.nybe.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.ControlBase;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * This class formats the Interview object as JSON in a reliable and useful
 * fashion.  For use in api consumption.
 * 
 * @author APfoltzer
 * @since August 2024
 */
@SuppressWarnings("serial")
public class InterviewObjectSerializer extends StdSerializer<Object> {
	
	ObjectMapper mapper = new ObjectMapper();
	public InterviewObjectSerializer() {
		this(null);
	}

	public InterviewObjectSerializer(Class<Object> t) {
		super(t);
	}

	/**
	 * Do the serialization magic
	 * 
	 * @param value - the Object to serialize
	 * @param jgen - the Json Generator
	 * @param provider - the SerializerProvider
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @author APfoltzer
	 */
	@Override
	public void serialize(
			Object value, JsonGenerator jgen, SerializerProvider provider) 
					throws IOException, JsonProcessingException {
		// if it is null, just write null
		if(value == null) {
			jgen.writeNull();
			return;
		}
		// if it is not an interview object, and is a primitive or native object
		// just write it out normally.
		@SuppressWarnings("rawtypes")
		Class clazz = value.getClass();
		if(!clazz.getPackageName().startsWith("com.oracle.determinations.server.interview")) {
			mapper.writeValue(jgen, value);
			return;
		}
		
		// begin
		jgen.writeStartObject();
		try {
			// All Controls are subclasses of ControlBase
			// We need to determine the "controlType" of each control
			// We add this property to each control in the JSON.
			// Knowing controlType is critical for application function.
			if(value instanceof ControlBase) {
				@SuppressWarnings("unchecked")
				XmlType controlType = (XmlType)clazz.getAnnotation(XmlType.class);
				if(controlType != null) {
					String strControlType = controlType.name();
					if(strControlType != null && strControlType.length() > 0) {
						jgen.writeStringField("controlType", strControlType);					
					}
				}	
			}
			
			// gather a list of all the fields in this and parent classes
			List<Field> allFields = new ArrayList<Field>();
			@SuppressWarnings("rawtypes")
			Class thisClazz = clazz;
			while(thisClazz != null) {
				Field[] fields = thisClazz.getDeclaredFields();
				allFields.addAll(Arrays.asList(fields));
				thisClazz = thisClazz.getSuperclass();
				if(thisClazz.equals(Object.class)) {
					break;
				}
			}
			
			// iterate all the fields.
			for(Field field: allFields) {
				if(field.isEnumConstant()) {
					// ignore
				} else {
					try {
						// attempt to make the field accessible so we can get the value
						field.setAccessible(true);
						
						// check the element annotation for a good name for the field
						XmlElement elementAnnotation = (XmlElement)field.getAnnotation(XmlElement.class);
						String varName = field.getName();
						if(elementAnnotation != null) {
							String annotationName = elementAnnotation.name();
							if(annotationName != null && !"##default".equals(annotationName)) {
								varName = annotationName;
							} 
						}
						
						// check the attribute annotation for a good name for the field
						XmlAttribute attributeAnnotation = (XmlAttribute)field.getAnnotation(XmlAttribute.class);
						if(attributeAnnotation != null) {
							String annotationName = attributeAnnotation.name();
							if(attributeAnnotation != null) {
								varName = annotationName;
							} 
						}
						
						// if there is a variable name, write it out with the value
						if(varName != null) {
							// lets not continute to call it the long winded and mildly non-inclusive
							// name of "textcontrolordatecontrolordatetimecontrol".
							// Instead, lets just call the array of controls "controls".
							if(varName.toLowerCase().equals("textcontrolordatecontrolordatetimecontrol")) {
								varName = "controls";
							}
							// will likely be a hyphenated word.  Lets use camel case
							// for ease of use when consuming in React.js (JavaScript)
							varName = convertToCamelCase(varName);
							Object subObject = field.get(value);
							
							// if the subObject is null, just write a null field
							if(subObject == null) {
								jgen.writeNullField(varName);
							} else if(subObject instanceof List) {
								// if this is a list of subObjects, lets iterate them
								jgen.writeFieldName(varName);
								jgen.writeStartArray();
								@SuppressWarnings("rawtypes")
								List list = (List)subObject;
								for(Object o: list) {
									if(!o.getClass().isEnum()) {
										// recursively call this serialize method (if not an Enum)
										this.serialize(o, jgen, provider);									
									}
								}
								jgen.writeEndArray();
							}else {
								// if this is a single object, lets write the field name prior to it
								// then recursively call this serialize method.
								jgen.writeFieldName(varName);
								this.serialize(subObject, jgen, provider);		
							}
							
						}					
					} catch(Exception exc) {
						// cant access the field value more than likely
						// so just ignore it.
					}
				}
			}
		} catch(Exception exc) {
			// TODO: implement a good logging framework and log this Exception
//			exc.printStackTrace();
		}
		
		// finish up.
		jgen.writeEndObject();
	}
	
	
	/**
	 * Converts the given text to camel case
	 * 
	 * @param text - potentially not camel case text
	 * @return camel case text seeded by the parameter.
	 * @author APfoltzer
	 * @since August 2024
	 */
	private String convertToCamelCase(String text) {
		String[] words = text.split("[\\W_]+");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
		    String word = words[i];
		    if (i == 0) {
		        word = word.isEmpty() ? word : word.toLowerCase();
		    } else {
		        word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();      
		    }
		    builder.append(word);
		}
		return builder.toString();
	}
}
