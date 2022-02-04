package com.sportstore.tenantyeventservice.eventbus;

import lombok.Data;

@Data
public abstract class Event {
    private String tenant;
    private String name;
    private boolean doNotCheckForCustomisation;
}
