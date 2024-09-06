import java.util.Scanner;

class Person {
    protected String name;
    protected int ID;

    public Person(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}

class Admin extends Person {

    public Admin(String name, int ID) {
        super(name, ID);
    }

    public void addSong(Song[] songs, String title, String artist) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i] == null) {
                songs[i] = new Song(title, artist);
                System.out.println("Song added: " + title);
                return;
            }
        }
        System.out.println("No space available to add more songs.");
    }

    public void viewSummary(Song[] songs) {
        System.out.println("Music Library Summary:");
        for (Song song : songs) {
            if (song != null) {
                System.out.println(song.getTitle() + " by " + song.getArtist() + " | Available: " + song.isAvailable());
            }
        }
    }
}

class User extends Person {

    public User(String name, int ID) {
        super(name, ID);
    }

    public void listenToSong(Song[] songs, String title) {
        for (Song song : songs) {
            if (song != null && song.getTitle().equalsIgnoreCase(title) && song.isAvailable()) {
                song.listen();
                System.out.println("Listening to song: " + title);
                return;
            }
        }
        System.out.println("Song is not available or does not exist.");
    }

    public void returnSong(Song[] songs, String title) {
        for (Song song : songs) {
            if (song != null && song.getTitle().equalsIgnoreCase(title) && !song.isAvailable()) {
                song.returnSong();
                System.out.println("Song returned: " + title);
                return;
            }
        }
        System.out.println("Song was not listened to or does not exist.");
    }
}

class Song {
    private String title;
    private String artist;
    private boolean isAvailable;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void listen() {
        isAvailable = false;
    }

    public void returnSong() {
        isAvailable = true;
    }
}

class MusicLibrary {
    private Song[] songs;
    private User[] users;

    public MusicLibrary(int songCapacity, int userCapacity) {
        songs = new Song[songCapacity];
        users = new User[userCapacity];
    }

    public void addUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                System.out.println("User added: " + user.getName());
                return;
            }
        }
        System.out.println("No space to add more users.");
    }

    public Song[] getSongs() {
        return songs;
    }

    public User[] getUsers() {
        return users;
    }
}

public class MusicLibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MusicLibrary library = new MusicLibrary(5, 3); // Music library with 5 songs and 3 users capacity
        Admin admin = new Admin("Admin", 101);

        System.out.print("Enter the name of User 1: ");
        String user1Name = scanner.nextLine();
        User user1 = new User(user1Name, 201);

        System.out.print("Enter the name of User 2: ");
        String user2Name = scanner.nextLine();
        User user2 = new User(user2Name, 202);

        // Adding users to library
        library.addUser(user1);
        library.addUser(user2);

        // Menu for user interaction
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a song");
            System.out.println("2. Listen to a song");
            System.out.println("3. Return a song");
            System.out.println("4. View Library Summary");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    // Admin adds song
                    System.out.print("Enter song title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter song artist: ");
                    String artist = scanner.nextLine();
                    admin.addSong(library.getSongs(), title, artist);
                    break;

                case 2:
                    // User listens to a song
                    System.out.print("Enter the song title to listen to: ");
                    String songToListen = scanner.nextLine();
                    System.out.print("Who is listening (1 for " + user1.getName() + ", 2 for " + user2.getName() + "): ");
                    int listener = scanner.nextInt();
                    if (listener == 1) {
                        user1.listenToSong(library.getSongs(), songToListen);
                    } else if (listener == 2) {
                        user2.listenToSong(library.getSongs(), songToListen);
                    } else {
                        System.out.println("Invalid user.");
                    }
                    break;

                case 3:
                    // User returns a song
                    System.out.print("Enter the song title to return: ");
                    String songToReturn = scanner.nextLine();
                    System.out.print("Who is returning (1 for " + user1.getName() + ", 2 for " + user2.getName() + "): ");
                    int returner = scanner.nextInt();
                    if (returner == 1) {
                        user1.returnSong(library.getSongs(), songToReturn);
                    } else if (returner == 2) {
                        user2.returnSong(library.getSongs(), songToReturn);
                    } else {
                        System.out.println("Invalid user.");
                    }
                    break;

                case 4:
                    // Admin views library summary
                    admin.viewSummary(library.getSongs());
                    break;

                case 5:
                    // Exit the application
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
