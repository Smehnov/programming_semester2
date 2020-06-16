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
