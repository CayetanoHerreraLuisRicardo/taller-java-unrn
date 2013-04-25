// JavaScript Document

function subMnavUp(){
	document.getElementById('sub_main_nav').style.display="";
}
function subMnavDown(){
	document.getElementById('sub_main_nav').style.display="none";
}
function salir(texto){
	var in_value, out_value;
	var maxlong=500;

	if (texto.value.length > maxlong){
		in_value = texto.value;
		out_value = in_value.substring(0,maxlong);
		texto.value = out_value;
		alert("La longitud m�xima es de " + maxlong + " caract�res");
		return false;
	}
	return true;
}

function isNum(texto,e){
	var in_value, out_value;
	if(!isNaN(document.getElementById("precioInput").value)){
		return true;
	}
	tecla = e.keyCode;
	if (tecla == 8) return true;
	else{
		in_value = texto.value;
		out_value = in_value.substring(0,0);
		texto.value = out_value;
		alert("S�lo se pueden ingresar carateres num�ricos y el punto (.)");
		return false;
	}
}
function validarNick(e){
	var k;
	document.all ? k = e.keyCode : k = e.which;
	
	return((k > 96 && k < 123) || k == 8 || k == 9 || k == 2 || k == 3 || (k >= 48 && k <= 57));
}
function validarPass(e){
	var k;
	document.all ? k = e.keyCode : k = e.which;
	
	return((k > 33 && k < 35) || (k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 9 || k == 2 || k == 3 || (k >= 48 && k <= 57));
}
function validarPass1(texto,e){
	var minlong=6;

	if (texto.value.length < minlong){
		document.getElementById("errorPass1").style.display="";
		return false;
	}
	document.getElementById("errorPass1").style.display="none";
	return true;
}
function validarPass2(texto,e){
	clave1 = document.getElementById("passContent1").value;
	clave2 = texto.value;
	
	if (clave1 != clave2){
		document.getElementById("errorPass2").style.display="";
		return false;
	}
	document.getElementById("errorPass2").style.display="none";
	return true;
}