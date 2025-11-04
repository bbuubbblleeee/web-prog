const hours = document.getElementById("hours")
const minutes = document.getElementById("minutes")
const seconds = document.getElementById("seconds")
const date = document.getElementById("date")

function updateClock(){
    const time = new Date();
    let hoursNow = time.getHours();
    let minutesNow = time.getMinutes();
    let secondsNow = time.getSeconds();

    hours.textContent = formatDate(hoursNow)
    minutes.textContent = formatDate(minutesNow)
    seconds.textContent = formatDate(secondsNow)
}

function formatDate(time) {
    return time.toString().padStart(2, '0')
}


updateClock()
setInterval(updateClock, 1000)
