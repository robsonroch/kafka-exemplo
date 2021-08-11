package br.com.alura.ecommerce;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class CustomDeserializer<T extends Serializable> implements Deserializer<T> {

    private ObjectMapper objectMapper = new ObjectMapper();
	private Class<T> type;
    public static final String VALUE_CLASS_NAME_CONFIG = "br.com.alura.ecommerce.type_config";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    	String typeName = String.valueOf(configs.get(VALUE_CLASS_NAME_CONFIG));
    	try {
			this.type = (Class<T>) Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Tipo não existe para o caminho!");
		}
    }

   @SuppressWarnings("unchecked")
    @Override
    public T deserialize(String topic, byte[] bytes) {	   
       
			try {
				return (T) objectMapper.readValue(new String(bytes), type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			
		
    }

    @Override
    public void close() {
    }
}