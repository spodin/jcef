package com.spodin.v.jcef;

/**
 * CEF event serializer interface.
 *
 * @param <R> serialized object type
 * @author spodin
 */
public interface CefSerializer<R> {

    /**
     * Serializes specified CEF event.
     *
     * @param event CEF event
     * @return serialized event
     */
    R serialize(CefEvent event);
}
