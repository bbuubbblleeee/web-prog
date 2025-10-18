<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%
    double[] allX = {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2};
    pageContext.setAttribute("allX", allX);
%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lab02</title>
    <link rel="stylesheet" href="css/home_page_style.css">
</head>
<body>
<header>
    <p id="name">Makhneva Irina Alexandrovna</p>
    <p id="group">P3217</p>
    <p id="variant">Variant: 2007</p>
</header>
<main>
    <div class = "leftSection">
            <svg width="450" height="450" id="graph" style="margin-top: 20px">
                <defs>
                    <marker id="arrowhead" markerWidth="10" markerHeight="7"
                            refX="8.5" refY="3.5" orient="auto">
                        <polygon points="0 0, 10 3.5, 0 7" fill="currentColor"></polygon>
                    </marker>
                </defs>
                <polygon id="triangle" points="125 200, 200 350, 200 200" fill="blue"></polygon>
                <polygon id="square" points="200 200, 350 200, 350 350, 200 350" fill="blue"></polygon>
                <path d="
                    M 200 200,
                    L 350 200,
                    A 150 150 0 0 0 200 50
                    Z"
                      fill="blue" stroke="blue"></path>
                <line id="xLine" x1="0" y1="200" x2="400" y2="200" stroke="white" stroke-width="2" marker-end="url(#arrowhead)"></line>
                <line id="yLine" x1="200" y1="400" x2="200" y2="3" stroke="white" stroke-width="2" marker-end="url(#arrowhead)"></line>

                <line class="stick" x1="275" y1="195" x2="275" y2="205" stroke="white" stroke-width="2"></line>
                <line class="stick" x1="350" y1="195" x2="350" y2="205" stroke="white" stroke-width="2"></line>

                <line class="stick" x1="125" y1="195" x2="125" y2="205" stroke="white" stroke-width="2"></line>
                <line class="stick" x1="50" y1="195" x2="50" y2="205" stroke="white" stroke-width="2"></line>

                <line class="stick" x1="195" y1="125" x2="205" y2="125" stroke="white" stroke-width="2"></line>
                <line class="stick" x1="195" y1="50" x2="205" y2="50" stroke="white" stroke-width="2"></line>

                <line class="stick" x1="195" y1="275" x2="205" y2="275" stroke="white" stroke-width="2"></line>
                <line class="stick" x1="195" y1="350" x2="205" y2="350" stroke="white" stroke-width="2"></line>

                <text class="names" x="400" y="220" fill="white" stroke="white" stroke-width="0">x</text>
                <text class="names" x="210" y="10" fill="white" stroke="white" stroke-width="0">y</text>


                <text class="rDiv2" x="275" y="225" fill="white" stroke="white" stroke-width="0" text-anchor="middle">R/2</text>
                <text class="rDiv2" x="190" y="130" fill="white" stroke="white" stroke-width="0" text-anchor="end">R/2</text>

                <text class="-rDiv2" x="125" y="225" fill="white" stroke="white" stroke-width="0" text-anchor="middle">-R/2</text>
                <text class="-rDiv2" x="190" y="280" fill="white" stroke="white" stroke-width="0" text-anchor="end">-R/2</text>

                <text class="r" x="350" y="225" fill="white" stroke="white" stroke-width="0" text-anchor="middle">R</text>
                <text class="r" x="190" y="55" fill="white" stroke="white" stroke-width="0" text-anchor="end">R</text>

                <text class="-r" x="50" y="225" fill="white" stroke="white" stroke-width="0" text-anchor="middle">-R</text>
                <text class="-r" x="190" y="355" fill="white" stroke="white" stroke-width="0" text-anchor="end">-R</text>

                <text x="185" y="220" fill="white" stroke="white" stroke-width="0">0</text>
                <g id="points-holder"></g>

            </svg>
        <div id="submit-container">
            <form method="post" action="" id="input-form">
                <div id="input-container">
                    <div id="x-choose">
                        <label>Choose X</label>
                        <div id="xs">
                            <c:forEach items="${allX}" var="x" varStatus="index">
                                <label class="checkbox-label" for="x-${index.count}">
                                <input id="x-${index.count}" type="checkbox" name="x" value="${x}">
                                    <span class="custom-box"></span>

                                    <span class="checkbox-text">${x}</span>
                                </label>
                            </c:forEach>
                        </div>
                    </div>

                    <div id="text-input">
                        <label for="input-y" class="inputLabel">Enter Y
                            <input id="input-y" name="y" type="number" step="any">
                        </label>

                        <label for="input-r" class="inputLabel">Enter R
                            <input id="input-r" name="r" type="number" step="any">
                        </label>
                    </div>
                </div>
                <button id="submit" type="submit">Submit</button>
            </form>
        </div>
    </div>

    <div class = "rightSection">
        <div class="allSubmits">
            <h3>All submits</h3>
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
                    <c:forEach items="${sessionHistory.allRequests}" var="request">
                        <fmt:setLocale value="en_US" scope="session"/>
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
                            </td>
                            <td>${request.currentTime}</td>
                            <td>${request.executionTime} ns</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="pop-up">
        <h3>Error</h3>
        <span id="error-message">
        </span>
        <button class="close-button">&#x2715</button>
    </div>
</main>
<script src="${pageContext.request.contextPath}/js/script.js">
</script>
</body>
</html>