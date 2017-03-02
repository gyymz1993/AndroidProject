package com.rx.lib.utils;

public final class EventConstant {
    public final static int REFRESH = 0;
    public final static int LOADMORE = 1;
    public final static int DELETE = 2;
    public final static int ADD = 4;

    private EventConstant() throws InstantiationException {
        throw new InstantiationException("This utility class is not created for instatiation");
    }
}