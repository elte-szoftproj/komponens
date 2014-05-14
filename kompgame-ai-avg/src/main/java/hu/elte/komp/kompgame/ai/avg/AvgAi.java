package hu.elte.komp.kompgame.ai.avg;

import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.GameGraphInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless(name = "hu.elte.komp.ai.avg", mappedName = "hu.ekte.komp.ai.avg")
@LocalBean
public class AvgAi implements AiInterface {

    private static final int MAX_DEPTH = 3;
    
    @Override
    public Object getNextStep(GameGraphInterface gameGraph, 
            boolean youArePlayerOne) {        
        // root has no score
        Node root = new Node(gameGraph.getCurrentStep(), 0L);
        // root is the 0th level
        generateTree(gameGraph, root, youArePlayerOne, 0);
        int m = 2, n = 2;
        Long bestBranchScore = evaluateTree(root, m, n, 0);
        return selectNextStep(root, bestBranchScore);
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
            Node child = new Node(possibleStep, stepScore);
            generateTree(gameGraph, child, youArePlayerOne, depth + 1);
            node.addChild(child);
        }
    }
    
    public Long evaluateTree(Node tree, int m, int n, int depth) {
        Long value = 0L;
        
        if (tree.getChildren().isEmpty()) {
            return tree.getScore(); // this is a leaf
        }
        
        Collection<Node> children = tree.getChildren();
        List<Long> values = new ArrayList<>();
        
        for (Node child : children) {
            Long updatedChildScore = evaluateTree(child, m, n, depth + 1);
            values.add(updatedChildScore);
            //value += updatedChildScore;
        }
        
        // FIXME: hack!!
        int i = 0;
        Long score = 0L;
        values.sort(new Comparator<Long>() {

            @Override
            public int compare(Long o1, Long o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else if (o1 > o2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        if (depth % 2 == 0) {
            while (i < m) {
                score += values.get(i);
                values.remove(i);
                if (values.isEmpty()) {
                    break;
                }
                ++i;
            }
            value /= i;
        } else {
            while (i < n) {
                score += values.get(values.size() - i - 1);
                values.remove(i);
                if (values.isEmpty()) {
                    break;
                }
                ++i;
            }
            value /= i;
        }
        
        tree.setScore(value);
        return value;
    }
    
    private Long maxval(Collection<Long> iterable) {
        Long max = Long.MIN_VALUE;
        for (Long val : iterable) {
            max = Long.max(max, val);
        }
        return max;
    }
    
    public Object selectNextStep(Node root, Long bestBranchScore) {
        
        Long maxScore = Long.MIN_VALUE;
        for (Node child : root.getChildren()) {
            maxScore = Long.max(maxScore, child.getScore());
        }
        
        for (Node child : root.getChildren()) {
            if (child.getScore().equals(maxScore)) {
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
    
    private Long average(Long lhs, Long rhs) {
        return (lhs + rhs) / 2;
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
