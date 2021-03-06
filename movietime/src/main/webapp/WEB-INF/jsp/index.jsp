<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>电影播放</title>
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="/favicon.ico">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="./resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet"
	href="./resources/bootstrapTable/bootstrap-table.min.css">
<link rel="stylesheet" href="./resources/css/index.css">

</head>
<body>
	<div style="text-align: center;">
		<br>
		<h2>电影首页</h2>
		<br>
	</div>

	<!--查询条件-->
	<div class="panel-body"
		style="padding-bottom: 0px; width: 104%; margin-left: 15px">
		<div class="panel panel-default">

			<div class="panel-body">
				<div style="margin-top: 10px;">
					查找电影：<input type="text" id="queryKey" placeholder="请输入电影名进行查询"
						style="height: 35px; width: 170px; margin-left: 10px; margin-top: -34px; border-radius: 6px; border: 1px #cccccc solid; outline: none">
					<button type="button"
						style="width: 70px; height: 35px; margin-left: 20px; margin-top: -3px"
						id="btn_query" class="btn btn-success">查询</button>
				</div>
			</div>
		</div>
	</div>
	<br>
	<table id="table" style="table-layout: fixed;"></table>


	<script src="./resources/jquery/jquery-3.4.1.min.js"></script>
	<script src="./resources/bootstrap/popper.min.js"></script>
	<script src="./resources/bootstrap/bootstrap.min.js"></script>
	<script src="./resources/bootstrapTable/bootstrap-table.min.js"></script>
	<script src="./resources/bootstrapTable/bootstrap-table-zh-CN.min.js"></script>
	<script src="./resources/js/common.js"></script>
	<script src="./resources/js/table.js"></script>
	<script type="text/javascript">$(function() {
		var url = 'moviepages';
		InitTable(url);

		//获取数据并显示在表格上
		function InitTable(url) {
			//先销毁表格
		    $('#table').bootstrapTable("destroy");
			//加载表格
			$('#table')
					.bootstrapTable(
							{
								url : url,
								clickToSelect : true, //点击表格项即可选择
								dataType : "json", //后端数据传递类型
								pageSize : 10,
								pageList : [ 10, 20, 50 ],
								method : 'get', //请求类型
								striped : true, //是否显示行间隔色
								cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
								pagination : true, //是否显示分页（*）
								sortable : true, //是否启用排序
								sortOrder : "asc", //排序方式
								sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
								showHeader : true, //是否显示列头
								toolbarAlign : 'left',
								paginationHAlign : 'right',
								responseHandler : function(res) {
									//在ajax获取到数据，渲染表格之前，修改数据源
									return res;
								},
								columns : [
										{
											field : 'SN',
											title : '序号',
											align : 'center',
											halign : 'center',
											formatter : function(value,
													row, index) {
												var pageSize = $('#table')
														.bootstrapTable(
																'getOptions').pageSize; //通过table的#id 得到每页多少条
												var pageNumber = $('#table')
														.bootstrapTable(
																'getOptions').pageNumber; //通过table的#id 得到当前第几页
												return pageSize
														* (pageNumber - 1)
														+ index + 1; // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
											}
										},
										{
											field : 'filmName',
											title : '电影名',
											align : 'center',
											formatter : function(value,rows, index) {
												var e = '<div id="div_td">'+rows.filmName+'</div>';
												return e;
											}
										},
										{
											field : 'size',
											title : '文件大小',
											align : 'center',
											sortable : true,
											formatter : function(value, rows, index){
												var value = rows.size;
											    if(null==value||value==''){
											        return "0 Bytes";
											    } 
											    var unitArr = new Array("Bytes","KB","MB","GB","TB","PB","EB","ZB","YB");
											    var index=0;
											    var srcsize = parseFloat(value);
											    index=Math.floor(Math.log(srcsize)/Math.log(1024));
											    var size =srcsize/Math.pow(1024,index);
											    size=size.toFixed(2);//保留的小数位数
											    return size+unitArr[index]; 
											}
											
										},{
											field : 'uploadTime',
											title : '上传时间',
											align : 'center',
											sortable : true,
											width : 120,
											formatter : function(value, rows, index) {
												var e = new Date(rows.uploadTime).Format("yyyy-MM-dd");

												return e;
											}
										},
										{
											field : 'hotWord',
											title : '热门分类',
											align : 'center',
											sortable : true
										},
										{
											field : 'playTimes',
											title : '播放次数',
											align : 'center',
											sortable : true,
											width : 130,
											formatter : function(value,
													rows, index) {
												var e = rows.playTimes
														+ '&nbsp;&nbsp;&nbsp;<a href="play?filmName='
														+ rows.filmName
														+ '">[点击播放]</a> ';

												return e;
											}
										},
										{
											title : '下载次数',
											field : 'downloadTimes',
											align : 'center',
											sortable : true,
											formatter : function(value,
													rows, index) {
												var e = rows.downTimes
														+ '&nbsp;&nbsp;&nbsp;<a href="download?filmName='
														+ rows.filmName
														+ '">[点击下载]</a> ';

												return e;
											}

										}]

							});
			
			
			var windowScreenWidth =window.screen.width;
			if(windowScreenWidth<1024){
				$('#table').bootstrapTable('hideColumn', 'downloadTimes');
				$('#table').bootstrapTable('hideColumn', 'hotWord');
				$('#table').bootstrapTable('hideColumn', 'size');
				$('#table').bootstrapTable('hideColumn', 'SN');
			}
		}

		//查询数据
		$("#btn_query").click(function() {
			var filmName = $("#queryKey").val();
			var url = "moviepages?filmName=" + filmName + "";
			InitTable(url);
		});

		Date.prototype.Format = function(fmt){ 
			var o = {   
		    	"M+" : this.getMonth()+1,                 //月份   
		    	"d+" : this.getDate(),                    //日   
		    	"h+" : this.getHours(),                   //小时   
		    	"m+" : this.getMinutes(),                 //分   
		    	"s+" : this.getSeconds(),                 //秒   
		    	"q+" : Math.floor((this.getMonth()+3)/3), //季度   
		    	"S"  : this.getMilliseconds()             //毫秒   
		  	};   
		  	if(/(y+)/.test(fmt))   
		    	fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  	for(var k in o)   
		   	 if(new RegExp("("+ k +")").test(fmt))   
		 	 fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		  	return fmt;   
		} 
		});</script>
	

</body>

</html>
