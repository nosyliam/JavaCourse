package com.liam;


import java.util.*;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import javax.swing.*;

public class InfixTranslator extends JFrame implements ActionListener {
    private JLabel Output = new JLabel("Test output");
    private JTextField input = new JTextField();

    private class Node {
        public Node left;
        public Node right;
        public String value;

        Node(String v) {
            value = v;
        }

        public Node setRight(Node n) {
            right = n;
            return this;
        }

        public Node setLeft(Node n) {
            left = n;
            return this;
        }
    }
    private static List<String> ops = Collections.unmodifiableList(Arrays.asList("+", "-", "*", "/"));

    InfixTranslator() {
        super("Infix Translator");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JButton prefixButton = new JButton("Prefix");
        prefixButton.setPreferredSize(new Dimension(100, 30));
        prefixButton.setActionCommand("calcPrefix");
        prefixButton.addActionListener(this);
        contentPane.add(prefixButton, constraints);

        constraints.gridx++;
        JButton postfixButton = new JButton("Postfix");
        postfixButton.setPreferredSize(new Dimension(100, 30));
        postfixButton.setActionCommand("calcPostfix");
        postfixButton.addActionListener(this);
        contentPane.add(postfixButton, constraints);

        constraints.gridy++;
        constraints.gridx--;
        constraints.gridwidth = 2;
        input.setPreferredSize(new Dimension(100, 30));
        contentPane.add(input, constraints);

        constraints.gridy++;
        Output.setPreferredSize(new Dimension(100, 100));
        Output.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(Output, constraints);

        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void recursePrefix(Node root, StringBuilder out) {
        out.append(root.value);
        if (root.left != null) {
            recursePrefix(root.left, out);
        }
        if (root.right != null) {
            recursePrefix(root.right, out);
        }
    }

    private void recursePostfix(Node root, StringBuilder out) {
        if (root.left != null) {
            recursePostfix(root.left, out);
        }
        if (root.right != null) {
            recursePostfix(root.right, out);
        }
        out.append(root.value);
    }

    private Node buildTree() {
        Deque<Node> stack = new LinkedList<>();

        for (char ctoken : input.getText().toCharArray()) {
            String token = String.valueOf(ctoken);
            Node node = new Node(token);
            if (token.equals("(")) {
                stack.push(node);
            }
            if (isInteger(token)) {
                if (ops.contains(stack.peek().value)) {
                    stack.push(stack.pop().setRight(node));
                } else {
                    stack.push(node);
                }
            }
            if (ops.contains(token)) {
                stack.push(node.setLeft(stack.pop()));
            }
            if (token.equals(")")) {
                Node current = stack.pop();
                Node top = stack.pop();
                while (!top.value.equals("(")) {
                    current = top.setRight(current);
                    top = stack.pop();
                }
                stack.push(current);
            }
        }

        return stack.getFirst();
    }

    public void actionPerformed(ActionEvent e) {
        StringBuilder out = new StringBuilder();
        switch (e.getActionCommand()) {
            case "calcPrefix":
                recursePrefix(buildTree(), out);
                Output.setText(out.toString());
                break;
            case "calcPostfix":
                recursePostfix(buildTree(), out);
                Output.setText(out.toString());
                break;

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InfixTranslator Program = new InfixTranslator();
            Program.setVisible(true);
        });
    }
}
