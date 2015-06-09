<%@page import="java.util.List"%>
<%@page  import="circularPrimes.Configs" %>
<%@page  import="circularPrimes.Main" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Primos circulares MercadoLibre</title>
</head>
<body>
	Primos circulares debajo de  <%= Configs.MAX %>:
	<% 
	long initialTime = System.currentTimeMillis();
	List<Integer> circularPrimes = Main.generatePrimes();
	long endTime = System.currentTimeMillis();
	long executionTime = endTime - initialTime;
	%>
	<br>
	<%=circularPrimes%>
	<br>
	</br>
	Tiempo de ejecución: <%=executionTime%> ms.
	<br>
	Cantidad de elementos: <%=circularPrimes.size()%>
	<br>
	Cantidad de Hilos configurados: <%=Configs.THREAD_POOLS%>
	<br>
	</br>
	Ejercicio hecho por: Leandro Torres
</body>
</html>