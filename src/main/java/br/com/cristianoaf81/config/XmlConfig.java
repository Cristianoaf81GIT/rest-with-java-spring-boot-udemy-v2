package br.com.cristianoaf81.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Configuration
public class XmlConfig {

  @Bean
  public MappingJackson2XmlHttpMessageConverter xmlHttpMessageConverter() {
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    return new MappingJackson2XmlHttpMessageConverter(xmlMapper);
  }
}
