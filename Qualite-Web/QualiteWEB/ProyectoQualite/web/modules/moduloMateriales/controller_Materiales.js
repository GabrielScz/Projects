let materiales;
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
    let datos = {token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/material/getAll", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaM(data);
                }
            });
}

export function cargarTablaM(data) {
    materiales = data;
    let contenidoA = "";
    for (var i = 0; i < materiales.length; i++) {
        let idMaterial = materiales[i].idMaterial;
        let nombre = materiales[i].nombre;
        let precioC = materiales[i].precioCompra;
        let precioV = materiales[i].precioVenta;


        contenidoA += "<tr onclick='moduloMateriales.seleccionarMaterial(" + i + ");'>";
        contenidoA += "<td style='width: 25%';>" + idMaterial + "</td>";
        contenidoA += "<td style='width: 25%';>" + nombre + "</td>";
        contenidoA += "<td style='width: 25%';>" + precioC + "</td>";
        contenidoA += "<td style='width: 25%';>" + precioV + "</td>";
        contenidoA += "</tr>";
    }
    document.getElementById("tblMateriales").innerHTML = contenidoA;
}

export function buscarMaterial() {
    let busqueda = document.getElementById("txtSearch").value;

    let parametros = {"buscar": busqueda};

    fetch('api/material/buscar?busqueda=' + busqueda)
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaM(data);
                }
            });
}

export function insertarMaterial() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;

    let material = JSON.stringify({nombre: nombre, precioCompra: precioCompra, precioVenta: precioVenta});
    let parametros = new URLSearchParams({datos: material, token: token});

    fetch('api/material/insert',
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
                    let msj = "Material insertado con el ID: " + data.idMaterial;
                    Swal.fire({
                        title: '¿Desea Agregar el Material?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Agregar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Material Agregado!', msj, 'success');
                            showTable();
                            limpiarMaterial();
                        }
                    });
                }
            });
}

export function actualizarMaterial() {

    let nombre = document.getElementById("txtNombre").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let idMaterial = document.getElementById("txtIdMaterial").value;

    let material = JSON.stringify({nombre: nombre, precioCompra: precioCompra, precioVenta: precioVenta, idMaterial: idMaterial});
    let parametros = new URLSearchParams({datos: material, token: token});

    fetch('api/material/update',
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
                    let msj = "Material con ID: " + data.idMaterial + " Actualizado Correctamente";
                    Swal.fire({
                        title: '¿Desea Actualizar el Material?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Actualizar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Material Actualizado!', msj, 'success');
                            showTable();
                            limpiarMaterial();
                        }
                    });
                }
            });
}

export function seleccionarMaterial(i) {

    document.getElementById("txtNombre").value = materiales[i].nombre;
    document.getElementById("txtPrcCompra").value = materiales[i].precioCompra;
    document.getElementById("txtPrcVenta").value = materiales[i].precioVenta;
    document.getElementById("txtIdMaterial").value = materiales[i].idMaterial;

}

export function limpiarMaterial() {

    document.getElementById("txtNombre").value = "";
    document.getElementById("txtPrcCompra").value = "";
    document.getElementById("txtPrcVenta").value = "";
    document.getElementById("txtIdMaterial").value = "";
}

export function agregarMaterial() {

    const terminos = document.getElementById('terminos');
    if (campos.nombre && campos.pcompra && campos.pventa) {
        insertarMaterial();
    } else {
        Swal.fire('¡Campos Incompletos!', '', 'error');
    }
}

const formulario = document.getElementById('formulario');

const inputs = document.querySelectorAll('#formulario input');

const expresiones = {
    nombre: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    pcompra: /^\d[0-9]+([.][0-9]+)?$/, // 4 a 12 digitos.
    pventa: /^\d[0-9]+([.][0-9]+)?$/
};

const campos = {
    nombre: false,
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
    if (campos.nombre && campos.pcompra && campos.pventa && terminos.checked) {
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