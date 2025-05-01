package br.com.order.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilsTest {

  static class Dummy {
    public String name;
    public int age;

    public Dummy(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  @Test
  void shouldReturnJsonStringWhenObjectIsValid() {
    Dummy dummy = new Dummy("Alice", 30);
    String json = JsonUtils.toJson(dummy);

    assertThat(json)
      .isNotNull()
      .contains("\"name\":\"Alice\"")
      .contains("\"age\":30");
  }

  @Test
  void shouldReturnNullWhenObjectIsNotSerializable() {
    Object notSerializable = new Object() {
    };

    String json = JsonUtils.toJson(notSerializable);

    assertThat(json).isNull();
  }
}
