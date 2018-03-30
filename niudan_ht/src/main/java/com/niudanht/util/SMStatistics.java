package com.niudanht.util;

//超级会员统计
public class SMStatistics {

	// 今日推广超级会员数
	public int TodayMemberNum;
	// 昨日推广超级会员数
	public int YesterdayMemberNum;
	// 今日推广超级会员商家数
	public int TodayCustomerNum;
	// 昨日推广超级会员商家数
	public int YesterdayCustomerNum;
	// 总计将推广超级会员数量
	public int AllMemberNum;
	// 当前已推广超级会员数量
	public int NowMemberNum;
	// 剩余未推广超级会员数量
	public int SurplusMemberNum;
	// 当前累计收入会费
	public float NowMemberCash;
	// 当前累计销售免费出蛋数
	public int NowEggNum;
	// 当前累计已免费出蛋次数
	public int NowOutEggNum;
	// 当前剩余免费出蛋次数
	public int NowSurplusEggNum;
	// 当前累计销售超级会员的商户数量
	public int AllCustomerNum;
	// 当前累计销售超级会员的商户佣金
	public float AllCustomerCash;

}
