let soluciones;
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

    fetch("api/solucion/getAll", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaS(data);
                }
            });
}

export function cargarTablaS(data) {
    
    soluciones = data;
    let contenidoA = "";
    for (var i = 0; i < soluciones.length; i++) {
        let idSolucion = soluciones[i].idSolucion;
        let nombre = soluciones[i].producto.nombre;
        let marca = soluciones[i].producto.marca;
        let precioC = soluciones[i].producto.precioCompra;
        let precioV = soluciones[i].producto.precioVenta;
        let existencias = soluciones[i].producto.existencias;
        let estatus = soluciones[i].producto.estatus;


        contenidoA += "<tr onclick='moduloSoluciones.seleccionarSolucion(" + i + ");'>";
        contenidoA += "<td>" + idSolucion + "</td>";
        contenidoA += "<td>" + marca + "</td>";
        contenidoA += "<td>" + nombre + "</td>";
        contenidoA += "<td>" + precioC + "</td>";
        contenidoA += "<td>" + precioV + "</td>";
        contenidoA += "<td>" + existencias + "</td>";
        contenidoA += "<td>" + estatus + "</td>";
        contenidoA += "</tr>";
    }
    document.getElementById("tblSoluciones").innerHTML = contenidoA;
}

export function buscarSolucion() {
    
    let busqueda = document.getElementById("txtSearch").value;

    let parametros = {"buscar": busqueda};

    fetch('api/solucion/buscar?busqueda=' + busqueda)
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaS(data);
                }
            });
}

export function insertarSolucion() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let marca = sanitizar(document.getElementById("txtMarca").value);
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;

    let producto = {nombre: nombre, marca: marca, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus};
    let solucion = JSON.stringify({producto: producto});
    let parametros = new URLSearchParams({datos: solucion, token: token});

    fetch('api/solucion/insert',
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
                    let msj = "Solución insertado con el ID: " + data.idSolucion;
                    Swal.fire({
                        title: '¿Desea Agregar el Solución?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Agregar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Solución Agregado!', msj, 'success');
                            showTable();
                            limpiarSolucion();
                        }
                    });
                }
            });
}

export function actualizarSolucion() {

    let nombre = document.getElementById("txtNombre").value;
    let marca = document.getElementById("txtMarca").value;
    let codigoBarras = document.getElementById("txtCodigoBarras").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idProducto = document.getElementById("txtIdProducto").value;
    let idSolucion = document.getElementById("txtIdSolucion").value;

    let producto = {nombre: nombre, marca: marca, codigoBarras: codigoBarras, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus, idProducto: idProducto};
    let solucion = JSON.stringify({producto: producto, idSolucion: idSolucion});
    let parametros = new URLSearchParams({datos: solucion, token: token});

    fetch('api/solucion/update',
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
                    let msj = "Solución con ID: " + data.idSolucion + " Actualizado Correctamente";
                    Swal.fire({
                        title: '¿Desea Actualizar el Solución?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Actualizar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Solución Actualizado!', msj, 'success');
                            showTable();
                            limpiarSolucion();
                        }
                    });
                }
            });
}

export function activarSolucion() {

    let nombre = document.getElementById("txtNombre").value;
    let marca = document.getElementById("txtMarca").value;
    let codigoBarras = document.getElementById("txtCodigoBarras").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idProducto = document.getElementById("txtIdProducto").value;
    let idSolucion = document.getElementById("txtIdSolucion").value;

    let producto = {nombre: nombre, marca: marca, codigoBarras: codigoBarras, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus, idProducto: idProducto};
    let solucion = JSON.stringify({producto: producto, idSolucion: idSolucion});
    let parametros = new URLSearchParams({datos: solucion, token: token});

    fetch('api/solucion/activate',
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
                    let msj = "Solución con ID: " + data.idSolucion + " Activado Correctamente";
                    Swal.fire({
                        title: '¿Desea Activar el Solución?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Activar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Solución Activado!', msj, 'success');
                            showTable();
                            limpiarSolucion();
                        }
                    });
                }
            });
}

export function eliminarSolucion() {

    let nombre = document.getElementById("txtNombre").value;
    let marca = document.getElementById("txtMarca").value;
    let codigoBarras = document.getElementById("txtCodigoBarras").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idProducto = document.getElementById("txtIdProducto").value;
    let idSolucion = document.getElementById("txtIdSolucion").value;

    let producto = {nombre: nombre, marca: marca, codigoBarras: codigoBarras, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus, idProducto: idProducto};
    let solucion = JSON.stringify({producto: producto, idSolucion: idSolucion});
    let parametros = new URLSearchParams({datos: solucion, token: token});

    fetch('api/solucion/delete',
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
                    let msj = "Solución con ID: " + data.idSolucion + " Eliminado Correctamente";
                    Swal.fire({
                        title: '¿Desea Eliminar el Solución?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Eliminar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Solución Eliminado!', msj, 'success');
                            showTable();
                            limpiarSolucion();
                        }
                    });
                }
            });
}

export function seleccionarSolucion(i) {

    document.getElementById("txtNombre").value = soluciones[i].producto.nombre;
    document.getElementById("txtMarca").value = soluciones[i].producto.marca;
    document.getElementById("txtCodigoBarras").value = soluciones[i].producto.codigoBarras;
    document.getElementById("txtPrcCompra").value = soluciones[i].producto.precioCompra;
    document.getElementById("txtPrcVenta").value = soluciones[i].producto.precioVenta;
    document.getElementById("txtExistencias").value = soluciones[i].producto.existencias;
    document.getElementById("txtEstatus").value = soluciones[i].producto.estatus;
    document.getElementById("txtIdProducto").value = soluciones[i].producto.idProducto;
    document.getElementById("txtIdSolucion").value = soluciones[i].idSolucion;

    document.getElementById("btnDelete").classList.add("disabled");
}

export function limpiarSolucion() {

    document.getElementById("txtNombre").value = "";
    document.getElementById("txtMarca").value = "";
    document.getElementById("txtCodigoBarras").value = "";
    document.getElementById("txtPrcCompra").value = "";
    document.getElementById("txtPrcVenta").value = "";
    document.getElementById("txtExistencias").value = "";
    document.getElementById("txtEstatus").value = "Activo";
    document.getElementById("txtIdProducto").value = "";
    document.getElementById("txtIdSolucion").value = "";
}

export function agregarSolucion() {

    const terminos = document.getElementById('terminos');
    if (campos.nombre && campos.marca && campos.pcompra && campos.pventa && campos.existencias) {
        insertarSolucion();
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
    pventa: /^\d[0-9]+([.][0-9]+)?$/,
    existencias: /^\d{1,14}$/
};

const campos = {
    nombre: false,
    marca: false,
    pcompra: false,
    pventa: false,
    existencias: false
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
        case "existencias":
            validarCampo(expresiones.existencias, e.target, 'existencias');
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