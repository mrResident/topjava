<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 08.12.18
  Time: 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add meal to Database</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h1><a href="meals">Go to Meals list</a></h1><br>
<h2>${param.get("page_caption")}</h2>
<c:if test="${mealBeen != null}">
    <jsp:useBean id="mealBeen" scope="request" type="ru.javawebinar.topjava.model.Meal" />
</c:if>
<form action = "<c:url value="meals"/>" method="post">
    <table>
        <tr>
            <td>Date:</td>
            <td>
                <label>
                    <input type="datetime-local" name="datetime-local" value="${mealBeen.dateTime}">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                Description:
            </td>
            <td>
                <input required type="text" name="description" placeholder="Description" value="${mealBeen.description}">
            </td>
        </tr>
        <tr>
            <td>
                Calories:
            </td>
            <td>
                <input required type="text" name="calories" placeholder="Calories" value="${mealBeen.calories}">
            </td>
        </tr>
    </table>
    <br><br>
    <input type="hidden" name="id_edit" value="${mealBeen.id}">
    <input type="hidden" name="action" value="create">
    <input type="submit" name="create" value="${param.get("button_caption")}">
</form>
</body>
</html>
