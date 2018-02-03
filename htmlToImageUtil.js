Date.prototype.format = function(fmt) { 
    var o = { 
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S"  : this.getMilliseconds()
    }; 
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)); 
    }
    for(var k in o) {
        if(new RegExp("(" + k + ")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt; 
};
//获得系统操作对象
var system = require('system');
var fs = require('fs');
console.log("pid_"+system.pid);    //本次进程pid
//网页地址,如：http://www.baidu.com
var url = "";
//图片保存地址,如：D:\work_note\htmlToImg\demo_phantomjs\html_.png
var savePath = "";
//日志输出地址,如：
var logPath = "";
//浏览器宽度
var viewWidth = 0;
//浏览器高度
var viewHeight = 0;
//printscreen需要截图的区域开始位置
var ps_top = 0;
//左边距位置	
var ps_left = 0;
//宽度
var ps_width = 0;
//高度
var ps_height = 0;
//页面渲染时长,单位毫秒
var waitTime = 0;
//时间格式化
var dateFormat = "yyyy-MM-dd hh:mm:ss";
var HTML_TO_IMG_FAIL = "HTML_TO_IMG_FAIL";
var HTML_TO_IMG_SUCCESS = "HTML_TO_IMG_SUCCESS";
var HTML_TO_IMG_PARAMS_ERROR = "HTML_TO_IMG_PARAMS_ERROR";
//phantom输出字符集格式
phantom.outputEncoding="utf-8";

//获得参数对象
var json_str = system.args[1];
var json = eval('(' + json_str + ')');

if(checkParamer(json.url)&&
   checkParamer(json.savePath)&&
   checkParamer(json.logPath)&&
   checkParamer(json.viewWidth)&&
   checkParamer(json.viewHeight)&&
   checkParamer(json.ps_top)&&
   checkParamer(json.ps_left)&&
   checkParamer(json.ps_width)&&
   checkParamer(json.ps_height)&&
   checkParamer(json.waitTime)
   ){
	url = json.url;
    savePath = json.savePath;
    logPath = json.logPath;
    viewWidth = json.viewWidth;
    viewHeight = json.viewHeight;
    ps_top = json.ps_top;
    ps_left = json.ps_left;
    ps_width = json.ps_width;
    ps_height = json.ps_height;
    waitTime = json.waitTime;
    
    log("-------------------------------");
    log("url = "+url);
    log("savePath = "+savePath);
    log("logPath = "+logPath);
    log("viewWidth = "+viewWidth);
    log("viewHeight = "+viewHeight);
    log("ps_top = "+ps_top);
    log("ps_left = "+ps_left);
    log("ps_width = "+ps_width);
    log("ps_height = "+ps_height);
    log("waitTime = "+waitTime);
}else{
	console.log(HTML_TO_IMG_PARAMS_ERROR);
	log("params is error!");
	phantom.exit();
}



var page = require('webpage').create({
	viewportSize : { width: viewWidth, height: viewHeight}
});

page.open(url, function (status) {
	if (status !== 'success') {
        console.log(HTML_TO_IMG_FAIL);
		log('Unable to load this '+url+' !');
        phantom.exit(1);
    } else {
        window.setTimeout(function () {
			var pageInfo = page.evaluate(function () {
				var data = new Array();
				var doc = document.getElementsByTagName('html')[0];
				data[0]=doc.getBoundingClientRect().top;  
                data[1]=doc.getBoundingClientRect().left;  
                data[2]=doc.getBoundingClientRect().width;       
                data[3]=doc.getBoundingClientRect().bottom;    
                data[4]=document.documentElement.scrollWidth;   
                data[5]=document.documentElement.scrollHeight; 
				return data; 
			});
			page.zoomFactor = viewWidth/pageInfo.width;
			
			page.paperSize = {width: viewWidth, height: viewHeight, margin: '0px'};
			
            page.clipRect = { top: 0, left: 0, width: viewWidth, height: viewHeight };
            page.render(savePath);
			log('html to images success!');
			console.log(HTML_TO_IMG_SUCCESS);
            phantom.exit();
        }, waitTime);
    }
});

function log(content){
	try{
		fs.write(logPath, (new Date().format(dateFormat)) + " [" + content + "]\r\n", 'a');
	} catch(e){
		console.log(e);
	}
}

function checkParamer(arg){
	if(arg){
		return true;
	}else{
		return false;
	}
}
