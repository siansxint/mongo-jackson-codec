package net.sintaxis.codec.example.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.beans.ConstructorProperties;

import java.util.Arrays;
import java.util.List;

@JsonSerialize(as = User.class)
public class DummyUser
        implements User {

 private final String id;
 private final List<String> dummies;

 @ConstructorProperties("_id")
 public DummyUser(String id) {
  this.id = id;
  this.dummies = Arrays.asList("Example", "Dummy", "User");
 }

 @Override
 public String getId() {
  return id;
 }

 @Override
 public List<String> getDummies() {
  return dummies;
 }
}