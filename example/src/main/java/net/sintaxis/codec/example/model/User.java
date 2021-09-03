package net.sintaxis.codec.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = DummyUser.class)
public interface User {

 @JsonProperty("_id")
 String getId();

 List<String> getDummies();
}