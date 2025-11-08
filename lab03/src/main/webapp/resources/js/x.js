function restoreXClass(x){
    if (x == null) {
        return;
    }
    const xLinks = document.querySelectorAll("#x-values a");
    xLinks.forEach(xLink => {
        if (+xLink.innerHTML === +x) {
            xLink.classList.add('last')
        }
        else{
            xLink.classList.remove('last')
        }
    })
}
