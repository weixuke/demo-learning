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

var system = require("system");
console.log(system.pid);    //本次进程pid
var fs = require('fs');

//网页地址,如：http://www.baidu.com
var url = "";
//图片保存地址,如：D:\work_note\htmlToImg\demo_phantomjs\html_.png
var savePath = "";
//日志输出地址,如：
var logPath = "D:/work_note/htmlToImg/sheng_chan/log.log";
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
var HTML_TO_IMG_INIT_SUCCESS = "HTML_TO_IMG_INIT_SUCCESS";
var HTML_TO_IMG_SUCCESS = "HTML_TO_IMG_SUCCESS";
var HTML_TO_IMG_FAIL = "HTML_TO_IMG_FAIL";
var HTML_TO_IMG_PARAMS_ERROR = "HTML_TO_IMG_PARAMS_ERROR";
//phantom输出字符集格式
phantom.outputEncoding="utf-8";

//获得参数对象
var json_str = system.args[1];

if(!checkParamer(json_str)){
	log("params is null!");
	console.log(HTML_TO_IMG_PARAMS_ERROR);
	phantom.exit();
}

var json = eval('(' + json_str + ')');

log(checkParamer(json.logPath));
log(checkParamer(json.viewWidth));
log(checkParamer(json.viewHeight));
log(checkParamer(json.ps_top));
log(checkParamer(json.ps_left));
log(checkParamer(json.ps_width));
log(checkParamer(json.ps_height));
log(checkParamer(json.waitTime));





//监听std输入
var listen = function() {
	log(" listening... ");
	var args = system.stdin.readLine();    //接收std内容为url
	log(" listen data "+ args);
	
	if(checkParamer(args)){
		var arg = eval('(' + args + ')');
		//"{'key':'001','result':'','savePath':'test.png','url':'http://www.baidu.com'}"
		if(checkParamer(arg)||
		   checkParamer(arg.url)||
		   checkParamer(arg.savePath)
		){
			var url = arg.url;
		    var savePath = arg.savePath;
		    log("json url = "+url);
		    log("json savePath = "+savePath);
		    var page = require('webpage').create({
		    	viewportSize : { width: viewWidth, height: viewHeight}
		    });
		    
		    page.open(url, function (status) {
		    
    	    if (status !== 'success') {
		    	log(" page fail ");
		    	system.stdout.writeLine(HTML_TO_IMG_FAIL);
		    	system.stdout.flush();            //立即输出
            } else {
		    	log(" page request ");
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
		    		log(" page success ");
		    		system.stdout.writeLine(HTML_TO_IMG_SUCCESS);    //再通过stdout输出
		    		system.stdout.flush();            //立即输出
		    		
		    		listen();
                }, waitTime);
            }
		    });
		}else{
			system.stdout.writeLine(HTML_TO_IMG_PARAMS_ERROR);    //再通过stdout输出
			system.stdout.flush();            //立即输出
			log("html params is error! "+args);
			window.setTimeout(function () {
				listen();
			}, 200);
		}
		
	}
};

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
		if(0==arg){
			return true;
		}
		return false;
	}
}



if(checkParamer(json.logPath)&&
   checkParamer(json.viewWidth)&&
   checkParamer(json.viewHeight)&&
   checkParamer(json.ps_top)&&
   checkParamer(json.ps_left)&&
   checkParamer(json.ps_width)&&
   checkParamer(json.ps_height)&&
   checkParamer(json.waitTime)
   ){
    //logPath = json.logPath;
    viewWidth = json.viewWidth;
    viewHeight = json.viewHeight;
    ps_top = json.ps_top;
    ps_left = json.ps_left;
    ps_width = json.ps_width;
    ps_height = json.ps_height;
    waitTime = json.waitTime;
    
    log("--------connector init ---------------");
    
    log("logPath = "+logPath);
    log("viewWidth = "+viewWidth);
    log("viewHeight = "+viewHeight);
    log("ps_top = "+ps_top);
    log("ps_left = "+ps_left);
    log("ps_width = "+ps_width);
    log("ps_height = "+ps_height);
    log("waitTime = "+waitTime);
	console.log(HTML_TO_IMG_INIT_SUCCESS);
	
	listen();
}else{
	console.log(HTML_TO_IMG_PARAMS_ERROR);
	log("---init params is error!---");
	phantom.exit();
}



