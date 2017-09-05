<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- saved from url=(0048)http://v3.bootcss.com/examples/jumbotron-narrow/ -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- <link rel="icon" href="http://v3.bootcss.com/favicon.ico"> -->

    <title>CODE NET</title>

    <!-- Bootstrap core CSS -->
    <link href="./static/lib/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="./static/css/index.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="./static/lib/ie-emulation-modes-warning.js.下载"></script>
    <!-- <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"> -->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">
      <div class="header">
         <ul class="nav nav-pills pull-right" role="tablist">
          <li><a href="login.html">登录</a></li>
        </ul> 
        <h3 class="text-muted"><a href="index_ch.html">CODE NET</a></h3>
      </div>
		<c:forEach var="dataset" items="${requestScope.datasets} ">
         <div class="panel panel-default">
        <div class="panel-heading"><c:out value="${requestScope.dataset.name }"/></div>
        <div class="panel-body">
          <p><c:out value="${requestScope.dataset.detail }"/> <a href=<c:out value="detail.html?id=${dataset.id }"/>>details</a> </p>
          <p><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 贡献人: <c:out value="${dataset.contributor }"/></p>
          <p><span class="glyphicon glyphicon-star" aria-hidden="true"></span> 来源: <c:out value="${dataset.reference }"/></p>
          <p><span class="glyphicon glyphicon-download" aria-hidden="true"></span> <c:out value="${dataset.dowloads }"/>次下载 </p>
          <a type="button" class="btn btn-success" href="detail.html#download">Download</a>
          <a type="button" class="btn btn-info" href="detail.html#discuss">disccusion</a>
        </div>
      </div>    
      <%-- <c:out value="${dataset }"/> --%>
		</c:forEach>
<!--       <div class="panel panel-default">
        <div class="panel-heading">title</div>
        <div class="panel-body">
          <p>here goes description <a href="./detail.html">details</a></p>
          <p><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 贡献人: SEKE</p>
          <p><span class="glyphicon glyphicon-star" aria-hidden="true"></span> 来源: (这里是来源)</p>
          <p><span class="glyphicon glyphicon-download" aria-hidden="true"></span> 0次下载 </p>
          <a type="button" class="btn btn-success" href="detail.html#download">Download</a>
          <a type="button" class="btn btn-info" href="detail.html#discuss">disccusion</a>
        </div>
      </div>

      <div class="panel panel-default">
        <div class="panel-heading">title</div>
        <div class="panel-body">
          <p>here goes description <a href="./detail.html">details</a></p>
          <p><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 贡献人: SEKE</p>
          <p><span class="glyphicon glyphicon-star" aria-hidden="true"></span> 来源: (这里是来源)</p>
          <p><span class="glyphicon glyphicon-download" aria-hidden="true"></span> 0次下载 </p>
          <a type="button" class="btn btn-success" href="detail.html#download">Download</a>
          <a type="button" class="btn btn-info" href="detail.html#discuss">disccusion</a>
        </div>
      </div>   -->

      <div class="footer">
        <p><div>© PKU SEKE 2017  </div><div> <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> w2qiao@pku.edu.cn </div></div>

    </div> <!-- /container -->

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="./static/lib/ie10-viewport-bug-workaround.js.下载"></script>
    <!-- <script src="./Narrow Jumbotron Template for Bootstrap_files/iconfont.js"></script> -->
  

</body></html>