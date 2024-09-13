window.addEventListener('load', function () {
    const formulario = document.querySelector('#crearTurnoForm');

    // Obtener los pacientes disponibles
    fetch('/pacientes', {
        method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            let pacienteSelect = document.querySelector('#paciente_id');
            data.forEach(paciente => {
                let option = document.createElement('option');
                option.value = paciente.id;
                option.textContent = `${paciente.nombre} ${paciente.apellido}`;
                pacienteSelect.appendChild(option);
            });
        });

    // Obtener los odontólogos disponibles
    fetch('/odontologos', { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            let odontologoSelect = document.querySelector('#odontologo_id');
            data.forEach(odontologo => {
                let option = document.createElement('option');
                option.value = odontologo.id;
                option.textContent = `${odontologo.nombre} ${odontologo.apellido}`;
                odontologoSelect.appendChild(option);
            });
        });

    // Manejar el envío del formulario para crear un turno
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const turno = {
            paciente: { id: document.querySelector('#paciente_id').value },
            odontologo: { id: document.querySelector('#odontologo_id').value },
            fecha: document.querySelector('#fecha').value
        };

        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(turno)
        };

        fetch('/turnos', settings)
            .then(response => response.json())
            .then(data => {
                document.querySelector('#response').innerHTML = `<div class="alert alert-success">Turno creado exitosamente!</div>`;
                document.querySelector('#fecha').value = '';
            })
            .catch(error => {
                document.querySelector('#response').innerHTML = `<div class="alert alert-danger">Error al crear turno: ${error}</div>`;
            });
    });
});