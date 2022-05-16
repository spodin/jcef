package com.spodin.v.jcef;

import java.io.Serializable;
import java.util.Objects;

/**
 * Holds CEF event identifiers.
 *
 * @author spodin
 */
public class EventId implements Serializable {

    private final String id;
    private final String name;

    private EventId(String id, String name) {
        this.id = Assert.notNullOrBlank(id, "id is required");
        this.name = Assert.notNullOrBlank(name, "name is required");
    }

    /**
     * Creates CEF event identifier from specified id and name.
     *
     * <p><b>Event id (Device Event Class ID)</b> is a unique identifier per event-type. This can be
     * a string or an integer. Device Event ClassID identifies the type of event reported. In
     * the intrusion detection system (IDS) world, each signature or rule that detects certain
     * activity has a unique Device Event Class ID assigned. This is a requirement for other
     * types of devices as well, and helps correlation engines process the events.
     * Also known as Signature ID.</p>
     *
     * <p><b>The event name</b> should not contain information that is specifically mentioned in
     * other fields. For example: "Port scan from 10.0.0.1targeting 20.1.1.1" is not a good event
     * name. It should be: "Port scan". The other information is redundant and can be picked up from
     * the other fields.</p>
     *
     * @param id event id
     * @param name event name
     * @return event identifier instance
     * @throws IllegalArgumentException if id or name is {@code null} or blank
     */
    public static EventId of(String id, String name) {
        return new EventId(id, name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventId eventId = (EventId) o;
        return id.equals(eventId.id) && name.equals(eventId.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "EventId{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
