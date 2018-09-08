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


/**
 * 管理每个监听者
 *
 * @author Jobsz
 */
public interface AnalysisEventRegisterCenter {

    /**
     * 增加监听者
     * @param name 名称
     * @param listener 监听器
     */
    void appendLister(String name, AnalysisEventListener<?> listener);


    /**
     * 通知所有监听者
     * @param event 事件
     */
    void notifyListeners(OneRowAnalysisFinishEvent event);

    /**
     * 清空所有监听者
     */
    void cleanAllListeners();
}
