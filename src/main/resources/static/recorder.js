//webkitURL is deprecated but nevertheless
URL = window.URL || window.webkitURL;
 
var gumStream; //stream from getUserMedia()
var rec; //Recorder.js object
var input; //MediaStreamAudioSourceNode we'll be recording
 
// shim for AudioContext when it's not avb. 
var AudioContext = window.AudioContext || window.webkitAudioContext;
var audioContext = new AudioContext; //new audio context to help us record
 
var recordButton = document.getElementById("recordButton");
var stopButton = document.getElementById("stopButton");
 
//add events to those 3 buttons
recordButton.addEventListener("click", startRecording);
stopButton.addEventListener("click", stopRecording);

//Stopwatch Codes
var seconds = 0, minutes = 0, hours = 0, t, timerdiv = document.getElementById('timer');

function startRecording() {
    timer();
    var constraints = { audio: true, video:false }
 
    /*
    Disable the record button until we get a success or fail from getUserMedia()
    */
    recordButton.disabled = true;
    stopButton.disabled = false;
 
    navigator.mediaDevices.getUserMedia(constraints).then(function(stream) {
        /* assign to gumStream for later use */
        gumStream = stream;
 
        /* use the stream */
        input = audioContext.createMediaStreamSource(stream);
 
        /* 
        Create the Recorder object and configure to record mono sound (1 channel)
        Recording 2 channels  will double the file size
        */
        rec = new Recorder(input,{numChannels:1})
 
        //start the recording process
        rec.record()
 
    }).catch(function(err) {
        //enable the record button if getUserMedia() fails
        recordButton.disabled = false;
        stopButton.disabled = true;
    });
}


function stopRecording() {
    console.log("stopButton clicked");
 
    //disable the stop button, enable the record too allow for new recordings
    stopButton.disabled = true;
    recordButton.disabled = false;
 
    //tell the recorder to stop the recording
    rec.stop();
 
    //stop microphone access
    gumStream.getAudioTracks()[0].stop();
 
    //create the wav blob and pass it on to createDownloadLink
    rec.exportWAV(createDownloadLink);

    //Timer Codes
    clearTimeout(t);
    seconds = 0;
    minutes = 0;
    hours = 0;
    timerdiv.textContent = '';
}

function createDownloadLink(blob) {
 
    var url = URL.createObjectURL(blob);
    var au = document.createElement('audio');
    var li = document.createElement('li');
    var link = document.createElement('a');
 
    //add controls to the <audio> element
    au.controls = true;
    au.src = url;
 
    //link the a element to the blob
    link.href = url;
    link.download = new Date().toISOString() + '.wav';
    link.innerHTML = link.download;
 
    //add the new audio and a elements to the li element
    li.appendChild(au);
    li.appendChild(link);
    
    var filename = new Date().toISOString(); //filename to send to server without extension
    //upload link
    createDownload(blob, filename, li);
    //add the li element to the ordered list
    recordingsList.appendChild(li);
}
function createDownload(blob, filename, li) {
    var upload = document.createElement('a');
    upload.href = "#";
    upload.innerHTML = "Fetch Transcript";
    upload.addEventListener("click", function () {
        var xhr = new XMLHttpRequest();
        xhr.onload = function (e) {
            if (this.readyState === 4) {
                console.log("Server returned: ", e.target.responseText);
            }
        };
        var fd = new FormData();
        fd.append("audio_data", blob, filename);
        xhr.open("POST", "/api/savesound", true);
        xhr.send(fd);
    });
    li.appendChild(document.createTextNode(" "));
    li.appendChild(upload);
}

function add() {
    seconds++;
    if (seconds >= 60) {
        seconds = 0;
        minutes++;
        if (minutes >= 60) {
            minutes = 0;
            hours++;
        }
    }
    timerdiv.textContent = (hours ? (hours > 9 ? hours : "0" + hours) : "00") + ":" + (minutes ? (minutes > 9 ? minutes : "0" + minutes) : "00") + ":" + (seconds > 9 ? seconds : "0" + seconds);
    timer();
}
function timer() {
    t = setTimeout(add, 1000);
}
