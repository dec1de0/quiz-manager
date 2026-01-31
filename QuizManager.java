import java.util.*;

public class QuizManager {
    public static void main(String[] args) {
        ArrayList<Question> allQuestions = new ArrayList<>();
        Scanner input = new Scanner(System.in); // Creating Scanner
        System.out.println("Welcome to Quiz Manager!");  // Welcome text

        String name = isEmpty(input, "\nEnter your name: "); // Checking name
        int numberOfQuestions = isInt(input, "Enter the number of questions (1-15): "); // checking number of questions

        for (int i = 1; i <= numberOfQuestions; i++) { // creating loop {number of questions} times
            System.out.println("\nQuestion #" + i + ": "); // asking type of question
            System.out.println("Choose question type:");
            System.out.println("1. Regular Question (4 options)");
            System.out.println("2. Question with Hint");
            System.out.println("3. Bonus Question (extra points)");
            System.out.println("4. True/False Question");

            int type = isInt(input, "Enter type (1-4): ");
            while (type > 4 || type <= 0) { // asking until user's type will be from 1 to 4
                System.out.println("Please enter a number between 1 and 4");
                type = isInt(input, "Enter type (1-4): ");
            }
            String questionText = isEmpty(input, "Enter the question: "); // checking question text

            Question q = null; // creating null object of class Question

            switch (type) {
                case 1:
                    q = regularQuestion(questionText, input); // creating regular question
                    break;
                case 2:
                    q = hintQuestion(questionText, input); // creating question with hint
                    break;
                case 3:
                    q = bonusQuestion(questionText, input); // creating question with bonus points
                    break;
                case 4:
                    q = trueFalseQuestion(questionText, input); // creating True/False question
                    break;
                default:
                    System.out.println("Invalid type. Creating regular question "); // if there is some mistake/issue,  creating regular question
                    q = regularQuestion(questionText, input);
            }
            allQuestions.add(q); // adding object q to ArrayList of all objects
        }

        Quiz quiz = new Quiz(name, allQuestions); // creating new object of class Quiz

        quiz.displayAllQuestions(); // calling method to display all questions

        System.out.println("\nPress any button to start the quiz"); // waiting user to confirm
        input.nextLine(); // scanning next line
        quiz.startQuiz(input); // starting quiz
    }

    public static String isEmpty(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt); // printing parameter prompt (question)
            String input = scanner.nextLine().trim(); // scanning input
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again"); // if empty -> ask again
            } else {
                return input; // if not -> return text
            }
        }
    }

    public static int isInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt); // printing parameter prompt (question)
            if (scanner.hasNextInt()) { // scanning whether input is number
                int num = scanner.nextInt(); // scanning number
                scanner.nextLine();
                if (num > 0 && num < 15) { // checking whether number is between 1 and 15
                    return num; // if yes -> return number
                }
                System.out.println("Please enter a number between 1 and 15"); // if not -> ask again
            }
            else {
                System.out.println("Please enter a valid number"); //if not number -> ask again
                scanner.nextLine();
            }
        }
    }

    public static Question regularQuestion(String questionText, Scanner scanner) { // method for creating regular question
        ArrayList<String> answers = new ArrayList<>();  // creating ArrayList of answers
        for (int i = 1; i <= 3; i++) { // asking 3 wrong options
            answers.add(isEmpty(scanner, "Enter wrong option #" + i + ": ")); // check, and then add to answers
        }

        String correctAnswer = isEmpty(scanner, "Enter the correct option: "); // check, and then add to answers
        answers.add(correctAnswer);

        int correctIndex = answers.indexOf(correctAnswer); // saving index of correct answer

        return new Question(questionText, answers, correctIndex); // return values to object q (27 line)
    }

    public static HintQuestion hintQuestion(String questionText, Scanner scanner) { // method for creating question with hint
        ArrayList<String> answers = new ArrayList<>(); // creating ArrayList of answers

        for (int i = 1; i <= 3; i++) { // asking 3 wrong options
            answers.add(isEmpty(scanner, "Enter wrong option #" + i + ": ")); // check, and then add to answers
        }

        String correctAnswer = isEmpty(scanner, "Enter the correct option: "); // check, and then add to answers
        answers.add(correctAnswer);

        String hint = isEmpty(scanner, "Enter a hint: ");  // asking hint text and then check

        int correctIndex = answers.indexOf(correctAnswer); // saving index of correct answer

        return new HintQuestion(questionText, answers, correctIndex, hint); // return values to object q (27 line)
    }


    public static BonusQuestion bonusQuestion(String questionText, Scanner scanner) { // method for creating bonus question
        ArrayList<String> answers = new ArrayList<>(); // creating ArrayList of answers

        for (int i = 1; i <= 3; i++) { // asking 3 wrong options
            answers.add(isEmpty(scanner, "Enter wrong option #" + i + ": "));
        }

        String correctAnswer = isEmpty(scanner, "Enter the correct option: "); // check, and then add to answers
        answers.add(correctAnswer);

        int bonusPoints = isInt(scanner, "Enter bonus points (1-5): "); // asking bonus point

        int correctIndex = answers.indexOf(correctAnswer); // saving index of correct answer

        return new BonusQuestion(questionText, answers, correctIndex, bonusPoints);  // return values to object q (27 line)
    }

    public static TrueFalseQuestion trueFalseQuestion(String questionText, Scanner scanner) { // method for creating true/false question
        System.out.print("Is the statement TRUE or FALSE?: "); // ask user
        String answer = scanner.nextLine().toLowerCase().trim(); // remove all spaces and lowercase the answer

        while (!answer.equals("true") && !answer.equals("false")) { // loop until answer equals "true" or "false"
            System.out.print("Please enter True or False: ");
            answer = scanner.nextLine().toLowerCase().trim();
        }
        boolean isTrue = answer.equals("true"); // if answer is "true" boolean will be also True
        return new TrueFalseQuestion(questionText, isTrue); // return values to object q (27 line)
    }
}

// CLASSES

class Question {
    // attributes
    private String question;
    private ArrayList<String> answers;
    private int correctIndex;
    // constructor
    public Question(String question, ArrayList<String> answers, int correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }
    // getters
    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void shuffleAnswers() {
        String correctAnswer = answers.get(correctIndex); // save correct answer
        Collections.shuffle(answers); // shuffle answers
        correctIndex = answers.indexOf(correctAnswer); // replace correct index with real correct index
    }

    public void display() {
        System.out.println("\n" + question);
        for (int i = 0; i < answers.size(); i++) { // loop for displaying questions
            System.out.println("  " + (i + 1) + ". " + answers.get(i));
        }
    }
    public boolean checkAnswer(int userAnswer) { // if userAnswer == correctIndex, return true
        return (userAnswer - 1) == correctIndex;
    }

    public int getPoints() {
        return 1;
    }
}

    // SUBCLASSES

class HintQuestion extends Question {
    // attributes
    private String hint;
    // constructor
    public HintQuestion(String question, ArrayList<String> answers, int correctIndex, String hint) {
        super(question, answers, correctIndex);
        this.hint = hint;
    }
    // getter of hint
    public String getHint() {
        return hint;
    }

    // extend the super's method display() by adding hint
    public void display() {
        super.display();
        System.out.println("Hint: " + hint);
    }
}


class BonusQuestion extends Question {
    // attributes
    private int bonusPoints;
    // constructor
    public BonusQuestion(String question, ArrayList<String> answers, int correctIndex, int bonusPoints) {
        super(question, answers, correctIndex);
        this.bonusPoints = bonusPoints;
    }
    // getter of bonus points
    public int getBonusPoints() {
        return bonusPoints;
    }

    // extend the super's method display() by adding bonus points
    @Override
    public void display() {
        System.out.println("\nBonus question (+" + bonusPoints + " points)");
        System.out.println(getQuestion());
        for (int i = 0; i < getAnswers().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + getAnswers().get(i));
        }
    }

    @Override
    public int getPoints() {
        return bonusPoints;
    }
}
class TrueFalseQuestion extends Question {

    // constructor
    public TrueFalseQuestion(String question, boolean correctAnswer) {
        super(question,
                new ArrayList<>(Arrays.asList("True", "False")),
                correctAnswer ? 0 : 1);
    }
    // extend the super's method display() by adding true/false options
    public void display() {
        System.out.println("\n" + getQuestion() + " (True/False)");
        System.out.println("  1. True");
        System.out.println("  2. False");
    }
}

class Quiz {
    // attributes
    private String name;
    private ArrayList<Question> allQuestions;
    private int score;
    private int totalPoints;
    // constructor
    public Quiz(String name, ArrayList<Question> allQuestions) {
        this.name = name;
        this.allQuestions = allQuestions;
        this.score = 0;
        this.totalPoints = calculateTotalPoints();
    }
    // getters
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
    private int calculateTotalPoints() {
        int total = 0;
        for (Question q : allQuestions) {
            total += q.getPoints();
        }
        return total;
    }


    public void displayAllQuestions() {
        System.out.println("    QUIZ BY: " + name);
        System.out.println("Total questions: " + allQuestions.size());
        System.out.println("Total possible points: " + totalPoints);

        for (int i = 0; i < allQuestions.size(); i++) { // loop for displaying all questions
            Question q = allQuestions.get(i);
            System.out.println("\n" + (i + 1) + ". " + q.getQuestion()); // display question

            ArrayList<String> answers = q.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                String mark = (j == q.getCorrectIndex()) ? " <-- the right option" : ""; // if j==correctIndex, mark it with pointer
                System.out.println("   " + (j + 1) + ". " + answers.get(j) + mark); // display answers
            }

            if (q instanceof HintQuestion) { // if question is in subclass HintQuestion, print hint
                System.out.println("Hint: " + ((HintQuestion) q).getHint());
            }
            if (q instanceof BonusQuestion) { // if question is in subclass BonusQuestion, print bonus points
                System.out.println("Bonus: +" + ((BonusQuestion) q).getBonusPoints() + " points");
            }
        }
    }

    public void startQuiz(Scanner scanner) {
        score = 0; // initial score of user
        System.out.println("Total questions: " + allQuestions.size());
        System.out.println("Total possible points: " + totalPoints);

        for (int i = 0; i < allQuestions.size(); i++) {
            Question q = allQuestions.get(i); // get question
            int maxOptions = q.getAnswers().size();
            q.shuffleAnswers();
            System.out.println("Question " + (i + 1) + " of " + allQuestions.size());
            q.display();
            int userAnswer = getAnswer(scanner, maxOptions); // send userAnswer to method getAnswer to get valid option
            if (q.checkAnswer(userAnswer)) { // check answer
                System.out.println("Correct answer!");
                int points = q.getPoints();
                score += points;
                if (points > 1) {
                    System.out.println("Congratulations! You earned " + points + " points!"); // if there are bonus points, print
                }
            } else {
                System.out.println("Wrong! Correct answer was option " + (q.getCorrectIndex() + 1)); // if wrong, show correctIndex
            }

            System.out.println("Current score: " + score + "/" + totalPoints); // count score
            System.out.println();
        }
        showResults();
    }
    private int getAnswer(Scanner scanner, int max) {
        while (true) {
            System.out.print("\nYour answer (1- " + max + "): ");
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                scanner.nextLine();
                if (answer >= 1 && answer <= max) {
                    return answer;
                }
                System.out.println("Please enter a number between 1 and " + max);
            } else {
                System.out.println("Please enter a valid number");
                scanner.nextLine();
            }
        }
    }

    public void showResults() {
        System.out.println("\n========== QUIZ COMPLETED ========== ");
        System.out.println("Quiz by: " + name);
        System.out.println("Your score: " + score + " / " + totalPoints + " points");

        double percentage = (double) score / totalPoints * 100;
        System.out.printf("Percentage: %.2f%%\n", percentage);

        System.out.println("\nOverall grade: ");
        if (percentage >= 90) {
            System.out.println("You got a grade A!");
        } else if (percentage >= 80) {
            System.out.println("You got a grade B!");
        } else if (percentage >= 70) {
            System.out.println("You got a grade C!");
        } else if (percentage >= 60) {
            System.out.println("You got a grade D!");
        }
        else {
            System.out.println("You got a grade F!");
        }
    }
}