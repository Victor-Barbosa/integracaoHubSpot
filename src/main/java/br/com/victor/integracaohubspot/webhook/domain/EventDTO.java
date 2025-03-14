package br.com.victor.integracaohubspot.webhook.domain;

public class EventDTO {

    private Long appId;
    private Long eventId;
    private Long subscriptionId;
    private Long portalId;
    private Long occurredAt;
    private String subscriptionType;
    private Integer attemptNumber;
    private Long objectId;
    private String changeSource;
    private String changeFlag;

    public EventDTO() {
    }

    public EventDTO(
            Long appId,
            Long eventId,
            Long subscriptionId,
            Long portalId,
            Long occurredAt,
            String subscriptionType,
            Integer attemptNumber,
            Long objectId,
            String changeSource,
            String changeFlag
    ) {
        this.appId = appId;
        this.eventId = eventId;
        this.subscriptionId = subscriptionId;
        this.portalId = portalId;
        this.occurredAt = occurredAt;
        this.subscriptionType = subscriptionType;
        this.attemptNumber = attemptNumber;
        this.objectId = objectId;
        this.changeSource = changeSource;
        this.changeFlag = changeFlag;
    }

    // Getters
    public Long getAppId() {
        return appId;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public Long getPortalId() {
        return portalId;
    }

    public Long getOccurredAt() {
        return occurredAt;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public Long getObjectId() {
        return objectId;
    }

    public String getChangeSource() {
        return changeSource;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "appId=" + appId +
                ", eventId=" + eventId +
                ", subscriptionId=" + subscriptionId +
                ", portalId=" + portalId +
                ", occurredAt=" + occurredAt +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", attemptNumber=" + attemptNumber +
                ", objectId=" + objectId +
                ", changeSource='" + changeSource + '\'' +
                ", changeFlag='" + changeFlag + '\'' +
                '}';
    }
}