/* (C) 2022-2023 Mondelez Insights */
package com.mondelez;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mondelez.api.dto.UserSnacksLogRequest;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;
import lombok.NonNull;

public interface Helper {
  ObjectMapper MAPPER = new ObjectMapper();
  String productsDetailJson =
      "[{\"type\":\"Baked Snacks\",\"name\":\"PERFECT SNACKS\",\"image\":\"PerfectSnacks.png\"},{\"type\":\"Baked Snacks\",\"name\":\"7DAYS\",\"image\":\"7DAYS_logo.jpg\"},{\"type\":\"Baked Snacks\",\"name\":\"GRENADE\",\"image\":\"grenade-logo.png\"},{\"type\":\"Beverages\",\"name\":\"TANG\",\"image\":\"Tang.png\"},{\"type\":\"Beverages\",\"name\":\"B0URNVITA\",\"image\":\"Bournvita.png\"},{\"type\":\"Biscuits\",\"name\":\"OREO\",\"image\":\"Oreo.png\"},{\"type\":\"Biscuits\",\"name\":\"TATE'S BAKE SHOP\",\"image\":\"Tates.png\"},{\"type\":\"Biscuits\",\"name\":\"PRINCE\",\"image\":\"Prince.png\"},{\"type\":\"Biscuits\",\"name\":\"RITZ\",\"image\":\"Ritz.png\"},{\"type\":\"Biscuits\",\"name\":\"CLUB SOCIAL\",\"image\":\"ClubSocial.png\"},{\"type\":\"Biscuits\",\"name\":\"BELVITA\",\"image\":\"belVita.png\"},{\"type\":\"Biscuits\",\"name\":\"ENJOY LIFE FOODS\",\"image\":\"EnjoyLife.png\"},{\"type\":\"Biscuits\",\"name\":\"TRISCUIT\",\"image\":\"Triscuit.png\"},{\"type\":\"Biscuits\",\"name\":\"HONEY MAID\",\"image\":\"HoneyMaid.png\"},{\"type\":\"Biscuits\",\"name\":\"BARNI\",\"image\":\"Barni.png\"},{\"type\":\"Biscuits\",\"name\":\"KINH DO\",\"image\":\"KinhDo.png\"},{\"type\":\"Biscuits\",\"name\":\"LU\",\"image\":\"LU.png\"},{\"type\":\"Biscuits\",\"name\":\"CHIPS AHOY!\",\"image\":\"ChipsAhoy.png\"},{\"type\":\"Biscuits\",\"name\":\"WHEAT THINS\",\"image\":\"WheatThins.png\"},{\"type\":\"Biscuits\",\"name\":\"TIGER\",\"image\":\"TIGER.png\"},{\"type\":\"Biscuits\",\"name\":\"MIKADO\",\"image\":\"mikado.png\"},{\"type\":\"Biscuits\",\"name\":\"TUC\",\"image\":\"TUC.png\"},{\"type\":\"Chocolate\",\"name\":\"MILKA\",\"image\":\"Milka.png\"},{\"type\":\"Chocolate\",\"name\":\"ALPEN GOLD\",\"image\":\"AlpenGold.png\"},{\"type\":\"Chocolate\",\"name\":\"CADBURY\",\"image\":\"Cadbury.png\"},{\"type\":\"Chocolate\",\"name\":\"CADBURY DAIRY MILK\",\"image\":\"CDM.png\"},{\"type\":\"Chocolate\",\"name\":\"CôTE D'OR\",\"image\":\"CoteDOr.png\"},{\"type\":\"Chocolate\",\"name\":\"DAIM\",\"image\":\"daim_logo_rgb_no202002.png\"},{\"type\":\"Chocolate\",\"name\":\"FREIA\",\"image\":\"Freia.png\"},{\"type\":\"Chocolate\",\"name\":\"HU\",\"image\":\"Hu.png\"},{\"type\":\"Chocolate\",\"name\":\"LACTA\",\"image\":\"Lacta.png\"},{\"type\":\"Chocolate\",\"name\":\"MARABOU\",\"image\":\"Marabou.png\"},{\"type\":\"Chocolate\",\"name\":\"5 STAR\",\"image\":\"5Star.png\"},{\"type\":\"Chocolate\",\"name\":\"TOBLERONE\",\"image\":\"Toblerone.png\"},{\"type\":\"Gum and Candy\",\"name\":\"SOUR PATCH KIDS\",\"image\":\"SPK.png\"},{\"type\":\"Gum and Candy\",\"name\":\"CLORETS\",\"image\":\"Clorets.png\"},{\"type\":\"Gum and Candy\",\"name\":\"STRIDE\",\"image\":\"Stride.png\"},{\"type\":\"Gum and Candy\",\"name\":\"MAYNARDS BASSETT'S\",\"image\":\"MaynardsBassets.png\"},{\"type\":\"Gum and Candy\",\"name\":\"TRIDENT\",\"image\":\"Trident.png\"},{\"type\":\"Gum and Candy\",\"name\":\"HALLS\",\"image\":\"Halls.png\"}]";

  static String toJsonString(@NonNull Object value) {
    try {
      return MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  // Unchecked IOException
  static String toJsonPrettyString(@NonNull Object value) {
    try {
      return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  // Unchecked IOException
  static <T> T fromJson(@NonNull final String json, @NonNull final TypeReference<T> type) {
    try {
      return MAPPER.readValue(json, type);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  static <T> T fromJsonFile(@NonNull final File jsonFile, @NonNull final TypeReference<T> type) {
    try {
      return MAPPER.readValue(jsonFile, type);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  static <T> T fromJsonFile(@NonNull final URL jsonFileUrl, @NonNull final TypeReference<T> type) {
    try {
      return MAPPER.readValue(jsonFileUrl, type);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  static String toJsonString(@NonNull final Map<String, Object> objectMap) {
    try {
      return MAPPER.writeValueAsString(objectMap);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  static Map<String, Object> toMap(String jsonString) {
    Map<String, Object> mapFromString = new HashMap<>();
    try {
      return MAPPER.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  static JsonNode toJsonNode(String jsonString) {
    try {
      return MAPPER.readTree(jsonString);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  static List<UserSnacksLogRequest> generateRandomSnackRecordRequests(int numberOfRecords) {
    List<SnackDetail> request =
        fromJson(productsDetailJson, new TypeReference<List<SnackDetail>>() {});
    return IntStream.range(0, numberOfRecords).mapToObj(i -> getRandomSnack(request)).toList();
  }

  private static UserSnacksLogRequest getRandomSnack(List<SnackDetail> productList) {
    Random random = new Random();
    int randomIndex = random.nextInt(productList.size());
    SnackDetail product = productList.get(randomIndex);
    return UserSnacksLogRequest.builder()
        .type(product.getType())
        .name(product.getName())
        .quantity(generateRandomQuantity())
        .tags(Collections.emptyList())
        .date(generateRandomDate())
        .rating(generateRandomRanting())
        .image(product.getImage())
        .createdAt(System.currentTimeMillis())
        .build();
  }

  private static String generateRandomDate() {
    Random random = new Random();
    int year = random.nextInt(24) + 2000;
    int month = random.nextInt(12) + 1;
    int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
    int day = random.nextInt(maxDay) + 1;
    LocalDate randomDate = LocalDate.of(year, month, day);
    return randomDate.toString();
  }

  private static int generateRandomQuantity() {
    Random random = new Random();
    return random.nextInt(9) + 1;
  }

  private static int generateRandomRanting() {
    Random random = new Random();
    return random.nextInt(5) + 1;
  }
}

class SnackDetail {
  @JsonProperty("type")
  private String type;

  @JsonProperty("name")
  private String name;

  @JsonProperty("image")
  private String image;

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getImage() {
    return image;
  }
}
