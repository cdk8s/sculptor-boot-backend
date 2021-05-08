/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：EmailStrategyInterface.java
 * 项目名称：sculptor-boot-starter-email
 * 项目描述：sculptor-boot-starter-email
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.email.strategy;



public interface EmailStrategyInterface {

	Boolean sendEmail(String receiverEmailAddress, String emailSubjectTitle, String emailHtmlBody);

}
