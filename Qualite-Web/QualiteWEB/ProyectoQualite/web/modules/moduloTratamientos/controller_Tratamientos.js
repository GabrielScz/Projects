let tratamientos;
let currentUser = localStorage.getItem('currentUser');
let e = JSON.parse(currentUser);
let token = e.usuario.lastToken;
showTable();

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
    let datos = {estatus: document.getElementById("txtEstatusS").value, token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/tratamiento/getAll", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaT(data);
                }
            });
}

export function cargarTablaT(data) {
    tratamientos = data;
    let contenidoT = "";
    for (var i = 0; i < tratamientos.length; i++) {
        let idTratamiento = tratamientos[i].idTratamiento;
        let nombre = tratamientos[i].nombre;
        let precioC = tratamientos[i].precioCompra;
        let precioV = tratamientos[i].precioVenta;
        let estatus = tratamientos[i].estatus;


        contenidoT += "<tr onclick='moduloTratamientos.seleccionarTratamiento(" + i + ");'>";
        contenidoT += "<td>" + idTratamiento + "</td>";
        contenidoT += "<td>" + nombre + "</td>";
        contenidoT += "<td>" + precioC + "</td>";
        contenidoT += "<td>" + precioV + "</td>";
        contenidoT += "<td>" + estatus + "</td>";
        contenidoT += "</tr>";
    }
    document.getElementById("tblTratamientos").innerHTML = contenidoT;
}

export function buscarTratamiento() {
    let busqueda = document.getElementById("txtSearch").value;

    let parametros = {"buscar": busqueda};

    fetch('api/tratamiento/buscar?busqueda=' + busqueda)
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaT(data);
                }
            });
}

export function insertarTratamiento() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let estatus = document.getElementById("txtEstatus").value;

    let tratamiento = JSON.stringify({nombre: nombre, precioCompra: precioCompra, precioVenta: precioVenta, estatus: estatus});
    let parametros = new URLSearchParams({datos: tratamiento, token: token});

    fetch('api/tratamiento/insert',
            {
                method: 'POST',
                body: parametros,
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
            }).then(response => {
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
                    let msj = "Tratamiento insertado con el ID: " + data.idTratamiento;
                    Swal.fire({
                        title: '¿Desea Agregar el Tratamiento?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Agregar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Tratamiento Agregado!', msj, 'success');
                            showTable();
                            limpiarTratamiento();
                        }
                    });
                }
            });
}

export function actualizarTratamiento() {

    let nombre = document.getElementById("txtNombre").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idTratamiento = document.getElementById("txtIdTratamiento").value;

    let tratamiento = JSON.stringify({nombre: nombre, precioCompra: precioCompra, precioVenta: precioVenta, estatus: estatus, idTratamiento: idTratamiento});
    let parametros = new URLSearchParams({datos: tratamiento, token: token});

    fetch('api/tratamiento/update',
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
                    let msj = "Tratamiento con ID: " + data.idTratamiento + " Actualizado Correctamente";
                    Swal.fire({
                        title: '¿Desea Actualizar el Tratamiento?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Actualizar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Tratamiento Actualizado!', msj, 'success');
                            showTable();
                            limpiarTratamiento();
                        }
                    });
                }
            });
}

export function activarTratamiento() {

    let nombre = document.getElementById("txtNombre").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idTratamiento = document.getElementById("txtIdTratamiento").value;

    let tratamiento = JSON.stringify({nombre: nombre, precioCompra: precioCompra, precioVenta: precioVenta, estatus: estatus, idTratamiento: idTratamiento});
    let parametros = new URLSearchParams({datos: tratamiento, token: token});

    fetch('api/tratamiento/activate',
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
                    let msj = "Tratamiento con ID: " + data.idTratamiento + " Activado Correctamente";
                    Swal.fire({
                        title: '¿Desea Activar el Tratamiento?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Activar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Tratamiento Activado!', msj, 'success');
                            showTable();
                            limpiarTratamiento();
                        }
                    });
                }
            });
}

export function eliminarTratamiento() {

    let nombre = document.getElementById("txtNombre").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idTratamiento = document.getElementById("txtIdTratamiento").value;

    let tratamiento = JSON.stringify({nombre: nombre, precioCompra: precioCompra, precioVenta: precioVenta, estatus: estatus, idTratamiento: idTratamiento});
    let parametros = new URLSearchParams({datos: tratamiento, token: token});

    fetch('api/tratamiento/delete',
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
                    let msj = "Tratamiento con ID: " + data.idTratamiento + " Eliminado Correctamente";
                    Swal.fire({
                        title: '¿Desea Eliminar el Tratamiento?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Eliminar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Tratamiento Eliminado!', msj, 'success');
                            showTable();
                            limpiarTratamiento();
                        }
                    });
                }
            });
}

export function seleccionarTratamiento(i) {

    document.getElementById("txtNombre").value = tratamientos[i].nombre;
    document.getElementById("txtPrcCompra").value = tratamientos[i].precioCompra;
    document.getElementById("txtPrcVenta").value = tratamientos[i].precioVenta;
    document.getElementById("txtEstatus").value = tratamientos[i].estatus;
    document.getElementById("txtIdTratamiento").value = tratamientos[i].idTratamiento;

    document.getElementById("btnDelete").classList.add("disabled");
}

export function limpiarTratamiento() {

    document.getElementById("txtNombre").value = "";
    document.getElementById("txtPrcCompra").value = "";
    document.getElementById("txtPrcVenta").value = "";
    document.getElementById("txtEstatus").value = "Activo";
    document.getElementById("txtIdTratamiento").value = "";
}

export function agregarTratamiento() {

    const terminos = document.getElementById('terminos');
    if (campos.nombre && campos.marca && campos.pcompra && campos.pventa) {
        insertarAccesorio();
    } else {
        Swal.fire('¡Campos Incompletos!', '', 'error');
    }
}

const formulario = document.getElementById('formulario');

const inputs = document.querySelectorAll('#formulario input');

const expresiones = {
    nombre: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    marca: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    pcompra: /^\d[0-9]+([.][0-9]+)?$/, // 4 a 12 digitos.
    pventa: /^\d[0-9]+([.][0-9]+)?$/
};

const campos = {
    nombre: false,
    marca: false,
    pcompra: false,
    pventa: false
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
        case "marca":
            validarCampo(expresiones.marca, e.target, 'marca');
            break;
        case "pcompra":
            validarCampo(expresiones.pcompra, e.target, 'pcompra');
            break;
        case "pventa":
            validarCampo(expresiones.pventa, e.target, 'pventa');
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
    if (campos.nombre && campos.marca && campos.pcompra && campos.pventa && campos.existencias && terminos.checked) {
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