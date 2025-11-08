function restoreX(x){

    if (x == null) {
        return;
    }
    console.log(x)
    const xLinks = document.querySelectorAll("#x-values a");
    xLinks.forEach(xLink => {
        if (+xLink.innerHTML === +x) {
            xLink.classList.add('last')
            console.log(xLink.innerHTML)
        }
        else{
            xLink.classList.remove('last')
        }
    })
}


// function restoreXAfterSubmit(){
//     const xLinks = document.querySelectorAll("#x-values a");
//     xLinks.forEach(xLink => {
//         if (+xLink.innerHTML === +x) {
//             xLink.classList.add('last')
//             console.log(xLink.innerHTML)
//         }
//         else{
//             xLink.classList.remove('last')
//         }
//     })
// }
