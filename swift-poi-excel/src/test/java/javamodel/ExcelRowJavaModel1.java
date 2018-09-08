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

import cn.zhucongqi.excel.annotation.Property;

/**
 * Created by Jobsz on 17/3/15.
 *
 * @author Jobsz
 * @date 2017/03/15
 */
public class ExcelRowJavaModel1 {

    @Property(index = 0,value = "银行放款编号")
    private String num;

    @Property(index = 1,value = "code")
    private String code;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ExcelRowJavaModel1{" +
            "num='" + num + '\'' +
            ", code='" + code + '\'' +
            '}';
    }
}
