package com.better.pattern.chain;

import java.util.Random;

public class ManagerHandler extends Handler {

    public ManagerHandler(String name) {
        super(name);
    }

    @Override
    boolean process(LeaveRequest request) {
        // 随机数大于3则为批准，否则不批准
        boolean result = (new Random().nextInt(10) > 3);
        String log = "经理<%s> 审批 <%s> 的请假申请，请假天数： <%d> ，审批结果：<%s> ";
        System.out
                .println(String.format(log, this.name, request.name, request.numOfDays, result == true ? "批准" : "不批准"));

        if (!result) {  // 不批准
            return false;
        } else if (request.numOfDays < 7) {
            return true;
        }
        return nextHandler.process(request); // 批准且天数大于等于3，提交给下一个处理者处理
    }
}