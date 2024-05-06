<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Board UpdateForm</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/detail.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>

    <%-- ckeditor 관련 자바 스크립트  --%>
    <script src="https://cdn.ckeditor.com/ckeditor5/12.4.0/classic/ckeditor.js"></script>
    <script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>

    <style type="text/css">
        .ck.ck-editor {
            width: 90%;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<!-- 내용 -->
<form id="updateForm" method="post" action="update" enctype="multipart/form-data">
    <%-- csrf 토큰 설정 --%>
    <sec:csrfInput/>

    <input type="hidden" name="board_id" value="${board.board_id}">
    <div class="detail-container">
        <div class="detail-inner-container">

            <div class="top-bno-container">
                <div class="top-bno">
                    No.     ${board.board_id}
                </div>
            </div>

            <div class="detail">
                <div class="detail-title">
                    <div class="title">
                        제목
                    </div>

                    <input type="text" name="board_title" id="board_title" class="content-input" value="${board.board_title}">
                </div>

                <div class="detail-content">
                    <div class="title">
                        내용
                    </div>

                    <textarea name="board_content" id="board_content" class="content-input" >${board.board_content}</textarea>

                </div>

                <div class="detail-file">
                    <div class="title">
                        첨부파일
                    </div>

                    <div class="file-content">
                        <!-- 파일 수정을 위한 input 태그 -->
                        <input type="file" name="file" id="file" class="file-input">
                    </div>
                </div>

                <div class="detail-writer">
                    <div class="title">
                        작성자
                    </div>

                    <div class="content">
                        ${board.board_writer}
                    </div>
                </div>

                <div class="detail-date">
                    <div class="title">
                        작성일
                    </div>

                    <div class="content">
                        <fmt:formatDate value="${board.board_date}" pattern="yyyy.MM.dd" />
                    </div>
                </div>



                <div class="detail-btn-container">
                    <a href="javascript:jsUpdate()" class="modify-btn">수정</a>
                </div>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">
    const input = document.getElementById('file');

    // 파일명 출력
    const myFile = new File([], '${board.boardFileVO.original_filename}');

    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(myFile);
    input.files = dataTransfer.files;


    // 페이지의 메타 태그 중에서 이름이 _csrf_parameter인 것을 찾아
    // 해당 내용(content)을 가져와 csrfParameter 변수에 저장
    // csrf 토큰의 매개변수 이름을 가져오는 것
    const csrfParameter = document.querySelector("meta[name='_csrf_parameter']").content;

    // 페이지의 메타 태그 중에서 이름이 _csrf인 것을 찾아
    // 해당 내용(content)을 가져와 csrfToken 변수에 저장
    // 실제 CSRF 토큰 값을 가져오는 것
    const csrfToken = document.querySelector("meta[name='_csrf']").content;

    // 이미지 업로드를 위한 URL 생성
    // ckfinder의 이미지 업로드 URL에 get방식으로 board_token, csrf token을 추가
    const board_image_url = "<c:url value="/board/boardImageUpload?board_token=${board_token}&"/>" + csrfParameter + "=" + csrfToken;

    // ck-editor
    let board_content; // ck-editor의 객체를 저장하기 위한 변수
    ClassicEditor.create(document.querySelector('#board_content'), {
        // 이미지 업로드 URL 설정
        ckfinder : {
            uploadUrl : board_image_url
        }
    })
        .then(editor => {
            console.log('편집기 초기화');
            window.board_content = editor;
        })
        .catch(error => {
            console.error(error);
        });

    function jsTest() {
        alert(window.board_content.getData());
    }


    /* 수정 */
    function jsUpdate() {
        document.getElementById("board_content").value = window.board_content.getData();
        if (confirm("수정하시겠습니까?")) {
            myFileFetch("update", "updateForm", json => {
                if (json.status === 0) {
                    // 성공
                    console.log("제대로냐" + document.getElementById("board_content").value);

                    alert(json.statusMessage);
                    location.href = "detail?board_id=${board.board_id}";
                } else {
                    alert(json.statusMessage);
                }
            });
        }
    }

</script>

</body>
</html>