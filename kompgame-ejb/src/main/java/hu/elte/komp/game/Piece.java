/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

interface Piece {
    
    public boolean isClickable();
    
    public String getCssClass();
    
    public String getStyleOverride();
    
    public String getContent();
}
