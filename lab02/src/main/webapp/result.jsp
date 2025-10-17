<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lab02_result</title>
    <link rel="stylesheet" href="css/result_page_style.css">
</head>
<body>
<main>
    <div class="allSubmits">
        <h3>Request results</h3>
        <div class="scroll">
            <table>
                <thead>
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Result</th>
                    <th>Current time</th>
                    <th>Execution time</th>
                </tr>
                </thead>
                <tbody id="result">
                <c:forEach items="${requestScope.currentHistory.allRequests}" var="request">
                    <tr>
                        <td><fmt:formatNumber value="${request.x}" pattern="#.#######"/></td>
                        <td><fmt:formatNumber value="${request.y}" pattern="#.#######"/></td>
                        <td><fmt:formatNumber value="${request.r}" pattern="#.#######"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${request.result}">
                                    hit
                                </c:when>
                                <c:otherwise>
                                    miss
                                </c:otherwise>
                            </c:choose>
                        </td>                        <td>${request.currentTime}</td>
                        <td>${request.executionTime} ns</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <a id="link-to-home-page" href="${pageContext.request.contextPath}">Back to home page</a>
    </div>
</main>
</body>
</html>