package br.com.cristianoaf81.serialization.converter;

import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

public final class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
   
  protected  YamlJackson2HttpMessageConverter() {
    super(
      new YAMLMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL), 
      MediaType.parseMediaType("application/yaml")        
    );
  }
}
