package br.com.cristianoaf81.serialization.converter;

import java.nio.charset.StandardCharsets;

import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
  
  public static final MediaType MEDIA_TYPE = new MediaType("application", "x-yaml", StandardCharsets.UTF_8);
  public static final MediaType ALT_MEDIA_TYPE = new MediaType("text", "yaml", StandardCharsets.UTF_8);

  public YamlJackson2HttpMessageConverter() {
    super(
      buildYamlMapper(), 
      MEDIA_TYPE,
      ALT_MEDIA_TYPE
    );
  }

  private static YAMLMapper buildYamlMapper() {
    YAMLMapper mapper = new YAMLMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return mapper;
  }
}
