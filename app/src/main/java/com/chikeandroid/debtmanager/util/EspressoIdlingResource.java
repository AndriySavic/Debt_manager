package com.chikeandroid.debtmanager.util;

import android.support.test.espresso.IdlingResource;

/**
 * Created by Chike on 12/12/2019.
 */

public final class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource sSimpleCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    private EspressoIdlingResource() {}

    public static void increment() {
        sSimpleCountingIdlingResource.increment();
    }

    public static void decrement() {
        sSimpleCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return sSimpleCountingIdlingResource;
    }

}
