package com.lsy.core;

import java.util.Arrays;

public class CompanyInfo {
		

		/**
		 * 公司简称
		 */
	 private String companyShortName;
		/**
		 * 公司id
		 */
	 private String companyId;
		/**
		 * 创建时间
		 */
	 private String createTime;
		/**
		 * 职位id
		 */
	 private String positionId;
	 /**
	  * 职位诱惑
	  */
	 private String positionAdvantage;
	
	 /**
	  * 福利
	  */
	 private String companyLabelList[]; 
	 
	 public String getCompanyShortName() {
			return companyShortName;
		}

		public void setCompanyShortName(String companyShortName) {
			this.companyShortName = companyShortName;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getPositionId() {
			return positionId;
		}

		public void setPositionId(String positionId) {
			this.positionId = positionId;
		}

		public String getPositionAdvantage() {
			return positionAdvantage;
		}

		public void setPositionAdvantage(String positionAdvantage) {
			this.positionAdvantage = positionAdvantage;
		}

		public String[] getCompanyLabelList() {
			return companyLabelList;
		}

		public void setCompanyLabelList(String[] companyLabelList) {
			this.companyLabelList = companyLabelList;
		}

		@Override
		public String toString() {
			return "CompanyInfo [companyShortName=" + companyShortName + ", companyId=" + companyId + ", createTime="
					+ createTime + ", positionId=" + positionId + ", positionAdvantage=" + positionAdvantage
					+ ", companyLabelList=" + Arrays.toString(companyLabelList) + "]";
		}
		
}
