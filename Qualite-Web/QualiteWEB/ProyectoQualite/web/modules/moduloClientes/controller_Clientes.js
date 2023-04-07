let clientes;
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

function sanitizar(texto) {

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

export function showTable() {

    let datos = {estatus: document.getElementById("txtEstatusS").value};
    let parametros = new URLSearchParams(datos);

    fetch("api/cliente/getAll", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaC(data);
                }
            });
}

export function cargarTablaC(data) {

    clientes = data;
    let contenidoC = "";
    for (var i = 0; i < clientes.length; i++) {
        let idCliente = clientes[i].idCliente;
        let nombre = clientes[i].persona.nombre;
        let primerA = clientes[i].persona.apellidoPaterno;
        let segundoA = clientes[i].persona.apellidoMaterno;
        let genero = clientes[i].persona.genero;
        let cp = clientes[i].persona.cp;
        let ciudad = clientes[i].persona.ciudad;
        let estado = clientes[i].persona.estado;
        let telm = clientes[i].persona.telmovil;
        let email = clientes[i].persona.email;
        let estatus = clientes[i].estatus;
        let fecha = clientes[i].persona.fechaNacimiento;

        contenidoC += "<tr onclick='moduloClientes.seleccionarCliente(" + i + ");'>";
        contenidoC += "<td>" + idCliente + "</td>";
        contenidoC += "<td>" + nombre + "</td>";
        contenidoC += "<td>" + primerA + "</td>";
        contenidoC += "<td>" + segundoA + "</td>";
        contenidoC += "<td>" + genero + "</td>";
        contenidoC += "<td>" + telm + "</td>";
        contenidoC += "<td>" + email + "</td>";
        contenidoC += "<td>" + cp + "</td>";
        contenidoC += "<td>" + ciudad + "</td>";
        contenidoC += "<td>" + estado + "</td>";
        contenidoC += "<td>" + fecha + "</td>";
        contenidoC += "<td>" + estatus + "</td>";
        contenidoC += "</tr>";
    }

    document.getElementById("tblClientes").innerHTML = contenidoC;

}

export function buscarCliente() {

    let busqueda = document.getElementById("txtSearch").value;

    let parametros = {"buscar": busqueda};

    fetch('api/cliente/buscar?busqueda=' + busqueda)
            .then(response => response.json())
            .then(data => {
                
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaC(data);

                }
            });
}

export function insertarCliente() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let apellidoPaterno = sanitizar(document.getElementById("txtApePaterno").value);
    let apellidoMaterno = sanitizar(document.getElementById("txtApeMaterno").value);
    let telcasa = sanitizar(document.getElementById("txtTelefonoC").value);
    let telmovil = sanitizar(document.getElementById("txtTelefonoM").value);
    let email = document.getElementById("txtCorreo").value;
    let genero = document.getElementById("txtGenero").value;
    let fechaNacimiento = formatDate();
    let calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    let colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    let numero = sanitizar(document.getElementById("txtNum").value);
    let cp = sanitizar(document.getElementById("txtCodigoP").value);
    let ciudad = normalizar(document.getElementById("txtCiudad").value);
    let estado = document.getElementById("txtEstado").value;

    let persona = {nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno,
        genero: genero, fechaNacimiento: fechaNacimiento, calle: calle, colonia: colonia,
        numero: numero, cp: cp, ciudad: ciudad, estado: estado, telcasa: telcasa, telmovil: telmovil, email: email};
    let cliente = JSON.stringify({persona: persona});
    let parametros = new URLSearchParams({datos: cliente, token: token});

    fetch('api/cliente/insert',
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

                    let msj = "Cliente insertado con el ID: " + data.idCliente;

                    Swal.fire({

                        title: '¿Desea Agregar el Cliente?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Agregar',
                        cancelButtonText: 'Cancelar'

                    }).then((result) => {

                        if (result.isConfirmed) {

                            Swal.fire('¡Cliente Agregado!', msj, 'success');
                            showTable();
                            limpiarCliente();

                        }
                    });
                }
            });
}

export function actualizarCliente() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let apellidoPaterno = sanitizar(document.getElementById("txtApePaterno").value);
    let apellidoMaterno = sanitizar(document.getElementById("txtApeMaterno").value);
    let telcasa = sanitizar(document.getElementById("txtTelefonoC").value);
    let telmovil = sanitizar(document.getElementById("txtTelefonoM").value);
    let email = document.getElementById("txtCorreo").value;
    let genero = document.getElementById("txtGenero").value;
    let fechaNacimiento = formatDate();
    let calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    let colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    let numero = sanitizar(document.getElementById("txtNum").value);
    let cp = sanitizar(document.getElementById("txtCodigoP").value);
    let ciudad = normalizar(document.getElementById("txtCiudad").value);
    let estado = document.getElementById("txtEstado").value;
    let idPersona = document.getElementById("txtIdPersona").value;
    let idCliente = document.getElementById("txtIdCliente").value;

    let persona = {nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno,
        genero: genero, fechaNacimiento: fechaNacimiento, calle: calle, colonia: colonia,
        numero: numero, cp: cp, ciudad: ciudad, estado: estado, telcasa: telcasa, telmovil: telmovil, email: email, idPersona: idPersona};
    let cliente = JSON.stringify({persona: persona, idCliente: idCliente});
    let parametros = new URLSearchParams({datos: cliente, token: token});

    fetch('api/cliente/update',
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

                    let msj = "Cliente con ID: " + data.idCliente + " Actualizado Correctamente";

                    Swal.fire({

                        title: '¿Desea Actualizar el Cliente?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Actualizar',
                        cancelButtonText: 'Cancelar'

                    }).then((result) => {

                        if (result.isConfirmed) {

                            Swal.fire('¡Cliente Actualizado!', msj, 'success');
                            showTable();
                            limpiarCliente();
                        }
                    });
                }
            });
}

export function activarCliente() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let apellidoPaterno = sanitizar(document.getElementById("txtApePaterno").value);
    let apellidoMaterno = sanitizar(document.getElementById("txtApeMaterno").value);
    let telcasa = sanitizar(document.getElementById("txtTelefonoC").value);
    let telmovil = sanitizar(document.getElementById("txtTelefonoM").value);
    let email = document.getElementById("txtCorreo").value;
    let genero = document.getElementById("txtGenero").value;
    let fechaNacimiento = formatDate();
    let calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    let colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    let numero = sanitizar(document.getElementById("txtNum").value);
    let cp = sanitizar(document.getElementById("txtCodigoP").value);
    let ciudad = normalizar(document.getElementById("txtCiudad").value);
    let estado = document.getElementById("txtEstado").value;
    let idPersona = document.getElementById("txtIdPersona").value;
    let idCliente = document.getElementById("txtIdCliente").value;

    let persona = {nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno,
        genero: genero, fechaNacimiento: fechaNacimiento, calle: calle, colonia: colonia,
        numero: numero, cp: cp, ciudad: ciudad, estado: estado, telcasa: telcasa, telmovil: telmovil, email: email, idPersona: idPersona};
    let cliente = JSON.stringify({persona: persona, idCliente: idCliente});
    let parametros = new URLSearchParams({datos: cliente, token: token});

    fetch('api/cliente/activate',
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

                    let msj = "Cliente con ID: " + data.idCliente + " Activado Correctamente";

                    Swal.fire({

                        title: '¿Desea Activar el Cliente?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Activar',
                        cancelButtonText: 'Cancelar'

                    }).then((result) => {
                        if (result.isConfirmed) {

                            Swal.fire('¡Cliente Activado!', msj, 'success');
                            showTable();
                            limpiarCliente();
                        }
                    });
                }
            });
}

export function eliminarCliente() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let apellidoPaterno = sanitizar(document.getElementById("txtApePaterno").value);
    let apellidoMaterno = sanitizar(document.getElementById("txtApeMaterno").value);
    let telcasa = sanitizar(document.getElementById("txtTelefonoC").value);
    let telmovil = sanitizar(document.getElementById("txtTelefonoM").value);
    let email = document.getElementById("txtCorreo").value;
    let genero = document.getElementById("txtGenero").value;
    let fechaNacimiento = formatDate();
    let calle = normalizar(sanitizar(document.getElementById("txtCalle").value));
    let colonia = normalizar(sanitizar(document.getElementById("txtColonia").value));
    let numero = sanitizar(document.getElementById("txtNum").value);
    let cp = sanitizar(document.getElementById("txtCodigoP").value);
    let ciudad = normalizar(document.getElementById("txtCiudad").value);
    let estado = document.getElementById("txtEstado").value;
    let idPersona = document.getElementById("txtIdPersona").value;
    let idCliente = document.getElementById("txtIdCliente").value;

    let persona = {nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno,
        genero: genero, fechaNacimiento: fechaNacimiento, calle: calle, colonia: colonia,
        numero: numero, cp: cp, ciudad: ciudad, estado: estado, telcasa: telcasa, telmovil: telmovil, email: email, idPersona: idPersona};
    let cliente = JSON.stringify({persona: persona, idCliente: idCliente});
    let parametros = new URLSearchParams({datos: cliente, token: token});

    fetch('api/cliente/delete',
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

                    let msj = "Cliente con ID: " + data.idCliente + " Eliminado Correctamente";

                    Swal.fire({

                        title: '¿Desea Eliminar el Cliente?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Eliminar',
                        cancelButtonText: 'Cancelar'

                    }).then((result) => {

                        if (result.isConfirmed) {

                            Swal.fire('¡Cliente Eliminado!', msj, 'success');
                            showTable();
                            limpiarCliente();
                        }
                    });
                }
            });
}

export function seleccionarCliente(i) {

    document.getElementById("txtNombre").value = clientes[i].persona.nombre;
    document.getElementById("txtApePaterno").value = clientes[i].persona.apellidoPaterno;
    document.getElementById("txtApeMaterno").value = clientes[i].persona.apellidoMaterno;
    document.getElementById("txtGenero").value = clientes[i].persona.genero;
    document.getElementById("txtTelefonoC").value = clientes[i].persona.telcasa;
    document.getElementById("txtTelefonoM").value = clientes[i].persona.telmovil;
    document.getElementById("txtCorreo").value = clientes[i].persona.email;
    document.getElementById("txtNumUnico").value = clientes[i].numeroUnico;
    document.getElementById("txtCalle").value = clientes[i].persona.calle;
    document.getElementById("txtColonia").value = clientes[i].persona.colonia;
    document.getElementById("txtNum").value = clientes[i].persona.numero;
    document.getElementById("txtCodigoP").value = clientes[i].persona.cp;
    document.getElementById("txtCiudad").value = clientes[i].persona.ciudad;
    document.getElementById("txtEstado").value = clientes[i].persona.estado;
    document.getElementById("txtFechaN").value = clientes[i].persona.fechaNacimiento;
    document.getElementById("txtEstatus").value = clientes[i].estatus;
    document.getElementById("txtNumUnico").value = clientes[i].numeroUnico;
    document.getElementById("txtIdPersona").value = clientes[i].persona.idPersona;
    document.getElementById("txtIdCliente").value = clientes[i].idCliente;

    document.getElementById("btnDelete").classList.add("disabled");

}

export function limpiarCliente() {

    document.getElementById("txtNombre").value = "";
    document.getElementById("txtApePaterno").value = "";
    document.getElementById("txtApeMaterno").value = "";
    document.getElementById("txtGenero").value = "";
    document.getElementById("txtTelefonoC").value = "";
    document.getElementById("txtTelefonoM").value = "";
    document.getElementById("txtCorreo").value = "";
    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtCalle").value = "";
    document.getElementById("txtColonia").value = "";
    document.getElementById("txtNum").value = "";
    document.getElementById("txtCodigoP").value = "";
    document.getElementById("txtCiudad").value = "";
    document.getElementById("txtEstado").value = "";
    document.getElementById("txtFechaN").value = "";
    document.getElementById("txtEstatus").value = "";
    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtIdCliente").value = "";
    document.getElementById("txtIdPersona").value = "";

}

export function agregarCliente() {

    const terminos = document.getElementById('terminos');
    if (campos.nombre && campos.apaterno && campos.tm && campos.calle && campos.colonia && campos.num && campos.ciudad && campos.cp && campos.email) {
        insertarCliente();

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
    tm: /^\d{2,14}$/,
    calle: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    colonia: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    num: /^\d{2,7}$/,
    ciudad: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    cp: /^\d{2,7}$/,
    email: /^[^@]+@[^@]+\.[a-zA-Z]{2,}$/

};

const campos = {

    nombre: false,
    apaterno: false,
    tm: false,
    calle: false,
    colonia: false,
    num: false,
    ciudad: false,
    cp: false,
    email: false

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
        case "email":
            validarCampo(expresiones.email, e.target, 'email');
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

    if (campos.nombre && campos.apaterno && campos.tm && campos.calle && campos.colonia && campos.num && campos.ciudad && campos.cp && campos.email && terminos.checked) {

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