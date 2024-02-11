<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/template/Top.jsp" %>    
    <div class="container" style="margin-top:50px">
        <div class="jumbotron bg-seconda">
        		<h1>
						회원정보 수정		<small>회원정보 수정 페이지</small>
				</h1>
         
        </div><!--jumbotron-->
         <form action="<c:url value="/myproject/MemberEdit.ict"/>" method="post">
			
			<div class="form-group">
				<label><kbd class="lead">아이디</kbd></label> <input type="text"
					class="form-control" name="username" value="${username}">
			</div>
			<div class="form-group">
				<label><kbd class="lead">비밀번호</kbd></label> <input type="password"
					class="form-control" name="password" placeholder="비밀번호">
			</div>

			<div class="form-group">
				<label><kbd class="lead">성별</kbd></label>
				<div class="d-flex">
					<div class="custom-control custom-radio mr-2">
						<input type="radio" class="custom-control-input" name="gender"
							value="M" id="male1"> <label for="male1"
							class="custom-control-label">남자</label>
					</div>
					<div class="custom-control custom-radio">
						<input type="radio" class="custom-control-input" name="gender"
							value="F" id="female1"> <label for="female1"
							class="custom-control-label">여자</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label><kbd class="lead">관심사항</kbd></label>
				<div class="d-flex">
					<div class="custom-control custom-checkbox">
						<input type="checkbox" class="custom-control-input" name="inters"
							value="POL" id="POL1"> <label
							class="custom-control-label" for="POL1">정치</label>
					</div>
					
					<div class="custom-control custom-checkbox mx-2">
						<input type="checkbox" class="custom-control-input" name="inters"
							value="ECO" id="ECO1"> <label
							class="custom-control-label" for="ECO1">경제</label>
					</div>
					
					<div class="custom-control custom-checkbox ml-2">
						<input type="checkbox" class="custom-control-input" name="inters"
							value="SPO" id="SPO1"> <label
							class="custom-control-label" for="SPO1">스포츠</label>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label><kbd class="lead">학력사항</kbd></label> <select name="education"
					class="custom-select mt-3 custom-select-lg">
					<option value="">학력을 선택하세요</option>
					<option value="ELE">초등학교</option>
					<option value="MID">중학교</option>
					<option value="HIG">고등학교</option>
					<option value="UNI">대학교</option>
				</select>
			</div>
			
			<div class="form-group">
				<label><kbd class="lead">자기소개</kbd> </kbd></label>
				<textarea class="form-control" rows="5" name="selfintroduce">
				
				</textarea>
			</div>
			<button type="submit" class="btn btn-primary">확인</button>
		</form>
        </div><!--container-->
<%@ include file="/template/Footer.jsp" %>