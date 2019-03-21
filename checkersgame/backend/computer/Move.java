/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersgame.backend.computer;

import checkersgame.backend.field.Position;
import checkersgame.backend.field.Step;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Magyar Bálint
 */
public class Move {
    private Position piecePos;
    private List<Step> steps;
    private List<Position> hits;
    private Step selectedStep;
    private Position hitPos;

    public Move(Position piecePos) {
        this.piecePos = piecePos;
        this.steps = new ArrayList<>();
        this.hits = new ArrayList<>();
    }
    
    public Move() {
        this.steps = new ArrayList<>();
        this.hits = new ArrayList<>();
    }
    
    public void setPiecePos(Position piecePos) {
        this.piecePos = piecePos;
    }
    
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    
    public void addPosToSteps(Position step) {
        this.steps.add(new Step(step));
    }
    
    public void addPosToSteps(Position step, Position hit) {
        Step newStep = new Step(step);
        newStep.setHitPosition(hit);
        this.steps.add(newStep);
    }
    
    public void setSelectedStepPos(Position selectedStepPos) {
        this.selectedStep = findStepByPosition(selectedStepPos);
    }
    
    private Step findStepByPosition(Position pos) {
        for(Step step : steps) {
            if(step.getStep().x == pos.x && step.getStep().y == pos.y) {
                return step;
            }
        }
        return null;
    }
    
    public Position getPiecePos() {
        return piecePos;
    }
    
    public List<Step> getSteps() {
        return steps;
    }
    
    public Position getStepPos(int index) {
        return steps.get(index).getStep();
    }
    
    public Step getSelectedStep() {
        return selectedStep;
    }
    
    public boolean hasSteps() {
        return steps.size() != 0;
    }
    
    public boolean hasHits() {
        for(Step step : steps) {
            if(step.isHitStep()) {
                return true;
            }
        }
        return false;
    }
    
    public void setHits(List<Position> hits) {
        this.hits = hits;
    }
    
    public void addPosToHits(Position hit) {
        this.hits.add(hit);
    }
    
    public List<Position> getHitPositions() {
        return hits;
    }
}
