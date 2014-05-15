package hu.elte.komp.kompgame.ai.negamax;

import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.GameGraphInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless(name = "hu.elte.komp.ai.negamax", 
        mappedName = "hu.elte.komp.ai.negamax")
@LocalBean
public class NegamaxAi implements AiInterface {

    private static final int MAX_DEPTH = 3;
    
    @Override
    public Object getNextStep(GameGraphInterface gameGraph, 
            boolean  youArePlayerOne) {
        // root has no score
        Node root = new Node(gameGraph.getCurrentStep(), 0L);
        // root is the 0th level
        generateTree(gameGraph, root, youArePlayerOne, 0);
        Long bestBranchScore = evaluateTree(root);
        return selectNextStep(root, bestBranchScore);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void generateTree(GameGraphInterface gameGraph, Node node, 
            boolean youArePlayerOne, int depth) {
        
        if (depth == MAX_DEPTH) {
            return;
        } else if (depth > MAX_DEPTH) {
            throw new RuntimeException("depth error!");
        }
        
        Iterable possibleStepsIt = gameGraph.getPossibleSteps(node.getStep());
        
//        for (Object possibleStep : possibleSteps) {
        Iterator possibleSteps = possibleStepsIt.iterator();
        while (possibleSteps.hasNext()) {
            Object possibleStep = possibleSteps.next();
            Long stepScore = gameGraph.getStepScoreForPlayer(possibleStep, 
                            youArePlayerOne);
            Node child = new Node(possibleStep, 
                    ((depth % 2 == 1) ? stepScore : -stepScore));
            generateTree(gameGraph, child, youArePlayerOne, depth + 1);
            node.addChild(child);
        }
    }
    
    public Long evaluateTree(Node tree) {
        Long value = Long.MIN_VALUE;
        if (tree.getChildren().isEmpty()) {
//            System.out.println("This is empty!");
            value = tree.getScore(); // this is a leaf
        }
        Collection<Node> children = tree.getChildren();
        
        for (Node child : children) {
            Long updatedChildScore = -1 * evaluateTree(child);
//            child.setScore(updatedChildScore);
            value = Math.max(value, updatedChildScore);
//            System.out.println("new cild: " + updatedChildScore + " | " +value);
        }
        tree.setScore(value);
        
        return value;
    }
    
    public Object selectNextStep(Node root, Long bestBranchScore) {
        
        for (Node child : root.getChildren()) {
            if (child.getScore().equals(-bestBranchScore)) {
                return child.getStep();
            }
        }
        
        throw new RuntimeException("Evaluation error! bestBranchScore: " +
                bestBranchScore + " children scores: " + 
                stringifyChildrenScore(root.getChildren()));
    }
    
    private String stringifyChildrenScore(Iterable<Node> children) {
        StringBuilder sbuilder = new StringBuilder();
    
        for (Node child : children) {
            sbuilder.append(child.getScore()).append(", ");
        }
        
        return sbuilder.substring(0, sbuilder.length() - 1);
    }
    
    public static class Node {
        
        private final Object step;
        private Long score;
        private final Collection<Node> children;
        
        Node(Object step, Long score) {
            this.step = step;
            this.score = score;
            this.children = new ArrayList<>();
        }
        
        @Override
        public boolean equals(Object other) {
            
            if (other instanceof Node) {
                Node node = (Node) other;
                return step.equals(node.step) &&
                        score.equals(node.score) &&
                        children.equals(node.children);
            }
            
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + Objects.hashCode(this.step);
            hash = 89 * hash + Objects.hashCode(this.score);
            hash = 89 * hash + Objects.hashCode(this.children);
            return hash;
        }

        public void addChild(Node node) {
            children.add(node);
        }
        
        public Object getStep() {
            return this.step;
        }
        
        public Long getScore() {
            return this.score;
        }
        
        public void setScore(Long score) {
            this.score = score;
        }
        
        public Collection<Node> getChildren() {
            return this.children;
        }
    }

}
