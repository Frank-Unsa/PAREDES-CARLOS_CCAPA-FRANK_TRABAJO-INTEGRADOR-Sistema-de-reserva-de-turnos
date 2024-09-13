window.addEventListener('load', function () {
    cargarPacientes();
    cargarOdontologos();
    const formulario = document.getElementById('update_turno_form');
    const cancelarButton = document.getElementById('cancelar_actualizacion');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const turnoId = document.getElementById('turno_id').value;
        const pacienteId = document.getElementById('pacienteSelectUpdate').value;
        const odontologoId = document.getElementById('odontologoSelectUpdate').value;
        const fecha = document.getElementById('fechaUpdate').value;

        const turno = {
            id: turnoId,
            paciente: { id: pacienteId },
            odontologo: { id: odontologoId },
            fecha: fecha
        };

        const url = '/turnos'; // Utiliza '/turnos' para actualizar
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(turno)
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                let turnoRow = document.getElementById('tr_' + turnoId);
                turnoRow.querySelector('.td_nombre_p').innerText = data.paciente.nombre + " " + data.paciente.apellido;
                turnoRow.querySelector('.td_nombre_o').innerText = data.odontologo.nombre + " " + data.odontologo.apellido;
                turnoRow.querySelector('.td_fecha_t').innerText = data.fecha;


                //document.getElementById('response').innerHTML = 'Turno actualizado con éxito. ID: ' + data.id;
                document.getElementById('div_turno_updating').style.display = 'none';
                document.querySelector('#fechaUpdate').value = '';
            })
            .catch(error => {
                document.getElementById('response').innerHTML = 'Error al actualizar turno: ' + error;
            });
    });

    function cargarPacientes() {
        fetch('/pacientes')
            .then(response => response.json())
            .then(data => {
                const pacienteSelectUpdate = document.getElementById('pacienteSelectUpdate');
                data.forEach(paciente => {
                    let option = document.createElement('option');
                    option.value = paciente.id;
                    option.textContent = paciente.nombre + ' ' + paciente.apellido;
                    pacienteSelectUpdate.appendChild(option);
                });
            })
            .catch(error => console.error('Error al cargar pacientes:', error));
    }

    function cargarOdontologos() {
        fetch('/odontologos')
            .then(response => response.json())
            .then(data => {
                const odontologoSelectUpdate = document.getElementById('odontologoSelectUpdate');
                data.forEach(odontologo => {
                    let option = document.createElement('option');
                    option.value = odontologo.id;
                    option.textContent = odontologo.nombre + ' ' + odontologo.apellido;
                    odontologoSelectUpdate.appendChild(option);
                });
            })
            .catch(error => console.error('Error al cargar odontólogos:', error));
    }

    window.editarTurno = function(id) {
        const url = `/turnos/${id}`;
        const settings = {
            method: 'GET'
        };
        fetch(url, settings)
            .then(response => response.json())
            .then(turno => {
                document.getElementById('turno_id').value = turno.id;
                document.getElementById('pacienteSelectUpdate').value = turno.paciente.id;
                document.getElementById('odontologoSelectUpdate').value = turno.odontologo.id;
                document.getElementById('fechaUpdate').value = turno.fecha;
                document.getElementById('div_turno_updating').style.display = 'block';
            })
            .catch(error => console.error('Error al cargar turno:', error));
    };

    cancelarButton.addEventListener('click', function () {
        // Limpiamos los campos del formulario
            document.querySelector('#turno_id').value = '';
            document.querySelector('#pacienteSelectUpdate').value = '';
            document.querySelector('#odontologoSelectUpdate').value = '';
            document.querySelector('#fechaUpdate').value = '';
            // Oculta el formulario de actualización
            document.querySelector('#div_turno_updating').style.display = "none";
            });

});