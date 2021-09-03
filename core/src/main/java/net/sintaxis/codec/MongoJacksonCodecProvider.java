package net.sintaxis.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class MongoJacksonCodecProvider
        implements CodecProvider {

 private final ObjectMapper mapper;

 public MongoJacksonCodecProvider(ObjectMapper mapper) {
  this.mapper = mapper;
 }

 @Override
 public <T> Codec<T> get(Class<T> clazz,
                         CodecRegistry registry
 ) {
  return new MongoJacksonCodec<>(mapper, registry, clazz);
 }
}