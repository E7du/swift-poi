/*
 * Copyright 2018 Jobsz (zcq@zhucongqi.cn)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
*/
package javamodel;

import java.util.Date;

import cn.zhucongqi.excel.annotation.Property;

/**
 * Created by Jobsz on 17/3/15.
 *
 * @author Jobsz
 * @date 2017/03/15
 */
public class ExcelRowJavaModel {
    @Property(index = 0,value = "银行放款编号")
    private int num;

    @Property(index = 1,value = "code")
    private Long code;

    @Property(index = 2,value = "银行存放期期")
    private Date endTime;

    @Property(index = 3,value = "测试1")
    private Double money;

    @Property(index = 4,value = "测试2")
    private String times;

    @Property(index = 5,value = "测试3")
    private int activityCode;

    @Property(index = 6,value = "测试4")
    private Date date;

    @Property(index = 7,value = "测试5")
    private Double lx;

    @Property(index = 8,value = "测试6")
    private String name;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(int activityCode) {
        this.activityCode = activityCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLx() {
        return lx;
    }

    public void setLx(Double lx) {
        this.lx = lx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExcelRowJavaModel{" +
            "num=" + num +
            ", code=" + code +
            ", endTime=" + endTime +
            ", money=" + money +
            ", times='" + times + '\'' +
            ", activityCode=" + activityCode +
            ", date=" + date +
            ", lx=" + lx +
            ", name='" + name + '\'' +
            '}';
    }
}
