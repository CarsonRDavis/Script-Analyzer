public class node {

    String word;
    int occurance;
    node left, right;
    int color;

    public node(String word) {
        this(word, null, null);
    }

    public node(String word, node left, node right) {
        this.word = word;
        this.left = left;
        this.right = right;
        occurance = 1;
        color = 1;
    }

}