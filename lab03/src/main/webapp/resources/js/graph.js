function createPoint(xPixels, yPixels, result){
    const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    circle.setAttribute("r", "5")
    circle.setAttribute("cx", xPixels.toString())
    circle.setAttribute("cy", yPixels.toString())
    circle.setAttribute("stroke-width", "1")
    circle.setAttribute("fill", result ? "#00f71a" : "#f50c0b")
    return circle
}

const graph = document.getElementById("points-holder")
const svg = document.getElementById("graph")

const centerX = 240
const centerY = 240
const rPixels = 180;
svg.addEventListener("click", event => {
    const rSelect = document.querySelector("option[selected='selected']")
    if (rSelect == null || rSelect.value === "") {
        PF("messages").show([{
            summary: "R is null",
            detail: "R must be chosen",
            severity: "error"
        }]);
        event.preventDefault()
        return
    }
    const rect = svg.getBoundingClientRect()
    const xPixels = event.clientX - rect.left
    const yPixels = event.clientY - rect.top
    const r = rSelect.value
    const x = (xPixels - centerX) * r / rPixels
    const y = (centerY - yPixels) * r / rPixels

    addPointFromGraph([
        {name: 'x',  value: x},
        {name: 'y',  value: y},
        {name: 'r', value: r}
    ]);
})

function drawPoint(point){
    if (point === undefined){
        console.log("point undef")
        return
    }
    const xPiexels = +point.x * rPixels / +point.r + centerX
    const yPiexels = - +point.y * rPixels / +point.r + centerY
    graph.appendChild(createPoint(xPiexels, yPiexels, point.result))
}


function restorePoints(history){
    console.log(history)
    graph.innerHTML = ""
    if (history === null) {
        return;
    }
    graph.innerHTML = ""
    const rSelect = document.querySelector("option[selected='selected']")
    if (rSelect === null){
        return;
    }
    rValue = +rSelect.value
    console.log(rValue)
    history.forEach(point => {
        if (point.r === rValue){
            console.log(point.r)

            drawPoint(point)
        }
    });
    console.log("---")
}

function redrawGraph(){
    const rSelect = document.querySelector("option[selected='selected']")
    if (rSelect === null){
        return;
    }
    rValue = +rSelect.value
    const halfR = document.querySelectorAll(".rDiv2")
    halfR.forEach(r => r.textContent = (rValue/2).toString())

    const minusHalfR = document.querySelectorAll(".-rDiv2")
    minusHalfR.forEach(r => r.textContent = (-rValue/2).toString())

    const r = document.querySelectorAll(".r")
    r.forEach(r => r.textContent = rValue.toString())

    const minusR = document.querySelectorAll(".-r")
    minusR.forEach(r => r.textContent = (-rValue).toString())
}

function restoreGraph(history){
    redrawGraph();
    restorePoints(history);
}


