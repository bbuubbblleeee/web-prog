// Валидация данных
const checkboxX = document.querySelectorAll('input[name="x"]');
const inputY = document.getElementById("input-y");
const inputR = document.getElementById("input-r");

function inputValidation(x, low, high, parameter){
    try{
        if (x === null || x === ""){
            showError(`The parameter ${parameter} can't be empty`)
            return false;
        }
        x = parseFloat(x);
        if (x < low || x > high){
            showError(`The parameter ${parameter} must be in [${low}; ${high}]`)
            return false
        }
        return true
    }
    catch (error){
        showError(error.message)
    }
}

function checkBoxValidation() {
    console.log(checkboxX.checked)
    for (let i = 0; i < checkboxX.length; i++){
        if (checkboxX[i].checked){
            return true
        }
    }
    showError("At least one option of checkbox must be chosen");
    return false
}


function validation() {
    return checkBoxValidation()
    && inputValidation(inputY.value, -3, 5, "y")
    && inputValidation(inputR.value, 2, 5, "r")
}

const form = document.getElementById("input-form");
form.addEventListener('submit',
    function (event){
        if (!validation()) {
            event.preventDefault();
            return
        }
        // localStorage.setItem("rValue", inputR.value)
        console.log(inputR.value)
    }
)

const inputs = document.querySelectorAll('input[type="number"]');

inputs.forEach(input => {
    input.addEventListener('invalid', function(e) {
        if (this.validity.badInput) {
            this.setCustomValidity('Enter a number');
        } else {
            this.setCustomValidity('');
        }
    });

    input.addEventListener('input', function() {
        this.setCustomValidity('');
    });
});


// Уведомление об ошибке клиента
const popUp = document.querySelector("div[class='pop-up']");
function showError(message){
    const span = document.getElementById("error-message");
    span.textContent = message;
    popUp.classList.add("show")
    setTimeout(() => popUp.classList.remove("show"), 5000)
}

const closeButton = document.querySelector("button[class='close-button']")
closeButton.addEventListener("click",
    () =>{
        popUp.classList.remove("show");
    })


// svg
const svg = document.getElementById("graph")
const points_holder = document.getElementById("points-holder")
const centerX = 200
const centerY = 200
const rPixels = 150;
svg.addEventListener("click", async function (event) {
    if (inputR.value == null || inputR.value === "") {
        showError("Parameter R must be chosen")
        event.preventDefault()
        return
    }
    if (!inputValidation(inputR.value, 2, 5, "R")){
        event.preventDefault()
        return
    }
    const rect = svg.getBoundingClientRect()
    const xPixels = event.clientX - rect.left
    const yPixels = event.clientY - rect.top
    const r = inputR.value
    const x = (xPixels - centerX) * r / rPixels
    const y = (centerY - yPixels) * r / rPixels

    const params = new URLSearchParams();
    params.append("x", x.toString())
    params.append("y", y.toString())
    params.append("r", r.toString())
    params.append("source", "graph")

    const response = await fetch(
        "", {
            method: "POST",
            headers: {
                "Content-Type":"application/x-www-form-urlencoded"
            },
            body: params.toString()
        }
    )
    if (!response.ok){
        showError(response.status)
    }
    const responseText = await response.json()
    points_holder.appendChild(drawPoint(xPixels, yPixels, responseText.result))
    // console.log(x)
    // console.log(y)
    // console.log("---")
    addToTable(Math.round(x * 10000000) / 10000000,
        Math.round(y * 10000000) / 10000000,
        Math.round(r * 10000000) / 10000000,
        responseText.result ? "hit" : "miss",
        responseText.currentTime,
        responseText.executionTime)
})

function drawPoint(xPixels, yPixels, result){
    const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    circle.setAttribute("r", "5")
    circle.setAttribute("cx", xPixels.toString())
    circle.setAttribute("cy", yPixels.toString())
    circle.setAttribute("stroke-width", "1")
    circle.setAttribute("fill", result ? "#00f71a" : "#f50c0b")
    console.log(circle.getAttribute("fill"))
    return circle
}

function showR(){
    const rValue = Math.round(inputR.value * 10000) / 10000
    if (inputR.value == null){
        return
    }
    const halfR = document.querySelectorAll(".rDiv2")
    halfR.forEach(r => r.textContent = (rValue/2).toString())

    const minusHalfR = document.querySelectorAll(".-rDiv2")
    minusHalfR.forEach(r => r.textContent = (-rValue/2).toString())

    const r = document.querySelectorAll(".r")
    r.forEach(r => r.textContent = rValue.toString())

    const minusR = document.querySelectorAll(".-r")
    minusR.forEach(r => r.textContent = (-rValue).toString())
}

function drawPointsFromTable(){
    points_holder.innerHTML = ""
    const allCells = document.querySelector("#result").querySelectorAll("tr")
    allCells.forEach(cell => {
        const params = cell.querySelectorAll("td")
        const x = +params[0].textContent
        const y = +params[1].textContent
        const r = +params[2].textContent
        const result = params[3].textContent.trim()
        const xPiexels = x * rPixels / inputR.value + centerX
        const yPiexels = - y * rPixels / inputR.value + centerY
        if (r === +inputR.value) {
            console.log("x - " + typeof x)
            console.log(x)
            console.log("r - " + typeof inputR.value)
            console.log(inputR.value)
            console.log("---")

            points_holder.appendChild(drawPoint(xPiexels, yPiexels, result === "hit"))
        }
    })

}

inputR.addEventListener("change", (event) => {
    if (!inputValidation(inputR.value, 2, 5, "R")){
        event.preventDefault()
        return
    }
    localStorage.setItem("rValue", inputR.value)
    showR()
        drawPointsFromTable()
    }
)


// Восстановление r
function restoreR(){
    console.log(localStorage.getItem("rValue"))
    if (localStorage.getItem("rValue") == null){
        return
    }
    inputR.value = localStorage.getItem("rValue")
    drawPointsFromTable()
}
restoreR()
showR()


// Работа с таблицей
const table = document.getElementById("result")

function addToTable(x, y, r, result, currentTime, executionTime){
    const row = document.createElement("tr")
    const all = [x, y, r, result, currentTime, executionTime]
    for (i = 0; i < 6; i++){
        const cell = document.createElement("td")
        cell.textContent = all[i]
        row.appendChild(cell)
    }
    table.insertBefore(row, table.firstChild);
}


