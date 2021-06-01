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

public class MongoJacksonCodec<T> implements Codec<T> {

  private final ObjectMapper objectMapper;
  private final Codec<RawBsonDocument> rawBsonDocumentCodec;
  private final Class<T> clazz;

  public MongoJacksonCodec(ObjectMapper mapper,
                           CodecRegistry registry,
                           Class<T> clazz) {
    this.objectMapper = mapper;
    this.rawBsonDocumentCodec = registry.get(RawBsonDocument.class);
    this.clazz = clazz;
  }

  @Override
  public void encode(BsonWriter writer,
                     T value,
                     EncoderContext encoderContext) {
    try {
      rawBsonDocumentCodec
              .encode(
                      writer,
                      RawBsonDocument.parse(
                              objectMapper.writeValueAsString(value)),
                      encoderContext
              );
    } catch (IOException exception) {
      throw new UncheckedIOException(exception);
    }
  }

  @Override
  public T decode(BsonReader reader,
                  DecoderContext decoderContext) {
    try {
      return objectMapper.readValue(
              rawBsonDocumentCodec.decode(reader, decoderContext).toJson(),
              clazz);
    } catch (IOException exception) {
      throw new UncheckedIOException(exception);
    }
  }

  @Override
  public Class<T> getEncoderClass() {
    return this.clazz;
  }
}