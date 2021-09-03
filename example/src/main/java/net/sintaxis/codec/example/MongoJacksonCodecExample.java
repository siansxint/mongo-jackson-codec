package net.sintaxis.codec.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.sintaxis.codec.MongoJacksonCodecProvider;
import net.sintaxis.codec.example.model.DummyUser;
import net.sintaxis.codec.example.model.User;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;

public class MongoJacksonCodecExample {

 public static void main(String[] args) {

    /*
    Use the default codec registries for default mongo operations
    and add the project codec provider
     */
  CodecRegistry registry = CodecRegistries.fromRegistries(
          MongoClientSettings.getDefaultCodecRegistry(),
          CodecRegistries.fromProviders(new MongoJacksonCodecProvider(new ObjectMapper()))
  );

  MongoClientSettings settings = MongoClientSettings
          .builder()
          .codecRegistry(registry)
          .applyConnectionString(new ConnectionString("mongodb://localhost"))
          .build();

  MongoDriverInformation information = MongoDriverInformation
          .builder()
          .driverName("sync")
          .build();

  MongoClient client = MongoClients.create(
          settings,
          information
  );

    /*
    Tell mongo this collection will be of the user model,
    now, the mongo registry knows which provider will use to
    encode/decode this model
     */
  MongoCollection<User> userCollection = client.getDatabase("example")
          .getCollection("users", User.class);

  //That is just to test the codec with the model
  String id = UUID.randomUUID().toString();

  userCollection.insertOne(new DummyUser(id));

  User user = userCollection.find(
          Filters.eq("_id", id)
  ).first();

  //This will never happen
  if (user == null) {
   System.out.println("User with id '" + id + "' not found.");
   return;
  }

  System.out.println(user.getId() + " : " + user.getDummies());
 }
}