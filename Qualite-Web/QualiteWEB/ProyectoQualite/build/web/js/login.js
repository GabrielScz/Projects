/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

async function encriptar(texto) {
    const encoder = new TextEncoder();
    const data = encoder.encode(texto);
    const hash = await crypto.subtle.digest('SHA-256', data);
    const hashArray = Array.from(new Uint8Array(hash));
    const hashHex = hashArray.map((b) => b.toString(16).padStart(2, '0')).join('');
    return hashHex;
}

function iniciarSesion() {
    let usuario = document.getElementById("txtUser").value;
    let contrasenia = document.getElementById("txtPassword").value;

    encriptar(contrasenia).then((textoEncriptado) => {

        let datos = JSON.stringify({
            nombreUsuario: usuario, contrasenia: contrasenia
        });

        let parametros = new URLSearchParams({datos: datos});
        fetch('api/log/in',
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
                        Swal.fire('Error', 'Error interno del servidor!', 'error');
                    }
                    if (data.error != null) {
                        Swal.fire('', data.error, 'warning');
                        return;
                    } else {
                        localStorage.setItem('currentUser', JSON.stringify(data));
                        window.location = "menuPrincipal.html";
                        Swal.fire('¡Acceso Correcto!', "Bienvenido " + data.usuario.nombreUsuario, 'success');
                    }
                });
    });
}

function cerrarSesion() {

    let e = localStorage.getItem('currentUser');
    let empleado = {"empleado": e};

    let parametros = new URLSearchParams(empleado);

    fetch('api/log/out',
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
                    Swal.fire('Error', 'Error interno del servidor!', 'error');
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                } else {
                    Swal.fire('¡Sesión Cerrada!', "Sesión cerrada de manera correcta", 'success');
                    localStorage.removeItem('currentUser');
                    //window.location = "index.html";
                    

                }
            });
}




