let productos;
let indexVS = 0;
let ventaProducto = [];
let currentUser = localStorage.getItem('currentUser');
let e = JSON.parse(currentUser);
let token = e.usuario.lastToken;
showTable();

export function showTable() {

    let datos = {estatus: 1, token: token};
    let parametros = new URLSearchParams(datos);

    fetch("api/venta/getAll", {
        method: 'POST',
        body: parametros,
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaP(data);
                }
            });
}

export function cargarTablaP(data) {

    productos = data;
    let contenidoP = "";
    for (var i = 0; i < productos.length; i++) {
        let codigoBarras = productos[i].codigoBarras;
        let nombre = productos[i].nombre;
        let marca = productos[i].marca;
        let precioV = productos[i].precioVenta;
        let existencias = productos[i].existencias;


        contenidoP += "<tr>";
        contenidoP += "<td style='padding-top: 20px; width: 20%'>" + codigoBarras + "</td>";
        contenidoP += "<td style='padding-top: 20px; width: 20%'>" + nombre + "</td>";
        contenidoP += "<td style='padding-top: 20px; width: 20%'>" + marca + "</td>";
        contenidoP += "<td style='padding-top: 20px; width: 20%'>" + existencias + "</td>";
        contenidoP += "<td style='padding-top: 20px; width: 20%'>" + "$" + precioV + "</td>";
        contenidoP += '<td style="display: flex; justify-content: end;"><button onclick="moduloVentas.agregar(' + i + ');" style="border-color: #233A54; border-radius: 3px; color: #233A54" class="formulario_select_btn"><i class="fa fa-cart-plus"></i></button></td>';
        contenidoP += "</tr>";
    }
    document.getElementById("tblProductos").innerHTML = contenidoP;
}

export function agregar(i) {

    if (productoEnCarrito()) {
        Swal.fire('¡Atención!', '¡El producto ya se encuentra en el carrito!', 'warning');
    } else {

        let contenidoP = "";
        let reglon =
                
        contenidoP += '<tr style="width: 100%;">';
        contenidoP += '<td style="width: 20%;padding-top: 20px">' + productos[i].codigoBarras + "</td>";
        contenidoP += '<td style="width: 20%;padding-top: 20px">' + productos[i].nombre + "</td>";
        contenidoP += '<td style="width: 20%;padding-top: 20px">' + "$" + productos[i].precioVenta + "</td>";
        contenidoP += '<td style="width: 20%; padding-top: 10.5px"> <input style="width: 70% !important;" class="formulario__input_ventas" type="number" value="1" id="txtCantidad' + indexVS + '" onchange="moduloVentas.precioTotal();"></td>';
        contenidoP += '<td style="width: 20%; padding-top: 10.5px"> <input style="width: 70% !important;" class="formulario__input_ventas" type="number" value="0" id="txtDescuento' + indexVS + '" onchange="moduloVentas.precioTotal();"></td>';
        contenidoP += '<td style="display: flex; justify-content: center; "><button onclick="moduloVentas.eliminar(' + indexVS + ');" style="border-color: #233A54; border-radius: 3px; color: #233A54" class="formulario_select_btn"><i class="fa fa-trash"></i></button></td>';
        contenidoP += '</tr>';

        document.getElementById("tblProductosVenta").innerHTML += contenidoP;

        ventaProducto[indexVS] = {'producto': productos[i], 'cantidad': 1,
            'precioUnitario': productos[i].precioVenta, 'descuento': 0};
        indexVS++;

        precioTotal();
    }

    function productoEnCarrito() {
        let idProductoI = productos[i].idProducto;
        for (let i = 0; i < ventaProducto.length; i++) {
            let idProductoC = ventaProducto[i].producto.idProducto;
            if (idProductoC === idProductoI) {
                return true;
            }
        }
        return false;
    }

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
    let tabla = document.getElementById("tblProductosVenta");
    let rows = tabla.rows.length;

    if (rows === 0) {
        limpiarTabla();

    } else {
        
        for (let i = 0; i < ventaProducto.length; i++) {
            precioT = ventaProducto[i].producto.precioVenta;
            posicioC = document.getElementById("txtCantidad" + i).value;
            posicionD = document.getElementById("txtDescuento" + i).value;
            cantidad = posicioC;
            descuento = posicionD;

            subtotal = precioT * cantidad;
            descuentoAplicado = subtotal * descuento / 100;
            total += subtotal - descuentoAplicado;
            
        }
        document.getElementById("txtTotalVenta").innerText = "$" + total.toLocaleString('en-US', {style: 'decimal', minimumFractionDigits: 2});
    }
}

export function eliminar(i) {

    let tabla = document.getElementById("tblProductosVenta");
    let rows = tabla.rows.length;

    if (rows === 0) {
        limpiarTabla();

    } else {
        ventaProducto.splice(i, 1);
        indexVS--;
        tabla.deleteRow(i);
        precioTotal();
    }
}

export function validarExistencias() {

    let cantidadSeleccionada;
    let existencias;
    for (let i = 0; i < ventaProducto.length; i++) {
        cantidadSeleccionada = document.getElementById("txtCantidad" + i).value;
        existencias = ventaProducto[i].producto.existencias;
        if (cantidadSeleccionada > existencias) {
            Swal.fire('¡Advertencia!', '¡El producto: ' + ventaProducto[i].producto.nombre + ' no cuenta con suficiente stock!', 'warning');
            return false;
        }
    }
    return true;
}

export function limpiarTabla() {

    ventaProducto = [];
    indexVS = 0;
    document.getElementById("tblProductosVenta").innerHTML = "";
    document.getElementById("txtTotalVenta").innerText = "$0.00";
}

export function generarCompra() {

    if (validarExistencias()) {
        for (var i = 0; i < ventaProducto.length; i++) {
            ventaProducto[i].cantidad = document.getElementById("txtCantidad" + i).value;
            ventaProducto[i].descuento = document.getElementById("txtDescuento" + i).value;
        }
        let venta = {clave: Math.random() * 10000000, empleado: e};
        let dvp = {venta: venta, listaVentaProducto: ventaProducto};
        let datos = {datos: JSON.stringify(dvp)};
        let parametros = new URLSearchParams(datos);

        fetch("api/venta/guardar", {
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
                                showTable();
                            }
                        });
                    }

                });
    }
}

export function buscarProducto() {

    let busqueda = document.getElementById("txtSearch").value;
    let parametros = {"buscar": busqueda, token: token};

    fetch('api/venta/buscar?busqueda=' + busqueda)
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert(data.error);
                } else {
                    cargarTablaP(data);
                }
            });
}