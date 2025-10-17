const inputX = document.getElementById("inputX");
const inputR = document.getElementById("inputR");
const selectY = document.getElementById("selectY");
const table = document.getElementById("result")

function inputValidation(x, low, high, parameter){
    try{
        if (x === null || x === ""){
            //подсветить поле, передавать в функцию не x, а inputX. выкидывать ошибку
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

function selectValidation(y){
    if (y === null || y === ""){
        //подсветить поле, передавать в функцию не y, а selectY
        showError("The parameter Y mustn't be empty")
        return false
    }
    return true
}

function validation() {
    return inputValidation(inputX.value, -5, 5, "X")
        && selectValidation(selectY.value)
        && inputValidation(inputR.value, 2, 5, "R")
}

function addToTable(x, y, r, result, currentTime, execTime){
    const row = document.createElement("tr")
    const all = [x, y, r, result, currentTime, execTime]
    for (i = 0; i < 6; i++){
        const cell = document.createElement("td")
        cell.textContent = all[i]
        row.appendChild(cell)
    }
    table.insertBefore(row, table.firstChild);
}
const popUp = document.querySelector("div[class='popUp']");
function showError(message){
    const span = document.getElementById("errorMessage");
    span.textContent = message;
    popUp.classList.add("show")
    setTimeout(() => popUp.classList.remove("show"), 5000)
}

const closeButton = document.querySelector("button[class='close-button']")
closeButton.addEventListener("click",
    () =>{
        popUp.classList.remove("show");
    })


const form = document.getElementById("inputForm");
form.addEventListener('submit',
    async function (event){
        event.preventDefault()
        if (!validation()) {
            return;
        }
        const params = new URLSearchParams();
        params.append("x", inputX.value.toString())
        params.append("y", selectY.value.toString())
        params.append("r", inputR.value.toString())
        const response = await fetch("/calculate", {
            method: "POST",
            headers: {
                "Content-type": "application/x-www-urlencoded"
            },
            body: params.toString()
        })
        if (!response.ok){
            const error = await response.json()
            showError(error.errorMessage)
            return;
        }
        const answer = await response.json()
        if (answer.hasOwnProperty("x")){
            addToTable(answer.x, answer.y, answer.r, answer.result ? "hit" : "didn't hit", answer.currentTime, answer.execTime)
        }
        else{
            showError(answer.error)
        }
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


