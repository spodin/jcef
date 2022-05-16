package com.spodin.v.jcef;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StdCefSerializerTest {

    private static final CefSerializer<String> SERIALIZER = new StdCefSerializer();

    @Test
    void correctCefMessageShouldBeCreated() {
        var event = CefEvent.builder()
            .device(Device.builder().vendor("iPlatform").product("USO").version("1").build())
            .eventId(EventId.of("some_event", "This event has been occurred"))
            .extension(Extension.builder().add("ip", "10.91.161.67").add("source", "my_server").build())
            .severity(10)
            .build();

        Assertions.assertEquals(
            "CEF:0|iPlatform|USO|1|some_event|This event has been occurred|10|ip=10.91.161.67 source=my_server",
            SERIALIZER.serialize(event));
    }

    /*
       Message example:
       Jan 18 11:07:53 host CEF:Version|Device Vendor|Device Product|Device Version|Device Event Class ID|Name|Severity|[Extension]
     */
    @Test
    void extensionIsNotMandatory() {
        var event = CefEvent.builder()
            .device(Device.builder().vendor("iPlatform").product("USO").version("1").build())
            .eventId(EventId.of("some_event", "This event has been occurred"))
            .severity(10)
            .build();

        Assertions.assertEquals(
            "CEF:0|iPlatform|USO|1|some_event|This event has been occurred|10|",
            SERIALIZER.serialize(event));
    }
}
