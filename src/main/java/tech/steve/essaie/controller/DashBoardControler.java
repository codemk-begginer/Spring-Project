package tech.steve.essaie.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.steve.essaie.dto.DashboardStatsDto;
import tech.steve.essaie.service.DashboardService;

/**
 * <b>CRUD endpoints for DashBoard</b>
 */
@RestController
@AllArgsConstructor
public class DashBoardControler {
    private final DashboardService dashboardService;

    /**
     * @return all dashboard statistics
     */
    @GetMapping(path = "GetDashboardStatistiques")
    public ResponseEntity<DashboardStatsDto> GetDashboardStatistiques(){
       return ResponseEntity.ok(this.dashboardService.getStatistiquesGlobales());
    }
}
