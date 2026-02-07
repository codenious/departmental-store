package com.store.departmentalstore.common;

import java.time.OffsetDateTime;

public record ErrorResponse(
    String message,
    OffsetDateTime timestamp
) {
}
