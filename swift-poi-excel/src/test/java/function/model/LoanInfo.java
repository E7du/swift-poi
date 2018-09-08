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

import java.math.BigDecimal;
import java.util.Date;

import cn.zhucongqi.excel.annotation.Property;

/**
 * Created by Jobsz on 17/3/28.
 *
 * @author Jobsz
 * @date 2017/03/28
 */
public class LoanInfo implements Comparable<LoanInfo> {
    @Property(index = 0)
    private String bankLoanId;

    @Property(index = 1)
    private Long customerId;

    @Property(index = 2,format = "yyyy/MM/dd")
    private Date loanDate;

    @Property(index = 3)
    private BigDecimal quota;

    @Property(index = 4)
    private String bankInterestRate;

    @Property(index = 5)
    private Integer loanTerm;

    @Property(index = 6,format = "yyyy/MM/dd")
    private Date loanEndDate;

    @Property(index = 7)
    private Date interestPerMonth;



    //    public String getLoanName() {
    //        return loanName;
    //    }
    //
    //    public void setLoanName(String loanName) {
    //        this.loanName = loanName;
    //    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public String getBankInterestRate() {
        return bankInterestRate;
    }

    public void setBankInterestRate(String bankInterestRate) {
        this.bankInterestRate = bankInterestRate;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Date getInterestPerMonth() {
        return interestPerMonth;
    }

    public void setInterestPerMonth(Date interestPerMonth) {
        this.interestPerMonth = interestPerMonth;
    }

    public String getBankLoanId() {
        return bankLoanId;
    }

    public void setBankLoanId(String bankLoanId) {
        this.bankLoanId = bankLoanId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ExcelLoanInfo{" +
            "bankLoanId='" + bankLoanId + '\'' +
            ", customerId='" + customerId + '\'' +
            ", loanDate=" + loanDate +
            ", quota=" + quota +
            ", bankInterestRate=" + bankInterestRate +
            ", loanTerm=" + loanTerm +
            ", loanEndDate=" + loanEndDate +
            ", interestPerMonth=" + interestPerMonth +
            '}';
    }

    public int compareTo(LoanInfo info) {
        boolean before = this.getLoanDate().before(info.getLoanDate());
        return before ? 1 : 0;
    }
}
