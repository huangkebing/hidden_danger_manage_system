let curRequestPath = window.document.location.href;
let pathName = window.document.location.pathname;
let ipAndPort = curRequestPath.indexOf(pathName);
let localhostPath = curRequestPath.substring(0,ipAndPort);
let projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);

let basePath = localhostPath + projectName;