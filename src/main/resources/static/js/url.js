let curRequestPath = window.document.location.href;
let pathName = window.document.location.pathname;
let ipAndPort = curRequestPath.indexOf(pathName);
let localhostPath = curRequestPath.substring(0,ipAndPort);
let projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);

let basePath = localhostPath + projectName;

function generateUUID() {
    var d = new Date().getTime();
    if (window.performance && typeof window.performance.now === "function") {
        d += performance.now();
    }
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
}