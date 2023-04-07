let examenVista;
let lentesContacto;
let presupuesto;
let presupuestoLenteContacto = [];
let clientes;
let indexPLC = 0;
let indexVS = 0;
let ventaPresupuesto = [];
let currentUser = localStorage.getItem('currentUser');
let e = JSON.parse(currentUser);
let token = e.usuario.lastToken;
showTableC();

export function showTableEV(idCliente, indexCS) {
    let datos = {idCliente: idCliente, token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLenteContacto/getAll", {
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

export function showTableLC(indexLC) {
    let datos = {token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLenteContacto/getAllLC", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    lentesContacto = data;
                    const select = document.querySelector("#txtLenteC" + indexLC);
                    select.options.length = 0;

                    data.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.idLenteContacto;
                        option.text = item.idLenteContacto;
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
        contenidoC += '<td style="width: 20%;padding-top: 20px">' + nombre + ' ' + primerA + '</td>';
        contenidoC += '<td style="width: 20%;padding-top: 20px">' + telM + '</td>';
        contenidoC += '<td style="width: 20%; padding-top: 10.5px"> <select style="width: 70% !important;" class="formulario__input_ventas" type="number" id="txtExamenV' + i + '"></select></td>';
        contenidoC += '<td style="width: 20%; padding-top: 10.5px"> <select style="width: 70% !important; margin-left: 5px" class="formulario__input_ventas" type="number" id="txtLenteC' + i + '"></select></td>';
        contenidoC += '<td style="justify-content: center; "><button onclick="moduloVentaLenteContacto.selectCliente(' + i + ');" style="border-color: #233A54; border-radius: 3px; color: #233A54" class="formulario_select_btn"><i class="fa fa-search"></i></button></td>';
        contenidoC += '<td style="display: flex; justify-content: center; "><button onclick="moduloVentaLenteContacto.agregar(' + i + ');" style="border-color: #233A54; border-radius: 3px; color: #233A54" class="formulario_select_btn"><i class="fa fa-plus"></i></button></td>';
        contenidoC += "</tr>";
    }
    document.getElementById("tblClientes").innerHTML = contenidoC;
}

export function selectCliente(i) {
    showTableEV(clientes[i].idCliente, i);
    showTableLC(i);
}

export function agregar(i) {
    let contenidoEC = "";
    let selectEV = document.querySelector("#txtExamenV" + i);
    let selectLC = document.querySelector("#txtLenteC" + i);
    let indexEV = selectEV.selectedIndex;
    let indexLC = selectLC.selectedIndex;
    
    let reglon =
            contenidoEC += '<tr style="width: 100%;">';
    contenidoEC += '<td style="width: 15%;padding-top: 20px">' + examenVista[indexEV].cliente.idCliente + "</td>";
    contenidoEC += '<td style="width: 15%;padding-top: 20px">' + examenVista[indexEV].idExamenVista + "</td>";
    contenidoEC += '<td style="width: 15%;padding-top: 20px">' + examenVista[indexEV].graduacion.idGraduacion + "</td>";
    contenidoEC += '<td style="width: 15%;padding-top: 20px">' + lentesContacto[indexLC].idLenteContacto + "</td>";
    contenidoEC += '<td style="width: 15%;padding-top: 20px">' + examenVista[indexEV].clave + "</td>";
    contenidoEC += '<td style="width: 15%; padding-top: 10.5px"> <input style="width: 70% !important;" class="formulario__input_ventas" type="number" value="0" id="txtDescuento' + indexVS + '" onchange="moduloVentaLenteContacto.precioTotal();"></td>';
    contenidoEC += '<td style="display: flex; justify-content: center; "><button onclick="moduloVentaLenteContacto.eliminar(' + indexVS + ');" style="border-color: #233A54; border-radius: 3px; color: #233A54" class="formulario_select_btn"><i class="fa fa-trash"></i></button></td>';
    contenidoEC += '</tr>';

    document.getElementById("tblExamenV").innerHTML += contenidoEC;

    presupuesto = {'idExamenVista': examenVista[indexEV], 'clave': Math.random() * 10000000};
    presupuestoLenteContacto[indexVS] = {'examenVista': examenVista[indexEV], 'lenteContacto': lentesContacto[indexLC], 'presupuesto': presupuesto, 'clave': Math.random() * 10000000};
    ventaPresupuesto[indexVS] = {'cantidad': 1, 'precioUnitario': lentesContacto[indexLC].producto.precioVenta, 'descuento': 0, 'presupuestoLentesContacto': presupuestoLenteContacto[indexVS]};
    indexVS++;
    console.log(ventaPresupuesto);
    
    precioTotal();

}

export function generarPresupuestoLC() {

     for (var i = 0; i < ventaPresupuesto.length; i++) {
            ventaPresupuesto[i].descuento = document.getElementById("txtDescuento" + i).value;
        }
    let venta = {clave: Math.random() * 10000000, empleado: e};
    let dvp = {listaVentaPresupuesto: ventaPresupuesto, venta: venta};
    let datos = {datos: JSON.stringify(dvp)};
    let parametros = new URLSearchParams(datos);

    fetch("api/ventaLenteContacto/guardar", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    Swal.fire({
                        title: '¿Desea continuar con la Venta?',
                        showCancelButton: true,
                        showConfirmButton: true,
                        confirmButtonText: 'Aceptar',
                        cancelButtonText: 'Cancelar'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire('¡Venta Correcta!', '¡Venta realizada correctamente!', 'success');
                            limpiarTabla();
                        }
                    });
                }
            });
}

export function precioTotal() {

    let precioT;
    let posicioC;
    let posicionD;
    let cantidad;
    let descuento;
    let total = 0;
    let subtotal = 0;
    let descuentoAplicado = 0;
    let tabla = document.getElementById("tblExamenV");
    let rows = tabla.rows.length;

    if (rows === 0) {
        limpiarTabla();

    } else {
        for (let i = 0; i < ventaPresupuesto.length; i++) {
            precioT = ventaPresupuesto[i].precioUnitario;
            //posicioC = document.getElementById("txtCantidad" + i).value;
            posicionD = document.getElementById("txtDescuento" + i).value;
            //cantidad = posicioC;
            descuento = posicionD;

            subtotal = precioT;
            descuentoAplicado = subtotal * descuento / 100;
            total += subtotal - descuentoAplicado;
            
        }
        document.getElementById("txtTotalVenta").innerText = "$" + total.toLocaleString('en-US', {style: 'decimal', minimumFractionDigits: 2});
    }
}

export function limpiarTabla() {

    ventaPresupuesto = [];
    indexVS = 0;
    document.getElementById("tblExamenV").innerHTML = "";
    document.getElementById("txtTotalVenta").innerText = "$0.00";
}


export function eliminar(i) {

    let tabla = document.getElementById("tblExamenV");
    let rows = tabla.rows.length;

    if (rows === 0) {
        limpiarTabla();

    } else {
        ventaPresupuesto.splice(i, 1);
        indexVS--;
        tabla.deleteRow(i);
        precioTotal();
    }
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