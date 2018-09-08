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
package function.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.zhucongqi.excel.annotation.Property;

/**
 * Created by Jobsz on 17/2/19.
 */
public class MultiLineHeadExcelModel {

    @Property(value = {"表头1","表头1","表头31"},index = 0)
    private String p1;

    @Property(value = {"表头1","表头1","表头32"},index = 1)
    private String p2;

    @Property(value = {"表头3","表头3","表头3"},index = 2)
    private int p3;

    @Property(value = {"表头4","表头4","表头4"},index = 3)
    private long p4;

    @Property(value = {"表头5","表头51","表头52"},index = 4)
    private String p5;

    @Property(value = {"表头6","表头61","表头611"},index = 5)
    private String p6;

    @Property(value = {"表头6","表头61","表头612"},index = 6)
    private String p7;

    @Property(value = {"表头6","表头62","表头621"},index = 7)
    private String p8;

    @Property(value = {"表头6","表头62","表头622"},index = 8)
    private String p9;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public int getP3() {
        return p3;
    }

    public void setP3(int p3) {
        this.p3 = p3;
    }

    public long getP4() {
        return p4;
    }

    public void setP4(long p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public String getP6() {
        return p6;
    }

    public void setP6(String p6) {
        this.p6 = p6;
    }

    public String getP7() {
        return p7;
    }

    public void setP7(String p7) {
        this.p7 = p7;
    }

    public String getP8() {
        return p8;
    }

    public void setP8(String p8) {
        this.p8 = p8;
    }

    public String getP9() {
        return p9;
    }

    public void setP9(String p9) {
        this.p9 = p9;
    }

    public static void main(String[] args) {
        Class<?> clazz = MultiLineHeadExcelModel.class;
        Field[] fields = clazz.getDeclaredFields();
        List<List<String>> head = new ArrayList<List<String>>();
        for (int i = 0; i < fields.length ; i++) {
            Field f = fields[i];
            Property p = f.getAnnotation(Property.class);
            String[] value =p.value();
            head.add(Arrays.asList(value));
        }
        System.out.println(head);
    }
}
