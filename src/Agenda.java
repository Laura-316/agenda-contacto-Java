import java.util.*;

public class Agenda {

    private Map<String, Contacto> contactos =new TreeMap<>();

    private Set<String> grupos =new TreeSet<>();

    private Map<String, Set<String>> contactosPorGrupo = new TreeMap<>();

    private List<Contacto> historial = new ArrayList<>();

    public void agregarContacto(Contacto c) {
        if (contactos.containsKey(c.getNombre())) {
            System.out.println("Error: Ya existe un contacto con el nombre '" + c.getNombre() + "'");
            return;
        }
        contactos.put(c.getNombre(), c);
        historial.add(c);
        System.out.println("Contacto agregado: " + c.getNombre());
    }

    public Contacto buscarContacto(String nombre) {
        return contactos.get(nombre);
    }

    public boolean eliminarContacto(String nombre) {
        Contacto c = contactos.remove(nombre);
        if (c == null) {
            return false;
        }
        historial.remove(c);

        for (Set<String> miembros : contactosPorGrupo.values()) {
            miembros.remove(nombre);
        }
        return true;
    }

    // Muestra los contactos en orden alfabetico (el TreeMap ya los tiene ordenados)
    public void listarContactos() {
        System.out.println("--- LISTA DE CONTACTOS (orden alfabetico) ---");
        for (Contacto c : contactos.values()) {
            System.out.println(c);
        }
    }

    // Muestra los contactos en el orden en que se agregaron (recorre el ArrayList)
    public void listarHistorial() {
        System.out.println("--- HISTORIAL (orden de insercion) ---");
        for (Contacto c : historial) {
            System.out.println(c);
        }
    }
    // Agrega un grupo nuevo; si ya existe, muestra error
    public void agregarGrupo(String nombreGrupo) {
        if (grupos.contains(nombreGrupo)) {
            System.out.println("Error: El grupo '" + nombreGrupo + "' ya existe");
            return;
        }
        grupos.add(nombreGrupo);
        contactosPorGrupo.put(nombreGrupo, new TreeSet<>()); // el grupo empieza vacio
        System.out.println("Grupo '" + nombreGrupo + "' creado");
    }

    // Agrega el nombre de un contacto a un grupo; valida que ambos existan
    public void agregarContactoAGrupo(String nombreContacto, String nombreGrupo) {
        if (!contactos.containsKey(nombreContacto)) {
            System.out.println("Error: El contacto '" + nombreContacto + "' no existe");
            return;
        }
        if (!grupos.contains(nombreGrupo)) {
            System.out.println("Error: El grupo '" + nombreGrupo + "' no existe");
            return;
        }
        contactosPorGrupo.get(nombreGrupo).add(nombreContacto);
        System.out.println("Contacto agregado al grupo '" + nombreGrupo + "'");
    }

    // Muestra los grupos en orden alfabetico (el TreeSet ya los ordena)
    public void listarGrupos() {
        System.out.println("--- GRUPOS ---");
        for (String g : grupos) {
            System.out.println("- " + g);
        }
    }

    // Muestra los contactos de un grupo
    public void mostrarContactosPorGrupo(String nombreGrupo) {
        if (!grupos.contains(nombreGrupo)) {
            System.out.println("Error: El grupo '" + nombreGrupo + "' no existe");
            return;
        }
        Set<String> miembros = contactosPorGrupo.get(nombreGrupo);
        if (miembros.isEmpty()) {
            System.out.println("El grupo '" + nombreGrupo + "' no tiene contactos");
            return;
        }
        System.out.println("Contactos en '" + nombreGrupo + "':");
        for (String nombre : miembros) {
            System.out.println("- " + contactos.get(nombre)); // buscamos el contacto completo por su nombre
        }
    }
}
