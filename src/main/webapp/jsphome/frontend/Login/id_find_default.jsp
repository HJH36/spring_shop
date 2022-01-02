<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div>

	<div style="margin: 20px; width: 500px;">
		<form name="FindForm" method="POST">

			<input class="form-control input-sm" type="text" name="member_name" value="" style="margin-bottom: 5px; width: 200px;" placeholder="회원 이름"> <br>
			 <input class="form-control input-sm" type="text" name="member_email" value="" style="margin-bottom: 5px; width: 200px;" placeholder="회원 이메일"> <br>

			<button class="btn btn-default btn-sm" type="button" OnClick="send_login();" style="margin-bottom: 5px;">회원아이디 찾기</button>
			<button class="btn btn-default btn-sm" type="button" OnClick="reset();" style="margin-bottom: 5px;">취소</button>

		</form>
	</div>

</div>


<script language="javascript">

	//로그인 보내기
	function send_login() {
		var obj = document.FindForm;

		if (obj.member_name.value == "") {
			alert("이름을 입력해 주세요.");
			obj.member_id.focus();
			return false;
		}

		if (obj.member_email.value == "") {
			alert("이메일을 입력해 주세요.");
			obj.member_pwd.focus();
			return false;
		}

		obj.action = "/id_find_ok.do";
		obj.submit();

	}
</script>
