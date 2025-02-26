/* (C) 2023 Mondelez Insights */
package com.mondelez.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mondelez.dao.SnackInsightDao;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnackInsightService {

  @Autowired SnackInsightDao snackInsightDao;

  public JsonNode quantityOverTheTimeLineChart() {
    String query =
        "WITH snack_data AS (\n"
            + "    SELECT\n"
            + "        EXTRACT(YEAR FROM purchase_date) AS year,\n"
            + "        type,\n"
            + "        SUM(quantity) AS total_quantity\n"
            + "    FROM\n"
            + "        user_snacks_log\n"
            + "    GROUP BY\n"
            + "        year, type\n"
            + ")\n"
            + "SELECT\n"
            + "    year,\n"
            + "    jsonb_agg(jsonb_build_object('data', total_quantity, 'label', type)) AS datasets\n"
            + "FROM\n"
            + "    snack_data\n"
            + "GROUP BY\n"
            + "    year\n"
            + "ORDER BY\n"
            + "    year\n";
    JsonNode chartDataList = snackInsightDao.query("SELECT json_agg(t) FROM (" + query + ") t");
    int usersCount = usersCount();
    int totalQuantity = totalQuantity();
    int totalPurchase = totalPurchase();
    // Initialize ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

    // Create a map to store datasets
    Map<String, List<Integer>> datasetsMap = new HashMap<>();

    // Create a list to store years
    List<Integer> yearsList = new ArrayList<>();

    // Iterate through the original data
    for (JsonNode yearNode : chartDataList) {
      int year = yearNode.get("year").asInt();
      yearsList.add(year);

      JsonNode datasetsNode = yearNode.get("datasets");
      for (JsonNode datasetNode : datasetsNode) {
        String label = datasetNode.get("label").asText();
        int data = datasetNode.get("data").asInt();

        // If the label exists in the map, add data to the existing list
        if (datasetsMap.containsKey(label)) {
          datasetsMap.get(label).add(data);
        } else {
          // If the label doesn't exist, create a new list and add data
          List<Integer> dataList = new ArrayList<>();
          dataList.add(data);
          datasetsMap.put(label, dataList);
        }
      }
    }

    // Create a formatted ObjectNode
    ObjectNode formattedData = objectMapper.createObjectNode();

    // Create a list to store dataset objects
    List<ObjectNode> datasetList = new ArrayList<>();

    // Convert the datasetsMap to a list of objects
    for (Map.Entry<String, List<Integer>> entry : datasetsMap.entrySet()) {
      ObjectNode dataset = objectMapper.createObjectNode();
      dataset.put("label", entry.getKey());

      // Create an ArrayNode for data and add individual data points
      ArrayNode dataNode = objectMapper.createArrayNode();
      for (Integer data : entry.getValue()) {
        dataNode.add(data);
      }
      dataset.set("data", dataNode);

      datasetList.add(dataset);
    }

    // Convert the lists to arrays and add them to the formattedData
    ArrayNode datasetsArray = objectMapper.valueToTree(datasetList);
    ArrayNode yearsArray = objectMapper.valueToTree(yearsList);

    formattedData.set("datasets", datasetsArray);
    formattedData.set("labels", yearsArray);
    formattedData.set("totalUsers", objectMapper.convertValue(usersCount, JsonNode.class));
    formattedData.set("totalQuantity", objectMapper.convertValue(totalQuantity, JsonNode.class));
    formattedData.set("totalPurchase", objectMapper.convertValue(totalPurchase, JsonNode.class));
    return formattedData;
  }

  public JsonNode overallPercentageByType() {
    String query =
        "SELECT\n"
            + "    type,\n"
            + "    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2) AS quantity_percentage\n"
            + "FROM\n"
            + "    user_snacks_log\n"
            + "GROUP BY\n"
            + "    type\n"
            + "ORDER BY\n"
            + "    type";
    JsonNode pieChartDataList = snackInsightDao.query("SELECT json_agg(t) FROM (" + query + ") t");

    // Create the desired JSON structure
    ObjectNode result = JsonNodeFactory.instance.objectNode();
    ArrayNode labelsArray = result.putArray("labels");
    ArrayNode data = result.putArray("datasets").addObject().putArray("data");

    List<Double> quantities = new ArrayList<>();

    // Iterate through the JSON array and populate the labels and data arrays
    for (JsonNode item : pieChartDataList) {
      String type = item.get("type").asText();
      double quantityPercentage = item.get("quantity_percentage").asDouble();
      labelsArray.add(type);
      quantities.add(quantityPercentage);
    }

    for (Double quantity : quantities) {
      data.add(quantity);
    }

    return result;
  }

  public JsonNode quantityOverTheTimeBySnack(String type, String name) {
    name = name.replace("'", "''");
    String query =
        "WITH snack_data AS (\n"
            + "    SELECT\n"
            + "        EXTRACT(YEAR FROM purchase_date) AS year,\n"
            + "        name,\n"
            + "        SUM(quantity) AS total_quantity\n"
            + "    FROM\n"
            + "        user_snacks_log\n"
            + "    WHERE\n"
            + "        type='"
            + type
            + "' AND name='"
            + name
            + "'\n"
            + "    GROUP BY\n"
            + "        year, name\n"
            + ")\n"
            + "SELECT\n"
            + "    year,\n"
            + "    jsonb_agg(jsonb_build_object('data', total_quantity, 'label', name)) AS datasets\n"
            + "FROM\n"
            + "    snack_data\n"
            + "GROUP BY\n"
            + "    year\n"
            + "ORDER BY\n"
            + "    year\n";
    JsonNode chartDataList = snackInsightDao.query("SELECT json_agg(t) FROM (" + query + ") t");
    // Initialize ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

    // Create a map to store datasets
    Map<String, List<Integer>> datasetsMap = new HashMap<>();

    // Create a list to store years
    List<Integer> yearsList = new ArrayList<>();

    // Iterate through the original data
    for (JsonNode yearNode : chartDataList) {
      int year = yearNode.get("year").asInt();
      yearsList.add(year);

      JsonNode datasetsNode = yearNode.get("datasets");
      for (JsonNode datasetNode : datasetsNode) {
        String label = datasetNode.get("label").asText();
        int data = datasetNode.get("data").asInt();

        // If the label exists in the map, add data to the existing list
        if (datasetsMap.containsKey(label)) {
          datasetsMap.get(label).add(data);
        } else {
          // If the label doesn't exist, create a new list and add data
          List<Integer> dataList = new ArrayList<>();
          dataList.add(data);
          datasetsMap.put(label, dataList);
        }
      }
    }

    // Create a formatted ObjectNode
    ObjectNode formattedData = objectMapper.createObjectNode();

    // Create a list to store dataset objects
    List<ObjectNode> datasetList = new ArrayList<>();

    // Convert the datasetsMap to a list of objects
    for (Map.Entry<String, List<Integer>> entry : datasetsMap.entrySet()) {
      ObjectNode dataset = objectMapper.createObjectNode();
      dataset.put("label", entry.getKey());

      // Create an ArrayNode for data and add individual data points
      ArrayNode dataNode = objectMapper.createArrayNode();
      for (Integer data : entry.getValue()) {
        dataNode.add(data);
      }
      dataset.set("data", dataNode);

      datasetList.add(dataset);
    }

    // Convert the lists to arrays and add them to the formattedData
    ArrayNode datasetsArray = objectMapper.valueToTree(datasetList);
    ArrayNode yearsArray = objectMapper.valueToTree(yearsList);

    formattedData.set("datasets", datasetsArray);
    formattedData.set("labels", yearsArray);
    return formattedData;
  }

  public int usersCount() {
    return snackInsightDao.usersCount();
  }

  public int totalQuantity() {
    return snackInsightDao.totalQuantity();
  }

  public int totalPurchase() {
    return snackInsightDao.totalPurchase();
  }
}
