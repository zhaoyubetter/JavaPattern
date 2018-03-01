package com.better.pattern.decorator3.after.abs;

/**
 * Created by zhaoyu on 2016/11/23.
 */
public abstract class Decorator extends Component {

    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    public abstract String getDescription();
}
