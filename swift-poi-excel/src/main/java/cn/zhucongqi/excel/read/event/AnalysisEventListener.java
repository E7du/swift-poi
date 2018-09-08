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
package cn.zhucongqi.excel.read.event;

import cn.zhucongqi.excel.read.context.AnalysisContext;

/**
 * 监听Excel解析每行数据
 * 不能单列，每次使用new一个
 * 不能单列，每次使用new一个
 * 不能单列，每次使用new一个
 * 重要事情说三遍
 *
 * @author Jobsz
 */
public abstract class AnalysisEventListener<T> {

    /**
     * when read one row trigger invoke function
     *
     * @param object  one row data
     * @param context read context
     */
    public abstract void invoke(T object, AnalysisContext context);

    /**
     * if have something to do after all  read
     *
     * @param context context
     */
    public abstract void doAfterAllAnalysed(AnalysisContext context);
}
