package com.better.pattern.chain;

import java.util.Random;

public class DirectorHandler extends Handler {

    public DirectorHandler(String name) {
        super(name);
    }

    @Override
    boolean process(LeaveRequest request) {
        boolean result = (new Random().nextInt(10) > 3);
        String log = "主管<%s> 审批 <%s> 的请假申请，请假天数： <%d> ，审批结果：<%s> ";
        System.out
                .println(String.format(log, this.name, request.name, request.numOfDays, result == true ? "批准" : "不批准"));

        if (!result) {  // 随机数大于3
            return false;
        } else if (request.numOfDays < 3) {
            return true;
        }
        return nextHandler.process(request); // 批准且天数大于等于3，提交给下一个处理者处理
    }
}