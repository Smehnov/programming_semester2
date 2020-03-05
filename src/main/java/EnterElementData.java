import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EnterElementData {
    static MusicBand createMusicBand() {
        Scanner sc = new Scanner(System.in);
        MusicBand musicBand = new MusicBand();
        String name = enterName();
        Coordinates coords = enterCoordinates();
        Integer numberOfParticipants = enterNumberOfParticipants();
        MusicGenre genre = enterMusicGenre();
        Album bestAlbum = enterBestAlbum();


        musicBand.setName(name);
        musicBand.setCoordinates(coords);
        musicBand.setNumberOfParticipants(numberOfParticipants);
        musicBand.setGenre(genre);
        musicBand.setBestAlbum(bestAlbum);
        return musicBand;
    }

    static String enterName() {
        System.out.println("Enter name");
        String name = "";
        while (name.equals("")) {
            name = Inputting.readLine().trim();
            if (name.equals("")) {
                System.out.println("Field can't be empty");
            }
        }
        return name;
    }

    static Coordinates enterCoordinates() {
        Double coordX = null;
        Float coordY = null;
        while (coordX == null) {
            try {
                System.out.println("Enter coordinate x");
                coordX = Double.parseDouble(Inputting.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format");
            }
        }
        while (coordY == null) {
            try {
                System.out.println("Enter coordinate y");
                coordY = Float.parseFloat(Inputting.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format");
            }
        }
        return new Coordinates(coordX, coordY);
    }

    static Integer enterNumberOfParticipants() {
        System.out.println("Enter number of participants");
        Integer numberOfParticipants = 0;
        while (numberOfParticipants <= 0) {
            try {
                String line = Inputting.readLine();
                if (line.isEmpty()) {
                    return null;
                }
                numberOfParticipants = Integer.parseInt(line);

                if (numberOfParticipants <= 0) {
                    System.out.println("Number of participants must be greater then 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong number format. Field must be integer");
            }
        }
        return numberOfParticipants;
    }

    static MusicGenre enterMusicGenre() {
        MusicGenre genre = null;
        MusicGenre[] possibleGenres = MusicGenre.values();
        String[] possibleGenresStrings = new String[possibleGenres.length];
        for (int i = 0; i < possibleGenres.length; i++) {
            MusicGenre possibleGenre = possibleGenres[i];
            possibleGenresStrings[i] = possibleGenre.name();
        }
        System.out.println("Enter music genre(" + String.join(", ", possibleGenresStrings) + ")");
        boolean correctInput = false;
        while (!correctInput) {
            List<String> genresList = Arrays.asList(possibleGenresStrings);

            String genreS = Inputting.readLine();
            if (!genresList.contains(genreS)) {
                System.out.println("Enter genre from the list(" + String.join(", ", possibleGenresStrings) + ")");
            } else {
                if (genresList.contains(genreS)) {
                    genre = MusicGenre.valueOf(genreS);
                }
                correctInput = true;
            }
        }
        return genre;
    }

    static Album enterBestAlbum() {
        Album bestAlbum = null;
        System.out.println("Enter the name of the best album(or leave it empty for a null value");
        String albumNameInput = Inputting.readLine().trim();
        if (!albumNameInput.equals("")) {
            System.out.println("Enter best album length(must be greater then 0)");
            Long albumLength = (long) 0;
            while (albumLength <= 0) {
                try {
                    albumLength = Long.parseLong(Inputting.readLine());
                    if (albumLength <= 0) {
                        System.out.println("Album length must be greater then 0");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong number format");
                }


            }
            bestAlbum = new Album(albumNameInput, albumLength);
        }
        return bestAlbum;
    }


}
