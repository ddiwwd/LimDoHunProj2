<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 로그인 여부 판단:필터 사용시 아래 주석처리 -->
<%--@ include file="/common/IsMember.jsp"--%>
<jsp:include page="/template/Top.jsp" />
<div class="container" style="margin-top: 50px">
	<div class="jumbotron bg-secondary">
		<h1>
						글 수정	<small>수정 페이지</small>
				</h1>
	</div>
	<!--jumbotron-->
	<form method="post" action="<c:url value="/myproject/Edit.ict"/>" >
	<input type="hidden" name="username" value="${sessionScope.SUCCFAIL }">
		<div class="form-group" >
		<input type="hidden" name="no" value="${no}">
		<input type="hidden" name="nowPage" value="${param.nowPage}"/>
		<input type="hidden" name="pageSize" value="${param.pageSize}"/>
			<label><kbd class="lead">제목</kbd></label> <input type="text"
				class="form-control" placeholder="제목을 입력하세요" name="title" value="${records.title }">
		</div>
		<div class="form-group">
			<label><kbd class="lead">내용</kbd></label>
			<textarea class="form-control" rows="5" name="content" >${records.content }</textarea>
		</div>
		<button type="submit" class="btn btn-primary">수정</button>
	</form>
</div>
<!--container-->
<jsp:include page="/template/Footer.jsp" />
