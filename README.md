200702 목요일
웹 7일차
오늘 배운 내용
- #### 자바와 데이터베이스 연동
  
  1. ##### 오라클 JDBC 드라이버 연동
  2. ##### ConnectionProvider, DBCPInit, JdbcUtil 클래스 생성(conn 객체를 생성하고 닫기 위한 클래스들)
3. ##### DAO 클래스 생성(conn 객체를 생성하고 쿼리를 DB에 던져주고 결과 받는 클래스)

- #### 회원등록 기능

  1. ##### 회원가입 버튼을 누르면 회원가입폼을 보여주는 커맨드클래스

  ```java
  public class RegisterCommand implements Command {
  
  	@Override
  	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
  		
  		return "/WEB-INF/views/member/register.jsp";
  	
  	}
  }
  ```

  2. ##### 회원가입 폼

  ```html
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!DOCTYPE html>
  <c:set var="root" value="${ pageContext.request.contextPath }"/>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <link rel="stylesheet" href="./../css/member/registerStyle.css">
  <script type="text/javascript" src="${root}/javascript/member/register.js"></script>
  </head>
  <body>
  	
  	<div id=memberform>
  	<span style="display:block;text-align:center;">회원가입( * 필수입력사항입니다.)</span>
  	<form action="${root}/member/registerOk.do" method="post" class="form"
  			onsubmit="return createForm(this)" name="createChkForm">
  		<div class="label">
  			<label>아이디</label>
  			*<input type="text" name="id"/>
  			<button type="button" onclick="idCheck(createChkForm, ' ${root}')" >아이디중복확인</button>
  		</div>
  		<div class="label">
  			<label>비밀번호</label>
  			*<input type="password" name="password"/>
  		</div>
  		<div class="label">
  			<label>비밀번호확인</label>
  			*<input type="password" name="passwordCheck"/>
  		</div>
  		<div class="label">
  			<label>이름</label>
  			*<input type="text" name="name"/>
  		</div>
  		<div class="label">
  			<label>주민번호</label>
  			*<input type="text" name="jumin1" /> -
  			<input type="text" name="jumin2" style="margin-left:0px;"/>
  		</div>
  		<div class="label mar">
  			<label>이메일</label>
  			<input type="text" name="email"/>
  		</div>
  		<div class="label mar">
  			<label>우편번호</label>
  			<input type="text" name="zipcode"/>
  			<button type="button" name="idChk" onclick="zipCode('${root}')">우편번호검색</button>
  		</div>
  		<div class="label mar">
  			<label>주소</label>
  			<input type="text" name="address"/>
  		</div>
  		<div class="label">
  			<label>직업</label>
  			<select name="job">
  				<option value="판사" selected>판사</option>
  			    <option value="의사">의사</option>
  			    <option value="변호사">변호사</option>
  			    <option value="회계사">회계사</option>
  			</select>
  		</div>
  		<div class="label">
  			<label>메일수신</label>
  			<input type="checkbox" name="mailing" value="Yes">Yes
  		    <input type="checkbox" name="mailing" value="No">No
  		</div>
  		<div class="label">
  			<label>관심분야</label>
  			<input type="checkbox" name="interest" value="경제">경제
  		    <input type="checkbox" name="interest" value="IT">IT
  		    <input type="checkbox" name="interest" value="음악">음악
  		    <input type="checkbox" name="interest" value="미술">미술
  		    <input type="hidden" name="resultInterest" />
  		</div>
  		<div class="label" style="border-bottom: solid 0px; text-align: center;">
  			<input type="submit" value="가입"/>
  			<input type="reset" value="취소"/>
  		</div>
  	</form>
  	</div>
  </body>
  </html>
  ```

  3. ##### 회원가입 내용을 입력하고 가입 버튼을 누르면 DB에 저장하는 커맨드 클래스

  ```java
  public class RegisterOkCommand implements Command {
  
  	@Override
  	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
  		request.setCharacterEncoding("utf-8");
  		
  		MemberDto memberDto = new MemberDto();
  		
  		memberDto.setId(request.getParameter("id"));
  		memberDto.setPassword(request.getParameter("password"));
  		memberDto.setName(request.getParameter("name"));
  		memberDto.setJumin1(request.getParameter("jumin1"));
  		memberDto.setJumin2(request.getParameter("jumin2"));
  		memberDto.setEmail(request.getParameter("email"));
  		memberDto.setZipcode(request.getParameter("zipcode"));
  		memberDto.setAddress(request.getParameter("address"));
  		memberDto.setJob(request.getParameter("job"));
  		memberDto.setMailing(request.getParameter("mailing"));
  		memberDto.setInterest(request.getParameter("resultInterest"));
  		memberDto.setMemberLevel("BB");
  		
  		logger.info(logMsg + memberDto.toString());
  		
  		MemberDao dao = MemberDao.getInstance();
  		int check = dao.insert(memberDto);
  		logger.info(logMsg + check);
  		
  		request.setAttribute("check", check);
  	
  		return "/WEB-INF/views/member/registerOk.jsp";
  	}
  }
  ```

  > 사용자가 요청한 데이터를 getParameter로 받아와서 memberDto 객체에 다 넣어주고
  > 데이터가 다 담긴 Dto 객체를 dao에게 전달하여 DB에 최종적으로 저장한다.
  > dao에서 반환된 결과 값으로 회원등록 성공여부를 판단하여 뷰를 뿌린다.

  4. ##### 회원데이터를 DB에 최종적으로 저장하는 Dao 클래스

  ```java
  public int insert(MemberDto memberDto) {
  		Connection conn = null;
  		PreparedStatement pstmt = null;
  		int value = 0;
  		
  		try {
  			String sql = "insert into member values(member_num_seq.nextval, ?,?,?,?,?,?,?,?,?,?,?,?, sysdate)";
  			conn = ConnectionProvider.getConnection();
  			pstmt = conn.prepareStatement(sql);
  			
  			pstmt.setString(1, memberDto.getId());
  			pstmt.setString(2, memberDto.getPassword());
  			pstmt.setString(3, memberDto.getName());
  			pstmt.setString(4, memberDto.getJumin1());
  			pstmt.setString(5, memberDto.getJumin2());
  			
  			pstmt.setString(6, memberDto.getEmail());
  			pstmt.setString(7, memberDto.getZipcode());
  			pstmt.setString(8, memberDto.getAddress());
  			pstmt.setString(9, memberDto.getJob());
  			pstmt.setString(10, memberDto.getMailing());
  			
  			pstmt.setString(11, memberDto.getInterest());
  			pstmt.setString(12, memberDto.getMemberLevel());
  			
  			value = pstmt.executeUpdate();
  		} catch(SQLException e) {
  			e.printStackTrace();
  		} finally {
  			JdbcUtil.close(pstmt);
  			JdbcUtil.close(conn);
  		}
  		
  		return value;
  	}
  ```

  5. ##### dao 객체가 결과 값을 반환 받아서 가입성공여부를 사용자 화면에게 뿌려주는 JSP

  ```html
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
  <%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  </head>
  <body>
  	<c:set var="root" value="${pageContext.request.contextPath}"/>
  	<c:if test="${check > 0}">
  		<script type="text/javascript">
  			alert("회원가입이 완료되었습니다.");
  			location.href="${root}/member/register.do";
  		</script>
  	</c:if>
  	<c:if test="${check == 0}">
  		<script type="text/javascript">
  			alert("회원가입이 안되었습니다.");
  			location.href="${root}/member/register.do";
  		</script>
  	</c:if>
  </body>
  </html>
  ```

  

- #### 회원등록시 중복 ID 체크 기능

  1. ##### 자바스크립트를 이용하여 새창을 띄운뒤 URL를 요청하여 데이터 전달
  ```javascript
    function idCheck(obj, root){
        if(obj.id.value == ""){
            alert("아이디를 입력하세요");
            obj.id.focus();
            return false;
        }
        var url = root + "/member/idCheck.do?id=" + obj.id.value;
        //console.log(url);
        window.open(url, "", "width=400, height=200");
    }
  ```

  2. ##### 아이디 정보를 받아 DB에 저장된 아이디와 비교하는 커맨드 클래스
  
  ```java
  public class IdCheckCommand implements Command{
  	@Override
  	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
  		String id = request.getParameter("id");
  		logger.info(logMsg + id);
  		
  		int check = MemberDao.getInstance().idCheck(id);
  		logger.info(logMsg + check);
  		
  		request.setAttribute("check", check);
  		request.setAttribute("id", id);
  		
  		return "/WEB-INF/views/member/idCheck.jsp";
  	}
  }
  ```
  
  > 사용자에게 받은 id 값을 dao에게 전달하여 DB에 아이디와 비교하여 중복되는 아이디가 있으면
  > 1을 반환하고 없으면 0을 반환한다.
  
  3. ##### dao가 반환한 값을 받아 사용자 화면에 뿌려주는 JSP
  
  ```jsp
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  </head>
  <body>
  	<c:set var="root" value="${ pageContext.request.contextPath }"/>
  	<c:if test="${check != 0}">
  		<div align="center">
  			이미 사용중입니다.
  			<form action="${root}/member/idCheck.do" method="get">
  				<input type="text" name="id"/>
  				<input type="submit" value="확인"/>
  			</form>
  		</div>
  	</c:if>
  	<c:if test="${check == 0}">
  		<div align="center">
  			사용 가능한 아이디 입니다.
  		</div>
  		<script type="text/javascript">
  			opener.createChkForm.id.value = ${ id };
  		</script>
  	</c:if>
  	<div align="center">
  		<a href="javascript:self.close()">닫기</a>
  	</div>
  </body>
  </html>
  ```
  
  > 자바에서 넘어온 데이터( ${check} )로 중복여부를 체크하고 사용 가능한 아이디면은
  > `opener.createChkForm.id.value`로 아이디 값을 넘겨주게 되면 회원가입 폼에게 value가 전달되어 아이디 값을 input에 보낼 수 있게 된다.