/* (C) 2023 Mondelez Insights */
package com.mondelez.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.mondelez.annotation.CurrentUser;
import com.mondelez.service.SnackInsightService;
import com.mondelez.service.model.LocalUser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/insights")
public class SnackInsightController {

  @Autowired SnackInsightService snackInsightService;

  @GetMapping("quantityOverTheTime")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<JsonNode> getQuantityOverTheTimeLineChart(@CurrentUser LocalUser user) {
    JsonNode lineChartData = snackInsightService.quantityOverTheTimeLineChart();
    return ResponseEntity.ok(lineChartData);
  }

  @GetMapping("overallPercentageByType")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<JsonNode> getOverallPercentageByType(@CurrentUser LocalUser user) {
    JsonNode lineChartData = snackInsightService.overallPercentageByType();
    return ResponseEntity.ok(lineChartData);
  }

  @PostMapping("quantityOverTheTimeBySnack")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<JsonNode> quantityOverTheTimeBySnack(
      @CurrentUser LocalUser user, @RequestBody @Validated BarCharRequest request) {
    JsonNode lineChartData =
        snackInsightService.quantityOverTheTimeBySnack(request.type, request.name);
    return ResponseEntity.ok(lineChartData);
  }
}

@Data
class BarCharRequest {
  public String type;
  public String name;
}
