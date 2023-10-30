# BookShelf 📚

An intuitive Android application designed for book enthusiasts. Dive deep into an extensive list of books, browse based on publication year, and have a personalized experience by marking books as your favorite and jotting down notes!

## Features 🌟
- **User Authentication:**
  - Sign up with ease, providing details such as name, email, password and choosing from a dynamically loaded country dropdown.
  - Log in to your existing account or simply log out when done.
  - Comprehensive validation error system with contextual feedback to ensure data integrity.

- **Dynamic Country Selection on Signup:**
  - Country list dynamically fetched from the backend API.
  - The default country is selected based on the user's IP geolocation.

- **Book List:**
  - Witness a curated list of books immediately after logging in/signing up.
  - Efficiently fetched from a remote API with the power of caching & background processing.
  - No internet? No problem! Access the book list from the cached data on your phone.
  - Filter books using tabs based on the publication year. Clicking on a specific year will auto-scroll to the first book of that year.

- **Book Details & Personalized Experience:**
  - Click on a book and immerse yourself in its details.
  - Personalize your reading experience:
    - Mark books as your favorite 🌟.
    - Add/Edit notes, tags, and annotations 📝.
    - Your data remains secure and persists across login sessions.

## Libraries Used 🛠
- **Dagger Hilt** - For dependency injection.
- **Room** - For local data storage.
- **Glide** - Efficient image loading.
- **Navigation Components with SafeArgs Plugin** - Seamless app navigation.
- **Retrofit** - Network calls made easy.
- **Coroutines** - Handle asynchronous tasks with ease.
- **Flexbox** - To display multiple note tags/annotations in a linear fashion.

## Languages Used 🖥
- **Kotlin** - Modern, expressive, and safe language for Android development.
- Built following MVVM architecture with clean architecture.

## How to Run & Verify 🏃‍♂️
Clone the Repository:
   git clone https://github.com/aashish-ranjan/bookshelf

Open in Android Studio:

Navigate to Open an existing Android Studio project.
Select the cloned directory.
Run the App:

Click on Run > Run 'app'.
Choose an emulator or a connected device.
Verify its Functionality:

Signup: Enter the required details and observe the country dropdown.
Login: Use the credentials you just created.
Booklist: Post-authentication, you'll land on the booklist page. Dive in, filter, and enjoy!
Book Details: Tap on any book to uncover its details, mark favorites, or annotate with notes/tags.
Logout:

Simply use the logout option when you're done.
