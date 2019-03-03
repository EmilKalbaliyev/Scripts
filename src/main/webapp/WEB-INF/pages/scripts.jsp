<%-- 
    Document   : index
    Created on : Jul 23, 2018, 8:27:05 PM
    Author     : Emil Kalbaliyev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="300">
        <title>Scripts</title>
        <link rel="icon" type="image/ico" href="https://cdn2.iconfinder.com/data/icons/picons-basic-2/57/basic2-207_shell_unix_code-512.png">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <style>
            .table>tbody>tr>td, 
            .table>tfoot>tr>td,
            .table>thead>tr>td{
                text-align:center;
                vertical-align: middle;
                max-width: 350px;
            }
            .table>tbody>tr>th,
            .table>tfoot>tr>th, 
            .table>thead>tr>th{
                text-align:center;
                max-width: 350px;
            }
        </style>
        <script>
            $(document).ready(function () {
                $('#scripts').DataTable({
                    fixedHeader: true
                });
            });
//                 $(document).ready(function () {
//                     $(".links").click(function (event) {
//                         alert("salam");
//                         var address = $(this).attr("href");
//                         var param = prompt("Please enter parameter", "Harry Potter");
//                         alert(address + "/" + param);
//                         window.location.replace(address + "/" + param);
//                         $("#forLinks").attr("href", address + "/" + param);
//                         $("#forLinks").trigger("click");
//                     });
//                 });
        </script> 
    </head>
    <body>

        <nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header"style="margin-left:4.5%">
                    <a class="navbar-brand" href="#" >NMOC Scripts</a>
                </div>

                <ul class="nav navbar-nav navbar-right"style="margin-right:4%;font-size:15px;">
                    <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> <b>Logout</b></a></li>
                </ul>
            </div>
        </nav>
        <div class="" style="margin-top:70px; margin-left: 5%;margin-right: 5%;">

            <table id="scripts" class="table table-striped table-bordered" >
                <thead>
                    <tr>
                        <th>Scripts</th>
                        <th>Description</th>
                        <th>Automatic Run Date</th>

                        <th>Comments</th>
                        <th>Duration</th>
                        <th>Insert SiteName (RTESTW) </th>
                        <th>Process</th>
                        <th>Count</th>
                        <th>Last run by</th>
                        <th> Last Result</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <c:forEach var="script" items="${scripts}">
                        <tr>
                            <td><b>${script.name}</b></td>
                            <td style=" font-size: 12px;">${script.note}</td>
                            <td>${script.date}</td>

                            <td>${script.comment}</td>      
                            <td>${script.duration}</td>
                            <c:if test="${script.id!=1997}">
                                <c:url value='start/${script.id}' var='url'/>
                            </c:if>    
                            <c:if test="${script.id==1997}">
                                <c:url value='NPCstart' var='url'/>
                            </c:if>    
                            <form:form method="POST" action="${url}" >
                                <c:if test="${script.additionalParam==1}">
                                    <td>
                                        <input class="form-control"  style="width:120px;"placeholder="Type.." name="addParam" type="text" required="">
                                    </td>
                                </c:if>
                                <c:if test="${script.additionalParam==0}">
                                    <td>
                                        <input value="" name="addParam" type="hidden">
                                    </td>
                                </c:if>
                                <c:if test="${script.status==0}">
                                    <td>
                                        <input type="submit" value="Start Process" class="btn btn-primary " />
                                    </td>
                                </c:if>        
                            </form:form>

                            <c:if test="${script.status==1}">
                                <td ><b style="color:red">Process Started</b>
                                </td>
                            </c:if>
                            <td>${script.count}</td>
                            <td >${script.lastRunBy}</td>

                            <c:if test="${script.isDownloadable==1}">
                                <td>
                                    <a href="download/${script.id}" class="btn btn-success"> 
                                        <i class="fas fa-download fa-lg"></i>
                                        <c:if test="${script.realtime==0}">
                                            <b> ${script.fileModifiedTime}</b>                                        
                                        </c:if>
                                        <c:if test="${script.realtime==1}">
                                            <b>Last Result</b>                                        
                                        </c:if>
                                    </a>
                                </td>
                            </c:if>
                            <c:if test="${script.isDownloadable==0}">
                                <td></td>
                            </c:if>     
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
