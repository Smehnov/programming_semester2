package special;

import java.util.HashMap;
import java.util.Map;

public class Dict {
    static String currentLang = "ru";
    public static HashMap<String, Word> dict = new HashMap<String, Word>();

    static {
        addWord("Add", new Word("Добавить", "Додати", "dodaj", "añadir"));
        addWord("Add if max", new Word("Добавить если макс","Додати, якщо макс","Dodaj, če maks","Agregar si max"));
        addWord("Add if min", new Word("Добавить если мин","Додати, якщо мин","Dodaj, če min","Agregar si min"));
        addWord("Clear", new Word("Очистить","Очистити","Jasno","Claro"));
        addWord("Help", new Word("Помощь","Допомога","Pomoč","Ayuda"));
        addWord("Info", new Word("Информация","інформація","Informacije","Información"));
        addWord("Sum participants", new Word("Сумма участников","сума учасників","Število udeležencev","Cantidad de participantes"));
        addWord("Remove greater", new Word("Удалить большие","видалити великі","Odstranite velike","Eliminar grande"));
        addWord("Name", new Word("Имя","ім'я","Ime","Nombre"));
        addWord("Number of participants", new Word("Число участников","число учасників","Število udeležencev","Número de participantes"));
        addWord("Music genre", new Word("Музыкальный жанр","Музичний жанр","Glasbeni žanr","Genero musical"));
        addWord("Name of the best album", new Word("Название лучшего альбома","Назва кращого альбому","Ime najboljšega albuma","El nombre del mejor álbum"));
        addWord("Length", new Word("Длина","Довжина","Dolžina","Longitud"));
        addWord("Enter", new Word("Ввод","Введення","Vnesite","Entrar"));
        addWord("New element was added!", new Word("Новый элемент добавлен!", "Новий елемент доданий!", "Nov izdelek je dodan!", "Nov izdelek je dodan!"));
        addWord("Collection was cleared", new Word("Коллекция очищена", "Колекція очищена", "Zbirka je očiščena", "Colección borrada"));
        addWord("Band was removed", new Word("Группа удалена", "Група видалена", "Skupina je izbrisana", "Grupo eliminado"));
        addWord("Band info was updated", new Word("Информация о группе была обновлена","Інформація про групу була оновлена","Podatki o skupini so bili posodobljeni.","La información del grupo ha sido actualizada."));
        addWord("New element isn't less then min element", new Word("Новый элемент не меньше текущих", "Новий елемент не менше поточних", "Nov izdelek ne manj kot trenutni", "Nuevo elemento no menos que el actual"));
        addWord("New element isn't greater than max element", new Word("Новый элемент не  больше текущих", "Новий елемент не більше поточних", "Nov element ne več kot trenutni", "Nuevo elemento no más que el actual"));
        addWord("Collection is empty, use >add command instead", new Word("Коллекция пустая, используйте команду добавить","Колекція порожня, використовуйте команду додати","Zbirka je prazna, uporabite ukaz add","La colección está vacía, use el comando agregar"));
        addWord("Nothing to delete", new Word("Удалять нечего","Видаляти нічого","Ničesar za brisanje","Nada que eliminar"));

        addWord("Edit", new Word("Редактировать", "Редагувати", "Uredi", "Editar"));
        addWord("Delete", new Word("Удалить", "Вилучити", "Izbriši", "Eliminar"));
        addWord("Removed", new Word("Удаление выполнено","Видалення виконано","Odstranite dokončano","Desinstalar completo"));
    }

    public static void setCurrentLang(String lang) {
        currentLang = lang;
    }

    public static String getCurrentLang() {
        return currentLang;
    }

    static void addWord(String en, Word word) {
        dict.put(en, word);
    }

    public static String getTranslation(String en) {
        if (dict.containsKey(en)) {
            switch (currentLang) {
                case "ru":
                    return dict.get(en).getRu();
                case "ua":
                    return dict.get(en).getUa();
                case "sl":
                    return dict.get(en).getSl();
                case "es":
                    return dict.get(en).getEs();
                default:
                    return en;
            }
        } else {
            return en;
        }
    }

}
