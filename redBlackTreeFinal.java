import java.util.Scanner;
import java.io.*;

public class redBlackTreeFinal {

    private node current;
    private node parent;
    private node grand;
    private node great;
    private node header;
    private static node nullNode;

    int count = 0;

    static {
        nullNode = new node(" ");
        nullNode.left = nullNode;
        nullNode.right = nullNode;
    }

    static final int BLACK = 1;
    static final int RED = 0;

    public redBlackTreeFinal(String word) {
        header = new node(word);
        header.left = nullNode;
        header.right = nullNode;
    }

    // Inserts the word into the tree and checks for any changes that need to be
    // made to the tree as it is being added
    public void insert(String word) {

        if (contains(word) == true) {
            addOccurance(header.right, word);
            return;
        }

        current = parent = grand = header;
        nullNode.word = word;

        while (current.word != word) {

            great = grand;
            grand = parent;
            parent = current;

            current = (word.compareTo(current.word) < 0) ? current.left : current.right;

            if (current.left.color == RED && current.right.color == RED) {
                rotation(word);
            }
        }

        if (current != nullNode) {
            return;
        }

        current = new node(word, nullNode, nullNode);

        if (word.compareTo(parent.word) < 0) {
            parent.left = current;
        } else {
            parent.right = current;
        }

        rotation(word);
    }

    // Determines if something is needed to be rotated
    private void rotation(String word) {

        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if (parent.color == RED) {
            grand.color = RED;
            if ((word.compareTo(grand.word) < 0) != (word.compareTo(parent.word) < 0)) {
                parent = rotate(word, grand);
            }
            current = rotate(word, great);
            current.color = BLACK;
        }

        header.right.color = BLACK;

    }

    // Rotates the left or right child of the current parent as needed
    private node rotate(String word, node parent) {
        if (word.compareTo(parent.word) < 0) {
            return parent.left = word.compareTo(parent.left.word) < 0 ? rotateLeftChild(parent.left)
                    : rotateRightChild(parent.left);
        } else {
            return parent.right = word.compareTo(parent.right.word) < 0 ? rotateLeftChild(parent.right)
                    : rotateRightChild(parent.right);
        }
    }

    // Rotates the left child if the tree needs to reorient itself
    private node rotateLeftChild(node n2) {
        node n1 = n2.left;
        n2.left = n1.right;
        n1.right = n2;
        return n1;
    }

    // Rotates the right child if the tree needs to reorient itself
    private node rotateRightChild(node n1) {
        node n2 = n1.right;
        n1.right = n2.left;
        n2.left = n1;
        return n2;
    }

    // Sets up to return if the word is in the tree or not
    public boolean contains(String word) {

        boolean found = contains(header.right, word);

        return found;
    }

    // Checks to see if the tree contains the word
    public boolean contains(node temp, String word) {
        boolean found = false;

        while ((temp != nullNode) && !found) {

            String tempWord = temp.word;

            if (word.compareTo(tempWord) < 0) {
                temp = temp.left;
            } else if (word.compareTo(tempWord) > 0) {
                temp = temp.right;
            } else {
                found = true;
                break;
            }

            found = contains(temp, word);

        }

        return found;
    }

    // If a word is already in the tree, it adds an occurance of that word to its
    // node
    private void addOccurance(node temp, String word) {

        boolean found = false;

        while (!found) {
            String tempWord = temp.word;
            if (word.compareTo(tempWord) < 0) {
                temp = temp.left;
            } else if (word.compareTo(tempWord) == 0) {
                temp.occurance++;
                return;
            } else {
                temp = temp.right;
            }
        }

    }

    // Prints out the node info if the node is found, else it tells the user the
    // word is not in the tree
    public void search(String word) {

        node temp = search(header.right, word);
        boolean found = contains(word);

        if (found == true) {
            char c = 'B';
            if (temp.color == 0) {
                c = 'R';
            }
            System.out.printf("Word: " + word + "\t\tColor: " + c + "\t\tOccurance: " + temp.occurance + "\n");
        } else {
            System.out.printf("Word: %s was not found!\n", word);
        }

        return;
    }

    // Finds and returns if a word is in the tree and returns the node if found
    private node search(node temp, String word) {

        boolean found = false;

        node searched = nullNode;

        while ((temp != nullNode) && !found) {

            String tempWord = temp.word;

            if (word.compareTo(tempWord) < 0) {
                temp = temp.left;
            } else if (word.compareTo(tempWord) > 0) {
                temp = temp.right;
            } else {
                found = true;
                searched = temp;
                break;
            }

            searched = search(temp, word);

        }

        return searched;

    }

    // Sets up to display in order of the tree
    public void inorder() {
        System.out.print("\nInOrder:\n");
        inorder(header.right);
    }

    // Actually prints out the in order display of the tree
    private void inorder(node temp) {
        if (temp != nullNode) {
            inorder(temp.left);
            char c = 'B';
            if (temp.color == 0) {
                c = 'R';
            }
            System.out.println("Word: " + temp.word + "\t\tColor: " + c + "\t\tOccurance: " + temp.occurance);
            inorder(temp.right);
        } else {
            return;
        }
    }

    // Sets up to display pre order of the tree
    public void preorder() {
        System.out.print("\nPreOrder:\n");
        preorder(header.right);
    }

    // Actually prints out the pre order display of the tree
    private void preorder(node temp) {

        if (temp != nullNode) {
            char c = 'B';
            if (temp.color == 0) {
                c = 'R';
            }
            System.out.println("Word: " + temp.word + "\t\tColor: " + c + "\t\tOccurance: " + temp.occurance);
            preorder(temp.left);
            preorder(temp.right);
        } else {
            return;
        }
    }

    // Sets up to display post order of the tree
    public void postorder() {
        System.out.print("\nPostOrder:\n");
        postorder(header.right);
    }

    // Actually prints out the post order of the ree
    private void postorder(node temp) {
        if (temp != nullNode) {
            postorder(temp.left);
            postorder(temp.right);
            char c = 'B';
            if (temp.color == 0) {
                c = 'R';
            }
            System.out.println("Word: " + temp.word + "\t\tColor: " + c + "\t\tOccurance: " + temp.occurance);
        } else {
            return;
        }
    }

    // Takes in a movie script and adds it to the tree
    public void takeMovieScript(String file) {
        try {
            File script = new File(file);
            Scanner scan = new Scanner(script);
            while (scan.hasNext()) {
                String word = scan.next().toLowerCase().replaceAll("[-^+=.!?,\"|\"$]", "");
                insert(word);
            }
        } catch (Exception e) {
            System.out.print("\nInvalid script entered\n");
        }
    }

    // Empties the tree
    public void purge() {
        header.right = nullNode;
        System.out.print("\nItems have been purged\n");
    }

    // Finds and displays all words in the script
    public void treeSize() {
        int count = treeSize(header.right);
        int wordCount = wordCount(header.right);
        System.out.printf("\nThere are %d unique words in the script\n", count);
        System.out.printf("There are %d total words in the script\n", wordCount);
    }

    // Adds all word occurances together
    private int wordCount(node temp) {
        if (temp == nullNode) {
            return 0;
        } else {
            wordCount(temp.left);
            wordCount(temp.right);
            count += getOccurance(temp);
            return count;
        }
    }

    // Returns the occurance associated with the entered node
    private int getOccurance(node temp) {
        return temp.occurance;
    }

    // Checks how many nodes are in the tree
    private int treeSize(node temp) {
        if (temp == nullNode) {
            return 0;
        } else {
            int count = 1;
            count += treeSize(temp.left);
            count += treeSize(temp.right);
            return count;
        }
    }

    /*
     * Here is where the menu is created and it is what the user of the script
     * analyzer is going to see and allows them to use what they want to
     */
    public void menu() {

        int choice = 1;
        Scanner scan = new Scanner(System.in);

        String option = " ";
        long startTime = 0;
        long endTime = 0;

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter("Time Log.txt"));

            writer.write(java.time.LocalDate.now() + "\n\n");

            System.out.print("\nChoices:\n\n");

            System.out.print("1) Insert a word\n");
            System.out.print("2) Search for a word\n");
            System.out.print("3) PreOrder Traversal\n");
            System.out.print("4) Inorder Traversal\n");
            System.out.print("5) PostOrder Traversal\n");
            System.out.print("6) Take in a Movie Script\n");
            System.out.print("7) Purge\n");
            System.out.print("8) Size of tree\n");
            System.out.print("0) Exit\n");

            while (choice != 0) {

                System.out.print("\nEnter your choice: ");
                while (!scan.hasNextInt()) {
                    System.out.print("\nInvalid choice, try again!\n");
                    System.out.print("Enter another choice: ");
                    scan.next();
                }

                choice = scan.nextInt();

                switch (choice) {

                case 0:
                    System.out.print("\nThank You!\n");
                    writer.close();
                    return;
                case 1:
                    System.out.print("\nEnter a word to add: ");
                    String add = scan.next();
                    option = "Insert Word: ";
                    startTime = System.currentTimeMillis();
                    insert(add);
                    endTime = System.currentTimeMillis();
                    break;
                case 2:
                    System.out.print("\nEnter a word to search for: ");
                    String word = scan.next();
                    option = "Searching for word: ";
                    startTime = System.currentTimeMillis();
                    search(word);
                    endTime = System.currentTimeMillis();
                    break;
                case 3:
                    option = "PreOrder Traversal: ";
                    startTime = System.currentTimeMillis();
                    preorder();
                    endTime = System.currentTimeMillis();
                    break;
                case 4:
                    option = "InOrder Traversal: ";
                    startTime = System.currentTimeMillis();
                    inorder();
                    endTime = System.currentTimeMillis();
                    break;
                case 5:
                    option = "PostOrder Traversal: ";
                    startTime = System.currentTimeMillis();
                    postorder();
                    endTime = System.currentTimeMillis();
                    break;
                case 6:
                    String file;
                    System.out.print("\nEnter a movieScript.txt: ");
                    file = scan.next();
                    option = "Inserting Movie Script: ";
                    startTime = System.currentTimeMillis();
                    takeMovieScript(file);
                    endTime = System.currentTimeMillis();
                    break;
                case 7:
                    option = "Clearing Tree: ";
                    startTime = System.currentTimeMillis();
                    purge();
                    endTime = System.currentTimeMillis();
                    break;
                case 8:
                    option = "Getting Size of Tree: ";
                    startTime = System.currentTimeMillis();
                    treeSize();
                    endTime = System.currentTimeMillis();
                    break;
                default:
                    System.out.print("\nInvalid choice, try again!\n");
                    break;
                }

                writer.write(option);
                writer.write("\t\t\tStarting Time: " + startTime + "ms");
                writer.write("\t\t\tEnd Time: " + endTime + "ms");
                writer.write("\t\t\tTime Taken: " + (endTime - startTime) + "ms\n\n");

            }

            scan.close();

        } catch (Exception e) {
            System.err.print("\n" + e + "\n");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.print("\n" + e + "\n");
                }
            }
        }
 
    }

}