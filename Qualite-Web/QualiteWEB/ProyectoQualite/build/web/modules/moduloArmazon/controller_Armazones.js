let armazones;
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

    fetch("api/armazon/getAll", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaA(data);
                }
            });
}

export function cargarTablaA(data) {

    armazones = data;
    let contenidoA = "";
    for (var i = 0; i < armazones.length; i++) {
        let idArmazon = armazones[i].idArmazon;
        let marca = armazones[i].producto.marca;
        let modelo = armazones[i].modelo;
        let color = armazones[i].color;
        let dimensiones = armazones[i].dimensiones;
        let precioC = armazones[i].producto.precioCompra;
        let precioV = armazones[i].producto.precioVenta;
        let existencias = armazones[i].producto.existencias;
        let estatus = armazones[i].producto.estatus;

        contenidoA += "<tr onclick='moduloArmazones.seleccionarArmazon(" + i + ");'>";
        contenidoA += "<td>" + idArmazon + "</td>";
        contenidoA += "<td>" + marca + "</td>";
        contenidoA += "<td>" + modelo + "</td>";
        contenidoA += "<td>" + color + "</td>";
        contenidoA += "<td>" + dimensiones + "</td>";
        contenidoA += "<td>" + precioC + "</td>";
        contenidoA += "<td>" + precioV + "</td>";
        contenidoA += "<td>" + existencias + "</td>";
        contenidoA += "<td>" + estatus + "</td>";
        contenidoA += "</tr>";
    }

    document.getElementById("tblArmazones").innerHTML = contenidoA;

}

export function buscarArmazon() {

    let busqueda = document.getElementById("txtSearch").value;

    let parametros = {"buscar": busqueda};

    fetch('api/armazon/buscar?busqueda=' + busqueda)
            .then(response => response.json())
            .then(data => {
                
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaA(data);
                }
            });
}

export function insertarAccesorio() {

    let nombre = sanitizar(document.getElementById("txtNombre").value);
    let marca = sanitizar(document.getElementById("txtMarca").value);
    let modelo = sanitizar(document.getElementById("txtModelo").value);
    let dimensiones = sanitizar(document.getElementById("txtDimensiones").value);
    let descripcion = sanitizar(document.getElementById("txtDescripcion").value);
    let color = normalizar(document.getElementById("txtColor").value);
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;

    let producto = {nombre: nombre, marca: marca, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus};
    let armazones = JSON.stringify({producto: producto, modelo: modelo, dimensiones: dimensiones, descripcion: descripcion, color: color});
    let parametros = new URLSearchParams({datos: armazones, token: token});

    fetch('api/armazon/insert',
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
                    let msj = "Armazón insertado con el Id: " + data.idArmazon;
                    
                    Swal.fire({
                        
                        title: '¿Desea Agregar el Armazón?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Agregar',
                        cancelButtonText: 'Cancelar'
                        
                    }).then((result) => {
                        
                        if (result.isConfirmed) {
                            
                            Swal.fire('¡Armazón Agregado!', msj, 'success');
                            showTable();
                            limpiarArmazon();
                            
                        }
                    });
                }
            });
}

export function actualizarArmazon() {

    let nombre = document.getElementById("txtNombre").value;
    let marca = document.getElementById("txtMarca").value;
    let modelo = sanitizar(document.getElementById("txtModelo").value);
    let dimensiones = sanitizar(document.getElementById("txtDimensiones").value);
    let descripcion = sanitizar(document.getElementById("txtDescripcion").value);
    let color = normalizar(document.getElementById("txtColor").value);
    let codigoBarras = document.getElementById("txtCodigoBarras").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idProducto = document.getElementById("txtIdProducto").value;
    let idArmazon = document.getElementById("txtIdArmazon").value;

    let producto = {nombre: nombre, marca: marca, codigoBarras: codigoBarras, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus, idProducto: idProducto};
    let armazon = JSON.stringify({producto: producto, idArmazon: idArmazon, modelo: modelo, dimensiones: dimensiones, descripcion: descripcion, color: color});
    let parametros = new URLSearchParams({datos: armazon, token: token});

    fetch('api/armazon/update',
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
                    let msj = "Armazón con Id: " + data.idArmazon + " Actualizado Correctamente";
                    
                    Swal.fire({
                        
                        title: '¿Desea Actualizar el Armazón?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Armazón',
                        cancelButtonText: 'Cancelar'
                        
                    }).then((result) => {
                        
                        if (result.isConfirmed) {
                            
                            Swal.fire('¡Armazón Actualizado!', msj, 'success');
                            showTable();
                            limpiarArmazon();
                            
                        }
                    });
                }
            });
}

export function activarArmazon() {

    let nombre = document.getElementById("txtNombre").value;
    let marca = document.getElementById("txtMarca").value;
    let modelo = sanitizar(document.getElementById("txtModelo").value);
    let dimensiones = sanitizar(document.getElementById("txtDimensiones").value);
    let descripcion = sanitizar(document.getElementById("txtDescripcion").value);
    let color = normalizar(document.getElementById("txtColor").value);
    let codigoBarras = document.getElementById("txtCodigoBarras").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idProducto = document.getElementById("txtIdProducto").value;
    let idArmazon = document.getElementById("txtIdArmazon").value;

    let producto = {nombre: nombre, marca: marca, codigoBarras: codigoBarras, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus, idProducto: idProducto};
    let armazon = JSON.stringify({producto: producto, idArmazon: idArmazon, modelo: modelo, dimensiones: dimensiones, descripcion: descripcion, color: color});
    let parametros = new URLSearchParams({datos: armazon, token: token});

    fetch('api/armazon/activate',
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
                    
                    let msj = "Armazón con Id: " + data.idArmazon + " Activado Correctamente";
                    
                    Swal.fire({
                        
                        title: '¿Desea Activar el Armazón?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Activar',
                        cancelButtonText: 'Cancelar'
                        
                    }).then((result) => {
                        
                        if (result.isConfirmed) {
                            
                            Swal.fire('¡Armazón Activado!', msj, 'success');
                            showTable();
                            limpiarArmazon();
                            
                        }
                    });
                }
            });
}

export function eliminarArmazon() {

    let nombre = document.getElementById("txtNombre").value;
    let marca = document.getElementById("txtMarca").value;
    let modelo = sanitizar(document.getElementById("txtModelo").value);
    let dimensiones = sanitizar(document.getElementById("txtDimensiones").value);
    let descripcion = sanitizar(document.getElementById("txtDescripcion").value);
    let color = normalizar(document.getElementById("txtColor").value);
    let codigoBarras = document.getElementById("txtCodigoBarras").value;
    let precioCompra = document.getElementById("txtPrcCompra").value;
    let precioVenta = document.getElementById("txtPrcVenta").value;
    let existencias = document.getElementById("txtExistencias").value;
    let estatus = document.getElementById("txtEstatus").value;
    let idProducto = document.getElementById("txtIdProducto").value;
    let idArmazon = document.getElementById("txtIdArmazon").value;

    let producto = {nombre: nombre, marca: marca, codigoBarras: codigoBarras, precioCompra: precioCompra, precioVenta: precioVenta, existencias: existencias,
        estatus: estatus, idProducto: idProducto};
    let armazon = JSON.stringify({producto: producto, idArmazon: idArmazon, modelo: modelo, dimensiones: dimensiones, descripcion: descripcion, color: color});
    let parametros = new URLSearchParams({datos: armazon, token: token});

    fetch('api/armazon/delete',
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
                    let msj = "Armazón con ID: " + data.idArmazon + " Eliminado Correctamente";
                    Swal.fire({
                        title: '¿Desea Eliminar el Armazón?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Eliminar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Armazón Eliminado!', msj, 'success');
                            showTable();
                            limpiarArmazon();
                        }
                    });
                }
            });

}

export function seleccionarArmazon(i) {

    document.getElementById("txtNombre").value = armazones[i].producto.nombre;
    document.getElementById("txtMarca").value = armazones[i].producto.marca;
    document.getElementById("txtModelo").value = armazones[i].modelo;
    document.getElementById("txtColor").value = armazones[i].color;
    document.getElementById("txtDescripcion").value = armazones[i].descripcion;
    document.getElementById("txtDimensiones").value = armazones[i].dimensiones;
    document.getElementById("txtCodigoBarras").value = armazones[i].producto.codigoBarras;
    document.getElementById("txtPrcCompra").value = armazones[i].producto.precioCompra;
    document.getElementById("txtPrcVenta").value = armazones[i].producto.precioVenta;
    document.getElementById("txtExistencias").value = armazones[i].producto.existencias;
    document.getElementById("txtEstatus").value = armazones[i].producto.estatus;
    document.getElementById("txtIdProducto").value = armazones[i].producto.idProducto;
    document.getElementById("txtIdArmazon").value = armazones[i].idArmazon;

    document.getElementById("btnDelete").classList.add("disabled");

}

export function limpiarArmazon() {

    document.getElementById("txtNombre").value = "";
    document.getElementById("txtMarca").value = "";
    document.getElementById("txtCodigoBarras").value = "";
    document.getElementById("txtPrcCompra").value = "";
    document.getElementById("txtPrcVenta").value = "";
    document.getElementById("txtExistencias").value = "";
    document.getElementById("txtEstatus").value = "Activo";
    document.getElementById("txtIdProducto").value = "";
    document.getElementById("txtIdArmazon").value = "";
    document.getElementById("txtModelo").value = "";
    document.getElementById("txtColor").value = "";
    document.getElementById("txtDescripcion").value = "";
    document.getElementById("txtDimensiones").value = "";

}

export function agregarArmazon() {

    const terminos = document.getElementById('terminos');
    
    if (campos.nombre && campos.marca && campos.modelo && campos.color && campos.dimensiones && campos.descripcion && campos.pcompra && campos.pventa && campos.existencias) {
        
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
    modelo: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    color: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    descripcion: /^[a-zA-ZÀ-ÿ\s]{1,100}$/,
    dimensiones: /^\d[0-9]+([.][0-9]+)?$/,
    pcompra: /^\d[0-9]+([.][0-9]+)?$/, // 4 a 12 digitos.
    pventa: /^\d[0-9]+([.][0-9]+)?$/,
    existencias: /^\d{1,14}$/

};

const campos = {

    nombre: false,
    marca: false,
    modelo: false,
    color: false,
    dimensiones: false,
    descripcion: false,
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
        case "modelo":
            validarCampo(expresiones.modelo, e.target, 'modelo');
            break;
        case "color":
            validarCampo(expresiones.color, e.target, 'color');
            break;
        case "descripcion":
            validarCampo(expresiones.descripcion, e.target, 'descripcion');
            break;
        case "dimensiones":
            validarCampo(expresiones.dimensiones, e.target, 'dimensiones');
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
    
    if (campos.nombre && campos.marca && campos.modelo && campos.color && campos.dimensiones && campos.descripcion && campos.pcompra && campos.pventa && campos.existencias && terminos.checked) {
        
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