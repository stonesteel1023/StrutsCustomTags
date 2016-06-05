<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/reservation.tld" prefix="rtag"%>
<html>
<head>
    <title>Customer</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
</head>
<body>
    <rtag:reservationTags element="FareFamily">FareFamilyCode;AncillaryAirComponent(AncillaryAirComponentCode);</rtag:reservationTags>
</body>
</html>
