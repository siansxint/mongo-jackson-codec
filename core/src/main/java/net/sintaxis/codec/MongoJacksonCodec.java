package net.sintaxis.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.RawBsonDocument;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.io.IOException;
import java.io.UncheckedIOException;

public class MongoJacksonCodec<T>
        implements Codec<T> {

 private final ObjectMapper mapper;
 private final Codec<RawBsonDocument> rawBsonCodec;
 private final Class<T> clazz;

 public MongoJacksonCodec(ObjectMapper mapper,
                          CodecRegistry registry,
                          Class<T> clazz) {
  this.mapper = mapper;
  this.rawBsonCodec = registry.get(RawBsonDocument.class);
  this.clazz = clazz;
 }

 @Override
 public void encode(BsonWriter writer,
                    T value,
                    EncoderContext encoderContext
 ) {
  try {
   rawBsonCodec
           .encode(
                   writer,
                   RawBsonDocument.parse(
                           mapper.writeValueAsString(value)),
                   encoderContext
           );
  } catch (IOException exception) {
   throw new UncheckedIOException(exception);
  }
 }

 @Override
 public T decode(BsonReader reader,
                 DecoderContext decoderContext
 ) {
  try {
   return mapper.readValue(
           rawBsonCodec.decode(reader, decoderContext).toJson(),
           clazz
   );
  } catch (IOException exception) {
   throw new UncheckedIOException(exception);
  }
 }

 @Override
 public Class<T> getEncoderClass() {
  return clazz;
 }
}