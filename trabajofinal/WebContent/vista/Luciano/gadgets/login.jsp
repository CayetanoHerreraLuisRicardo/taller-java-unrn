<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
				<form id="formInic" action="InicioSesion" onsubmit="return validar(this)" method="post">
					<fieldset id="datos">
						<legend class="campoUsu">Iniciar Sesi�n</legend>
						<div id="nom">
							<label class="infoCampo">Usuario:</label>
							<input autofocus="autofocus" class="campo" name="v_nombre" type="text" required="required" maxlength="12" />
						</div>
						<br>
						<div id="pass">
							<label class="infoCampo">Contrase�a:</label>
							<input class="campo" name="v_pass" maxlength="11" required="required" type="password" />
						</div>
					</fieldset>
					<div id="envio">
						<input type="submit" value="Iniciar sesi&oacute;n"/>
					</div>
				</form>
				