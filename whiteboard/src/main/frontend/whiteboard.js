// ===== DOM refs =====
const canvas       = document.getElementById("whiteboard");
const ctx          = canvas.getContext("2d");

const statusSpan   = document.getElementById("status");
const colorPicker  = document.getElementById("colorPicker");
const penSizeInput = document.getElementById("penSize");
const penOpacity   = document.getElementById("penOpacity");
const eraserSize   = document.getElementById("eraserSize");

const penButton    = document.getElementById("penTool");
const eraserButton = document.getElementById("eraserTool");
const textButton   = document.getElementById("textTool");
const imageInput   = document.getElementById("imageInput");

const nicknameInput   = document.getElementById("nicknameInput");
const saveNicknameBtn = document.getElementById("saveNicknameBtn");
const currentUserDiv  = document.getElementById("currentUser");
const lastActionDiv   = document.getElementById("lastAction");
const cursorLabel = document.getElementById("cursorLabel");

// nickname + known users map
let nickname = localStorage.getItem("nickname") || ("User-" + Math.floor(Math.random() * 1000));
nicknameInput.value = nickname;
currentUserDiv.textContent = "You: " + nickname;

const users = {};
users[USER_ID] = nickname;
saveNicknameBtn.onclick = () => {
    const newName = nicknameInput.value.trim();
    if (!newName) return;

    nickname = newName;
    localStorage.setItem("nickname", nickname);
    currentUserDiv.textContent = "You: " + nickname;

    // notify others that we changed/declared our nickname
    sendMessage({
        type: "nick",
        boardId: BOARD_ID,
        userId: USER_ID,
        nickname: nickname
    });
};


// ===== State =====
let currentTool = "pen";   // "pen" | "eraser" | "text"
let drawing = false;
let lastX = 0;
let lastY = 0;
let socket = null;

// background colour (used for eraser + clear)
const CANVAS_BG = "#FFFFFF";

// ===== Tool selection =====
function setActiveTool(btn) {
    document.querySelectorAll(".tool-btn").forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
}

penButton.onclick = () => {
    currentTool = "pen";
    setActiveTool(penButton);
};

eraserButton.onclick = () => {
    currentTool = "eraser";
    setActiveTool(eraserButton);
};

textButton.onclick = () => {
    currentTool = "text";
    setActiveTool(textButton);
};


// ===== 1. WebSocket connection =====
function connect() {
    const url = "ws://localhost:8080/ws/whiteboard";
    console.log("Connecting to", url);

    socket = new WebSocket(url);
    statusSpan.textContent = "Connecting…";

    socket.onopen = () => {
        console.log("WebSocket OPEN");
        statusSpan.textContent = "Connected";

        sendMessage({
            type: "join",
            boardId: BOARD_ID,
            userId: USER_ID,
            nickname: nickname,
        }, false);
    };

    socket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log("MESSAGE:", data);
        handleIncoming(data);
    };

    socket.onclose = () => {
        console.log("WebSocket CLOSED");
        statusSpan.textContent = "Disconnected – reconnecting…";
        setTimeout(connect, 1000);
    };

    socket.onerror = (err) => {
        console.error("WebSocket ERROR:", err);
    };
}

function sendMessage(payload, warnIfClosed = true) {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(payload));
    } else if (warnIfClosed) {
        console.warn("Tried to send while socket not open", payload);
    }
}

connect();

// ===== 2. Helpers =====

// hex colour + opacity slider → rgba string
function hexToRgba(hex, alpha) {
    // hex like "#RRGGBB"
    const clean = hex.replace("#", "");
    const r = parseInt(clean.substring(0, 2), 16);
    const g = parseInt(clean.substring(2, 4), 16);
    const b = parseInt(clean.substring(4, 6), 16);
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}

function drawLine(x1, y1, x2, y2, color, size = 2) {
    ctx.strokeStyle = color;
    ctx.lineWidth = size;
    ctx.lineCap = "round";

    ctx.beginPath();
    ctx.moveTo(x1, y1);
    ctx.lineTo(x2, y2);
    ctx.stroke();
}

function drawText(x, y, text, color, size) {
    ctx.fillStyle = color;
    ctx.font = `${size}px sans-serif`;
    ctx.textBaseline = "top";
    ctx.fillText(text, x, y);
}

function drawImageFromDataUrl(dataUrl, x = 50, y = 50) {
    const img = new Image();
    img.onload = () => {
        ctx.drawImage(img, x, y);
    };
    img.src = dataUrl;
}

// ===== 3. Local drawing events =====
canvas.addEventListener("mousedown", (e) => {
    if (currentTool === "pen" || currentTool === "eraser") {
        drawing = true;
        lastX = e.offsetX;
        lastY = e.offsetY;
    }
});

canvas.addEventListener("mouseup", () => drawing = false);
canvas.addEventListener("mouseout", () => {
    drawing = false;
    cursorLabel.style.display = "none";  // hide label when leaving canvas
});

canvas.addEventListener("mousemove", (e) => {
    const x = e.offsetX;
    const y = e.offsetY;

    // --- move nickname label with cursor ---
    cursorLabel.style.display = "block";
    cursorLabel.textContent = nickname;
    cursorLabel.style.left = x + "px";
    cursorLabel.style.top  = y + "px";

    // --- drawing logic (only when pen/eraser + mouse down) ---
    if (!drawing) return;
    if (currentTool !== "pen" && currentTool !== "eraser") return;

    let size;
    let color;
    let type;

    if (currentTool === "pen") {
        size = parseInt(penSizeInput.value, 10) || 2;

        // opacity 10–100 → 0.1–1.0
        const alpha = (parseInt(penOpacity.value, 10) || 100) / 100;
        color = hexToRgba(colorPicker.value, alpha);
        type = "draw";
    } else { // eraser
        size = parseInt(eraserSize.value, 10) || 20;
        color = CANVAS_BG;
        type = "erase";
    }

    // local draw
    drawLine(lastX, lastY, x, y, color, size);

    // sync
    const msg = {
        type,
        boardId: BOARD_ID,
        userId: USER_ID,
        nickname: nickname,
        lastX,
        lastY,
        x,
        y,
        color,
        size
    };

    sendMessage(msg);

    lastX = x;
    lastY = y;
});

// ===== 3b. Text tool (click to place) =====
canvas.addEventListener("click", (e) => {
    if (currentTool !== "text") return;

    const x = e.offsetX;
    const y = e.offsetY;

    const alpha = (parseInt(penOpacity.value, 10) || 100) / 100;
    const color = hexToRgba(colorPicker.value, alpha);
    const fontSize = (parseInt(penSizeInput.value, 10) || 2) + 8;

    const text = prompt("Enter text:");
    if (!text) return;

    // local
    drawText(x, y, text, color, fontSize);

    // sync
    const msg = {
        type: "text",
        boardId: BOARD_ID,
        userId: USER_ID,
        nickname: nickname,
        x,
        y,
        text,
        color,
        size: fontSize
    };
    sendMessage(msg);
});

// ===== 3c. Upload image =====
imageInput.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (evt) => {
        const dataUrl = evt.target.result;

        // local
        drawImageFromDataUrl(dataUrl);

        // sync
        const msg = {
            type: "image",
            boardId: BOARD_ID,
            userId: USER_ID,
            nickname: nickname,
            dataUrl
        };
        sendMessage(msg);
    };

    reader.readAsDataURL(file);
});

// ===== 4. Clear board =====
function clearBoard() {
    ctx.fillStyle = CANVAS_BG;
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    const msg = {
        type: "clear",
        boardId: BOARD_ID,
        userId: USER_ID,
        nickname: nickname,
    };
    sendMessage(msg);
}

// initialize blank canvas
clearBoard();

function upsertUser(from) {
    if (from.userId && from.nickname) {
        users[from.userId] = from.nickname;
    }
}

function getNickname(userId) {
    return users[userId] || userId || "Unknown";
}

function setLastAction(text) {
    lastActionDiv.textContent = text;
}

function handleIncoming(data) {
    // learn/update nickname if message has one
    upsertUser(data);

    const name = getNickname(data.userId);

    switch (data.type) {
        case "join":
            setLastAction(name + " joined the board");
            break;

        case "nick":
            setLastAction(name + " updated their nickname");
            break;

        case "draw":
        case "erase":
            // only draw if it's NOT our own stroke (we already drew locally)
            if (data.userId !== USER_ID) {
                drawLine(data.lastX, data.lastY, data.x, data.y, data.color, data.size);
            }
            setLastAction(name + " drew on the board");
            break;

        case "clear":
            // clear for everyone
            ctx.fillStyle = CANVAS_BG;
            ctx.fillRect(0, 0, canvas.width, canvas.height);
            setLastAction(name + " cleared the board");
            break;

        case "text":
            if (data.userId !== USER_ID) {
                drawText(data.x, data.y, data.text, data.color, data.size);
            }
            setLastAction(name + " added text");
            break;

        case "image":
            if (data.userId !== USER_ID) {
                drawImageFromDataUrl(data.dataUrl);
            }
            setLastAction(name + " uploaded an image");
            break;

        default:
            console.log("Unknown message type:", data.type);
    }
}
