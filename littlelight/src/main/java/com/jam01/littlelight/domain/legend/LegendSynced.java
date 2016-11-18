package com.jam01.littlelight.domain.legend;

import com.jam01.littlelight.domain.DomainEvent;

import java.util.Date;

/**
 * Created by jam01 on 11/1/16.
 */
public class LegendSynced implements DomainEvent {
    private final Date occuredOn;
    private final Legend legendUpdated;

    public LegendSynced(Legend legend) {
        occuredOn = new Date();
        legendUpdated = legend;
    }

    public Legend getLegendUpdated() {
        return legendUpdated;
    }

    @Override
    public Date occurredOn() {
        return occuredOn;
    }
}
