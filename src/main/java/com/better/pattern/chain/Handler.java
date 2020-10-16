package com.better.pattern.chain;

public abstract class Handler {
    protected String name;
    public Handler nextHandler;

    public Handler(String name) {
        this.name = name;
    }

    abstract boolean process(LeaveRequest request);
}

