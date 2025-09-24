package br.com.cristianoaf81.mapper;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectMapper {

  private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();


  public static <O, D> D parseObject(O origin, Class<D> destination) {
    return mapper.map(origin, destination);
  }

  public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
    
    Function <O, D> mapToDestination = originObj -> { 
      return mapper.map(originObj, destination);
    };

    return origin.stream().map(mapToDestination).collect(Collectors.toList());
  }
}
