![portada.jpg](Untitled%2066b4ef4ab64b473eae15a7d01af91813/portada.jpg)

# Mi primera aplicación

El proyecto consiste en desarrollar una aplicación móvil de gestión de tareas que permita a los usuarios crear una cuenta, iniciar sesión, listar tareas existentes y agregar nuevas tareas. La aplicación tiene como objetivo ayudar a los usuarios a organizar sus actividades diarias de manera eficiente.

## Objetivos

1. **Desarrollar una interfaz intuitiva:** Crear una interfaz de usuario fácil de usar y atractiva que permita a los usuarios navegar de manera eficiente por las diferentes funcionalidades de la aplicación.
2. **Implementar funciones de autenticación:** Permitir que los usuarios creen una cuenta con información segura y autenticación para garantizar la privacidad y la seguridad de sus datos.
3. **Gestión de tareas eficiente:** Desarrollar funcionalidades que permitan a los usuarios listar tareas existentes, agregar nuevas tareas y marcar tareas como completadas.
4. **Integrar notificaciones:** Implementar un sistema de notificaciones para recordar a los usuarios sobre las tareas pendientes, mejorando así la gestión del tiempo.
5. **Añadir imágenes:** Permitir a los usuarios personalizar sus tareas mediante la adición de imágenes relevantes, mejorando la experiencia visual y la identificación de tareas.
6. **Opciones de filtrado:** Permitir al usurio filtrar la vista de las tareas dependiendo de las hechas o no hechas o su tipo de prioridad.

## Requisitos

**Elementos Obligatorios:**

- **View (TextView, EditText, Button, ...):** Implementar componentes de vista esenciales para la interacción del usuario, como TextView para mostrar información, EditText para la entrada de datos y Button para acciones.
- **Formato en las Vistas (color, tamaño, tipo, ...):** Personalizar el formato de las vistas para mejorar la estética de la aplicación, incluyendo colores atractivos, tamaños de texto adecuados y un estilo coherente.
- **Layouts Mínimo:** Desarrollar al menos tres layouts diferentes para organizar y presentar la información de manera efectiva en diferentes secciones de la aplicación.
- **Activities Mínimo:** Crear al menos dos actividades distintas para gestionar las funciones clave de la aplicación, como la actividad de inicio de sesión y la actividad principal de la lista de tareas.
- **Añadir Imágenes:** Integrar la capacidad de agregar imágenes relevantes a las tareas para mejorar la experiencia visual del usuario.
- **Notificaciones:** Implementar un sistema de notificaciones para recordar a los usuarios sobre tareas pendientes o eventos importantes.
- **BBDD (Base de Datos):** Utilizar una base de datos para almacenar y gestionar la información del usuario, como detalles de inicio de sesión y tareas creadas.
- **Multimedia (Audio y/o Video):** Integrar la capacidad de añadir archivos de audio y/o video a las tareas, si es relevante para la gestión de tareas.
- **Memoria:** Preparar una memoria detallada que aborde aspectos clave del proyecto.

## Diseño de la páginas principales

![Añadir un poco de texto (1).png](Untitled%2066b4ef4ab64b473eae15a7d01af91813/Aadir_un_poco_de_texto_(1).png)

### Gama Cromática

![![Paleta_Colores.PNG](httpsprod-files-secure.s3.us-west-2.amazonaws.com182a567c-500f-4112-9f26-b41c809dd61aa6684268-7195-42bd-bfe3-742cb7aaa745Paleta_Colores.png).png](Untitled%2066b4ef4ab64b473eae15a7d01af91813/!Paleta_Colores.PNG(httpsprod-files-secure.s3.us-west-2.amazonaws.com182a567c-500f-4112-9f26-b41c809dd61aa6684268-7195-42bd-bfe3-742cb7aaa745Paleta_Colores.png).png)

## Organización de las clases

El paquete de las clases esta dividido en 2 la primera es la correspondiente a los datos que se manejan, como Usuario, Tarea y el Administrador de la base de datos (AdminSQL) y una segunda carpeta denominada UI que es toda la relativa a la usabilidad de la interfaz, como la funcionalidad del video animado con el logo, lista de tareas, diálogos que interaccionan con el usuario y clases necesarias para eventos concretos, como el pulsar un checkbox o deslizar una tarea, entro otras funcionalidades.

![diagramaCompleto.jpg](Untitled%2066b4ef4ab64b473eae15a7d01af91813/diagramaCompleto.jpg)

## Layout utilizados

1. **Login:** Página inicial. el usuario entra con su nombre y contraseña.
2. **Registro:** Si no se encuentra en la base de datos aparece un dialogo para registrarse, con la posibilidad de salir por si se ha equivocado.
3. **Listar tareas:** Con un switch el usuario selecciona la vista de la tareas, no hechas (por defecto) o hechas. Si se pulsa el check box la tarea seleccionada, pasa a las no hechas y automáticamente desaparece de la vista y viceversa. Para eliminar la tarea el usuario debe arrastrarla a la izquierda.
4. **Crear tarea:** Si se pulsa el botón inferior con un más circular, se abre un dialogo posicionado en la parte inferior para registrar la tarea nueva.
5. **Filtrar:** Si se pulsa el botón filtrar aparece otro dialogo pudiendo filtrar la vista por tipo de prioridad y borrar.

![estructuraLayout.png](Untitled%2066b4ef4ab64b473eae15a7d01af91813/estructuraLayout.png)

## Sistema de prioridad de tareas

**Matriz de Covey**

![1705959215694.png](Untitled%2066b4ef4ab64b473eae15a7d01af91813/1705959215694.png)

Según la Matriz de Covey, la clave para no bloquearse a la hora de hacer las tareas pendientes, y terminar abrumado sin saber por dónde empezar, es saber priorizar y clasificar las tareas en base a su urgencia e importancia. Con el objetivo de poder ir al día y  descansar y disfrutar del tiempo libre.

El sistema que he escogido para mi aplicación es una especie de interpretación de esta matriz con el objetivo de facilitar a los usuarios la organización de sus tareas pendientes por orden de prioridades.

|  | Urgente  | No urgente |
| --- | --- | --- |
| Inmediato | Crucial | Inmediato |
| No inmediato | Estratégico | Ocasional |

# Video de prueba

[video_front_1.mp4](Untitled%2066b4ef4ab64b473eae15a7d01af91813/video_front_1.mp4)
