window.addEventListener('load', function () {

    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado de la pelicula
    const formulario = document.querySelector('#update_odontologo_form');
    const cancelarButton = document.querySelector('#cancelar_actualizacion');
    formulario.addEventListener('submit', function (event) {
        //event.preventDefault();  // Evita que el formulario se envíe por defecto
        let odontologoId = document.querySelector('#odontologo_id').value;

        //creamos un JSON que tendrá los datos de la película
        //a diferencia de una pelicula nueva en este caso enviamos el id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
            id: document.querySelector('#odontologo_id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            matricula: document.querySelector('#matricula').value,

        };

        //invocamos utilizando la función fetch la API peliculas con el método PUT que modificará
        //la película que enviaremos en formato JSON
        const url = '/odontologos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
        fetch(url,settings)
            .then(response => response.json())
            .then(data => {
                console.log('Odontólogo actualizado', data);
                // Opcional: puedes añadir una notificación o feedback al usuario
                document.querySelector('#response').style.display = "block";
                document.querySelector('#response').innerHTML = "Odontólogo actualizado correctamente";
            })
            .catch(error => {
                console.error('Error al actualizar el odontólogo:', error);
        });
    });
    // Evento para el botón de cancelar
    cancelarButton.addEventListener('click', function () {
    // Limpiamos los campos del formulario
        document.querySelector('#odontologo_id').value = '';
        document.querySelector('#nombre').value = '';
        document.querySelector('#apellido').value = '';
        document.querySelector('#matricula').value = '';
        // Oculta el formulario de actualización
        document.querySelector('#div_odontologo_updating').style.display = "none";

        });
 })

    //Es la funcion que se invoca cuando se hace click sobre el id de una pelicula del listado
    //se encarga de llenar el formulario con los datos de la pelicula
    //que se desea modificar
    function findBy(id) {
          const url = '/odontologos'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let odontologo = data;
              document.querySelector('#odontologo_id').value = odontologo.id;
              document.querySelector('#nombre').value = odontologo.nombre;
              document.querySelector('#apellido').value = odontologo.apellido;
              document.querySelector('#matricula').value = odontologo.matricula;
              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_odontologo_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }