<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta charset="UTF-8">
<title>종목정보</title>
<script type="text/javascript">
$(function() {
    processData('${STOCK_DATA}', '#info');
    processDividendData('${DIVIDEND_DATA}', '#dividend');
});

function processData(data, targetSelector) {
	var jsonObject = JSON.parse(data);
    var itemList = jsonObject.response.body.items.item;
    
    var item = itemList[0];
    var formattedStckCnt = parseInt(item.issuStckCnt).toLocaleString('ko-KR');
    $(targetSelector).append("상장사 명 : " + item.stckIssuCmpyNm);
    $(targetSelector).append("<br>");
    $(targetSelector).append("유가증권 종류 : " + item.scrsItmsKcdNm);
    $(targetSelector).append("<br>");
    $(targetSelector).append("발행 주식 수 : " + formattedStckCnt + "주");
}

function processDividendData(data, targetSelector) {
    var jsonObject = JSON.parse(data);
    var itemList = jsonObject.response.body.items.item;
	
   
    for (var i = itemList.length-1; i >= itemList.length-5	; i--) {
    	var item = itemList[i];
    	
    	var formattedcashDvdnPayDt = formatDate(item.cashDvdnPayDt);
        var formatteddvdnBasDt = formatDate(item.dvdnBasDt);
	
        $(targetSelector).append("배당 지급일 : " + formattedcashDvdnPayDt);
        $(targetSelector).append("<br>");
        $(targetSelector).append("배당 기준일 : " + formatteddvdnBasDt);
        $(targetSelector).append("<br>");
        $(targetSelector).append("배당 종류 : " + item.stckDvdnRcdNm);
        $(targetSelector).append("<br>");
        $(targetSelector).append("<br>");
    }
}

function formatDate(dateString) {
    var year = dateString.substr(0, 4);
    var month = dateString.substr(4, 2);
    var day = dateString.substr(6, 2);
    return year + "-" + month + "-" + day;
}
</script>
</head>
<body>
	<h1><a href="index.dis">stock_info</a></h1>
<hr>
	<form action="index.dis">
		<input type="text" name="stckIssuCmpyNm" placeholder="증권 발행사 명을 적어주세요">
		<input type="submit" value="검색">
	</form>
<hr>
	<h2>발행정보</h2>
	<div id="info"></div>
	<h2>배당정보</h2>
	<div id="dividend"></div>
</body>
</html>