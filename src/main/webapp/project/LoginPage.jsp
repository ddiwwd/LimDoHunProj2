<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/template/Top.jsp" %>    
    <div class="container" style="margin-top:50px">
         <div class="jumbotron bg-secondary">
           <h1>
						로그인	<small>로그인 하세요</small>
			</h1>       
        </div><!--jumbotron-->
        
	    <span class="font-weight-bold text-danger"></span>
		<form class="form-inline" action="<c:url value="/myproject/Password.ict"/>" method="POST">
			<label>아이디</label> 
			<input type="text" name="username" class="form-control mx-2" value="${username}" /> 
			<label>비밀번호</label> 
			<input type="password" name="password" class="form-control mx-2" value="" /> 
			<input type="submit" class="btn btn-danger mx-2" value="로그인" />
		</form> 
		  
    </div><!--container-->
<%@ include file="/template/Footer.jsp" %>