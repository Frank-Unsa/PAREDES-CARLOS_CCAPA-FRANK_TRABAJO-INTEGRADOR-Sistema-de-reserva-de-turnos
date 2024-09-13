window.addEventListener('load', function () {
    // Buscamos y obtenemos el formulario donde están los datos que el usuario puede modificar del paciente
    const formulario = document.querySelector('#update_paciente_form');
    const cancelarButton = document.querySelector('#cancelar_actualizacion');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();  // Evita que el formulario se envíe por defecto
        let pacienteId = document.querySelector('#paciente_id').value;

        // Creamos un JSON que tendrá los datos del paciente

        // Primero creamos  un objeto para el domicilio
        const domicilioData = {
            id: document.querySelector('#domicilio_id').value,  // el id del domicilio
            calle: document.querySelector('#calle').value,
            numero: document.querySelector('#numero').value,
            localidad: document.querySelector('#localidad').value,
            provincia: document.querySelector('#provincia').value
        };
        // Creamos un objeto para el paciente e incluimos el domicilio
        const formData = {
            id: pacienteId,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            dni: document.querySelector('#dni').value,
            fechaAlta: document.querySelector('#fechaAlta').value,
            domicilio: domicilioData
        };

        // Invocamos utilizando la función fetch la API pacientes con el método PUT
        const url = '/pacientes';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                // Actualizamos la fila en la tabla sin recargar la página
                let pacienteRow = document.getElementById('tr_' + pacienteId);
                pacienteRow.querySelector('.td_nombre').innerText = data.nombre.toUpperCase();
                pacienteRow.querySelector('.td_apellido').innerText = data.apellido.toUpperCase();
                pacienteRow.querySelector('.td_dni').innerText = data.dni;
                pacienteRow.querySelector('.td_fechaAlta').innerText = data.fechaAlta;

                // Ocultar el formulario de actualización
                document.querySelector('#div_paciente_updating').style.display = "none";
            })
            .catch(error => {
                console.error('Error al actualizar el paciente:', error);
                //document.querySelector('#response').innerHTML = "Error al actualizar el paciente";

            });
    });

    // Evento para el botón de cancelar
    cancelarButton.addEventListener('click', function () {
        document.querySelector('#paciente_id').value = '';
        document.querySelector('#nombre').value = '';
        document.querySelector('#apellido').value = '';
        document.querySelector('#dni').value = '';
        document.querySelector('#fechaAlta').value = '';
        document.querySelector('#domicilio_id').value = '';
        document.querySelector('#calle').value = '';
        document.querySelector('#numero').value = '';
        document.querySelector('#localidad').value = '';
        document.querySelector('#provincia').value = '';
        document.querySelector('#div_paciente_updating').style.display = "none";

    });
});

// Es la función que se invoca cuando se hace clic sobre el id de un paciente del listado
// Se encarga de llenar el formulario con los datos del paciente que se desea modificar
function findBy(id) {
    const url = '/pacientes/' + id;
    const settings = {
        method: 'GET'
    }
    fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            let paciente = data;
            document.querySelector('#paciente_id').value = paciente.id;
            document.querySelector('#nombre').value = paciente.nombre;
            document.querySelector('#apellido').value = paciente.apellido;
            document.querySelector('#dni').value = paciente.dni;
            document.querySelector('#fechaAlta').value = paciente.fechaAlta;

            // Cargamos los datos del domicilio
            document.querySelector('#domicilio_id').value = paciente.domicilio.id;
            document.querySelector('#calle').value = paciente.domicilio.calle;
            document.querySelector('#numero').value = paciente.domicilio.numero;
            document.querySelector('#localidad').value = paciente.domicilio.localidad;
            document.querySelector('#provincia').value = paciente.domicilio.provincia;

            // Hacemos visible el formulario para la edición
            document.querySelector('#div_paciente_updating').style.display = "block";
        })
        .catch(error => {
            alert("Error: " + error);
        });
}
