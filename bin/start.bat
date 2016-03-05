
@echo off
java -jar test_cashier.jar 8

@echo=
@echo=
@echo -------------------------------------------------
@echo 1、测试方法类：com.test.shoping.test.TestShoping
@echo 2、修改配制文件(conf/conf.xml)可实现下述功能：
@echo    1）、调整活动策略，修改结算方式
@echo    2）、支持修改店名
@echo    3）、打印支持多语言版本
@echo 3、支持两种基本活动
@echo    1）、打折活动
@echo    2）、买M个送N个活动
@echo 4、活动特点
@echo    1）、可为活动设置活动开始日期和截止日期
@echo    2）、可设置参加活动商品
@echo    3）、可设置所有商品都纳入活动（默认false）
@echo    4）、一个商品可参加多个活动
@echo 5、继承ActiveStrategy类，可扩展新的活动策略，新增结算策略

@echo -------------------------------------------------

pause