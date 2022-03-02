import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortWordResults(ArrayList<String> sortingList)
  {
    for (int j = 1; j < sortingList.size(); j++)
    {
      String temp = sortingList.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(sortingList.get(possibleIndex - 1)) < 0)
      {
        sortingList.set(possibleIndex, sortingList.get(possibleIndex - 1));
        possibleIndex--;
      }
      sortingList.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Enter a person to search for (first or last name): ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> castList = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++) {
      String actors = movies.get(i).getCast();
      String[] actorList = actors.split("\\|");
      for (int j = 0; j < actorList.length; j++) {
        String checkingCast = actorList[j].toLowerCase();
        if (castList.indexOf(actorList[j]) == -1 && checkingCast.indexOf(searchTerm) != -1)
        {
          castList.add(actorList[j]);
        }
      }
    }

    sortWordResults(castList);

    for (int i = 0; i < castList.size(); i++)
    {
      String actor = castList.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + actor);
    }

    System.out.println("Which actor?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedActor = castList.get(choice - 1);

    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieCast = movies.get(i).getCast();


      if (movieCast.indexOf(selectedActor) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int pick = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(pick - 1);


    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieKeyword = movies.get(i).getKeywords();
      movieKeyword = movieKeyword.toLowerCase();

      if (movieKeyword.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {

    ArrayList<String> genreList = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++) {
      String genres = movies.get(i).getGenres();
      String[] genreListsss = genres.split("\\|");
      for (int j = 0; j < genreListsss.length; j++) {
        if (genreList.indexOf(genreListsss[j]) == -1)
        {
          genreList.add(genreListsss[j]);
        }
      }
    }

    sortWordResults(genreList);

    for (int i = 0; i < genreList.size(); i++)
    {
      String actor = genreList.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + actor);
    }

    System.out.println("What would you like to see all movies for?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedGenre = genreList.get(choice - 1);

    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieGenre = movies.get(i).getGenres();


      if (movieGenre.indexOf(selectedGenre) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int pick = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(pick - 1);


    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }


//  private void listHighestRated()
//  {
//    ArrayList<Movie> top50Rated = new ArrayList<>();
//    top50Rated.add(movies.get(0));
//    Movie weak = top50Rated.get(0);
//    int weakDex = 0;
//    for (int i = 1; i < movies.size(); i++) {
//
//      Movie current = movies.get(i);
//      if (top50Rated.size() >= 50)
//      {
//        if (current.getUserRating() > weak.getUserRating())
//        {
//          top50Rated.remove(weakDex);
//          top50Rated.add(current);
//          boolean ahhh = false;
//          for (int j = 0; j < top50Rated.size(); j++) {
//            if (top50Rated.get(j).getUserRating() < current.getUserRating()) {
//              ahhh = true;
//
//            }
//          }
//          if (!ahhh)
//          {
//            weak = current;
//            weakDex = top50Rated.indexOf(weak);
//          }
//        }
//      } else
//      {
//        top50Rated.add(current);
//        boolean ahhh = false;
//        for (int j = 0; j < top50Rated.size(); j++) {
//          if (top50Rated.get(j).getUserRating() < current.getUserRating()) {
//            ahhh = true;
//
//          }
//        }
//        if (!ahhh)
//        {
//          weak = current;
//          weakDex = i;
//        }
//      }
//    }
//
//    sortDoubleResults(top50Rated);
//
//    // now, display them all to the user
//    for (int i = 0; i < top50Rated.size(); i++)
//    {
//      String title = top50Rated.get(i).getTitle();
//      Double rating = top50Rated.get(i).getUserRating();
//
//      // this will print index 0 as choice 1 in the results list; better for user!
//      int choiceNum = i + 1;
//
//      System.out.println("" + choiceNum + ". " + title + ": " + rating);
//    }
//
//    System.out.println("Which movie would you like to learn more about?");
//    System.out.print("Enter number: ");
//
//    int pick = scanner.nextInt();
//    scanner.nextLine();
//
//    Movie selectedMovie = top50Rated.get(pick - 1);
//
//
//    displayMovieInfo(selectedMovie);
//
//    System.out.println("\n ** Press Enter to Return to Main Menu **");
//    scanner.nextLine();
//
//  }

  private void listHighestRated()
  {
    ArrayList<Movie> rateMovies = movies;
    sortRating(rateMovies);
    ArrayList<Movie> top50Rated = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      top50Rated.add(rateMovies.get(i));
    }


        // now, display them all to the user
    for (int i = 0; i < top50Rated.size(); i++)
    {
      String title = top50Rated.get(i).getTitle();
      Double rating = top50Rated.get(i).getUserRating();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + ": " + rating);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int pick = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50Rated.get(pick - 1);


    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }

  private void sortRating(ArrayList<Movie> sortingList)
  {
    for (int j = 1; j < sortingList.size(); j++)
    {
      Movie temp = sortingList.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.getUserRating() > sortingList.get(possibleIndex - 1).getUserRating())
      {
        sortingList.set(possibleIndex, sortingList.get(possibleIndex - 1));
        possibleIndex--;
      }
      sortingList.set(possibleIndex, temp);
    }
  }

  private void listHighestRevenue()
  {
    ArrayList<Movie> rateMovies = movies;
    sortRevenue(rateMovies);
    ArrayList<Movie> top50Revenue = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      top50Revenue.add(rateMovies.get(i));
    }


    // now, display them all to the user
    for (int i = 0; i < top50Revenue.size(); i++)
    {
      String title = top50Revenue.get(i).getTitle();
      int revenue = top50Revenue.get(i).getRevenue();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + ": " + revenue);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int pick = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50Revenue.get(pick - 1);


    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortRevenue(ArrayList<Movie> sortingList)
  {
    for (int j = 1; j < sortingList.size(); j++)
    {
      Movie temp = sortingList.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.getRevenue() > sortingList.get(possibleIndex - 1).getRevenue())
      {
        sortingList.set(possibleIndex, sortingList.get(possibleIndex - 1));
        possibleIndex--;
      }
      sortingList.set(possibleIndex, temp);
    }
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] movieFromCSV = line.split(",");

        // pull out the data for this cereal
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}