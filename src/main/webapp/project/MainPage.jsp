<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/template/Top.jsp" />
<div class="container" style="margin-top: 250px;">
    <div class="row justify-content-center">
        <div class="col-md-6 text-center">
            <fieldset class="form-group border p-3" style="background-color: #ced4da;">
                <legend class="w-auto px-3">회원만 이용 가능 합니다</legend>
                <div class="d-grid gap-2">
                    <a href="<c:url value="/project/JoinMembership.jsp" />" class="btn btn-primary btn-lg mb-2">회원가입</a>
                    <a href="<c:url value="/project/LoginPage.jsp" />" class="btn btn-primary btn-lg mb-2">로그인</a>
                </div>
            </fieldset>
        </div>
    </div>
</div>
<!--container-->
<jsp:include page="/template/Footer.jsp" />
