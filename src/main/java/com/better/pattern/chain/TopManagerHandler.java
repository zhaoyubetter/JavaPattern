package com.better.pattern.chain;

import java.util.Random;

public class TopManagerHandler extends Handler {

    public TopManagerHandler(String name) {
        super(name);
    }

    @Override
    boolean process(LeaveRequest request) {
        // 随机数大于3则为批准，否则不批准
        boolean result = (new Random().nextInt(10) > 3);
        String log = "总经理<%s> 审批 <%s> 的请假申请，请假天数： <%d> ，审批结果：<%s> ";
        System.out
                .println(String.format(log, this.name, request.name, request.numOfDays, result == true ? "批准" : "不批准"));

        if (!result) { // 不批准
            return false;
        }

        return true;
    }
}