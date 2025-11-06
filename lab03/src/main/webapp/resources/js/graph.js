function createPoint(xPixels, yPixels, result){
    const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    circle.setAttribute("r", "5")
    circle.setAttribute("cx", xPixels.toString())
    circle.setAttribute("cy", yPixels.toString())
    circle.setAttribute("stroke-width", "1")
    circle.setAttribute("fill", result ? "#00f71a" : "#f50c0b")
    console.log(circle.getAttribute("fill"))
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
        //TODO ошибку выкинуть
        event.preventDefault()
        return
    }
    // if (!inputValidation(inputR.value, 2, 5, "R")){
    //     event.preventDefault()
    //     return
    // }

    const rect = svg.getBoundingClientRect()
    const xPixels = event.clientX - rect.left
    const yPixels = event.clientY - rect.top
    console.log(xPixels)
    console.log(yPixels)
    // graph.appendChild(drawPoint(xPixels, yPixels))
    const r = rSelect.value
    const x = (xPixels - centerX) * r / rPixels
    const y = (centerY - yPixels) * r / rPixels
    console.log(x)
    console.log(y)

    // executeCalculation()
    console.log(addPointFromGraph([
        {name: 'x',  value: x},
        {name: 'y',  value: y}
    ]));
    console.log(":)")



    // if (!response.ok){
    //     showError(response.status)
    // }
    // const responseText = await response.json()
    // points_holder.appendChild(drawPoint(xPixels, yPixels, responseText.result)
    // console.log(x)
    // console.log(y)
    // console.log("---")
})

function drawPoint(point){
    const xPiexels = +point.x * rPixels / +point.r + centerX
    const yPiexels = - +point.y * rPixels / +point.r + centerY
    console.log(point.result)
    graph.appendChild(createPoint(xPiexels, yPiexels, point.result))
}

