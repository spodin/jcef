package com.spodin.v.jcef;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CefMessageTest {

    @Test
    void correctCefMessageShouldBeCreated() {
        var event = CefEvent.builder()
            .device(Device.builder().vendor("iPlatform").product("USO").version("1").build())
            .id("some_event")
            .name("This event has been occurred")
            .extension(new Extension().add("ip", "10.91.161.67").add("source", "my_server"))
            .severity(10)
            .build();

        Assertions.assertEquals(
            "CEF:0|iPlatform|USO|1|some_event|This event has been occurred|10|ip=10.91.161.67 source=my_server",
            new CefMessage(event).getValue());
    }
}
