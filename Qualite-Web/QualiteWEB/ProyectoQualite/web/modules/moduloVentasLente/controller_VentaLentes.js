let tiposMica;
let materiales;
let examenVista;
let armazones;
let presupuesto;
let clientes;
let tratamientos;

let indexA = 0;
let indexM = 0;
let indexT = 0;
let indexEV = 0;
let indexTM = 0;
let indexPL = 0;
let indexVS = 0;

let tratamientosSeleccionado = [];
let presupuestoLente = [];
let ventaPresupuestoLente = [];

let totalPrecios = 0;
let cantidadT = 0;

let currentUser = localStorage.getItem('currentUser');
let e = JSON.parse(currentUser);
let token = e.usuario.lastToken;
showTableC();

export function showTableExamenV(idCliente, indexCS) {

    let datos = {idCliente: idCliente, token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLente/getAllExamenVista", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    examenVista = data;
                    const select = document.querySelector("#txtExamenV" + indexCS);
                    select.options.length = 0;

                    data.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.clave;
                        option.text = item.clave;
                        select.appendChild(option);
                    });
                }
            });
}

export function showTableMaterial(indexM) {

    let datos = {token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLente/getAllMaterial", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    materiales = data;
                    const select = document.querySelector("#txtMaterial");
                    select.options.length = 0;

                    data.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.nombre;
                        option.text = item.nombre;
                        select.appendChild(option);
                    });
                }
            });
}

export function showTableTratamiento(indexT) {

    let datos = {token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLente/getAllTratamiento", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    tratamientos = data;

                    var container = document.createElement('div');

                    tratamientos.forEach(function (elemento) {
                        var checkbox = document.createElement('input');
                        checkbox.type = 'checkbox';
                        checkbox.name = 'tratamientos';
                        checkbox.className = 'classcheck';
                        checkbox.value = elemento.id;

                        var label = document.createElement('label');
                        label.htmlFor = elemento.id;
                        label.appendChild(document.createTextNode(elemento.nombre));

                        var div = document.createElement('div');
                        div.className = 'divcheck';
                        div.appendChild(label);
                        div.appendChild(checkbox);

                        container.appendChild(div);
                    });

                    document.getElementById('contenedor-tratamientos').appendChild(container);

                }
            });
}


export function showTableArmazon(indexA) {

    let datos = {token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLente/getAllArmazon", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    armazones = data;
                    const select = document.querySelector("#txtArmazon");
                    select.options.length = 0;

                    data.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.modelo;
                        option.text = item.producto.marca + " - " + item.modelo;
                        select.appendChild(option);
                    });
                }
            });
}

export function showTableMica(indexTM) {

    let datos = {token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLente/getAllTipoMica", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    tiposMica = data;
                    const select = document.querySelector("#txtTipoMica");
                    select.options.length = 0;

                    data.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.nombre;
                        option.text = item.nombre;
                        select.appendChild(option);
                    });
                }
            });
}

export function showTableC() {

    let datos = {estatus: 1};
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
        let nombre = clientes[i].persona.nombre;
        let primerA = clientes[i].persona.apellidoPaterno;
        let telM = clientes[i].persona.telmovil;

        contenidoC += '<tr>';
        contenidoC += '<td style="width: 30%;padding-top: 20px">' + nombre + ' ' + primerA + '</td>';
        contenidoC += '<td style="width: 30%;padding-top: 20px">' + telM + '</td>';
        contenidoC += '<td style="width: 30%; padding-top: 10.5px"> <select style="width: 70% !important;" class="formulario__input_ventas" type="number" id="txtExamenV' + i + '"></select></td>';
        contenidoC += '<td style="justify-content: center; "><button onclick="moduloVentaLente.selectCliente(' + i + ');" style="border-color: #233A54; border-radius: 3px; color: #233A54" class="formulario_select_btn"><i class="fa fa-search"></i></button></td>';
        contenidoC += '<td style="display: flex; justify-content: center; "><button onclick="moduloVentaLente.agregar(' + i + ');" style="border-color: #233A54; border-radius: 3px; color: #233A54" class="formulario_select_btn"><i class="fa fa-plus"></i></button></td>';
        contenidoC += "</tr>";
    }
    document.getElementById("tblClientes").innerHTML = contenidoC;
}

export function selectCliente(i) {

    showTableExamenV(clientes[i].idCliente, i);
}

export function selectProductos(i) {

    showTableMaterial(i);
    showTableArmazon(i);
    showTableTratamiento(i);
    showTableMica(i);

}

export function agregar(i) {

    let contenidoEC = "";
    let selectEV = document.querySelector("#txtExamenV" + i);

    let nombre = clientes[i].persona.nombre;
    let primerA = clientes[i].persona.apellidoPaterno;
    indexEV = selectEV.selectedIndex;

    document.getElementById("txtNombreCliente").value = nombre + ' ' + primerA;
    document.getElementById("txtClaveExamenVista").value = examenVista[indexEV].clave;
    document.getElementById("txtTipoMica").value = tiposMica[indexTM].nombre;

    presupuesto = {'examenVista': examenVista[indexEV], 'clave': Math.random() * 10000000};

    Swal.fire({
        title: '¡Cliente Agregado!',
        icon: 'success',
        text: '¡Cliente agregado al carrito!',
        showConfirmButton: false,
        timer: 2000
    });
}

function tSeleccionados() {

    var checkbox = document.getElementsByName("tratamientos");
    
    for (var i = 0; i < checkbox.length; i++) {
        if (checkbox[i].checked && tratamientos[i]) {
        }

    }
    
}

export function generarVentaLente() {
    
    Swal.fire({
        title: '¿Desea continuar con la Venta?',
        showCancelButton: true,
        showConfirmButton: true,
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire('¡Venta Correcta!', '¡Venta realizada correctamente!', 'success');
            generarPresupuestoLente();
            limpiarTabla();
        }
    });
}

export function generarPresupuestoLente() {

    let selectA = document.querySelector("#txtArmazon");
    let selectM = document.querySelector("#txtMaterial");
    let selectTM = document.querySelector("#txtTipoMica");

    indexA = selectA.selectedIndex;
    indexM = selectM.selectedIndex;
    indexTM = selectTM.selectedIndex;

    let alturaOblea = document.getElementById("txtAlturaOblea").value;
    let descuento = document.getElementById("txtDescuento").value;
    let cantidad = document.getElementById("txtCantidad").value;
    
    tSeleccionados();

    presupuestoLente[indexVS] = {'alturaOblea': alturaOblea, 'presupuesto': presupuesto, 'tipoMica': tiposMica[indexTM], 'material': materiales[indexM], 'armazon': armazones[indexA], 'listaTratamiento': tratamientosSeleccionado};
    ventaPresupuestoLente[indexVS] = {'presupuestoLentes': presupuestoLente[indexVS], 'cantidad': cantidad, 'precioUnitario': cantidadT, 'descuento': descuento};
    indexVS++;

    let venta = {clave: Math.random() * 10000000, empleado: e};
    let dvp = {ListaVentaPresupuestoLentes: ventaPresupuestoLente, venta: venta};
    let datos = {datos: JSON.stringify(dvp)};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLente/guardar", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    
                }
            });
}

selectProductos();

export function precioTotal() {

    let selectA = document.querySelector("#txtArmazon");
    let selectM = document.querySelector("#txtMaterial");
    let selectTM = document.querySelector("#txtTipoMica");

    indexA = selectA.selectedIndex;
    indexM = selectM.selectedIndex;
    indexTM = selectTM.selectedIndex;

    let precioTotal = 0;
    let precioTratamiento = 0;
    let descuento = document.getElementById('txtDescuento').value / 100;
    let cantidad = document.getElementById("txtCantidad").value;

    tSeleccionados();

    console.log(tratamientosSeleccionado);
    
    tratamientosSeleccionado.forEach(preciot => {
        precioTratamiento += preciot.precioVenta;
    });

    let precioMica = tiposMica[indexTM].precioVenta;
    let precioArmazon = armazones[indexA].producto.precioVenta;
    let precioMaterial = materiales[indexM].precioVenta;

    precioTotal = (precioArmazon + precioMaterial + precioMica + precioTratamiento) * cantidad;
    console.log(indexTM + " " + indexA + " " + indexM);
    console.log("Precios " + precioTratamiento + " " + precioArmazon + " " + precioMaterial + " " + precioMica);

    cantidadT = precioTotal - (precioTotal * descuento);

    document.getElementById("txtTotalVenta").innerText = "$" + cantidadT.toLocaleString('en-US', {style: 'decimal', minimumFractionDigits: 2});

}

export function limpiarTabla() {

    ventaPresupuestoLente = [];
    indexVS = 0;
    document.getElementById("txtNombreCliente").value = "";
    document.getElementById("txtClaveExamenVista").value = "";
    document.getElementById("txtTipoMica").value = "";
    document.getElementById("txtArmazon").value = "";
    document.getElementById("txtMaterial").value = "";
    document.getElementById("txtAlturaOblea").value = "0";
    document.getElementById("txtCantidad").value = "0";
    document.getElementById("txtDescuento").value = "0";
    document.getElementById("txtTotalVenta").innerText = "$0.00";
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

export function agregarCompra() {
    
    const terminos = document.getElementById('terminos');
    
    if (campos.mica && campos.armazon && campos.material && campos.alturaO && campos.cantidad && campos.tratamientos) {
        
        generarPresupuestoLente();
        
    } else {
        
        Swal.fire('¡Campos Incompletos!', '', 'error');
    }
}
;

const formulario = document.getElementById('formulario');
const inputs = document.querySelectorAll('#formulario input');

const expresiones = {
    
    mica: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    armazon: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // Letras y espacios, pueden llevar acentos.
    material: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    alturaO: /^\d{1,50}$/,
    cantidad: /^\d{1,50}$/,
    tratamientos: /^[a-zA-ZÀ-ÿ\s]{1,40}$/

};

const campos = {
    
    mica: false,
    armazon: false,
    material: false,
    alturaO: false,
    cantidad: false,
    tratamientos: false
    
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
        case "mica":
            validarCampo(expresiones.mica, e.target, 'mica');
            break;
        case "armazon":
            validarCampo(expresiones.armazon, e.target, 'armazon');
            break;
        case "material":
            validarCampo(expresiones.material, e.target, 'material');
            break;
        case "alturaO":
            validarCampo(expresiones.alturaO, e.target, 'alturaO');
            break;
        case "cantidad":
            validarCampo(expresiones.cantidad, e.target, 'cantidad');
            break;
        case "tratamientos":
            validarCampo(expresiones.tratamientos, e.target, 'tratamientos');
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
    
    if (campos.mica && campos.armazon && campos.material && campos.alturaO && campos.cantidad && campos.tratamientos && terminos.checked) {
        
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