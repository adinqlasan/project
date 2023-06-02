import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;
    private List<Post> posts;
    private List<User> following;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.posts = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<User> getFollowing() {
        return following;
    }
}

class Post {
    private String content;
    private User author;
    private int likes;

    public Post(String content, User author) {
        this.content = content;
        this.author = author;
        this.likes = 0;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public int getLikes() {
        return likes;
    }

    public void like() {
        likes++;
    }
}

class SocialMediaApp {
    private Map<String, User> users;

    public SocialMediaApp() {
        this.users = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            User newUser = new User(username, password);
            users.put(username, newUser);
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }

    public void loginUser(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                displayNewsFeed(user);
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        } else {
            System.out.println("User not found. Please register before logging in.");
        }
    }

    public void createPost(String username, String content) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            Post newPost = new Post(content, user);
            user.getPosts().add(newPost);
            System.out.println("Post created successfully!");
        } else {
            System.out.println("User not found. Please register before creating a post.");
        }
    }

    public void likePost(User user, int postIndex) {
        List<Post> posts = user.getPosts();
        if (postIndex >= 0 && postIndex < posts.size()) {
            Post post = posts.get(postIndex);
            post.like();
            System.out.println("Post liked successfully!");
        } else {
            System.out.println("Invalid post index.");
        }
    }

    public void followUser(User follower, User following) {
        if (!follower.getFollowing().contains(following)) {
            follower.getFollowing().add(following);
            System.out.println("You are now following " + following.getUsername() + "!");
        } else {
            System.out.println("You are already following " + following.getUsername() + ".");
        }
    }

    public void displayNewsFeed(User user) {
        System.out.println("Welcome to the News Feed, " + user.getUsername() + "!");
        System.out.println("---------------------------------------------");

        List<User> following = user.getFollowing();
        List<Post> newsFeed = new ArrayList<>();

        for (User followedUser : following) {
            newsFeed.addAll(followedUser.getPosts());
        }

        if (newsFeed.isEmpty()) {
            System.out.println("No posts found in the news feed.");
        } else {
            for (int i = newsFeed.size() - 1; i >= 0; i--) {
                Post post = newsFeed.get(i);
                System.out.println("Author: " + post.getAuthor().getUsername());
                System.out.println("Content: " + post.getContent());
                System.out.println("Likes: " + post.getLikes());
                System.out.println("---------------------------------------------");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SocialMediaApp socialMediaApp = new SocialMediaApp();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    socialMediaApp.registerUser(username, password);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    socialMediaApp.loginUser(username, password);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
