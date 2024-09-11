window.addEventListener('load', function () {
    // Cargar pacientes y odontólogos al cargar la página
    cargarPacientes();
    cargarOdontologos();

    document.getElementById('registrarTurnoForm').addEventListener('submit', function (event) {
        event.preventDefault();

        // Obtener valores del formulario
        const pacienteId = document.getElementById('pacienteSelect').value;
        const odontologoId = document.getElementById('odontologoSelect').value;
        const fecha = document.getElementById('fecha').value;

        // Crear objeto turno
        const turno = {
            paciente: { id: pacienteId },
            odontologo: { id: odontologoId },
            fecha: fecha
        };
        console.log(turno);
        // Enviar datos al servidor
        fetch('/turnos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(turno)
        })
        .then(response => response.json())
        .then(data => {
            document.getElementById('response').innerHTML = 'Turno creado con éxito. ID: ' + data.id;
        })
        .catch(error => {
            document.getElementById('response').innerHTML = 'Error al crear turno: ' + error;
        });
    });

    function cargarPacientes() {
        fetch('/pacientes')
            .then(response => response.json())
            .then(data => {
                const pacienteSelect = document.getElementById('pacienteSelect');
                data.forEach(paciente => {
                    let option = document.createElement('option');
                    option.value = paciente.id;
                    option.textContent = paciente.nombre + ' ' + paciente.apellido;
                    pacienteSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error al cargar pacientes:', error));
    }

    function cargarOdontologos() {
        fetch('/odontologos')
            .then(response => response.json())
            .then(data => {
                const odontologoSelect = document.getElementById('odontologoSelect');
                data.forEach(odontologo => {
                    let option = document.createElement('option');
                    option.value = odontologo.id;
                    option.textContent = odontologo.nombre + ' ' + odontologo.apellido;
                    odontologoSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error al cargar odontólogos:', error));
    }
});