let moduloEmpleados;
let moduloClientes;
let moduloExamen;
let moduloPresupuesto;

let moduloAccesorios;
let moduloArmazones;
let moduloSoluciones;
let moduloLenteContacto;
let moduloMateriales;
let moduloMicas;
let moduloTratamientos;

let moduloVentas;
let moduloVentaLente;
let moduloVentaLenteContacto;

function cargarSubModuloMateriales(){
fetch("modules/moduloMateriales/view_Materiales.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloMateriales/controller_Materiales.js").then(
                        function(controller){
                        moduloMateriales = controller;
                        }
                );
                }
        );
}

function cargarSubModuloTratamientos(){
fetch("modules/moduloTratamientos/view_Tratamientos.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloTratamientos/controller_Tratamientos.js").then(
                        function(controller){
                        moduloTratamientos = controller;
                        }
                );
                }
        );
}

function cargarSubModuloMicas(){
fetch("modules/moduloMicas/view_Micas.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloMicas/controller_Micas.js").then(
                        function(controller){
                        moduloMicas = controller;
                        }
                );
                }
        );
}

function cargarSubModuloAccesorios(){
fetch("modules/moduloAccesorios/view_Accesorios.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloAccesorios/controller_Accesorios.js").then(
                        function(controller){
                        moduloAccesorios = controller;
                        }
                );
                }
        );
}

function cargarSubModuloSoluciones(){
fetch("modules/moduloSoluciones/view_Soluciones.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloSoluciones/controller_Soluciones.js").then(
                        function(controller){
                        moduloSoluciones = controller;
                        }
                );
                }
        );
}

function cargarSubModuloArmazones(){
fetch("modules/moduloArmazon/view_Armazones.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloArmazon/controller_Armazones.js").then(
                        function(controller){
                        moduloArmazones = controller;
                        }
                );
                }
        );
}

function cargarSubModuloLentesContacto(){
fetch("modules/moduloLenteContacto/view_LentesContacto.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloLenteContacto/controller_LentesContacto.js").then(
                        function(controller){
                        moduloLenteContacto = controller;
                        }
                );
                }
        );
}

function cargarSubModuloVentasLentesContacto(){
fetch("modules/moduloVentasLenteContacto/view_VentaLenteContacto.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloVentasLenteContacto/controller_VentaLentesContacto.js").then(
                        function(controller){
                        moduloVentaLenteContacto = controller;
                        }
                );
                }
        );
}

function cargarSubModuloVentasLentes(){
fetch("modules/moduloVentasLente/view_VentaLente.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloVentasLente/controller_VentaLentes.js").then(
                        function(controller){
                        moduloVentaLente = controller;
                        }
                );
                }
        );
}

function cargarModuloEmpleados(){
fetch("modules/moduloEmpleados/view_Empleados.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloEmpleados/controller_Empleados.js").then(
                        function(controller){
                        moduloEmpleados = controller;
                        }
                );
                }
        );
}

function cargarModuloClientes(){
fetch("modules/moduloClientes/view_Clientes.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloClientes/controller_Clientes.js").then(
                        function(controller){
                        moduloClientes = controller;
                        }
                );
                }
        );
}

function cargarModuloVentas(){
fetch("modules/moduloVentas/view_Ventas.html")
        .then(
                function(response){
                return response.text();
                }
        )
        .then(
                function(html){
                document.getElementById("contenedorPrincipal").innerHTML = html;
                import ("../modules/moduloVentas/controller_Ventas.js").then(
                        function(controller){
                        moduloVentas = controller;
                        }
                );
                }
        );
};