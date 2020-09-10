var liuyiImJsSdkMap = function () {
    var holder = {};
    this.put = function (key, value) {
        holder[key] = value;
    }
    this.size = function () {
        var count = 0;
        for (var attr in holder) {
            count++;
        }
        return count;
    }
    this.get = function (key) {
        if (holder[key] || holder[key] === 0 || holder[key] === false) {
            return holder[key]
        } else {
            return null;
        }
    }
    this.remove = function (key) {
        if (holder[key] || holder[key] === 0 || holder[key] === false) {
            delete holder[key]
        }
    }
    //each方法,遍历方法
    this.each = function (callBack) {
        for (var attr in holder) {
            callBack(attr, holder[attr])
        }
    }

}

var liuyiImJsSdk = {
    sdk: {
        deviceLabel: function () {
            return liuyiImJsSdk.getBrowserName()
        },
        sdkName: "liuyi-im-jssdk",
        sdkVersion: "1.0.0",
    },

    commandType: {
        login: 101,
        sendMessage: 105,
        receivePush: 106,
    },

    operations: {
        msg: '提示',
        loginFail: '登录失败',
        loginSuccess: '登录成功',
        receive: '接收消息',
        sent: '发送消息',
    },

    _isDev: true,

    server: {
        devHost: "ws://127.0.0.1:33008/liuyi/gateway/ws",
        prodHost: "ws://127.0.0.1:33008/liuyi/gateway/ws",

        host: function () {
            console.log(this.devHost, this.prodHost)
            return this._isDev ? this.devHost : this.prodHost
        },
    },
    context: {
        userId: 0,
        token: "xx",
        appKey: "xx",
    },
    socket: null,

    init: function (isDev, openCallback, receiveCallback, closeCallback, errorCallback) {
        this._isDev = isDev;
        if (openCallback) {
            this.open = openCallback
        }
        if (receiveCallback) {
            this.receive = receiveCallback
        }
        if (closeCallback) {
            this.close = closeCallback
        }
        if (errorCallback) {
            this.error = errorCallback
        }
        return this
    },

    isDev: function () {
        return this._isDev
    },

    log: function (operation, msg) {
        console.log(operation, msg);
        return this
    },


    connect: function () {
        if (typeof (WebSocket) == "undefined") {
            this.log(this.operations.msg, "此浏览器不支持 WebSocket")
        } else {
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }
            this.socket = new WebSocket(this.server.host());
            this.socket.onopen = function () {
                liuyiImJsSdk.log(liuyiImJsSdk.operations.msg, "WebSocket 已打开")
                liuyiImJsSdk.open()
            };
            this.socket.onmessage = function (msg) {
                var command = JSON.parse(msg.data);
                liuyiImJsSdk.log(liuyiImJsSdk.operations.receive, msg.data)

                if (command.header.code == 0) {
                    liuyiImJsSdk.receive(command)
                } else {
                    liuyiImJsSdk.reply(command)
                }

            };
            this.socket.onclose = function () {
                liuyiImJsSdk.log(liuyiImJsSdk.operations.msg, "WebSocket 已关闭")
                liuyiImJsSdk.close()
            };
            this.socket.onerror = function () {
                liuyiImJsSdk.log(liuyiImJsSdk.operations.msg, "WebSocket 发生错误")
                liuyiImJsSdk.error()
            }
        }
    },
    map: new liuyiImJsSdkMap(),
    reply: function (command) {
        var func = this.map.get(command.header.seq)
        if (func) {
            this.map.remove(command.header.seq)
            func(command)
        }
    },

    shutdown: function () {
        this.socket.close()
        this.socket = null
    },
    open: function () {
    },
    receive: function (command) {

    },
    send: function (command, callback) {
        if (callback) {
            this.map.put(command.header.seq, callback)
        }
        var str = JSON.stringify(command)
        this.socket.send(str);
        this.log(this.operations.sent, str)
    },
    close: function () {
    },
    error: function () {
    },

    getBrowserName: function () {
        var userAgent = navigator.userAgent;
        var isOpera = userAgent.indexOf("Opera") > -1;
        if (isOpera) {      //Opera
            return "Opera"
        }
        if (userAgent.indexOf("Firefox") > -1) {     //Firefox
            return "FF";
        }
        if (userAgent.indexOf("Chrome") > -1) {   //Chrome
            return "Chrome";
        }
        if (userAgent.indexOf("Safari") > -1) {     //Safari
            return "Safari";
        }
        if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
            return "IE";
        }        //IE
    },

    login: function (token) {
        this.context.token = token;
        var that = this;
        this.send(this.buildCommandOfLogin(), function (command) {
            that.context.appKey = command.body.appKey;
            that.context.userId = command.body.userId;
        })
    },

    sendMessage: function (target, messageType, messageBody) {
        this.send(this.buildCommandOfSendMessage(target, messageType, messageBody));
    },

    newCommandSeq: function (len) {
        len = len || 32;
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
        var maxPos = $chars.length;
        var pwd = '';
        for (i = 0; i < len; i++) {
            pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return pwd
    },

    buildCommandOfLogin: function () {
        return {
            body: {
                deviceLabel: this.sdk.deviceLabel(),
                sdkName: this.sdk.sdkName,
                sdkVersion: this.sdk.sdkVersion,
            },
            header: {
                code: 0,
                seq: this.newCommandSeq(32),
                type: this.commandType.login
            },
            token: this.context.token,
        }
    },

    buildCommandOfSendMessage: function (target, messageType, messageBody) {
        return {
            body: {
                messageBody: messageBody,
                messageType: messageType,
                targetId: target
            },
            header: {
                code: 0,
                seq: this.newCommandSeq(32),
                type: this.commandType.sendMessage
            },
            token: this.context.token,
        }
    },

    buildCommandOfSendGroupMessage: function (msg, targetId) {
        return {
            body: {
                messageBody: msg,
                messageType: 2,
                targetId: targetId
            },
            header: {
                code: 0,
                seq: this.newCommandSeq(32),
                type: this.commandType.sendMessage
            },
            token: this.context.token,
        }
    },

}
