import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    boolean flag;
    int wrongAttempts;
    String secretWord;
    String input;
    int parsedInput;
    Scanner sc1 = new Scanner(System.in);
    List<String> words = new ArrayList<>();
    List<Character> secretWordList = new ArrayList<>();
    List<String> displayedWord = new ArrayList<>();
    Random random = new Random();


    void main() {
        System.out.println("Хотители вы начать новую игру ");
        System.out.println("1 - Да");
        System.out.println("2 - Нет(выход из приложения)");

        while (true) {

            inputCheck();
            if (parsedInput == 1) {
                System.out.println("Запускаем игру");
                readDictionary();
                getRandomWord();
                concealmentWord();
                processGuessing();
                //зачищаем переменные которые используются в каждой игре, можно было бы их вынести в отдельный класс было бы крассивее но я решил делать 1 проект без ООП
                secretWordList.clear();
                displayedWord.clear();
                flag = false;
                wrongAttempts = 0;
                System.out.println("Хотители вы начать новую игру ");
                System.out.println("1 - Да");
                System.out.println("2 - Нет(выход из приложения)");

            } else if (parsedInput == 2) {
                break;
            }
        }


    }
    public void inputCheck(){
        while(true) {
            input = sc1.next();
            try {
                parsedInput = Integer.parseInt(input);
                if (parsedInput != 1 && parsedInput != 2) {
                    System.out.println("Неправильно выбран пункт в меню повторите ввод:");

                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Вводить буквы нельзя, попробуйте еще раз:");
            }
        }

    }

    public void readDictionary() {//зыаписываем весь словарь в лист
        words.clear();
        try (Scanner sc = new Scanner(new File(".dictionaries/custom.dic.txt"))) {
            while (sc.hasNextLine()) {
                words.add(sc.nextLine());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Не удалось найти словарь", e);
        }
    }

    public void getRandomWord() {// при помози Random получаем из lista 1 слово которое будет отгадываться и преврааем его в list<char>

        int indexrandomword = random.nextInt(words.size());

        secretWord = words.get(indexrandomword);

        for (int i = 0; i < secretWord.length(); i++) {
            secretWordList.add(secretWord.charAt(i));
        }

    }

    public void concealmentWord() {// для сокрытия слова от пользователя
        System.out.print("Ваше слово:");
        for (int i = 0; i < secretWord.length(); i++) {
            displayedWord.add("_");
        }
        System.out.print(displayedWord);
        System.out.println(" \n");

    }


    public void processGuessing() {
        System.out.print("\nВведите букву которая по вашему мнению должна быть в этом слове:\n");

        //основной алгорит игры
        while(!flag && wrongAttempts < 5 ){
      while(true) {
          String rawInput = sc1.next().toLowerCase();
          if (rawInput.length() != 1) {
              System.out.print("Вы должны ввести ровно один символ, попробуйте ещё раз:\n");
              continue;
          }
                char letter = rawInput.charAt(0);
                if ((letter >= 'а' && letter <= 'я') || letter == 'ё') {
                    for (int j = 0; j < secretWordList.size(); j++) {
                        if ((secretWordList.get(j)) == (letter)) {
                            displayedWord.set(j, String.valueOf(letter));
                            flag = isWordGuessed();
                        }

                    }
                    System.out.print(displayedWord);
                    System.out.print("\n");

                    //вывод виселицы при неправильном вводе
                    if (!(secretWordList.contains(letter))) {
                        System.out.print("Не угадали");
                        System.out.print("\n");
                        ++wrongAttempts;
                        printHangman(wrongAttempts);
                        if (wrongAttempts == 5) {
                            System.out.print("Вы проиграли,ваше слово:\n" + secretWord + "\n");
                            break;
                        }

                    }
                } else {
                    System.out.print("Вы должны ввести одну русскую букву, которая по вашему мнению находится в этом слове :\n");
                    break;
                }
                break;
            }

        }


    }
    public boolean isWordGuessed() {
       boolean allGuessed = true;
        //проверка на то что челове все вывел правильно
        for (int i = 0; i < secretWordList.size(); i++) {
            if (!(String.valueOf(secretWordList.get(i)).equals(displayedWord.get(i)))) {
                allGuessed  = false;

            }
        }
            if ( allGuessed == true) {
                System.out.print("Вы победили! Ваше слово:\n" + secretWord);



            }

            return allGuessed;



    }
    public void printHangman(int n){
        System.out.println(hangmanStages[n] + "\n" +
                "Осталось " + (5 - n) + " попыток\n");

    }
    String[] hangmanStages = {

            """
     _______
    |      |
    |
    |
    |
    |
    |_________
    """,


            """
     _______
    |      |
    |      O
    |
    |
    |
    |_________
    """,


            """
     _______
    |      |
    |      O
    |      |
    |
    |
    |_________
    """,


            """
     _______
    |      |
    |      O
    |     /|
    |
    |
    |_________
    """,


            """
     _______
    |      |
    |      O
    |     /|\\
    |
    |
    |_________
    """,


            """
     _______
    |      |
    |      O
    |     /|\\
    |     / \\
    |
    |_________
    """
    };

}

