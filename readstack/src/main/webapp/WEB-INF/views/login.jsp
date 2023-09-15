<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Zaloguj się - ReadStack</title>
    <%@ include file="../segments/stylesheets.jspf"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/forms.css">
</head>
<body>
<div class="container">
    <%@ include file="../segments/header.jspf"%>

    <form action="j_security_check" method="post" class="user-form">
        <h2 class="user-form-title">Zaloguj się na ReadStack</h2>
        <input name="j_username" placeholder="Nazwa użytkownika" required>
        <input name="j_password" type="password" placeholder="Hasło" required>
        <button class="user-form-button">Zaloguj się</button>
        <p>Nie masz konta?<a href="${pageContext.request.contextPath}/signup">Zarejestruj się</a></p>
    </form>

    <%@ include file="../segments/footer.jspf"%>>
</div>
</body>
</html>