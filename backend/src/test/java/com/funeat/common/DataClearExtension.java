package com.funeat.common;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DataClearExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        final DataCleaner dataCleaner = getDataCleaner(context);
        dataCleaner.clear();
    }

    private DataCleaner getDataCleaner(final ExtensionContext extensionContext) {
        return SpringExtension.getApplicationContext(extensionContext)
                .getBean(DataCleaner.class);
    }
}
