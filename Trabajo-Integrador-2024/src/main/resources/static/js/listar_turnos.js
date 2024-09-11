window.addEventListener('load', function () {
    (function() {
        // Con fetch invocamos a la API de turnos con el método GET
        // Nos devolverá un JSON con una colección de turnos
        const url = '/turnos';
        const settings = {
            method: 'GET'
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                const tableBody = document.getElementById("turnoTableBody");
                tableBody.innerHTML = ''; // Limpiar el contenido actual

                for (const turno of data) {
                    console.log(turno);
                    // Por cada turno armamos una fila de la tabla
                    const turnoRow = tableBody.insertRow();
                    const tr_id = 'tr_' + turno.id;
                    turnoRow.id = tr_id;

                    // Botón para eliminar turno
                    const deleteButton = `<button id="btn_delete_${turno.id}" type="button" onclick="eliminarTurno(${turno.id})" class="btn btn-danger btn_delete">&times</button>`;

                    // Botón para actualizar turno
                    const updateButton = `<button id="btn_id_${turno.id}" type="button" onclick="editarTurno(${turno.id})" class="btn btn-info btn_id">${turno.id}</button>`;

                    // Añadir las celdas a la fila
                    turnoRow.innerHTML = `<td>${updateButton}</td>
                        <td class="td_nombre_p">${turno.paciente.nombre} ${turno.paciente.apellido}</td>
                        <td class="td_nombre_o">${turno.odontologo.nombre} ${turno.odontologo.apellido}</td>
                        <td class="td_fecha_t">${turno.fecha}</td>
                        <td>${deleteButton}</td>`;
                }
            })
            .catch(error => {
                console.error('Error al cargar turnos:', error);
            });
    })();

    (function() {
        let pathname = window.location.pathname;
        if (pathname === "/turnoList.html") {
            document.querySelector(".nav .nav-item a:last-child").classList.add("active");
        }
    })();
});