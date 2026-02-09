package br.com.cristianoaf81.integrationtests.controllers.withyaml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

public class YamlMapper implements ObjectMapper {

  private com.fasterxml.jackson.databind.ObjectMapper objectMapper;
  protected TypeFactory typeFactory;


  public YamlMapper() {
    objectMapper = new com.fasterxml.jackson.databind.ObjectMapper(new YAMLFactory())
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    typeFactory = TypeFactory.defaultInstance();
  }

  @Override
  public Object serialize(ObjectMapperSerializationContext context) {
    try {
      return objectMapper.writeValueAsString(context.getObjectToSerialize());
    } catch(JsonProcessingException e) {
      throw new IllegalArgumentException("Error serializing yaml ", e);
    }
  }

  @SuppressWarnings({"rawtypes"})
  @Override
  public Object deserialize(ObjectMapperDeserializationContext context) {
    var data = context.getDataToDeserialize().asString();
    Class type = (Class) context.getType();
    try {
      return objectMapper.readValue(data, typeFactory.constructType(type));    
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Error deserializing yaml ", e);
    }
  }
}
