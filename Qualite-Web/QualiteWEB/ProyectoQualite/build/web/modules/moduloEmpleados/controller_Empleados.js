let empleados;
let currentUser = localStorage.getItem('currentUser');
let e = JSON.parse(currentUser);
let token = e.usuario.lastToken;
showTable();

export function formatDate() {
    
    let fecha = document.getElementById("txtFechaN").value;
    let anio = fecha.toString().substr(0, 4);
    let mes = fecha.toString().substr(5, 2);
    let dia = fecha.toString().substr(8, 2);

    let fechaFormat = (dia + "/" + mes + "/" + anio);
    return fechaFormat;
}

function normalizar(texto) {
    
    texto = texto.toUpperCase();
    for (var i = 0; i < texto.length; i++) {
        texto = texto.replace("Á", "A");
        texto = texto.replace("É", "E");
        texto = texto.replace("Í", "I");
        texto = texto.replace("Ó", "O");
        texto = texto.replace("Ú", "U"); // texto = texto.replace("1","UNO");}returntexto;}

    }
    
    return texto;
}

function sanitizar(texto){
    
    for (var i = 0; i < texto.length; i++) {
        texto = texto.replace("(", "");
        texto = texto.replace(")", "");
        texto = texto.replace(";", "");
        texto = texto.replace("'", "");
        texto = texto.replace("\"", "");
        texto = texto.replace("-", "");
        texto = texto.replace("*", "");
        texto = texto.replace("%", "");
        texto = texto.replace("«", "");
        texto = texto.replace("»", "");
        texto = texto.replace('”', "");
        texto = texto.replace('“', "");
    }
    
    return texto;
    
}

export function buscarEmpleado() {
    
    let busqueda = document.getElementById("txtSearch").value;

    let parametros = {"buscar": busqueda};

    fetch('api/empleado/buscar?busqueda=' + busqueda)
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaE(data);
                }
            });
}

export function showTable() {
    let datos = {estatus: document.getElementById("txtEstatusS").value};
    let parametros = new URLSearchParams(datos);

    fetch("api/empleado/getAll", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaE(data);
                }
            });
}

export function cargarTablaE(data) {
    
    empleados = data;
    let contenidoE = "";
    
    for (var i = 0; i < empleados.length; i++) {
        
        let nombre = empleados[i].persona.nombre;
        let primerA = empleados[i].persona.apellidoPaterno;
        let segundoA = empleados[i].persona.apellidoMaterno;
        let genero = empleados[i].persona.genero;
        let cp = empleados[i].persona.cp;
        let ciudad = empleados[i].persona.ciudad;
        let estado = empleados[i].persona.estado;
        let telm = empleados[i].persona.telmovil;
        let email = empleados[i].persona.email;
        let estatus = empleados[i].estatus;
        let rol = empleados[i].usuario.rol;
        let usuario = empleados[i].usuario.nombreUsuario;
        let fecha = empleados[i].persona.fechaNacimiento;

        contenidoE += "<tr onclick='moduloEmpleados.seleccionarEmpleado(" + i + ");'>";
        contenidoE += "<td>" + nombre + "</td>";
        contenidoE += "<td>" + primerA + "</td>";
        contenidoE += "<td>" + segundoA + "</td>";
        contenidoE += "<td>" + genero + "</td>";
        contenidoE += "<td>" + rol + "</td>";
        contenidoE += "<td>" + telm + "</td>";
        contenidoE += "<td>" + email + "</td>";
        contenidoE += "<td>" + usuario + "</td>";
        contenidoE += "<td>" + cp + "</td>";
        contenidoE += "<td>" + ciudad + "</td>";
        contenidoE += "<td>" + estado + "</td>";
        contenidoE += "<td>" + fecha + "</td>";
        contenidoE += "<td>" + estatus + "</td>";
        contenidoE += "</tr>";
    }
    
    document.getElementById("tblEmpleados").innerHTML = contenidoE;

}

export function insertarEmpleado() {

    let empleado = new Object();
    empleado.usuario = new Object();
    empleado.persona = new Object();

    empleado.persona.nombre = sanitizar(document.getElementById("txtNombre").value);
    empleado.persona.apellidoPaterno = sanitizar(document.getElementById("txtApePaterno").value);
    empleado.persona.apellidoMaterno = sanitizar(document.getElementById("txtApeMaterno").value);
    empleado.persona.telcasa = sanitizar(document.getElementById("txtTelefonoC").value);
    empleado.persona.telmovil = sanitizar(document.getElementById("txtTelefonoM").value);
    empleado.persona.email = document.getElementById("txtCorreo").value;
    empleado.usuario.rol = document.getElementById("txtRol").value;
    empleado.persona.genero = document.getElementById("txtGenero").value;
    empleado.usuario.nombreUsuario = sanitizar(document.getElementById("txtUsuario").value);
    empleado.usuario.contrasenia = document.getElementById("txtContrasena").value;
    empleado.persona.fechaNacimiento = formatDate();
    empleado.persona.calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    empleado.persona.colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    empleado.persona.numero = sanitizar(document.getElementById("txtNum").value);
    empleado.persona.cp = sanitizar(document.getElementById("txtCodigoP").value);
    empleado.persona.ciudad = normalizar(document.getElementById("txtCiudad").value);
    empleado.persona.estado = document.getElementById("txtEstado").value;

    let parametros = new URLSearchParams({datos: JSON.stringify(empleado), token: token});

    fetch('api/empleado/insert',
            {
                method: 'POST',
                body: parametros,
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {

                if (data.exception != null) {
                    Swal.fire('', 'Error interno del Servidor', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('', '¡No tiene permisos para realizar la Operacion!', 'error');
                    return;
                } else {
                    
                    let msj = "Empleado insertado con el ID: " + data.idEmpleado;
                    
                    Swal.fire({
                        
                        title: '¿Desea Agregar el Empleado?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Agregar',
                        cancelButtonText: 'Cancelar'
                        
                    }).then((result) => {
                        
                        if (result.isConfirmed) {
                            
                            Swal.fire('¡Empleado Agregado!', msj, 'success');
                            showTable();
                            limpiarEmpleado();
                        }
                    });
                }
            });
}

export function actualizarEmpleado() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let apellidoPaterno = sanitizar(document.getElementById("txtApePaterno").value);
    let apellidoMaterno = sanitizar(document.getElementById("txtApeMaterno").value);
    let telcasa = sanitizar(document.getElementById("txtTelefonoC").value);
    let telmovil = sanitizar(document.getElementById("txtTelefonoM").value);
    let email = document.getElementById("txtCorreo").value;
    let rol = document.getElementById("txtRol").value;
    let genero = document.getElementById("txtGenero").value;
    let nombreUsuario = sanitizar(document.getElementById("txtUsuario").value);
    let contrasenia = document.getElementById("txtContrasena").value;
    let estatus = document.getElementById("txtEstatus").value;
    let fechaNacimiento = formatDate();
    let calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    let colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    let numero = sanitizar(document.getElementById("txtNum").value);
    let cp = sanitizar(document.getElementById("txtCodigoP").value);
    let ciudad = normalizar(document.getElementById("txtCiudad").value);
    let estado = document.getElementById("txtEstado").value;
    let idPersona = document.getElementById("txtIdPersona").value;
    let idUsuario = document.getElementById("txtIdUsuario").value;
    let idEmpleado = document.getElementById("txtIdEmpleado").value;

    let persona = {nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno,
        genero: genero, fechaNacimiento: fechaNacimiento, calle: calle, colonia: colonia,
        numero: numero, cp: cp, ciudad: ciudad, estado: estado, telcasa: telcasa, telmovil: telmovil, email: email, idPersona: idPersona};
    let usuario = {nombreUsuario: nombreUsuario, contrasenia: contrasenia, rol: rol, idUsuario: idUsuario};
    let empleado = JSON.stringify({persona: persona, usuario: usuario, estatus: estatus, idEmpleado: idEmpleado});
    let parametros = new URLSearchParams({datos: empleado, token: token});

    fetch('api/empleado/update',
            {
                method: 'POST',
                body: parametros,
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                if (data.exception != null) {
                    Swal.fire('', 'Error interno del Servidor', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('', '¡No tiene permisos para realizar la Operacion!', 'error');
                    return;
                } else {
                    
                    let msj = "Empleado con ID: " + data.idEmpleado + " Actualizado Correctamente";
                    
                    Swal.fire({
                        
                        title: '¿Desea Actualizar el Empleado?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Actualizar',
                        cancelButtonText: 'Cancelar'
                        
                    }).then((result) => {
                        if (result.isConfirmed) {
                            
                            Swal.fire('¡Empleado Actualizado!', msj, 'success');
                            showTable();
                            limpiarEmpleado();
                        }
                    });
                }
            });
}

export function activarEmpleado() {

    let nombre = document.getElementById("txtNombre").value;
    let apellidoPaterno = document.getElementById("txtApePaterno").value;
    let apellidoMaterno = document.getElementById("txtApeMaterno").value;
    let telcasa = document.getElementById("txtTelefonoC").value;
    let telmovil = document.getElementById("txtTelefonoM").value;
    let email = document.getElementById("txtCorreo").value;
    let rol = document.getElementById("txtRol").value;
    let genero = document.getElementById("txtGenero").value;
    let nombreUsuario = document.getElementById("txtUsuario").value;
    let contrasenia = document.getElementById("txtContrasena").value;
    let estatus = document.getElementById("txtEstatus").value;
    let fechaNacimiento = formatDate();
    let calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    let colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    let numero = document.getElementById("txtNum").value;
    let cp = document.getElementById("txtCodigoP").value;
    let ciudad = document.getElementById("txtCiudad").value;
    let estado = document.getElementById("txtEstado").value;
    let idPersona = document.getElementById("txtIdPersona").value;
    let idUsuario = document.getElementById("txtIdUsuario").value;
    let idEmpleado = document.getElementById("txtIdEmpleado").value;

    let persona = {nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno,
        genero: genero, fechaNacimiento: fechaNacimiento, calle: calle, colonia: colonia,
        numero: numero, cp: cp, ciudad: ciudad, estado: estado, telcasa: telcasa, telmovil: telmovil, email: email, idPersona: idPersona};
    let usuario = {nombreUsuario: nombreUsuario, contrasenia: contrasenia, rol: rol, idUsuario: idUsuario};
    let empleado = JSON.stringify({persona: persona, usuario: usuario, idEmpleado: idEmpleado});
    let parametros = new URLSearchParams({datos: empleado, token: token});

    fetch('api/empleado/activate',
            {
                method: 'POST',
                body: parametros,
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                if (data.exception != null) {
                    Swal.fire('', 'Error interno del Servidor', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('', '¡No tiene permisos para realizar la Operacion!', 'error');
                    return;
                } else {
                    
                    let msj = "Empleado con ID: " + data.idEmpleado + " Activado Correctamente";
                    
                    Swal.fire({
                        
                        title: '¿Desea Activar el Empleado?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Activar',
                        cancelButtonText: 'Cancelar'
                        
                    }).then((result) => {
                        if (result.isConfirmed) {
                            
                            Swal.fire('¡Empleado Activado!', msj, 'success');
                            showTable();
                            limpiarEmpleado();
                        }
                    });
                }
            });
}

export function eliminarEmpleado() {

    let nombre = document.getElementById("txtNombre").value;
    let apellidoPaterno = document.getElementById("txtApePaterno").value;
    let apellidoMaterno = document.getElementById("txtApeMaterno").value;
    let telcasa = document.getElementById("txtTelefonoC").value;
    let telmovil = document.getElementById("txtTelefonoM").value;
    let email = document.getElementById("txtCorreo").value;
    let rol = document.getElementById("txtRol").value;
    let genero = document.getElementById("txtGenero").value;
    let nombreUsuario = document.getElementById("txtUsuario").value;
    let contrasenia = document.getElementById("txtContrasena").value;
    let estatus = document.getElementById("txtEstatus").value;
    let fechaNacimiento = formatDate();
    let calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    let colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    let numero = document.getElementById("txtNum").value;
    let cp = document.getElementById("txtCodigoP").value;
    let ciudad = document.getElementById("txtCiudad").value;
    let estado = document.getElementById("txtEstado").value;
    let idPersona = document.getElementById("txtIdPersona").value;
    let idUsuario = document.getElementById("txtIdUsuario").value;
    let idEmpleado = document.getElementById("txtIdEmpleado").value;

    let persona = {nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno,
        genero: genero, fechaNacimiento: fechaNacimiento, calle: calle, colonia: colonia,
        numero: numero, cp: cp, ciudad: ciudad, estado: estado, telcasa: telcasa, telmovil: telmovil, email: email, idPersona: idPersona};
    let usuario = {nombreUsuario: nombreUsuario, contrasenia: contrasenia, rol: rol, idUsuario: idUsuario};
    let empleado = JSON.stringify({persona: persona, usuario: usuario, idEmpleado: idEmpleado});
    let parametros = new URLSearchParams({datos: empleado, token: token});

    fetch('api/empleado/delete',
            {
                method: 'POST',
                body: parametros,
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                if (data.exception != null) {
                    Swal.fire('', 'Error interno del Servidor', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('', '¡No tiene permisos para realizar la Operacion!', 'error');
                    return;
                } else {
                    let msj = "Empleado con ID: " + data.idEmpleado + " Eliminado Correctamente";
                    
                    Swal.fire({
                        
                        title: '¿Desea Eliminar el Empleado?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Eliminar',
                        cancelButtonText: 'Cancelar'
                        
                    }).then((result) => {
                        if (result.isConfirmed) {
                           
                            Swal.fire('¡Empleado Eliminado!', msj, 'success');
                            showTable();
                            limpiarEmpleado();
                            
                        }
                    });
                }
            });
}

export function seleccionarEmpleado(i) {

    document.getElementById("txtNombre").value = empleados[i].persona.nombre;
    document.getElementById("txtApePaterno").value = empleados[i].persona.apellidoPaterno;
    document.getElementById("txtApeMaterno").value = empleados[i].persona.apellidoMaterno;
    document.getElementById("txtGenero").value = empleados[i].persona.genero;
    document.getElementById("txtRol").value = empleados[i].usuario.rol;
    document.getElementById("txtTelefonoC").value = empleados[i].persona.telcasa;
    document.getElementById("txtTelefonoM").value = empleados[i].persona.telmovil;
    document.getElementById("txtCorreo").value = empleados[i].persona.email;
    document.getElementById("txtNumUnico").value = empleados[i].numeroUnico;
    document.getElementById("txtUsuario").value = empleados[i].usuario.nombreUsuario;
    document.getElementById("txtContrasena").value = empleados[i].usuario.contrasenia;
    document.getElementById("txtCalle").value = empleados[i].persona.calle;
    document.getElementById("txtColonia").value = empleados[i].persona.colonia;
    document.getElementById("txtNum").value = empleados[i].persona.numero;
    document.getElementById("txtCodigoP").value = empleados[i].persona.cp;
    document.getElementById("txtCiudad").value = empleados[i].persona.ciudad;
    document.getElementById("txtEstado").value = empleados[i].persona.estado;
    document.getElementById("txtFechaN").value = empleados[i].persona.fechaNacimiento;
    document.getElementById("txtEstatus").value = empleados[i].estatus;
    document.getElementById("txtNumUnico").value = empleados[i].numeroUnico;
    document.getElementById("txtIdPersona").value = empleados[i].persona.idPersona;
    document.getElementById("txtIdUsuario").value = empleados[i].usuario.idUsuario;
    document.getElementById("txtIdEmpleado").value = empleados[i].idEmpleado;

    document.getElementById("btnDelete").classList.add("disabled");

}

export function limpiarEmpleado() {

    document.getElementById("txtNombre").value = "";
    document.getElementById("txtApePaterno").value = "";
    document.getElementById("txtApeMaterno").value = "";
    document.getElementById("txtTelefonoC").value = "";
    document.getElementById("txtTelefonoM").value = "";
    document.getElementById("txtCorreo").value = "";
    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtUsuario").value = "";
    document.getElementById("txtContrasena").value = "";
    document.getElementById("txtCalle").value = "";
    document.getElementById("txtColonia").value = "";
    document.getElementById("txtNum").value = "";
    document.getElementById("txtCodigoP").value = "";
    document.getElementById("txtCiudad").value = "";
    document.getElementById("txtFechaN").value = "";
    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtIdPersona").value = "";
    
}

export function agregarEmpleado() {
    
    const terminos = document.getElementById('terminos');
    
    if (campos.nombre && campos.apaterno && campos.usr && campos.ctn && campos.tm && campos.email && campos.calle && campos.colonia && campos.num && campos.ciudad && campos.cp) {
        
        insertarEmpleado();
        
    } else {
        
        Swal.fire('¡Campos Incompletos!', '', 'error');
    }
}
;

const formulario = document.getElementById('formulario');
const inputs = document.querySelectorAll('#formulario input');

const expresiones = {
    
    nombre: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    apaterno: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    tm: /^\d{10,14}$/,
    email: /^[^@]+@[^@]+\.[a-zA-Z]{2,}$/,
    usr: /^[a-zA-ZÀ-ÿ\s0-9_-]{3,20}$/,
    ctn: /^[a-zA-ZÀ-ÿ\s0-9_-]{6,20}$/,
    calle: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    colonia: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    num: /^\d{2,7}$/,
    ciudad: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    cp: /^\d{2,7}$/

};

const campos = {
    
    nombre: false,
    apaterno: false,
    tm: false,
    email: false,
    usr: false,
    ctn: false,
    calle: false,
    colonia: false,
    num: false,
    ciudad: false,
    cp: false
    
};

const validarCampo = (expresion, input, campo) => {
    
    if (expresion.test(input.value)) {
        
        document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-incorrecto');
        document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-correcto');
        document.querySelector(`#grupo__${campo} i`).classList.add('fa-check-circle');
        document.querySelector(`#grupo__${campo} i`).classList.remove('fa-times-circle');
        document.querySelector(`#grupo__${campo} .formulario__input-error`).classList.remove('formulario__input-error-activo');
        campos[campo] = true;
        
    } else {
        
        document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-incorrecto');
        document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-correcto');
        document.querySelector(`#grupo__${campo} i`).classList.add('fa-times-circle');
        document.querySelector(`#grupo__${campo} i`).classList.remove('fa-check-circle');
        document.querySelector(`#grupo__${campo} .formulario__input-error`).classList.add('formulario__input-error-activo');
        campos[campo] = false;
    }
};

const validarFormulario = (e) => {
    
    switch (e.target.name) {
        case "nombre":
            validarCampo(expresiones.nombre, e.target, 'nombre');
            break;
        case "apaterno":
            validarCampo(expresiones.apaterno, e.target, 'apaterno');
            break;
        case "tm":
            validarCampo(expresiones.tm, e.target, 'tm');
            break;
        case "email":
            validarCampo(expresiones.email, e.target, 'email');
            break;
        case "usr":
            validarCampo(expresiones.usr, e.target, 'usr');
            break;
        case "ctn":
            validarCampo(expresiones.ctn, e.target, 'ctn');
            break;
        case "calle":
            validarCampo(expresiones.calle, e.target, 'calle');
            break;
        case "colonia":
            validarCampo(expresiones.colonia, e.target, 'colonia');
            break;
        case "num":
            validarCampo(expresiones.num, e.target, 'num');
            break;
        case "ciudad":
            validarCampo(expresiones.ciudad, e.target, 'ciudad');
            break;
        case "cp":
            validarCampo(expresiones.cp, e.target, 'cp');
            break;
    }
};

inputs.forEach((input) => {
    input.addEventListener('keyup', validarFormulario);
    input.addEventListener('blur', validarFormulario);
});

formulario.addEventListener('submit', (e) => {
    e.preventDefault();

    const terminos = document.getElementById('terminos');
    
    if (campos.nombre && campos.apaterno && campos.usr && campos.ctn && campos.tm && campos.email && campos.calle && campos.colonia && campos.num && campos.ciudad && campos.cp && terminos.checked) {
        
        formulario.reset();

        document.getElementById('formulario__mensaje-exito').classList.add('formulario__mensaje-exito-activo');
        setTimeout(() => {
            document.getElementById('formulario__mensaje-exito').classList.remove('formulario__mensaje-exito-activo');
        }, 5000);

        document.querySelectorAll('.formulario__grupo-correcto').forEach((icono) => {
            icono.classList.remove('formulario__grupo-correcto');
        });
    } else {
        document.getElementById('formulario__mensaje').classList.add('formulario__mensaje-activo');
    }
});
