package br.com.letscode.stwars.model;

import lombok.Data;

@Data
public class ResistanceReportEntity {
    float rebelPercentage;
    float traitorPercentage;
    ItemsEntity resourcesPerRebel;
    int pointsLostDueTreason;

    public ResistanceReportEntity(float rebelPercentage, float traitorPercentage, ItemsEntity resourcesPerRebel,
                                  int pointsLostDueTreason) {
        this.rebelPercentage = rebelPercentage;
        this.traitorPercentage = traitorPercentage;
        this.resourcesPerRebel = resourcesPerRebel;
        this.pointsLostDueTreason = pointsLostDueTreason;
    }
}
