package com.disclosureInfo.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.disclosureInfo.commons.Action;
import com.disclosureInfo.commons.ActionForward;

public class indexAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("indexAction 실행");
		
		//기본 준비
		String stckIssuCmpyNm=request.getParameter("stckIssuCmpyNm");
		if(stckIssuCmpyNm==null) {
			stckIssuCmpyNm="삼성전자";
		}
		
//		LocalDate date = LocalDate.now().minusYears(4).withMonth(1).withDayOfMonth(1);
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//	    String formattedDate = date.format(formatter);
	    //기본 준비

	    //api
		//주식발행정보
		System.out.println("주식 발행정보 실행");
		 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1160100/service/GetStocIssuInfoService/getItemBasiInfo"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=YWtGg9%2B2FZEgzpo5%2BhUbOtQysJ5bZm1IM6UKasT1d72tBaNezmjppy5vQezghnBnt1IbIZHmA%2Fl%2BeN84XqkmHA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*결과형식(xml/json)*/
	        urlBuilder.append("&" + URLEncoder.encode("stckIssuCmpyNm","UTF-8") + "=" + URLEncoder.encode(stckIssuCmpyNm, "UTF-8")); /*주식발행사의 명칭*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString());
	        //주식발행정보
	        
	        //주식배당정보
	        System.out.println("주식 배당정보 실행");
	        StringBuilder urlBuilder2 = new StringBuilder("http://apis.data.go.kr/1160100/service/GetStocDiviInfoService/getDiviInfo"); /*URL*/
	        urlBuilder2.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=YWtGg9%2B2FZEgzpo5%2BhUbOtQysJ5bZm1IM6UKasT1d72tBaNezmjppy5vQezghnBnt1IbIZHmA%2Fl%2BeN84XqkmHA%3D%3D"); /*Service Key*/
	        urlBuilder2.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder2.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder2.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*결과형식(xml/json)*/
	        urlBuilder2.append("&" + URLEncoder.encode("stckIssuCmpyNm","UTF-8") + "=" + URLEncoder.encode(stckIssuCmpyNm, "UTF-8")); /*주식발행사의 명칭*/
//	        urlBuilder2.append("&" + URLEncoder.encode("basDt","UTF-8") + "=" + URLEncoder.encode(formattedDate, "UTF-8")); /*작업 또는 거래의 기준이 되는 일자(년월일)*/
	        URL url2 = new URL(urlBuilder2.toString());
	        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
	        conn2.setRequestMethod("GET");
	        conn2.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn2.getResponseCode());
	        BufferedReader rd2;
	        if(conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300) {
	            rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
	        } else {
	            rd2 = new BufferedReader(new InputStreamReader(conn2.getErrorStream()));
	        }
	        StringBuilder sb2 = new StringBuilder();
	        String line2;
	        while ((line2 = rd2.readLine()) != null) {
	            sb2.append(line2);
	        }
	        rd2.close();
	        conn2.disconnect();
	        System.out.println(sb2.toString());
	        //주식배당정보
	        //api
		
		
	        //데이터 처리
	        final String STOCK_DATA=sb.toString();
	        request.setAttribute("STOCK_DATA", STOCK_DATA);
	        final String DIVIDEND_DATA=sb2.toString();
	        request.setAttribute("DIVIDEND_DATA", DIVIDEND_DATA);
		ActionForward forward = new ActionForward();
		forward.setPath("./index.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
