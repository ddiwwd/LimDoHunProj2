<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<jsp:include page="/template/Top.jsp" />
<div class="container" style="margin-top: 50px">
	<div class="jumbotron bg-secondary">
		<h1>
			자료실 상세 <small>상세보기 페이지 입니다</small>
		</h1>
	</div>
	<table class="table table-bordered">
		<tbody class="table-sm">
			<tr>
				<th class="w-25 bg-dark text-white text-center">번호</th>
				<td>${records.no}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">작성자</th>
				<td>${records.username}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">작성일</th>
				<td>${records.postDate}</td>
			</tr>
			<tr>
                <th class="w-25 bg-dark text-white text-center">조회수</th>
                <td>${records.visitcount}</td>
            </tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">제목</th>
				<td>${records.title}</td>
			</tr>
			<tr>
				<th class="bg-dark text-white text-center" colspan="2">내 용</th>
			</tr>
			<tr>
				<td colspan="2">${records.content}</td>
			</tr>
		</tbody>
	</table>

	<!-- 수정/삭제/목록 컨트롤 버튼 -->
	<div class="text-center">
<%-- /myproject/Edit.ict? no=${records.no}&${PagingUtil.NOWPAGE}=${param.nowPage} --%>
		<c:if test="${sessionScope.username eq records.username}">
		<a href="#" class="btn btn-success password-update-delete"
			 data-target="#passwordModal" data-toggle="modal"
			data-backdrop="static">수정</a> 
			
		<%-- ${pageContext.request.contextPath}/myproject/Delete.ict? no=${records.no} --%>
		<a href="#" class="btn btn-success password-update-delete"
			 data-target="#passwordModal" 
			 data-backdrop="static">삭제</a> 
		</c:if>
		 <a	href="<c:url value="/myproject/List.ict"/>" class="btn btn-success">목록</a>
	</div>
</div>
<!-- 수정/삭제시 사용할 모달 시작 -->
<div class="modal fade" id="passwordModal">	
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">비밀번호 입력창</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<!-- Modal body -->
			<div class="modal-body d-flex justify-content-center">
				<form class="form-inline" action="<c:url value="/myproject/PasswordCheck.ict"/>" method="POST">					
					<!-- 키값 -->
					<input type="hidden" name="no" value="${records.no}"/>
					<!-- 현재 페이지 번호 -->
					<input type="hidden" name="nowPage" value="${param.nowPage}"/>
					<input type="hidden" name="pageSize" value="${param.pageSize}"/>
					<label>비밀번호</label>
					<input type="password" name="password" class="form-control mx-2" placeholder="비밀번호를 입력하세요"/>
					<input type="submit" class="btn btn-danger mx-2" value="확인" />
				</form>			
			</div>
		</div>
	</div>
</div>
<script>
	//수정/삭제버튼 클릭시 이벤트 처리
	var mode=document.querySelector('input[username=mode]');
	var buttons = document.querySelectorAll('.password-update-delete');
	var modal_title = document.querySelector('.modal-title');
	buttons.forEach(function(button){
		button.onclick=(e)=>{
			console.log(e.target.textContent);
			if(e.target.textContent==="수정"){
				mode.value="UPDATE";
				modal_title.textContent='수정용 비밀번호 입력';
			}
			else{
				if(confirm('정말로 삭제 하시겠습니까?')){
					location.href='${pageContext.request.contextPath}/myproject/Delete.ict?no=${records.no}&nowPage=${param.nowPage}&pageSize=${param.pageSize}';
					/* mode.value="DELETE";
					//data-toggle="modal" 속성 추가 그래야 모달창이 뜬다
					e.target.setAttribute("data-toggle",'modal');
					modal_title.textContent='삭제용 비밀번호 입력';
					//바로 속성 지우면 모달창이 안뜨니까 띄 운후 몇 초후 속성 제거
					setTimeout(() => {
						e.target.removeAttribute("data-toggle");
					}, 2000); */
				}				
			}/////////			
		};
		
	});
</script>

<jsp:include page="/template/Footer.jsp" />
		