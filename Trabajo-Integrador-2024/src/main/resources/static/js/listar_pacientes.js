window.addEventListener('load', function () {
    (function() {
        // Con fetch invocamos a la API de pacientes con el método GET
        // Nos devolverá un JSON con una colección de pacientes
        const url = '/pacientes';
        const settings = {
            method: 'GET'
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                const tableBody = document.getElementById("pacienteTableBody");
                tableBody.innerHTML = ''; // Limpiar el contenido actual

                for (const paciente of data) {
                    console.log(paciente);
                    // Por cada paciente armamos una fila de la tabla
                    const pacienteRow = tableBody.insertRow();
                    const tr_id = 'tr_' + paciente.id;
                    pacienteRow.id = tr_id;

                    // Botón para eliminar paciente
                    const deleteButton = `<button id="btn_delete_${paciente.id}" type="button" onclick="deleteBy(${paciente.id})" class="btn btn-danger btn_delete">&times</button>`;

                    // Botón para actualizar paciente
                    const updateButton = `<button id="btn_id_${paciente.id}" type="button" onclick="findBy(${paciente.id})" class="btn btn-info btn_id">${paciente.id}</button>`;

                    // Añadir las celdas a la fila
                    pacienteRow.innerHTML = `<td>${updateButton}</td>
                        <td class="td_nombre">${paciente.nombre.toUpperCase()}</td>
                        <td class="td_apellido">${paciente.apellido.toUpperCase()}</td>
                        <td class="td_dni">${paciente.dni}</td>
                        <td class="td_fechaAlta">${paciente.fechaAlta}</td>
                        <td>${deleteButton}</td>`;
                }
            })
            .catch(error => {
                console.error('Error al cargar pacientes:', error);
            });
    })();

    (function() {
        let pathname = window.location.pathname;
        if (pathname === "/pacientesList.html") {
            document.querySelector(".nav .nav-item a:last-child").classList.add("active");
        }
    })();
});
