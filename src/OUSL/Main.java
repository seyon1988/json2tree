package OUSL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private String condition;

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\baums2\\Desktop\\js2.txt";
        String content = "";

        Main obj = new Main();
        content = obj.readFileAsString(filePath);


        List<Character> braces = new ArrayList<Character>();
        List<Integer> braceIndices = new ArrayList<Integer>();

        int[][] braceIndicesDetails = new int[3][1000];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1000; j++) {
                braceIndicesDetails[i][j] = -1;
            }
        }

        int p = 0, q = 0, r = 0;
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (ch == '{' || ch == '}' || ch == '[' || ch == ']') {
                braces.add(ch);
                braces.add(' ');
                braceIndices.add(i);
            }

            if (ch == '{') {
                braceIndicesDetails[0][p] = i;
                p++;
            } else if (ch == '}') {
                q = p - 1;
                while (braceIndicesDetails[1][q] != -1) q--;
                braceIndicesDetails[1][q] = i;
            }

        }

        StringBuilder sb = new StringBuilder();

        for (Character ch : braces) {
            sb.append(ch);
        }

        int depth = 0, maxDepth = 0, k = 0;
        for (int j = 0; j < p; j++) {

            if (j == 0) {
                braceIndicesDetails[2][j] = depth;
            } else if (braceIndicesDetails[0][j] > braceIndicesDetails[0][j - 1] && braceIndicesDetails[1][j] < braceIndicesDetails[1][j - 1]) {
                depth++;
                braceIndicesDetails[2][j] = depth;
                if (depth > maxDepth) maxDepth = depth;
            } else if (braceIndicesDetails[0][j] > braceIndicesDetails[0][j - 1] && braceIndicesDetails[1][j] > braceIndicesDetails[1][j - 1]) {
                k = j;
                while (braceIndicesDetails[0][j] > braceIndicesDetails[0][k - 1] && braceIndicesDetails[1][j] > braceIndicesDetails[1][k - 1]) {
                    k--;
                }
                depth = braceIndicesDetails[2][k];
                braceIndicesDetails[2][j] = depth;
            }

        }
        System.out.println("\n\n\n<<<----------------------------------------------\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < p; j++) {
                System.out.print(braceIndicesDetails[i][j] + "\t\t");
            }
            System.out.println("");
        }
        System.out.println("\n\n---------------------------------------------->>>\n\n\n");

        String values[] = new String[p];
        String value;
        for (int j = 0; j < p; j++) {
            k = braceIndicesDetails[0][j];
            while (content.charAt(k) != ':') k++;
            int startIndex = k;
            while (content.charAt(k) != ',') k++;
            int endIndex = k;
            value = content.substring(startIndex + 1, endIndex).strip().replace("'", "").replace("\"", "");
            values[j] = value;
        }

        Node root = new Node(0, values[0], null);
        root.setCondition(values[0]);
        Tree tree = new Tree(root);


        int currentDepth = 0;
        Node currentParent = root;
        int noOfChildren = 0;
        ArrayList<Node> children;

        for (int i = 1; i < p; i++) {

            Node n = new Node(braceIndicesDetails[2][i], values[i], currentParent);
            int diff = braceIndicesDetails[2][i] - braceIndicesDetails[2][(i - 1)];
            if (diff == 0) {
                currentParent.setChild(n);
                n.setParent(currentParent);
                n.setChildIndex();
                n.setCondition(currentParent.getCondition() + " AND " + values[i]);
                if (i + 1 < p && (braceIndicesDetails[2][i + 1] - braceIndicesDetails[2][(i)]) > 0) {
                    currentParent = n;
                }
            } else if (diff == 1) {
                currentParent.setChild(n);
                n.setParent(currentParent);
                n.setChildIndex();
                n.setCondition(currentParent.getCondition() + " AND " + values[i]);
                if (i + 1 < p && (braceIndicesDetails[2][i + 1] - braceIndicesDetails[2][(i)]) == 0) {
                    //currentParent = currentParent;
                } else {
                    currentParent = n;
                }

            } else if (diff < 0) {
                while (currentParent.getDepth() + 1 > braceIndicesDetails[2][i])
                    currentParent = currentParent.getParent();

                currentParent.setChild(n);
                n.setParent(currentParent);
                n.setChildIndex();
                n.setCondition(currentParent.getCondition() + " AND " + values[i]);
                if (i + 1 < p && (braceIndicesDetails[2][i + 1] - braceIndicesDetails[2][(i)]) == 0) {
                    //currentParent = currentParent;
                } else if ((i + 1 < p && (braceIndicesDetails[2][i + 1] - braceIndicesDetails[2][(i)]) == 1)) {
                    currentParent = n;
                }
            }
        }

        String tmp = "( " + root.getValue() + " AND ";
        obj.condition = "";
        obj.printLeaves(root, tmp);


    }

    private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    private void printLeaves(Node node, String tmp) {
        ArrayList<Node> children = node.getChildren();
        if (children.size() == 0) {
            System.out.println(node.getCondition());
        } else {
            for (Node child : children) {
                printLeaves(child, tmp);
            }
        }
    }


}
