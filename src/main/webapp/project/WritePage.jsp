<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 로그인 여부 판단:필터 사용시 아래 주석처리 -->
<%--@ include file="/common/IsMember.jsp"--%>
<jsp:include page="/template/Top.jsp" />
<div class="container" style="margin-top: 50px">
	<div class="jumbotron bg-secondary">
		<h1>
						게시판	<small>등록 페이지</small>
				</h1>
	</div>
	<!--jumbotron-->
	<form method="post" action="<c:url value="/myproject/write.ict"/>" >
	<input type="hidden" name="username" value="${sessionScope.username }">
		<div class="form-group" >
			<label><kbd class="lead">제목</kbd></label> <input type="text"
				class="form-control" placeholder="제목을 입력하세요" name="title">
		</div>
		<div class="form-group">
			<label><kbd class="lead">내용</kbd></label>
			<textarea class="form-control" rows="5" name="content"></textarea>
		</div>
		<button type="submit" class="btn btn-primary">등록</button>
	</form>
</div>
<!--container-->
<jsp:include page="/template/Footer.jsp" />
